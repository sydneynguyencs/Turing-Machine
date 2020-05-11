package turing.machine;

import com.google.common.base.Strings;
import com.google.common.primitives.Chars;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TuringMachineTest {
    private final String inputPattern = "%s1%s";
    private String firstMultiplicand;
    private String secondMultiplicand;
    private TuringMachine tm;

    @AfterEach
    void tearDown() {
        firstMultiplicand = null;
        secondMultiplicand = null;
        tm = null;
    }

    /**
     * case: 0*k, where k is a integer number
     */
    @Test
    void getStatesForMultiplicationFirstMultiplicandZero() {
        firstMultiplicand = "";
        secondMultiplicand = "000";
        setTuringMachine(firstMultiplicand, secondMultiplicand);
        tm.fastRun();
        Assertions.assertEquals("", tm.tapeToString().strip());
    }

    /**
     * case: ___10....00x0...00
     */
    @Test
    void getStatesForMultiplicationCalculationEnd() {
        String expected = "0000";
        firstMultiplicand = " ";
        secondMultiplicand = "000" + tm.OUTPUT_SEPARATOR + expected;
        setTuringMachine(firstMultiplicand, secondMultiplicand);
        tm.fastRun();
        Assertions.assertEquals(expected, tm.tapeToString().strip());
    }

    /**
     * case: 0...01
     */
    @Test
    void getStatesForMultiplicationSecondMultiplicandZero() {
        firstMultiplicand = "00000";
        secondMultiplicand = "";
        setTuringMachine(firstMultiplicand, secondMultiplicand);
        tm.fastRun();
        Assertions.assertEquals("", tm.tapeToString().strip());
    }

    /**
     * case: when the outer loop is finished
     */
    @Test
    void getStatesForMultiplicationFirstWordIsFinished() {
        firstMultiplicand = "0";
        secondMultiplicand = "0";
        setTuringMachine(firstMultiplicand, secondMultiplicand);
        tm.fastRun();
        Assertions.assertEquals("0", tm.tapeToString().strip());
    }

    /**
     * case: inner loop takes multiple steps
     */
    @Test
    void getStatesForMultiplicationMultipleStepsInnerLoop() {
        firstMultiplicand = "000";
        secondMultiplicand = "00000";
        setTuringMachine(firstMultiplicand, secondMultiplicand);
        tm.fastRun();
        Assertions.assertEquals("000000000000000", tm.tapeToString().strip());
    }

    @Test
    void getStatesForMultiplicationBigNumbers() {
        int firstNum = 13;
        int secondNum = 17;
        firstMultiplicand = getUnaryCode(firstNum);
        secondMultiplicand = getUnaryCode(secondNum);
        setTuringMachine(firstMultiplicand, secondMultiplicand);
        tm.fastRun();
        Assertions.assertEquals(getUnaryCode(firstNum * secondNum), tm.tapeToString().strip());
    }

    private void setTuringMachine(String firstMultiplicand, String secondMultiplicand) {
        tm = new TuringMachine(String.format(inputPattern, firstMultiplicand, secondMultiplicand));
    }

    private String getUnaryCode(int decimal) {
        String result = "";
        if (decimal < 0) {
            throw new IllegalArgumentException("decimal must be positive number!");
        }
        char[] cArray = new char[decimal];
        Arrays.fill(cArray, 0, cArray.length , '0');
        return String.valueOf(cArray).strip();
    }
}