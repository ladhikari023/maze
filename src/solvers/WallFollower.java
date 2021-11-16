package solvers;

import gui.Display;
import maze.Edge;
import maze.Vertex;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WallFollower extends MazeSolver{
    ExecutorService exec;
    boolean multithreaded;
    static boolean firstExec = true;

    public WallFollower(Display d, ExecutorService exec) {
        super(d);
        this.exec = exec;
        this.multithreaded = true;
    }

    public WallFollower(boolean multithreaded) {
        this(null, Executors.newFixedThreadPool(1000));
        this.multithreaded = multithreaded;
    }

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
