package turing.machine;

import java.util.HashMap;

public class State {
    private String state;
    private HashMap<String, Transition> transitions;

    public State(String state) {
        this.state = state;
        transitions = new HashMap<>();
    }

    public String getState() {
        return state;
    }

    public HashMap<String, Transition> getTransitions() {
        return transitions;
    }

    private void setTransitions(String value, String nextValue, Direction direction, State state, State nextState) {
        transitions.put(value, new Transition(value, nextValue, direction, this, nextState));
    }

    @Override
    public String toString() {
        return "(" + state + "," + transitions.get(state).getValue() +")";
    }


}
