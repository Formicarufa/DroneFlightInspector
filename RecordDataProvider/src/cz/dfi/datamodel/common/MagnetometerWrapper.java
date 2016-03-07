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
public class MagnetometerWrapper extends TopLevelSeriesGroupWrapper{
    public MagnetometerWrapper(String unit, TimeStampArray timeStamps, double [] xValues, double [] yValues, double [] zValues) {
        super("Magnetometer ",timeStamps);
        this.addChildren(
                new DoubleQuantity(xValues, "Magnetometer x", unit, timeStamps),
                new DoubleQuantity(yValues, "Magnetometer y", unit, timeStamps),
                new DoubleQuantity(zValues, "Magnetometer z", unit, timeStamps)
        );
    }
   
}
