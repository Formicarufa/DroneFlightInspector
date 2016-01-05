/*
 */
package cz.dfi.datamodel.graphable;

/**
 * Object of this class encapsulates the list of speed values.
 * It is expected that the importer inserts one object of this
 * type to the lookup for each axis of the movement of the drone.
 * @author Tomas Prochazka 24.12.2015
 */
public abstract class SpeedWrapper extends DoubleQuantity {

    public SpeedWrapper(double[] values, String name, String unit, double[] onBoardTimeValues, double[] recorderTimeValues) {
        super(values, name, unit, onBoardTimeValues, recorderTimeValues);
    }
}
