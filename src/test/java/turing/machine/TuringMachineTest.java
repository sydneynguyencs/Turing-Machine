package turing.machine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TuringMachineTest {
    private final String inputPattern = "%s1%s";
    private String firstMultiplicand;
    private String secondMultiplicand;
    private TuringMachine tm;

    @Test
    void getStatesForMultiplicationFirstMultiplicandZero() {
        firstMultiplicand = "";
        secondMultiplicand = "000";
        setTuringMachine(firstMultiplicand, secondMultiplicand);
        tm.fastRun();
        Assertions.assertEquals("", tm.tapeToString().strip());
    }

    private void setTuringMachine(String firstMultiplicand, String secondMultiplicand) {
        tm = new TuringMachine(String.format(inputPattern, firstMultiplicand, secondMultiplicand));
    }
}