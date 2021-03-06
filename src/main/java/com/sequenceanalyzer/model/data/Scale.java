package com.sequenceanalyzer.model.data;

import com.sequenceanalyzer.model.Chord;
import com.sequenceanalyzer.model.ProjectImpl;
import com.sequenceanalyzer.model.Tone;
import javafx.util.Pair;
import org.decimal4j.util.DoubleRounder;

import java.util.ArrayList;

public class Scale {

    private ArrayList<Pair<Integer, Integer>> mScaleArray;
    private ArrayList<Pair<Integer, Integer>> mScaleArrayOpposite;
    private int mScaleSize;
    private static Scale mCurrentScale;

    private Scale(int scaleSize) {
        this.mScaleSize = scaleSize;
        setScaleArray(scaleSize);
    }

    public static Scale getInstance(int scaleSize) {
        ProjectImpl project = ProjectImpl.getInstance();
        if(mCurrentScale == null) {
            mCurrentScale = new Scale(scaleSize);
            project.setCurrentScale(mCurrentScale);
            return mCurrentScale;
        } else if(mCurrentScale.mScaleSize != scaleSize){
            mCurrentScale.mScaleSize = scaleSize;
            mCurrentScale.setScaleArray(scaleSize);
            project.setCurrentScale(mCurrentScale);
        } return mCurrentScale;
    }

