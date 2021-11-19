package generators;

import gui.Display;
import maze.Edge;
import maze.Maze;
import maze.MazeState;

import java.util.function.Consumer;


public abstract class MazeGenerator {
    private Consumer<Maze> onGenerationComplete;
    private Display display;

    /*
     * Creates a new MazeGenerator
     * takes display to update during maze generation as a parameter
     * Another parameter onGenerationComplete calls with the maze upon the maze finishing
     * generation
     */
    public MazeGenerator(Display d, Consumer<Maze> onGenerationComplete) {
        this.display = d;
        this.onGenerationComplete = onGenerationComplete;
    }

    /*
     * Creates a new MazeGenerator
     */
    public MazeGenerator() {
        this.display = null;
        this.onGenerationComplete = null;
    }

    /*
     * Sets the display to be updated during generation.
     */
    public void setDisplay(Display d) {
        this.display = d;
    }

    /*
     * Sets the function to be called once generation is finished.
     */
    public void setOnGenerationComplete(Consumer<Maze> onGenerationComplete) {
        this.onGenerationComplete = onGenerationComplete;
    }

    /*
     * Creates a maze according to this generator's algorithm.
     */
    public abstract Maze makeMaze(int size);

    /*
     * Generates the maze, and calls onGenerationComplete once finished.
     */
    public void generate(int size) {
        this.onGenerationComplete.accept(makeMaze(size));
    }

    /*
     * Removes the wall that is represented by e (by setting its maze state to
     * EMPTY), and notify the Display of the cells changed.
     * takes edge as parameter to be teared down
     */
    public void tearDown(Edge e) {
        e.setState(MazeState.EMPTY);
        if (display != null) {
            display.cellsChanged(e.getStart(), e.getEnd());
        }
    }
}
