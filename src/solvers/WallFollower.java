package solvers;

import gui.Display;
import maze.Edge;
import maze.Vertex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * An implementation of the Wall Follower solving algorithm,
 * can be run as multi-thread and normal
 */
public class WallFollower extends MazeSolver{
    ExecutorService exec;
    boolean multithreaded;
    static boolean firstExec = true;

    /*
     * Constructor to create a new implementation of the Wall Follower algorithm
     * takes display to be displayed as a parameter
     * takes executor used when creating an additional thread as second parameter
     */
    public WallFollower(Display d, ExecutorService exec) {
        super(d);
        this.exec = exec;
        this.multithreaded = true;
    }

    /*
     * Constructor for multi-threaded version of the algorithm
     * Contains a call to the above constructor
     * takes boolean value as parameter that gives boolean for multithreading or not
     */
    public WallFollower(boolean multithreaded) {
        this(null, Executors.newFixedThreadPool(1000));
        this.multithreaded = multithreaded;
    }

    /**
     * Solves from a given vertex to the exit of the maze.
     * Or, in the case of when multithreaded, to the entrance of the maze.
     * takes starting vertex as first parameter
     * takes exit vertex as second parameter
     */
    @Override
    public void solveFrom(Vertex start, Vertex exit) {
        initDirection(start);
        if(multithreaded && firstExec) {
            newThread(exit, start);
            firstExec = false;
        }
        Vertex current = start;
        visit(current);

        while(current != exit) {
            // turn according to right hand rule
            this.rightHand(current);

            Edge connectionEdge = current.getEdge(getCurrentDirection());
            Vertex next = current.getVertexFromEdge(connectionEdge);
            visit(next);
            current = next;
        }

        //exec.shutdownNow();
    }

    /*
     * Creates a new instance of the algorithm on a separate thread
     * takes Vertex to begin upon as first parameter
     * takes vertex to exit as second parameter
     */
    public void newThread(Vertex v, Vertex exit) {
        Runnable r = () -> {
            WallFollower follower = new WallFollower(getDisplay(), exec);
            follower.solveFrom(v, exit);
        };
        if(!exec.isShutdown()) {
            exec.execute(r);
        }
    }
}
