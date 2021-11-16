package solvers;

import gui.Display;
import maze.Vertex;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RandomMouseSolver extends MazeSolver {
    ExecutorService exec;
    Set<Vertex> visited;
    boolean multithreaded;

    public RandomMouseSolver(Display d, ExecutorService exec,
                             Set<Vertex> visited) {
        super(d);
        this.exec = exec;
        this.visited = visited;
        this.multithreaded = true;
    }

    public RandomMouseSolver(boolean multithreaded) {
        this(null, Executors.newFixedThreadPool(2000),
                new HashSet<>());
        this.multithreaded = multithreaded;
    }

    @Override
    public void solveFrom(Vertex start, Vertex exit) {
        Vertex current = start;
        boolean deadEnd = false;

        while (current != exit) {
            visit(current);
            visited.add(current);
            List<Vertex> adjacents = null;
            // if we are doing the multithreaded algorithm, adjacents must be
            // unvisited
            if (multithreaded) {
                adjacents = current.getUnvisitedAdjacents(visited);
            } else {
                adjacents = current.getReachableAdjacents();
            }
            Collections.shuffle(adjacents);
            // make the first element the next current cell for this solver;
            // make new solvers for the remaining elements (if multithreaded).
            if (adjacents.size() > 0) {
                current = adjacents.get(0);
                if (multithreaded) {
                    for (int i = 1; i < adjacents.size(); i++) {
                        newMouse(adjacents.get(i), exit);
                    }
                }
            } else {
                // dead end
                try {
                    deadEnd = true;
                    Thread.currentThread().join();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

        if (!deadEnd) {
            visit(current);
        }

        // exit has been found, shut down all threads!
        exec.shutdownNow();
    }

    private void newMouse(Vertex v, Vertex exit) {
        Runnable r = () -> {
            RandomMouseSolver solver = new RandomMouseSolver(
                    getDisplay(), exec, this.visited
            );
            solver.solveFrom(v, exit);
        };
        if (!exec.isShutdown()) {
            exec.execute(r);
        }
    }
}
