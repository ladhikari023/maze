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

    /*
     * Adds an edge from a vertex in a specified direction
     */
    public void addEdge(Edge edge, Direction direction) {
        edges.put(direction, edge);
    }

    /*
     * Creates an edge from a vertex in a specified direction and
     * with a specific state
     */
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

    /*
     * Returns every vertex attached to this one via edges.
     */
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

    /*
     * Returns every vertex attached to this one via an edge that is not a wall
     * or boundary
     */
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

    /*
     * Returns every vertex that has not been visited, according to a set of
     * visited vertices.
     */
    public List<Vertex> getUnvisitedAdjacents(Set<Vertex> visited) {
        Predicate<Vertex> unvisitedFilter = v -> !visited.contains(v);
        List<Vertex> unvisited = getReachableAdjacents()
                                 .stream()
                                 .filter(unvisitedFilter)
                                 .collect(Collectors.toList());
        return unvisited;
    }

    /*
     * Returns the vertex found by travelling along an edge, checking to
     * ensure that the found vertex is not itself.
     */
    public Vertex getVertexFromEdge(Edge e) {
        if(e.getStart() == this) {
            return e.getEnd();
        } else {
            return e.getStart();
        }
    }

    /*
     * Returns the edge connecting this vertex with the given vertex (if it
     * exists). If there is no such edge, return null
     */
    public Edge connectingEdge(Vertex v) {
        for (Edge e : getEdges()) {
            if (e.getStart() == v || e.getEnd() == v) {
                return e;
            }
        }
        return null;
    }

    /*
     * Returns whether one of the edges of this vertex has an edge with the
     * given MazeState. This can be used to check if a vertex counts as an
     * entrance/exit.
     */
    public boolean hasEdge(MazeState state) {
        for (Edge e : getEdges()) {
            if (e.getState() == state) return true;
        }
        return false;
    }
}
