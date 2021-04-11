package com.sequenceanalyzer.model;

import com.sequenceanalyzer.model.data.Scale;
import javafx.util.Pair;

public class Tone {

    private final double intervalTeT;
    private final int intervalNatNumerator;
    private final int intervalNatDenominator;
    private int stepInChord;
    private int scaleStep;

    public Tone(Scale scale, int scaleStep) {
        Pair<Integer, Integer> intervalPair = getIntervalPairFromScale(scale, scaleStep);
        intervalNatNumerator = intervalPair.getKey();
        intervalNatDenominator = intervalPair.getValue();
        intervalTeT = Scale.calcTeTInterval(scaleStep, scale.getScaleSize());
        this.scaleStep = scaleStep;
    }

    private Pair<Integer, Integer> getIntervalPairFromScale(Scale scale, int scaleStep) {
        return scale.getCurrentScale().get(scaleStep);
    }
}
