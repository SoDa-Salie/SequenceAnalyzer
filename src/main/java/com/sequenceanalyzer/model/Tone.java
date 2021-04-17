package com.sequenceanalyzer.model;

import com.sequenceanalyzer.model.data.Scale;
import javafx.util.Pair;

public class Tone {

    private int mScaleStepRelToSequence;
    private int mStepInChord;
    private int mScaleStep;

    public Tone(int scaleStep, int stepInChord, int scaleStepRelToSequence) {
        mScaleStepRelToSequence = scaleStepRelToSequence;
        mStepInChord = stepInChord;
        mScaleStep = scaleStep;
    }

    public int getStepInChord() {
        return mStepInChord;
    }

    public int getScaleStep() {
        return mScaleStep;
    }

    public int getScaleStepRelToSequence() {
        return mScaleStepRelToSequence;
    }
}
