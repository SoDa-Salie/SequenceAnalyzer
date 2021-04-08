package com.sequenceanalyzer.util;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.sequenceanalyzer.model.Chord;
import com.sequenceanalyzer.model.Sequence;
import com.sequenceanalyzer.model.data.Scale;

public class Osc {

    private Chord mChord;
    private Sequence mSequence;

    public Osc(Chord chord) {
        mChord = chord;
    }

    public Osc(Sequence sequence) {
        mSequence = sequence;
    }

    public static void start() {
        Synthesizer synth = JSyn.createSynthesizer();
        LineOut myOut = new LineOut();
        SineOscillator so;

        double firstTone = Scale.calcInterval(15, 40);
        double secondTone = Scale.calcInterval(21, 40);
        double thirdTone = Scale.calcInterval(32, 40);

        /* 7, 12    5, 12
         *  19, 32   13, 32
         *
         * */

        synth.add(myOut);
        for (int i = 1; i < 25; i++) {
            synth.add(so = new SineOscillator(200 * i, 0.3 / (i * 3)));
            so.output.connect(0, myOut.input, 0);
            so.output.connect(0, myOut.input, 1);
        }
        for (int i = 1; i < 25; i++) {
            synth.add(so = new SineOscillator(200 * firstTone * i, 0.3 / (i * 3)));
            so.output.connect(0, myOut.input, 0);
            so.output.connect(0, myOut.input, 1);
        }
        for (int i = 1; i < 25; i++) {
            synth.add(so = new SineOscillator(200 * firstTone * secondTone * i, 0.3 / (i * 3)));
            so.output.connect(0, myOut.input, 0);
            so.output.connect(0, myOut.input, 1);
        }

        for (int i = 1; i < 25; i++) {
            synth.add(so = new SineOscillator(200 * firstTone * secondTone * thirdTone * i, 0.3 / (i * 3)));
            so.output.connect(0, myOut.input, 0);
            so.output.connect(0, myOut.input, 1);
        }

        myOut.start();
        synth.start();
    }
}
