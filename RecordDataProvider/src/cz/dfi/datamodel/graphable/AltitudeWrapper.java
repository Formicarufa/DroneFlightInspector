/*
 */

package cz.dfi.datamodel.graphable;

/**
 * Encapsulates a set of altitude values.
 * @author Tomas Prochazka
 * 24.12.2015
 */
public class AltitudeWrapper extends DoubleQuantity {

        public AltitudeWrapper(double[] values, String unit, long[] onBoardTimeValues, long[] recorderTimeValues) {
            super(values, "Altitude", unit, onBoardTimeValues, recorderTimeValues);
        }
    }