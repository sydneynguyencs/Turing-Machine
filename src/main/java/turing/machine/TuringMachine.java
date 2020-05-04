package turing.machine;

import java.util.*;

public class TuringMachine {

    private String input;
    private State startState;
    private Set<State> states;
    private Set<Transition> transitions;
    private List<Character> tape;
    private char blank;
    private ListIterator<String> head;

    public TuringMachine(String input) {
        this.input = input;
        startState = new State("q0");
        states = new HashSet<>();
        states.add(startState);
        transitions = new HashSet<>();
        tape = new ArrayList<>();
        blank = '_';
    }

    public void initializeTape() {
        for(int i = 0; i < input.length(); i++) {
            tape.add(input.charAt(i));
        }
    }

    @Override
    public String toString() {
        try {
            int headPos = head.previousIndex();
            StringBuilder s = new StringBuilder("[ ");

            for (int i = Math.max(headPos - 15, 0); i <= headPos; i++) {
                s.append(tape.get(i)).append(" ");
            }

            s.append("[H] ");

            for (int i = headPos + 1; i < Math.min(headPos + 15, tape.size()); i++) {
                s.append(tape.get(i)).append(" ");
            }

            return s.append(']').toString();
        } catch (Exception e) {
            return "";
        }
    }

    public List<String> run() {
        initializeTape();
        if(tape.size() == 0) {
            tape.add(blank);
        }

        return null;
    }


    //add states and transitions
    public void multiplication() {


    }

}
