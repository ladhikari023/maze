package maze;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Vertex {
    private int x, y;
    private HashMap<Direction, Edge> edges;

    public Vertex(int x, int y) {
        this.x = x;
        this.y = y;
        edges = new HashMap<>();
    }

    public void addEdge(Edge edge, Direction direction) {
        edges.put(direction, edge);
    }

    public void addEdge(MazeState state, Direction direction) {
        Edge edge = new Edge(this, null, state);
        addEdge(edge, direction);
    }

    public Collection<Edge> getEdges() {
        return edges.values();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Edge getEdge(Direction d) {
        return edges.get(d);
    }

    public Collection<Vertex> getAdjacents() {
        HashSet<Vertex> vertices = new HashSet<>();

        for (Edge e : getEdges()) {
            if (e.getStart() == this && e.getEnd() != null) {
                vertices.add(e.getEnd());
            } else if (e.getStart() != null) {
                vertices.add(e.getStart());
            }
        }

        return vertices;
    }

    public List<Vertex> getReachableAdjacents() {
        Collection<Edge> edges = this.getEdges()
                                     .stream()
                                     .filter(e -> !e.isWall())
                                     .collect(Collectors.toList());
        List<Vertex> vertices = new ArrayList<>();

        for (Edge e : edges) {
            if (e.getStart() == this && e.getEnd() != null) {
                vertices.add(e.getEnd());
            } else if (e.getStart() != null) {
                vertices.add(e.getStart());
            }
        }

        return vertices;
    }

    public List<Vertex> getUnvisitedAdjacents(Set<Vertex> visited) {
        Predicate<Vertex> unvisitedFilter = v -> !visited.contains(v);
        List<Vertex> unvisited = getReachableAdjacents()
                                 .stream()
                                 .filter(unvisitedFilter)
                                 .collect(Collectors.toList());
        return unvisited;
    }

    public Vertex getVertexFromEdge(Edge e) {
        if(e.getStart() == this) {
            return e.getEnd();
        } else {
            return e.getStart();
        }
    }

    public Edge connectingEdge(Vertex v) {
        for (Edge e : getEdges()) {
            if (e.getStart() == v || e.getEnd() == v) {
                return e;
            }
        }
        return null;
    }

    public boolean hasEdge(MazeState state) {
        for (Edge e : getEdges()) {
            if (e.getState() == state) return true;
        }
        return false;
    }
}
