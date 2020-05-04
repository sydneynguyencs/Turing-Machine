package turing.machine;

import java.util.*;

public class TuringMachine {

    private int state;
    private String input;
    private char[] tape;
    private char blank;
    private char symbolDivisor;
    private ListIterator<String> head;
    private int headPos;
    private Transition[] states;

    public TuringMachine(String input) {
        state = 0;
        this.input = input;
        tape = input.toCharArray();
        blank = ' ';
        symbolDivisor = '*';
        headPos = 0;
        states = getStatesForMultiplication();
    }

    public String toString() {
        try {
            StringBuilder s = new StringBuilder("[ ");

            for (int i = Math.max(headPos - 15, 0); i <= headPos; i++) {
                s.append(tape[i]).append(" ");
            }

            s.append("[Y] ");

            for (int i = headPos + 1; i < Math.min(headPos + 15, tape.length); i++) {
                s.append(tape[i]).append(" ");
            }

            return s.append(']').toString();
        } catch (Exception e) {
            return "";
        }
    }

    public void run() {

        while(state != -1) {
            boolean ok = true;
            for (Transition state : states) {
                if (state.getRead() == tape[headPos] && this.state == state.getState()) {
                    System.out.println(toString()); //tape print
                    tape[headPos] = state.getWrite();
                    this.state = state.getNextState();
                    headPos += state.getDirection().getDifference();
                    ok = false;
                }
            }
            if (ok) {
                break;
            }
        }
        System.out.println("Result: " + toString());
    }

    //add states and transitions
    public Transition[] getStatesForMultiplication() {
        Transition[] states = {
                new Transition('1', '0', 0, 1, Direction.RIGHT),
                new Transition('0', '0', 0, 9, Direction.LEFT),
                new Transition('1', '1', 1, 1, Direction.RIGHT),
                new Transition('0', '0', 1, 2, Direction.RIGHT),
                new Transition('0', '0', 2, 7, Direction.LEFT),
                new Transition('1', '0', 3, 3, Direction.RIGHT),
                new Transition('1', '1', 3, 3, Direction.RIGHT),
                new Transition('0', '0', 3, 4, Direction.RIGHT),
                new Transition('1', '1', 4, 4, Direction.RIGHT),
                new Transition('0', '1', 4, 5, Direction.LEFT),
                new Transition('1', '1', 5, 5, Direction.LEFT),
                new Transition('0', '0', 5, 6, Direction.LEFT),
                new Transition('1', '1', 6, 6, Direction.LEFT),
                new Transition('0', '1', 6, 2, Direction.RIGHT),
                new Transition('1', '1', 7, 7, Direction.LEFT),
                new Transition('0', '0', 7, 8, Direction.LEFT),
                new Transition('1', '1', 8, 8, Direction.LEFT),
                new Transition('0', '1', 8, 0, Direction.RIGHT),
                new Transition('1', '1', 9, 9, Direction.LEFT),
                new Transition('0', '0', 9, -1, Direction.RIGHT)
        };
        return states;

    }

}
