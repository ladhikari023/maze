package generators;

import gui.Display;
import maze.Maze;
import maze.Vertex;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class DepthFirstGenerator extends MazeGenerator {
    Stack<Vertex> stack;
    // make it easy to check if a cell has been visited
    Set<Vertex> visited;

    /*
     * Creates a new MazeGenerator
     * update display during maze generation
     * calls with the maze upon the maze finishing
     */

    public DepthFirstGenerator(Display d, Consumer<Maze> onGenerationComplete) {
        super(d, onGenerationComplete);
        stack = new Stack<>();
        visited = new HashSet<>();
    }

    public DepthFirstGenerator() {
        this(null, null);
    }

    /*
     * Generates a maze based on the constraints of the algorithm
     * takes size of the maze to generate as parameter
     * returns the generated maze
     */
    @Override
    public Maze makeMaze(int size) {
        Maze m = new Maze(size);
        stack.push(m.getEntrance());
        this.visited.add(m.getEntrance());
        Vertex current = null;

        Predicate<Vertex> unvisitedFilter = v -> !visited.contains(v);

        while (!stack.isEmpty()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            current = stack.pop();

            // filter into unvisited cells
            List<Vertex> unvisited = current.getAdjacents()
                                            .stream()
                                            .filter(unvisitedFilter)
                                            .collect(Collectors.toList());

            if (!unvisited.isEmpty()) {
                stack.push(current);
                Random random = new Random();
                Vertex next = unvisited.get(random.nextInt(unvisited.size()));
                // remove wall between the cells
                this.tearDown(current.connectingEdge(next));
                current = next;
                this.visited.add(current);
                this.stack.push(current);
            }
        }

        return m;
    }
}