    public void setScaleArray(int scaleSize) {
        try {
            int[][] retrievedScale = (int[][]) getClass().getField("NAT" + scaleSize).get(this);
            mScaleArray = new ArrayList<>();
            mScaleArrayOpposite = new ArrayList<>();
            for(int i = 0; i <= retrievedScale.length; i++) {
                mScaleArray.add(new Pair<>(retrievedScale[i][0], retrievedScale[i][1]));
                mScaleArrayOpposite.add(new Pair<>(retrievedScale[i][1], retrievedScale[i][0]));
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Pair<Integer, Integer>> getScaleArray() {
        return mScaleArray;
    }

    public ArrayList<Pair<Integer, Integer>> getScaleArrayOpposite() {
        return mScaleArrayOpposite;
    }

    public int getScaleSize() {
        return mScaleSize;
    }

    public Pair<Integer, Integer> getNatInterval(Tone tone) {
        int scaleStep = tone.getScaleStep();
        if(scaleStep >= 0) return mScaleArray.get(scaleStep);
        else return mScaleArrayOpposite.get(scaleStep * (-1));
    }

    public Pair<Integer, Integer> getNatIntervalRelToSequence(Tone tone) {
        int scaleStep = tone.getScaleStepRelToSequence();
        int octaves = calcOctavesInInterval(tone);
        int remainingStep = Math.abs(scaleStep) - getScaleSize() * octaves;
        Pair<Integer, Integer> interval;

        if(scaleStep > 0) {
            interval = getScaleArray().get(remainingStep);
            for(int i = 1; i <= octaves; i++) {
                interval = new Pair<>(interval.getKey() * 2, interval.getValue());
            }
            if(interval.getKey() % interval.getValue() == 0) {
                interval = new Pair<>(
                        interval.getKey() / interval.getValue(),
                        interval.getValue() / interval.getValue()
                );
            } return interval;
        } else if (scaleStep < 0) {
            interval = getScaleArrayOpposite().get(remainingStep);
            for(int i = 1; i <= octaves; i++) {
                interval = new Pair<>(interval.getValue() * 2, interval.getKey());
            }
            if(interval.getValue() % interval.getKey() == 0) {
                interval = new Pair<>(
                        interval.getKey() / interval.getKey(),
                        interval.getValue() / interval.getKey()
                );
            } return interval;
        } else return interval = getNatInterval(tone);
    }

    public double calcTeTInterval(Tone tone) {
        return DoubleRounder.round(Math.pow(2, (double)tone.getScaleStep() / mScaleSize), 12);
    }

    public int calcOctavesInInterval(Tone tone) {
        return Math.abs(tone.getScaleStepRelToSequence() / getScaleSize());
    }

    public int calcOctavesInInterval(Chord chord) {
        return Math.abs(chord.getLastTone().getScaleStepRelToSequence()
                - chord.getTone(1).getScaleStepRelToSequence()
                / getScaleSize());
    }

    public static final int[][] NAT42 = {{1,1},{61,60},{31,30},{20,19},{16,15},{12,11},{10,9},{9,8},{8,7},{7,6},{20,17},{6,5},
            {11,9},{26,21},{5,4},{9,7},{13,10},{4,3},{35,26},{11,8},{7,5},{17,12},{10,7},{16,11},
            {52,35},{3,2},{20,13},{14,9},{8,5},{21,13},{18,11},{5,3},{17,10},{12,7},{7,4},{16,9},
            {9,5},{11,6},{15,8},{19,10},{60,31},{120,61},{2,1}};

    public static final int[][] NAT41 = {{1,1},{59,58},{30,29},{20,19},{15,14},{12,11},{10,9},{9,8},{8,7},{7,6},{13,11},{6,5},
            {11,9},{5,4},{19,15},{9,7},{21,16},{4,3},{19,14},{11,8},{7,5},{10,7},{16,11},{28,19},
            {3,2},{32,21},{14,9},{30,19},{8,5},{18,11},{5,3},{22,13},{12,7},{7,4},{16,9},{9,5},
            {11,6},{28,15},{19,10},{29,15},{116,59},{2,1}};

    public static final int[][] NAT40 = {{1,1},{58,57},{29,28},{20,19},{15,14},{12,11},{10,9},{9,8},{8,7},{7,6},{19,16},{6,5},
            {16,13},{5,4},{14,11},{13,10},{29,22},{4,3},{15,11},{7,5},{17,12},{10,7},{22,15},{3,2},
            {44,29},{20,13},{11,7},{8,5},{13,8},{5,3},{32,19},{12,7},{7,4},{16,9},{9,5},{11,6},
            {28,15},{19,10},{56,29},{114,58},{2,1}};

    public static final int[][] NAT39 = {{1,1},{57,56},{29,28},{19,18},{14,13},{12,11},{10,9},{17,15},{15,13},{27,23},{6,5},{11,9},
            {21,17},{5,4},{9,7},{13,10},{4,3},{23,17},{11,8},{7,5},{10,7},{16,11},{34,23},{3,2},
            {20,12},{14,9},{8,5},{34,21},{18,11},{5,3},{46,27},{26,15},{30,17},{9,5},{22,12},{13,7},
            {36,19},{56,29},{112,57},{2,1}};

    public static final int[][] NAT38 = {{1,1},{55,54},{28,27},{18,17},{14,13},{11,10},{10,9},{17,15},{15,13},{13,11},{6,5},{11,9},
            {5,4},{19,15},{9,7},{21,16},{4,3},{15,11},{18,13},{17,12},{13,9},{22,15},{3,2},{32,21},
            {14,9},{30,19},{8,5},{18,11},{5,3},{22,13},{26,15},{30,17},{9,5},{20,11},{13,7},{17,9},
            {27,14},{108,55},{2,1}};

    public static final int[][] NAT37 = {{1,1},{54,53},{27,26},{18,17},{14,13},{11,10},{10,9},{17,15},{15,13},{13,11},{6,5},{11,9},
            {5,4},{14,11},{13,10},{4,3},{27,20},{11,8},{7,5},{10,7},{16,11},{40,27},{3,2},{20,13},
            {11,7},{8,5},{18,11},{5,3},{22,13},{26,15},{30,17},{9,5},{20,11},{13,7},{17,9},{52,27},
            {53,27},{2,1}};

    public static final int[][] NAT36 = {{1,1},{52,51},{26,25},{17,16},{13,12},{10,9},{9,8},{8,7},{7,6},{6,5},{17,14},
            {21,17},{5,4},{9,7},{13,10},{4,3},{15,11},{18,13},{17,12},{13,9},{22,15},{3,2},{20,13},
            {14,9},{8,5},{34,21},{28,17},{5,3},{12,7},{7,4},{16,9},{9,5},{24,13},{15,8},{25,13},{51,26},
            {2,1}};

    public static final int[][] NAT35 = {{1,1},{51,50},{25,24},{17,16},{13,12},{10,9},{9,8},{8,7},{7,6},{6,5},{11,9},
            {5,4},{14,11},{9,7},{29,22},{4,3},{11,8},{7,5},{10,7},{16,11},{3,2},{44,29},{14,9},
            {11,7},{8,5},{18,11},{5,3},{12,7},{7,4},{16,9},{9,5},{24,13},{32,17},{48,25},{100,51},{2,1}};

    public static final int[][] NAT34 = {{1,1},{50,49},{25,24},{16,15},{12,11},{10,9},{9,8},{15,13},{13,11},{6,5},{11,9},
            {5,4},{14,11},{13,10},{4,3},{19,14},{18,13},{17,12},{13,9},{28,19},{3,2},{20,13},{11,7},
            {8,5},{18,11},{5,3},{22,13},{26,15},{16,9},{9,5},{11,6},{15,8},{48,25},{49,25},{2,1}};

    public static final int[][] NAT33 = {{1,1},{48,47},{24,23},{16,15},{12,11},{10,9},{17,15},{7,6},{13,11},{6,5},{16,13},
            {5,4},{9,7},{21,16},{4,3},{11,8},{7,5},{10,7},{16,11},{3,2},{32,21},{14,9},{8,5},
            {13,8},{5,3},{22,13},{12,7},{30,17},{9,5},{11,6},{15,8},{23,12},{47,24},{2,1}};

    public static final int[][] NAT32 = {{1,1},{46,45},{23,22},{15,14},{12,11},{9,8},{8,7},{7,6},{19,16},{11,9},{5,4},
            {14,11},{13,10},{4,3},{19,14},{11,8},{17,12},{16,11},{28,19},{3,2},{20,13},{11,7},{8,5},
            {18,11},{32,19},{12,7},{7,4},{16,9},{11,6},{28,15},{44,23},{45,23},{2,1}};

    public static final int[][] NAT31 = {{1,1},{45,44},{22,21},{15,14},{11,10},{9,8},{8,7},{7,6},{6,5},{11,9},{5,4},
            {14,11},{13,10},{4,3},{15,11},{7,5},{10,7},{22,15},{3,2},{20,13},{11,7},{8,5},{18,11},
            {5,3},{12,7},{7,4},{16,9},{20,11},{28,15},{21,11},{88,45},{2,1}};

    public static final int[][] NAT30 = {{1,1},{43,42},{22,21},{14,13},{11,10},{9,8},{8,7},{20,17},{6,5},{16,13},{5,4},
            {9,7},{4,3},{19,14},{11,8},{17,12},{16,11},{28,19},{3,2},{14,9},{8,5},{13,8},{5,3},
            {17,10},{7,4},{16,9},{20,11},{13,7},{21,11},{84,43},{2,1}};

    public static final int[][] NAT29 = {{1,1},{42,41},{21,20},{14,13},{10,9},{9,8},{15,13},{13,11},{17,14},{5,4},{14,11},
            {13,10},{4,3},{15,11},{7,5},{10,7},{22,15},{3,2},{20,13},{11,7},{8,5},{28,17},{20,13},
            {26,15},{16,9},{9,5},{13,7},{40,21},{41,21},{2,1}};

    public static final int[][] NAT28 = {{1,1},{40,39},{20,19},{13,12},{10,9},{17,15},{7,6},{19,16},{11,9},{5,4},{9,7},
            {17,13},{4,3},{11,8},{17,12},{16,11},{3,2},{26,17},{14,9},{8,5},{18,11},{32,19},{12,7},
            {30,17},{9,5},{24,13},{19,10},{39,20},{2,1}};

    public static final int[][] NAT27 = {{1,1},{39,38},{19,18},{13,12},{10,9},{8,7},{7,6},{6,5},{11,9},{5,4},{9,7},
            {4,3},{15,11},{7,5},{10,7},{22,15},{3,2},{14,9},{8,5},{18,11},{5,3},{12,7},{7,4},
            {9,5},{24,13},{36,19},{76,39},{2,1}};

    public static final int[][] NAT26 = {{1,1},{38,37},{19,18},{13,12},{9,8},{8,7},{27,23},{6,5},{21,17},{14,11},{13,10},
            {4,3},{11,8},{17,12},{16,11},{3,2},{20,13},{11,7},{34,21},{5,3},{46,27},{7,4},{16,9},
            {24,13},{36,19},{74,38},{2,1}};

    public static final int[][] NAT25 = {{1,1},{36,35},{18,17},{12,11},{9,8},{8,7},{13,11},{17,14},{5,4},{9,7},{4,3},
            {19,14},{7,5},{10,7},{28,19},{3,2},{14,9},{8,5},{28,17},{22,13},{7,4},{16,9},{11,6},
            {17,9},{35,18},{2,1}};

    public static final int[][] NAT24 = {{1,1},{35,34},{17,16},{12,11},{9,8},{15,13},{19,16},{11,9},{5,4},{13,10},{4,3},
            {11,8},{17,12},{16,11},{3,2},{20,13},{8,5},{18,11},{32,19},{26,15},{16,9},{11,6},{32,17},
            {68,35},{2,1}};

    public static final int[][] NAT23 = {{1,1},{33,32},{17,16},{11,10},{9,8},{7,6},{6,5},{16,13},{14,11},{17,13},{23,17},
            {7,5},{10,7},{34,23},{26,17},{11,7},{13,8},{5,3},{12,7},{16,9},{20,11},{32,17},{64,33},
            {2,1}};

    public static final int[][] NAT22 = {{1,1},{32,31},{16,15},{11,10},{8,7},{7,6},{6,5},{5,4},{9,7},{4,3},{11,8},
            {17,12},{16,11},{3,2},{14,9},{8,5},{5,3},{12,7},{7,4},{20,11},{15,8},{31,16},{2,1}};

    public static final int[][] NAT21 = {{1,1},{30,29},{15,14},{10,9},{8,7},{13,11},{11,9},{5,4},{13,10},{4,3},{7,5},
            {10,7},{3,2},{20,13},{8,5},{18,11},{22,13},{7,4},{9,5},{28,15},{29,15},{2,1}};

    public static final int[][] NAT20 = {{1,1},{29,28},{14,13},{10,9},{7,6},{19,16},{16,13},{9,7},{4,3},{11,8},{17,12},
            {16,11},{3,2},{14,9},{13,8},{32,19},{12,7},{9,5},{13,7},{56,29},{2,1}};

    public static final int[][] NAT19 = {{1,1},{27,26},{14,13},{9,8},{7,6},{6,5},{5,4},{9,7},{4,3},{7,5},{10,7},
            {3,2},{14,9},{8,5},{5,3},{12,7},{16,9},{13,7},{52,27},{2,1}};

    public static final int[][] NAT18 = {{1,1},{26,25},{13,12},{9,8},{7,6},{17,14},{5,4},{17,13},{15,11},{17,12},{22,15},
            {26,17},{8,5},{28,17},{12,7},{16,9},{24,13},{25,13},{2,1}};

    public static final int[][] NAT17 = {{1,1},{25,24},{12,11},{9,8},{7,6},{11,9},{5,4},{4,3},{7,5},{10,7},{3,2},
            {8,5},{18,11},{12,7},{16,9},{11,6},{48,25},{2,1}};

    public static final int[][] NAT16 = {{1,1},{23,22},{12,11},{17,15},{19,16},{5,4},{13,10},{19,14},{17,12},{28,19},{20,13},
            {8,5},{32,19},{30,17},{11,6},{44,23},{2,1}};

    public static final int[][] NAT15 = {{1,1},{22,21},{11,10},{8,7},{6,5},{5,4},{4,3},{11,8},{16,11},{3,2},{8,5},
            {5,3},{7,4},{20,11},{21,11},{2,1}};

    public static final int[][] NAT14 = {{1,1},{20,19},{10,9},{7,6},{6,5},{9,7},{4,3},{17,12},{3,2},{14,9},{5,3},
            {12,7},{9,5},{19,10},{2,1}};

    public static final int[][] NAT13 = {{1,1},{19,18},{9,8},{7,6},{21,17},{4,3},{11,8},{16,11},{3,2},{34,21},{12,7},
            {16,9},{36,19},{2,1}};

    public static final int[][] NAT12 = {{1,1},{16,15},{9,8},{6,5},{5,4},{4,3},{17,12},{3,2},{8,5},{5,3},{16,9},
            {15,8},{2,1}};
}
