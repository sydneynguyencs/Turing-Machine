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
        symbolDivisor = '_';
        headPos = 0;
        states = getStatesForMultiplication();
    }

    public String print() {
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

        int count = 0;

        while(true) {
            boolean ok = true;
            for (Transition st : states) {
                if (st.getRead() == tape[headPos] && state == st.getState()) {
                    System.out.println("Step: " + count); //tape print
                    System.out.println(print()); //tape print
                    System.out.print("Head Position: " + headPos + " State: " + state + "\n\n");
                    tape[headPos] = st.getWrite();
                    state = st.getNextState();
                    headPos += st.getDirection().getDirection();
                    count++;
                    ok = false;
                }
            }
            if (ok) {
                break;
            }
        }
        System.out.println("Result: " + print());
    }


    //add states and transitions
    public Transition[] getStatesForMultiplication() {
        Transition[] states = {
                new Transition(blank, blank, 0, 1, Direction.RIGHT),
                new Transition('1', symbolDivisor, 0, 2, Direction.RIGHT),
                new Transition(blank, blank, 1, 14, Direction.RIGHT),
                new Transition('1', symbolDivisor, 1, 2, Direction.RIGHT),
                new Transition(blank, blank, 2, 3, Direction.RIGHT),
                new Transition('1', '1', 2, 2, Direction.RIGHT),
                new Transition(blank, blank, 3, 15, Direction.LEFT),
                new Transition('1', symbolDivisor, 3, 4, Direction.RIGHT),
                new Transition(blank, blank, 4, 5, Direction.RIGHT),
                new Transition('1', '1', 4, 4, Direction.RIGHT),
                new Transition(symbolDivisor, '1', 5, 6, Direction.LEFT),
                new Transition('1', '1', 5, 5, Direction.RIGHT),
                new Transition(blank, blank, 6, 7, Direction.LEFT),
                new Transition('1', '1', 6, 6, Direction.LEFT),
                new Transition(symbolDivisor, '1', 7, 9, Direction.LEFT),
                new Transition('1', '1', 7, 8, Direction.LEFT),
                new Transition(symbolDivisor, '1', 8, 3, Direction.RIGHT),
                new Transition('1', '1', 8, 8, Direction.LEFT),
                new Transition(blank, blank, 9, 10, Direction.LEFT),
                new Transition('1', '1', 9, 9, Direction.LEFT),
                new Transition(blank, blank, 10, 12, Direction.RIGHT),
                new Transition('1', '1', 10, 11, Direction.LEFT),
                new Transition(blank, blank, 11, 0, Direction.RIGHT),
                new Transition('1', '1', 11, 11, Direction.LEFT),
                new Transition(blank, blank, 12, 12, Direction.RIGHT),
                new Transition('1', symbolDivisor, 12, 13, Direction.RIGHT),
                new Transition(blank, blank, 13, 50, Direction.STAY),
                new Transition('1', symbolDivisor, 13, 13, Direction.RIGHT),
                new Transition(blank, blank, 14, 50, Direction.STAY),
                new Transition('1', symbolDivisor, 14, 14, Direction.RIGHT),
                new Transition(blank, blank, 15, 16, Direction.LEFT),
                new Transition('1', symbolDivisor, 15, 15, Direction.LEFT),
                new Transition(blank, blank, 16, 50, Direction.STAY),
                new Transition('1', symbolDivisor, 16, 16, Direction.LEFT)

        };
        return states;

    }

}
