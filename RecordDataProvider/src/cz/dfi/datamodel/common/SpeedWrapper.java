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
        super("Velocity",timeStamps);
        this.addChildren(
                new DoubleQuantity(xValues, "Speed in x", unit, timeStamps),
                new DoubleQuantity(yValues, "Speed in y", unit, timeStamps),
                new DoubleQuantity(zValues, "Speed in z", unit, timeStamps)
        );
        
    }
}
