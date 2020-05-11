package turing.machine;

import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.*;
import static turing.machine.Direction.LEFT;
import static turing.machine.Direction.RIGHT;

public class TuringMachine {

    public static final int INITIAL_STATE = 0;
    public static final char OUTPUT_SEPARATOR = 'x';
    private static Logger logger = getLogger(TuringMachine.class.getCanonicalName());
    private int state;
    private String input;
    private char[] tape;
    private ListIterator<String> head;
    private int headPos;
    private Transition[] states;

    public TuringMachine(String input) {
        //logger.addHandler(new ConsoleHandler());
        logger.setLevel(Level.INFO);
        state = INITIAL_STATE;
        this.input = input;
        tape = input.strip().toCharArray();
        headPos = 0;
        states = getStatesForMultiplication();
    }

    public void extendTapeRightByOne() {
        //char[] newTape = new char[tape.length + 1];
        char[] newTape = Arrays.copyOf(tape, tape.length + 1);
        newTape[newTape.length - 1] = ' ';
        tape = null;
        tape = newTape;
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

        while (true) {
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
        System.out.println("Result: " + print());
    }

    /*
    //add states and transitions
    public Transition[] getStatesForMultiplication() {
        Transition[] states = {

                new Transition('0', ' ', 0, 1, Direction.RIGHT),
                new Transition('0', '0', 1, 1, Direction.RIGHT),
                new Transition('1', '1', 1, 2, Direction.RIGHT),
                new Transition(' ', ' ', 2, 0, Direction.LEFT),

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

                new Transition(' ', ' ', 2, 0, Direction.LEFT),

                new Transition('x', 'x', 2, 7, Direction.LEFT),
                new Transition('0', '0', 7, 7, Direction.LEFT),
                new Transition('1', '1', 7, 8, Direction.LEFT),
                new Transition('0', '0', 8, 8, Direction.LEFT),
                new Transition(' ', ' ', 8, 0, Direction.RIGHT),

                new Transition('1', ' ', 0, 9, Direction.RIGHT),
                new Transition('0', ' ', 9, 9, Direction.RIGHT),
                new Transition('0', ' ', 9, 10, Direction.RIGHT),
                new Transition('x', ' ', 9, 10, Direction.RIGHT),

        };
        return states;

    }*/
    public void fastRun() {
        logger.info(String.format("state: %s  head position: %d tape: %s", state, headPos, tapeToString()));
        while (true) {
            boolean transitionFound = false;
            for (Transition t : getStatesForMultiplication()) {
                if ((tape[headPos] == t.getRead()) && (state == t.getState())) {
                    //move head
                    tape[headPos] = t.getWrite();
                    if (t.getDirection().equals(RIGHT)) {
                        headPos++;
                    } else {
                        headPos--;
                    }
                    if (headPos > tape.length - 1) {
                        extendTapeRightByOne();
                    }
                    if (headPos < 0) {
                        return;
                    }
                    state = t.getNextState();
                    transitionFound = true;
                    logger.info(String.format("new state: %s | head position: %d | tape symbol: %s |tape: %s", state, headPos,tape[headPos], tapeToString()));
                    break;
                }
            }
            if (!transitionFound) {
                break;
            }
        }
    }

    public Transition[] getStatesForMultiplication() {
        Transition[] states = {
                //case 0*k;
                new Transition('1', ' ', 0, 1, RIGHT),
                new Transition('0', ' ', 1, 1, RIGHT),
                new Transition(' ', ' ', 1, 2, RIGHT),
                new Transition(OUTPUT_SEPARATOR, ' ', 1, 2, RIGHT),
                //case k*0
                new Transition('0', ' ', 0, 3, RIGHT),
                new Transition('0', '0', 3, 3, RIGHT),
                new Transition('1', '1', 3, 4, RIGHT),
                new Transition(' ', ' ', 4, 5, LEFT),
                new Transition('1', '1', 3, 4, LEFT),
                new Transition('1', ' ', 5, 6, LEFT),
                new Transition('0', ' ', 6, 6, LEFT),
                new Transition(' ', ' ', 6, 2, RIGHT),
                //case write to output
                new Transition('0', ' ', 4, 7, RIGHT),
                new Transition('0', '0', 7, 7, RIGHT),
                new Transition(OUTPUT_SEPARATOR, OUTPUT_SEPARATOR, 7, 8, RIGHT),
                new Transition(' ', OUTPUT_SEPARATOR, 7, 8, RIGHT),
                new Transition('0', '0', 8, 8, RIGHT),
                new Transition(' ', '0', 8, 9, LEFT),
                new Transition('0', '0', 9, 9, LEFT),
                new Transition('x', 'x', 9, 10, LEFT),
                new Transition(' ', '0', 10, 11, LEFT),
                new Transition('0', '0', 11, 11, LEFT),
                new Transition('1', '1', 11, 12, LEFT),
                new Transition(' ', ' ', 12, 0, RIGHT),
                //inner loop with multiple steps
                new Transition('0', '0', 10, 13, LEFT),
                new Transition(' ', '0', 13, 4, RIGHT),
                new Transition('0', '0', 13, 13, LEFT),

                //
                new Transition('0', '0', 12, 14, LEFT),
                new Transition('0', '0', 14, 14, LEFT),
                new Transition(' ', ' ', 14, 0, RIGHT),

        };
        return states;

    }

    public String tapeToString() {
        return String.valueOf(tape);
    }
}
