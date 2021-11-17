package generators;

import maze.Edge;
import maze.Maze;
import maze.MazeState;
import maze.Vertex;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class PrimsGenerator extends MazeGenerator{

    @Override
    public Maze makeMaze(int size) {
        List<Vertex> adjacents = new ArrayList<>();
        Set<Vertex> processed = new HashSet<>();

        Maze m = new Maze(size);
        Random rand = new Random();

        // select a random vertex
        int r = rand.nextInt(size);
        int c = rand.nextInt(size);
        Vertex start = m.getCell(c, r);
        processed.add(start);
        adjacents.addAll(adjacentWallCells(start));

        while (!adjacents.isEmpty()) {
            int randIndex = rand.nextInt(adjacents.size());
            Vertex newCell = adjacents.get(randIndex);
            adjacents.remove(randIndex);

            // don't process the same cell twice
            if (processed.contains(newCell)) continue;

            // cells that we can tear down the wall between for the newCell
            List<Vertex> candidates = adjacentEmptyCells(newCell, processed);
            Vertex connectedCell = candidates.get(
                    rand.nextInt(candidates.size())
            );
            tearDown(newCell.connectingEdge(connectedCell));

            adjacents.addAll(adjacentWallCells(newCell));
            processed.add(newCell);
        }

        return m;
    }

    // returns all of the adjacent walls to the cell given
    private Set<Vertex> adjacentWallCells(Vertex v) {
        Set<Vertex> vertices = new HashSet<>();
        for (Edge e : v.getEdges()) {
            if (e.getState() == MazeState.WALL) {
                vertices.add(v.getVertexFromEdge(e));
            }
        }
        return vertices;
    }

    private List<Vertex> adjacentEmptyCells(Vertex v, Set<Vertex> processed) {
        List<Vertex> vertices = v.getAdjacents().stream()
                .filter(processed::contains)
                .collect(Collectors.toList());
        return vertices;
    }
}

