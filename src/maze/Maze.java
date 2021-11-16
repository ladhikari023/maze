package maze;

import java.util.ArrayList;
import java.util.Random;


public class Maze {
    private ArrayList<Vertex> cells;
    private Vertex exit;
    private Vertex entrance;
    private Vertex topRight;
    private int n;

    public Maze(int n) {
        cells = new ArrayList<>();
        this.n = n;

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                Vertex vertex = new Vertex(j,i);
                cells.add(vertex);

                if(i == 0) {
                    vertex.addEdge(MazeState.BOUNDARY, Direction.UP);
                } else {
                    Vertex upVertex = cells.get(cells.size() - n - 1);
                    connect(vertex, upVertex, Direction.UP);
                }

                if(i == n-1) {
                    vertex.addEdge(MazeState.BOUNDARY, Direction.DOWN);
                }

                if(j == 0) {
                    vertex.addEdge(MazeState.BOUNDARY, Direction.LEFT);
                } else {
                    Vertex leftVertex = cells.get(cells.size() - 2);
                    connect(vertex, leftVertex, Direction.LEFT);
                }

                if(j == n-1) {
                    vertex.addEdge(MazeState.BOUNDARY, Direction.RIGHT);
                }

                // make upper left corner the exit of the maze
                if (i == 0 && j == 0) {
                    this.topRight = vertex;
                }
            }
        }

        createEntranceAndExit();
    }

    private void makeEntrance(int x, int y, Direction dir) {
        this.entrance = getCell(x, y);
        entrance.addEdge(MazeState.ENTRANCE, dir);
    }

    private void makeExit(int x, int y, Direction dir) {
        this.exit = getCell(x, y);
        exit.addEdge(MazeState.EXIT, dir);
    }

    private void createEntranceAndExit() {
        Random random = new Random();
        int i = random.nextInt(this.n);
        int j = random.nextInt(this.n);
        boolean side = random.nextBoolean();
        // determines if it is entrance on top, exit on bottom or vise versa
        // (or the same for left, right)
        boolean flip = random.nextBoolean();

        // up down
        if (side) {
            // entrance on bottom, exit on top
            if (flip) {
                makeExit(i, 0, Direction.UP);
                makeEntrance(j, n-1, Direction.DOWN);
            } else {
                makeEntrance(i, 0, Direction.UP);
                makeExit(j, n-1, Direction.DOWN);
            }
        } else { // left right
            // entrance on right, exit on left
            if (flip) {
                makeExit(0, i, Direction.LEFT);
                makeEntrance(n-1, j, Direction.RIGHT);
            } else {
                makeEntrance(0, i, Direction.LEFT);
                makeExit(n-1, j, Direction.RIGHT);
            }

        }
    }

    // connects two vertices with a wall
    private void connect(Vertex v1, Vertex v2, Direction d) {
        Edge edge = new Edge(v1, v2, MazeState.WALL);
        v1.addEdge(edge, d);
        v2.addEdge(edge, d.reverse());
    }

    public Vertex getEntrance() {
        return this.entrance;
    }

    public Vertex getExit() {
        return exit;
    }

    public ArrayList<Vertex> getCells() {
        return cells;
    }

    public Vertex getCell(int x, int y) {
        Vertex current = this.topRight;
        while (current.getX() != x) {
            current = current.getEdge(Direction.RIGHT).getStart();
        }
        while (current.getY() != y) {
            current = current.getEdge(Direction.DOWN).getStart();
        }
        return current;
    }
}
