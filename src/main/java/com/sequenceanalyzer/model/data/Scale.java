package com.sequenceanalyzer.model.data;

import org.decimal4j.util.DoubleRounder;

public class Scale {

    public static double calcInterval(int scaleStep, int scaleSize) {

        if (scaleSize > 43) scaleSize = 43;
        else if (scaleSize < 12) scaleSize = 12;

        return DoubleRounder.round(Math.pow(2, (double)scaleStep/scaleSize), 12);
    }
}
