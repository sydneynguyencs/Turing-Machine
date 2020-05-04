package turing.machine;

public class Transition {
    private char read;
    private char write;
    private int state;
    private int nextState;
    private Direction direction;

    public Transition(char read, char write, int state, int nextState, Direction direction) {
        this.read = read;
        this.write = write;
        this.state = state;
        this.nextState = nextState;
        this.direction = direction;
    }

    public char getRead() {
        return read;
    }

    public char getWrite() {
        return write;
    }

    public int getState() { return state; }

    public int getNextState() { return nextState; }

    public Direction getDirection() {
        return direction;
    }


    @Override
    public String toString() {
        return " => " + write + " | " + direction;
    }
}
