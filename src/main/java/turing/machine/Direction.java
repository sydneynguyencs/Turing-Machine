package turing.machine;

public enum Direction {

    RIGHT(1), LEFT(-1), STAY(0);

    private int direction;

    Direction(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }
}
