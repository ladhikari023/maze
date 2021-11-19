package maze;


public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    /*
     * Reverses the given direction input
     * returns the reversed direction
     */
    public Direction reverse() {
        switch (this) {
            case UP: return DOWN;
            case DOWN: return UP;
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
        }
        // shouldn't happen
        return null;
    }

    /*
     * Turns right in the maze from the perspective of the solver
     * returns the right-turned direction
     */
    public Direction turnRight() {
        switch (this) {
            case UP: return RIGHT;
            case DOWN: return LEFT;
            case LEFT: return UP;
            case RIGHT: return DOWN;
        }
        return null;
    }

    /*
     * Turns left in the maze from the perspective of the solver
     * returns the left-turned direction
     */
    public Direction turnLeft() {
        switch (this) {
            case UP : return LEFT;
            case DOWN: return RIGHT;
            case LEFT: return DOWN;
            case RIGHT: return UP;
        }
        return null;
    }
}
