package com.sequenceanalyzer.model.data;

import org.junit.Test;

import static org.junit.Assert.*;

public class ScaleTest {

    @Test
    public void calcInterval() {
        double expected = 1.122462;
        double actual = Scale.calcTeTInterval(2, 12);
        assertEquals(expected, actual, 0.000001);
        System.out.println(expected + " " + actual);
    }
}