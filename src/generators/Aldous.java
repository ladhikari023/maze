package generators;

import gui.Display;
import maze.Maze;
import maze.Vertex;

import java.util.*;
import java.util.function.Consumer;


public class Aldous extends MazeGenerator {
    Set<Vertex> visited;

    /*
     * Creates a new instance of the Aldous-Broder implementation
     * takes the parameter d as the display to be updated
     * takes the parameter onGenerationComplete, which is used to call the solver
     */

    public Aldous(Display d, Consumer<Maze> onGenerationComplete) {
        super(d, onGenerationComplete);
        visited = new HashSet<>();
    }

    public Aldous() {
        this(null, null);
    }
    /*
     * Creates a new maze, using the constraints of the algorithm
     * the parameter size is the size of the maze to be generated
     * the function returns the generated maze
     */
    @Override
    public Maze makeMaze(int size) {
        Maze m = new Maze(size);

        Vertex currentVertex = m.getEntrance();
        visited.add(currentVertex);
        while(visited.size() != m.getCells().size()) {
            Random random = new Random();
            ArrayList<Vertex> neighborList = new ArrayList<>(currentVertex.getAdjacents());
            Vertex nextVertex = neighborList.get(random.nextInt(neighborList.size()));
            if(!visited.contains(nextVertex)) {
                visited.add(nextVertex);
                tearDown(currentVertex.connectingEdge(nextVertex));
            }
            currentVertex = nextVertex;
        }

        return m;
    }
}