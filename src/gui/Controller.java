package gui;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import maze.Direction;
import maze.Settings;
import maze.Vertex;
import utility.ColorTransition;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
/*
 * This is the main controller for the GUI.
 */
public class Controller extends Display {
    @FXML
    Canvas canvas;

    // cells are placed into this blocking queue by another thread, and drawn
    // using the animation timer. the first element of each pair is the cell to
    // be drawn, followed by the color to draw it in.
    BlockingQueue<Pair<Vertex, Color>> cellDrawingQueue;

    Settings settings;
    int fps;
    long lastDraw;

    ColorTransition transition;

    public Controller() {
        fps = 60;
        lastDraw = 0;
        cellDrawingQueue = new ArrayBlockingQueue<>(2);
        transition = new ColorTransition();
    }
    /*
     * Called when the controller starts
     */
    public void initialize() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if ((now - lastDraw) / 1000000 < 1000/fps) return;
                while (!cellDrawingQueue.isEmpty()) {
                    try {
                        Pair<Vertex, Color> cell = cellDrawingQueue.take();
                        drawCell(cell.getKey(), cell.getValue());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lastDraw = now;
            }
        };

        timer.start();
    }

    @Override
    public synchronized void cellsChanged(Color color, Vertex... cells) {
        for (Vertex c : cells) {
            this.queueForDrawing(c, color);
        }
    }

    @Override
    public synchronized void updateSolver(Vertex visited, Vertex lastVisited) {
        queueForDrawing(visited, Color.GREEN);
        transition.transition();
        queueForDrawing(lastVisited, transition.getColor());
    }

    private void queueForDrawing(Vertex v, Color c) {
        Pair<Vertex, Color> p = new Pair<>(v, c);
        try {
            this.cellDrawingQueue.put(p);
        } catch (InterruptedException e) {
            // in this case, the thread attempting to draw the cell has been
            // ended, so we can do nothing.
            return;
        }
    }

    private void drawBackground() {
        // black background
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    // draws the given cell. the center of the cell is 8x8, and each of the 4
    // borders of the cell are 8x2, making each cell 10x10
    private void drawCell(Vertex v, Color c) {
        int cellSize = settings.getCellSize();

        GraphicsContext gc = this.canvas.getGraphicsContext2D();
        // upper left corner of cell
        int x = cellSize*v.getX(), y = cellSize*v.getY();
        gc.setFill(c);
        // fill the center square (the empty part inside of the cell)
        gc.fillRect(x + 2, y + 2, cellSize-2, cellSize-2);

        // draw the passageways accordingly (note that we are drawing on a black
        // background)
        if (!v.getEdge(Direction.DOWN).isWall()) {
            gc.fillRect(x+2, y+cellSize, cellSize-2, 2);
        }
        if (!v.getEdge(Direction.RIGHT).isWall()) {
            gc.fillRect(x+cellSize, y+2, 2, cellSize-2);
        }
        if (!v.getEdge(Direction.UP).isWall()) {
            gc.fillRect(x+2, y, cellSize-2, 2);
        }
        if (!v.getEdge(Direction.LEFT).isWall()) {
            gc.fillRect(x, y+2, 2, cellSize-2);
        }
    }
    /*
     * Uses the settings to know the cell size and window size.
     * takes the parameter s as settings
     */
    public void setSettings(Settings s) {
        this.settings = s;
        this.canvas.setWidth(settings.getWindowSize());
        this.canvas.setHeight(settings.getWindowSize());
        this.fps = settings.getFPS();
        drawBackground();
    }
}
