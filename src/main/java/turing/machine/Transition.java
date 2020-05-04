package turing.machine;

public class Transition {
    private String value;
    private String nextValue;
    private Direction direction;
    private State state;
    private State nextState;

    public Transition(String value, String nextValue, Direction direction, State state, State nextState) {
        this.value = nextValue;
        this.nextValue = nextValue;
        this.state = state;
        this.nextState = nextState;
        this.direction = direction;
    }

    public String getValue() {
        return value;
    }

    public String getNextValue() {
        return nextValue;
    }

    public Direction getDirection() {
        return direction;
    }

    public State getNextState() {
        return nextState;
    }

    @Override
    public String toString() {
        return " => " + nextState + " | " + direction;
    }
}
