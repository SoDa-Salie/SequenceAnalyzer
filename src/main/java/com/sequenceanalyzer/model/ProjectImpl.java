package com.sequenceanalyzer.model;

import com.sequenceanalyzer.model.data.Scale;

import java.util.ArrayList;

public class ProjectImpl implements Project {

    private static ProjectImpl mProject;
    private Scale mCurrentScale;
    private Sequence mCurrentSequence;
    private ArrayList<Chord> listOfChords;
    private ArrayList<Sequence> listOfSequences;
    // TODO: Чекоть за реализацию добавления аккордов и последовательностей в списки

    public static ProjectImpl getInstance() {
        if(mProject != null) return mProject;
        else return new ProjectImpl();
    }

    public Sequence getCurrentSequence() {
        return mCurrentSequence;
    }

    public Scale getCurrentScale() {
        return mCurrentScale;
    }

    public void setCurrentScale(Scale currentScale) {
        mCurrentScale = currentScale;
    }

    public void setCurrentSequence(Sequence currentSequence) {
        mCurrentSequence = currentSequence;
    }
}
