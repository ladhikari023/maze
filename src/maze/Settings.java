package maze;

import generators.*;
import solvers.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
    handles input files
 */

public class Settings {
    private int windowSize;
    private int cellSize;
    private int fps;
    private String generationAlgo;
    private String solverAlgo;

    public Settings(File file) throws FileNotFoundException {
        Scanner s = new Scanner(file);
        windowSize = s.nextInt();
        cellSize = s.nextInt();
        generationAlgo = s.next();
        solverAlgo = s.next();
        this.fps = 60;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public int getCellSize() {
        return cellSize;
    }

    public int getFPS() {
        return fps;
    }

    /*
        returns which generator to use based on the file input
     */
    public MazeGenerator getGenerationAlgo() {
        int size = windowSize/cellSize;
        switch (this.generationAlgo) {
            case "dfs":
                return new DepthFirstGenerator();
            case "kruskal":
                return new Kruskal();
            case "prims":
                return new PrimsGenerator();
            case "aldous":
                return new Aldous();
        }

        throw new IllegalStateException("Invalid generator setting.");
    }
    /*
        returns which solver to use based on the file input
     */
    public MazeSolver getSolverAlgo() {
        switch (this.solverAlgo) {
            case "mouse_thread":
                return new RandomMouseSolver(true);
            case "mouse":
                return new RandomMouseSolver(false);
            case "wall":
                return new WallFollower(false);
            case "wall_thread":
                return new WallFollower(true);
            case "pledge":
                return new PledgeSolver();
        }

        throw new IllegalStateException("Invalid solver setting.");
    }
}
