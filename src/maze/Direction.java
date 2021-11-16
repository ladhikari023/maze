package maze;


public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

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

    public Direction turnRight() {
        switch (this) {
            case UP: return RIGHT;
            case DOWN: return LEFT;
            case LEFT: return UP;
            case RIGHT: return DOWN;
        }
        return null;
    }

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
