package gui;

import javafx.scene.paint.Color;
import maze.Vertex;

public abstract class Display {

    public void cellsChanged(Vertex... cells) {
        cellsChanged(Color.WHITE, cells);
    }

    public abstract void cellsChanged(Color color, Vertex... cells);

    public abstract void updateSolver(Vertex visited, Vertex lastVisited);
}
