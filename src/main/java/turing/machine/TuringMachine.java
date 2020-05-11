package turing.machine;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Logger.*;
import static turing.machine.Direction.LEFT;
import static turing.machine.Direction.RIGHT;

public class TuringMachine {

    public static final int INITIAL_STATE = 0;
    public static final char OUTPUT_SEPARATOR = 'x';
    public static final char BLANK = ' ';
    public final static int headVisionRadius = 15;
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
        char[] newTape = Arrays.copyOf(tape, tape.length + 1);
        newTape[newTape.length - 1] = BLANK;
        tape = null;
        tape = newTape;
    }

    public String printHeadPosition() {
        final char headSymbol = 'H';
        char[] cArray = new char[headVisionRadius];
        Arrays.fill(cArray, 0, headVisionRadius, '_');
        final String headRadius = String.valueOf(cArray);
        return String.format(headRadius + headSymbol + headRadius);
    }

    public String print() {
        StringBuilder s = new StringBuilder("");

        for (int i = headPos - headVisionRadius; i < headPos; i++) {
            if (i < 0) {
                s.append(BLANK);
            } else {
                s.append(tape[i]);
            }
        }
        s.append(tape[headPos]);

        for (int i = headPos + 0; i < headPos + headVisionRadius; i++) {
            if (i > tape.length - 1) {
                s.append(BLANK);
            } else {
                s.append(tape[i]);
            }
        }
        return s.toString();
    }

    public void run() {

        int count = 0;

        while (true) {
            boolean ok = true;
            for (Transition st : states) {
                if (st.getRead() == tape[headPos] && state == st.getState()) {
                    System.out.println("\nStep:" + BLANK + count); //tape print
                    System.out.println(st.toString());
                    System.out.println("Head" + BLANK + "Position: " + headPos);
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
        System.out.println("Result:" + BLANK + print());
    }


    public void fastRun() {
        //logger.info(String.format("state: %s | head position: %d | tape symbol: %s | tape:%s", state, headPos, tape[headPos], tapeToString()));
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
                    //logger.info(String.format("new state: %s | head position: %d | tape symbol: %s | tape:%s", state, headPos, tape[headPos], tapeToString()));
                    System.out.println(printHeadPosition());
                    System.out.println(print() + "\n");
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
                new Transition('1', BLANK, 0, 1, RIGHT),
                new Transition('0', BLANK, 1, 1, RIGHT),
                new Transition(BLANK, BLANK, 1, 2, RIGHT),
                new Transition(OUTPUT_SEPARATOR, BLANK, 1, 2, RIGHT),
                //case k*0
                new Transition('0', BLANK, 0, 3, RIGHT),
                new Transition('0', '0', 3, 3, RIGHT),
                new Transition('1', '1', 3, 4, RIGHT),
                new Transition(BLANK, BLANK, 4, 5, LEFT),
                new Transition('1', '1', 3, 4, LEFT),
                new Transition('1', BLANK, 5, 6, LEFT),
                new Transition('0', BLANK, 6, 6, LEFT),
                new Transition(BLANK, BLANK, 6, 2, RIGHT),
                //case write to output
                new Transition('0', BLANK, 4, 7, RIGHT),
                new Transition('0', '0', 7, 7, RIGHT),
                new Transition(OUTPUT_SEPARATOR, OUTPUT_SEPARATOR, 7, 8, RIGHT),
                new Transition(BLANK, OUTPUT_SEPARATOR, 7, 8, RIGHT),
                new Transition('0', '0', 8, 8, RIGHT),
                new Transition(BLANK, '0', 8, 9, LEFT),
                new Transition('0', '0', 9, 9, LEFT),
                new Transition(OUTPUT_SEPARATOR, OUTPUT_SEPARATOR, 9, 10, LEFT),
                new Transition(BLANK, '0', 10, 11, LEFT),
                new Transition('0', '0', 11, 11, LEFT),
                new Transition('1', '1', 11, 12, LEFT),
                new Transition(BLANK, BLANK, 12, 0, RIGHT),
                //inner loop with multiple steps
                new Transition('0', '0', 10, 13, LEFT),
                new Transition(BLANK, '0', 13, 4, RIGHT),
                new Transition('0', '0', 13, 13, LEFT),

                //
                new Transition('0', '0', 12, 14, LEFT),
                new Transition('0', '0', 14, 14, LEFT),
                new Transition(BLANK, BLANK, 14, 0, RIGHT),

        };
        return states;

    }

    public String tapeToString() {
        return String.valueOf(tape);
    }
}
