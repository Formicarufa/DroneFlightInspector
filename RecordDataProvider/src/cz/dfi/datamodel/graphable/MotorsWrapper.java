/*
 */
package cz.dfi.datamodel.graphable;

import cz.dfi.datamodel.series.TimeStampArray;

/**
 * Object of this class encapsulates the list of records of motor powers. The
 * values in the list should be in percent (range 0 - 100). Importer might more
 * than one instance of inheritors of this class to the lookup, because drones
 * can have multiple motors.
 *
 * @author Tomas Prochazka 24.12.2015
 */
public abstract class MotorsWrapper extends DoubleQuantity {

    public MotorsWrapper(double[] values, String name, TimeStampArray timeStamps) {
        super(values, name, "percent", timeStamps);
    }
}
