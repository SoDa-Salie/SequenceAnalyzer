package com.sequenceanalyzer.util;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.ContinuousRamp;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.sequenceanalyzer.model.Chord;
import com.sequenceanalyzer.model.ProjectImpl;
import com.sequenceanalyzer.model.Sequence;
import com.sequenceanalyzer.model.data.Scale;

import java.util.ArrayList;


public class Osc {

    private static Osc mCurrentOsc;

    private Osc() {
        mCurrentOsc = this;
    }

    public static Osc getInstance() {
        if (mCurrentOsc == null) mCurrentOsc = new Osc();
        return mCurrentOsc;
    }

    public void start() {
        ProjectImpl project = ProjectImpl.getInstance();
        Sequence currentSequence = project.getCurrentSequence();
        ArrayList<Chord> chords = currentSequence.getChords();
        for(Chord chord : chords) playChord(chord);
    }

    private void playChord(Chord chord) {
        Synthesizer synth = JSyn.createSynthesizer();
        LineOut lo = new LineOut();
        ContinuousRamp cr = new ContinuousRamp();
        SineOscillator so;
        synth.add(lo);
        synth.add(cr);



        Thread thread = new Thread(() -> {
            lo.start();
            synth.start();
            long time = System.currentTimeMillis();
            while (Math.abs(time - System.currentTimeMillis()) < 2000) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synth.stop();
        });
        thread.start();
    }

    private void addTone(Synthesizer synth, LineOut lo, ContinuousRamp cr) {
        // TODO: 16.04.2021  
        SineOscillator so;
        for (int i = 1; i < 25; i++) {
            synth.add(so = new SineOscillator(440 * i));
            so.output.connect(0, lo.input, 0);
            so.output.connect(0, lo.input, 1);
            cr.output.connect(so.amplitude);
            cr.current.set(1.0 / (i * 3));
            cr.time.set(2.0);
        }
    }
}
