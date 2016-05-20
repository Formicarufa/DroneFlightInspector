/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dfi.datamodel.common;

import cz.dfi.datamodel.graphable.DoubleQuantity;
import cz.dfi.datamodel.series.TimeStampArray;
import cz.dfi.datamodel.series.TopLevelSeriesGroupWrapper;

/**
 *
 * @author Tomas Prochazka
 */
public class AccelerationWrapper extends TopLevelSeriesGroupWrapper{
    public AccelerationWrapper(String unit, TimeStampArray timeStamps, double[] valuesX, double[] valuesY, double[] valuesZ){
        this(timeStamps);
        this.addChildren(
                new DoubleQuantity(valuesX, "Acceleration x", unit, timeStamps),
                new DoubleQuantity(valuesY, "Acceleration y", unit, timeStamps),
                new DoubleQuantity(valuesZ, "Acceleration z", unit, timeStamps)
        );
    }

    /**
     * Make sure that double quantities representing acceleration in x, y, z are added
     * into this container using {@link #addChild} or 
     * {@link #addChildren(cz.dfi.datamodel.series.SeriesWrapper...) } methods.
     * To do that automatically, use the 
     * {@link #AccelerationWrapper(java.lang.String, cz.dfi.datamodel.series.TimeStampArray, double[], double[], double[])  other constructor}.
     * @param timeStamps 
     */
    public AccelerationWrapper(TimeStampArray timeStamps) {
        super(NAME, timeStamps);
    }
    public static final String NAME = "Acceleration";
}
