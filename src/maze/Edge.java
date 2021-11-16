package maze;


public class Edge {
    private Vertex start, end;
    private MazeState state;

    public Edge(Vertex start, Vertex end, MazeState state) {
        this.start = start;
        this.end = end;
        this.state = state;
    }

    public Vertex getStart() {
        return start;
    }

    public Vertex getEnd() {
        return end;
    }

    public MazeState getState() {
        return state;
    }

    public boolean isWall() {
        return !(this.state == MazeState.EMPTY);
    }

    public void setState(MazeState state) {
        this.state = state;
    }
}
