package generators;

import gui.Display;
import maze.Edge;
import maze.Maze;
import maze.MazeState;

import java.util.function.Consumer;


public abstract class MazeGenerator {
    private Consumer<Maze> onGenerationComplete;
    private Display display;


    public MazeGenerator(Display d, Consumer<Maze> onGenerationComplete) {
        this.display = d;
        this.onGenerationComplete = onGenerationComplete;
    }

    public MazeGenerator() {
        this.display = null;
        this.onGenerationComplete = null;
    }

    public void setDisplay(Display d) {
        this.display = d;
    }

    public void setOnGenerationComplete(Consumer<Maze> onGenerationComplete) {
        this.onGenerationComplete = onGenerationComplete;
    }

    public abstract Maze makeMaze(int size);

    public void generate(int size) {
        this.onGenerationComplete.accept(makeMaze(size));
    }

    public void tearDown(Edge e) {
        e.setState(MazeState.EMPTY);
        if (display != null) {
            display.cellsChanged(e.getStart(), e.getEnd());
        }
    }
}
