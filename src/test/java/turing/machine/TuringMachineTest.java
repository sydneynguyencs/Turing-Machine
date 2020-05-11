package turing.machine;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

    @Test
    void getStatesForMultiplicationFirstMultiplicandZero() {
        firstMultiplicand = "";
        secondMultiplicand = "000";
        setTuringMachine(firstMultiplicand, secondMultiplicand);
        tm.fastRun();
        Assertions.assertEquals("", tm.tapeToString().strip());
    }

    @Test
    void getStatesForMultiplicationCalculationEnd() {
        String expected = "0000";
        firstMultiplicand = " ";
        secondMultiplicand = "000" + tm.OUTPUT_SEPARATOR + expected;
        setTuringMachine(firstMultiplicand, secondMultiplicand);
        tm.fastRun();
        Assertions.assertEquals(expected, tm.tapeToString().strip());
    }

    private void setTuringMachine(String firstMultiplicand, String secondMultiplicand) {
        tm = new TuringMachine(String.format(inputPattern, firstMultiplicand, secondMultiplicand));
    }
}