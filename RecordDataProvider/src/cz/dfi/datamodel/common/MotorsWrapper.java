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
     * @param timeStamps 
     * @param motorPowers An array of doubles (values in percent) for each motor of the drone.
     */
    public MotorsWrapper(TimeStampArray timeStamps, double []... motorPowers) {
        this(timeStamps);
        for (int i = 0; i < motorPowers.length; i++) {
            this.addChild(new DoubleQuantity(motorPowers[i], "Motor "+ (i+1)+" power", "percent", timeStamps));
        }
    }
    /**
     * Make sure that  quantities representing motor power values for each motor are added
     * into this container using {@link #addChild} or 
     * {@link #addChildren(cz.dfi.datamodel.series.SeriesWrapper...) } methods.
     * To do that automatically, use the 
     * {@link #MotorsWrapper(cz.dfi.datamodel.series.TimeStampArray, double[]...)  other constructor}.
     * @param timeStamps 
     */
    public MotorsWrapper(TimeStampArray timeStamps) {
        super(NAME, timeStamps);
    }
    public static final String NAME = "Motor powers";
}
