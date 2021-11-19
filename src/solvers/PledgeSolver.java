package solvers;

import maze.Direction;
import maze.Edge;
import maze.Vertex;
/*
 * Implementation of the pledge maze solver algorithm
 */
public class PledgeSolver extends MazeSolver {
    @Override
    public void solveFrom(Vertex start, Vertex exit) {
        initDirection(start);
        Vertex current = start;

        while (current != exit) {
            Edge edge = current.getEdge(getCurrentDirection());
            if (!edge.isWall()) {
                current = current.getVertexFromEdge(edge);
                visit(current);
            } else {
                current = avoidObstacle(current, exit);
            }
        }
    }

    private Vertex avoidObstacle(Vertex current, Vertex exit) {
        turn(Direction.LEFT);
        int angle = -90;

        while (angle != 0 && current != exit) {
            int newAngle = rightHand(current);
            angle += newAngle;
            visit(current);
            Edge edge = current.getEdge(getCurrentDirection());
            current = current.getVertexFromEdge(edge);
        }

        visit(current);
        return current;
    }
}
