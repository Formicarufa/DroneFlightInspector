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
        super("Acceleration", timeStamps);
        this.addChildren(
                new DoubleQuantity(valuesX, "Acceleration x", unit, timeStamps),
                new DoubleQuantity(valuesY, "Acceleration y", unit, timeStamps),
                new DoubleQuantity(valuesZ, "Acceleration z", unit, timeStamps)
        );
    }
}
