package com.sequenceanalyzer.model;

import com.sequenceanalyzer.model.data.Scale;

import java.util.ArrayList;

public class Sequence {

    private static final int LIMIT_TOP = 2;
    private static final int LIMIT_BOTTOM = -2;
    private static final int MAX_SIZE_OF_SEQUENCE = 8;
    private ArrayList<Chord> mChords = new ArrayList<>();

    public Sequence() {
        // TODO: Чекнуть за незамедлительное присваивание последовательности в проекте
        addChordToSequence();
        ProjectImpl.getInstance().setCurrentSequence(this);
    }

    public void addChordToSequence() {
        int stepInSequence = mChords.size() + 1;
        mChords.add(new Chord(stepInSequence));
    }

    public ArrayList<Chord> getChords() {
        return mChords;
    }

    public Chord getChord(int stepInSequence) {
        return mChords.get(stepInSequence - 1);
    }

    public Chord getLastChord() {
        return mChords.get(mChords.size() - 1);
    }

    public int isBeyondTheLimit() {
        Scale currentScale = ProjectImpl.getInstance().getCurrentScale();
        if(currentScale.calcOctavesInInterval(getLastChord().getTone(1)) == 2) return 1;
        else if(currentScale.calcOctavesInInterval(getLastChord().getLastTone()) == -2) return 2;
        else return 0;
    }
}
