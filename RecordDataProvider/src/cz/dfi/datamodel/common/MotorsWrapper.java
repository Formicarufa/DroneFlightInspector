/*
 */
package cz.dfi.datamodel.common;

import cz.dfi.datamodel.graphable.DoubleQuantity;
import cz.dfi.datamodel.series.TimeStampArray;
import cz.dfi.datamodel.series.TopLevelSeriesGroupWrapper;

/**
 * Object of this class encapsulates the list of records of motor powers. The
 * values in the list should be in percent (range 0 - 100).
 *
 * @author Tomas Prochazka 24.12.2015
 */
public  class MotorsWrapper extends TopLevelSeriesGroupWrapper {

    /**
     * 
     * @param values
     * @param name
     * @param timeStamps 
     * @param motorPowers An array of doubles (values in percent) for each motor of the drone.
     */
    public MotorsWrapper(TimeStampArray timeStamps, double []... motorPowers) {
        super("Motor powers", timeStamps);
        for (int i = 0; i < motorPowers.length; i++) {
            this.addChild(new DoubleQuantity(motorPowers[i], "Motor "+ (i+1)+" power", "percent", timeStamps));
        }
    }
}
