package turing.machine;

import java.util.*;

public class TuringMachine {

    private int state;
    private String input;
    private char[] tape;
    private ListIterator<String> head;
    private int headPos;
    private Transition[] states;

    public TuringMachine(String input) {
        state = 0;
        this.input = input;
        tape = input.toCharArray();
        headPos = 0;
        states = getStatesForMultiplication();
    }

    public String print() {
        StringBuilder s = new StringBuilder("[ ");

        for (int i = Math.max(headPos - 15, 0); i < headPos; i++) {
            s.append(tape[i]).append(" ");
        }

        s.append("[H] ");

        for (int i = headPos; i <= Math.min(headPos + 15, tape.length - 1); i++) {
            s.append(tape[i]).append(" ");
        }

        return s.append(']').toString();
    }

    public void run() {

        int count = 0;

        while(true) {
            boolean ok = true;
            for (Transition st : states) {
                if (st.getRead() == tape[headPos] && state == st.getState()) {
                    System.out.println("\nStep: " + count); //tape print
                    System.out.println(st.toString());
                    System.out.println("Head Position: " + headPos);
                    tape[headPos] = st.getWrite();
                    state = st.getNextState();
                    headPos += st.getDirection().getDirection();
                    System.out.println(print()); //tape print
                    count++;
                    ok = false;
                }
            }
            if (ok) {
                break;
            }
        }

        int res = 0;
        for(char c : tape) {
            if (c == '0') {
                res++;
            }
        }
        System.out.println("____________________________________________________________");
        System.out.println("Result als Band: " + print());
        System.out.println("Result als Dec: " + res);
    }


    //add states and transitions
    public Transition[] getStatesForMultiplication() {
        Transition[] states = {

                new Transition('0', ' ', 0, 1, Direction.RIGHT),
                new Transition('0', '0', 1, 1, Direction.RIGHT),
                new Transition('1', '1', 1, 2, Direction.RIGHT),

                new Transition('0', ' ', 2, 3, Direction.RIGHT),
                new Transition('0', '0', 3, 3, Direction.RIGHT),
                new Transition(' ', 'x', 3, 4, Direction.RIGHT),
                new Transition('x', 'x', 3, 4, Direction.RIGHT),
                new Transition('0', '0', 4, 4, Direction.RIGHT),
                new Transition(' ', '0', 4, 5, Direction.LEFT),
                new Transition('0', '0', 5, 5, Direction.LEFT),
                new Transition('x', 'x', 5, 6, Direction.LEFT),
                new Transition('0', '0', 6, 6, Direction.LEFT),
                new Transition(' ', '0', 6, 2, Direction.RIGHT),

                new Transition('x', 'x', 2, 7, Direction.LEFT),
                new Transition('0', '0', 7, 7, Direction.LEFT),
                new Transition('1', '1', 7, 8, Direction.LEFT),
                new Transition('0', '0', 8, 8, Direction.LEFT),
                new Transition(' ', ' ', 8, 0, Direction.RIGHT),

                new Transition('1', ' ', 0, 9, Direction.RIGHT),
                new Transition('0', ' ', 9, 9, Direction.RIGHT),
                new Transition(' ', ' ', 9, 10, Direction.RIGHT),
                new Transition('x', ' ', 9, 10, Direction.RIGHT),

                new Transition(' ', ' ', 2, 12, Direction.LEFT),
                new Transition('1', ' ', 12, 11, Direction.LEFT),
                new Transition('0', ' ', 11, 11, Direction.LEFT)
        };
        return states;

    }

}
