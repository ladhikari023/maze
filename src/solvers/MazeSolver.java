package solvers;

import gui.Display;
import javafx.scene.paint.Color;
import maze.Direction;
import maze.Maze;
import maze.MazeState;
import maze.Vertex;

public abstract class MazeSolver {
    private Display display;
    private Vertex lastVisited;
    private Direction dir;

    public MazeSolver(Display d) {
        this.display = d;
    }

    public MazeSolver() {
        this.display = null;
    }

    public void visit(Vertex v) {
        if (this.display != null) {
            if (lastVisited != null) {
                this.display.updateSolver(v, lastVisited);
            }
            this.lastVisited = v;
        }
    }

    public void solve(Maze m) {
        // draw exit.
        visit(m.getEntrance());
        display.cellsChanged(Color.RED, m.getExit());
        this.solveFrom(m.getEntrance(), m.getExit());
    }

    public abstract void solveFrom(Vertex start, Vertex exit);

    public void setDisplay(Display display) {
        this.display = display;
    }

    protected Display getDisplay() {
        return this.display;
    }

    public void initDirection(Vertex startVertex) {
        Direction entranceDirection = null;

        Vertex current = startVertex;
        if(current.getEdge(Direction.LEFT).getState() == MazeState.ENTRANCE ||
                current.getEdge(Direction.LEFT).getState() == MazeState.EXIT) {
            entranceDirection = Direction.LEFT;
        } else if (current.getEdge(Direction.UP).getState() == MazeState.ENTRANCE ||
                current.getEdge(Direction.UP).getState() == MazeState.EXIT) {
            entranceDirection = Direction.UP;
        } else if (current.getEdge(Direction.RIGHT).getState() == MazeState.ENTRANCE ||
                current.getEdge(Direction.RIGHT).getState() == MazeState.EXIT) {
            entranceDirection = Direction.RIGHT;
        } else if (current.getEdge(Direction.DOWN).getState() == MazeState.ENTRANCE ||
                current.getEdge(Direction.DOWN).getState() == MazeState.EXIT) {
            entranceDirection = Direction.DOWN;
        }
        this.dir = entranceDirection.reverse();
    }

    public Direction getCurrentDirection() {
        return this.dir;
    }

    public void turn(Direction d) {
       switch (d) {
           case UP:
               break;
           case RIGHT:
               this.dir = this.dir.turnRight();
               break;
           case LEFT:
               this.dir = this.dir.turnLeft();
               break;
           case DOWN:
               this.dir = this.dir.reverse();
               break;
       }
    }

    public int rightHand(Vertex current) {
        Direction dir = getCurrentDirection();
        if(current.getEdge(dir.turnRight()).getState() == MazeState.EMPTY) {
            this.turn(Direction.RIGHT);
            return 90;
        } else if(current.getEdge(dir).getState() == MazeState.EMPTY) {
            this.turn(Direction.UP);
            return 0;
        } else if (current.getEdge(dir.turnLeft()).getState() == MazeState.EMPTY) {
            this.turn(Direction.LEFT);
            return -90;
        } else {
            this.turn(Direction.DOWN);
            return -180;
        }
    }
}
