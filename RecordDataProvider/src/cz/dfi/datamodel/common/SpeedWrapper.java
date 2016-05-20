/*
 */
package cz.dfi.datamodel.common;

import cz.dfi.datamodel.graphable.DoubleQuantity;
import cz.dfi.datamodel.series.TimeStampArray;
import cz.dfi.datamodel.series.TopLevelSeriesGroupWrapper;

/**
 * @author Tomas Prochazka 24.12.2015
 */
public class SpeedWrapper extends TopLevelSeriesGroupWrapper {

    public SpeedWrapper(String unit, TimeStampArray timeStamps,double[] xValues, double[] yValues,double[] zValues  ) {
        this(timeStamps);
        this.addChildren(
                new DoubleQuantity(xValues, "Speed in x", unit, timeStamps),
                new DoubleQuantity(yValues, "Speed in y", unit, timeStamps),
                new DoubleQuantity(zValues, "Speed in z", unit, timeStamps)
        );
        
    }

        /**
     * Make sure that double quantities representing speed values in x, y, z are added
     * into this container using {@link #addChild} or 
     * {@link #addChildren(cz.dfi.datamodel.series.SeriesWrapper...) } methods.
     * To do that automatically, use the 
     * {@link #SpeedWrapper(java.lang.String, cz.dfi.datamodel.series.TimeStampArray, double[], double[], double[])  other constructor}.
     * @param timeStamps 
     */
    public SpeedWrapper(TimeStampArray timeStamps) {
        super(NAME,timeStamps);
    }
    public static final String NAME = "Velocity";
}
