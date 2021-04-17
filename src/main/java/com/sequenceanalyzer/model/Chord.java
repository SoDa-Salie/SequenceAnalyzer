package com.sequenceanalyzer.model;

import java.util.ArrayList;

public class Chord {

    private static final int MAX_SIZE_OF_CHORD = 5;
    private int mStepInSequence;
    private ArrayList<Tone> mTones = new ArrayList<>();

    public Chord(int stepInSequence) {
        mStepInSequence = stepInSequence;
        if(stepInSequence == 1) addToneToChord(0);
        else addToneToChord(1);
    }

    public void addToneToChord(int scaleStep) {
        int stepInChord = mTones.size() + 1;
        int scaleStepRelToSequence;
        if(scaleStep == 0) scaleStepRelToSequence = scaleStep;
        else if(scaleStep != 0 && stepInChord == 1) scaleStepRelToSequence =
                    getFirstToneFromPreviousChord().getScaleStepRelToSequence() + scaleStep;
        else scaleStepRelToSequence = getLastTone().getScaleStepRelToSequence() + scaleStep;
        mTones.add(new Tone(scaleStep, stepInChord, scaleStepRelToSequence));
    }

    public ArrayList<Tone> getTones() {
        return mTones;
    }

    public int getStepInSequence() {
        return mStepInSequence;
    }

    public Tone getTone(int stepInChord) {
        return mTones.get(stepInChord - 1);
    }

    public Tone getLastTone() {
        return mTones.get(mTones.size() - 1);
    }

    private Tone getFirstToneFromPreviousChord() {
        return ProjectImpl.getInstance()
                .getCurrentSequence()
                .getLastChord()
                .getTone(1);
    }

    public boolean isBeyondTheLimit() {
        int diffBetweenSteps =
                Math.abs(Math.abs(getTone(1).getScaleStepRelToSequence())
                        - Math.abs(getLastTone().getScaleStepRelToSequence()));
        int octaves = diffBetweenSteps / ProjectImpl.getInstance().getCurrentScale().getScaleSize();
        return octaves >= 2;
    }
}
