package gui;

import javafx.scene.paint.Color;
import maze.Vertex;
/*
 * Interface for drawing to the GUI during maze generation and solving.
 *
 * Methods overridden from this interface must be
 * thread-safe, as maze generation and solving can occur on
 * different threads.
 *
 */
public abstract class Display {
    /*
     * This is called during maze generation when one or more cells have been changed
     * takes the parameter cells as the cells that have been changed, whose newly updated form
     * will be drawn on the GUI, using the coordinates stored in the Vertex object
     */
    public void cellsChanged(Vertex... cells) {
        cellsChanged(Color.WHITE, cells);
    }
    /*
     * This is called during maze generation when one or more cells have been changed
     * takes the parameter color to know the  color to paint the cells
     * takes the parameter cells as the cells that have been changed, whose newly updated form
     * will be drawn on the GUI, using the coordinates stored in
     * the Vertex object
     */
    public abstract void cellsChanged(Color color, Vertex... cells);

    public abstract void updateSolver(Vertex visited, Vertex lastVisited);
}
