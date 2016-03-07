/*
 */

package cz.dfi.datamodel.common;

import cz.dfi.datamodel.graphable.DoubleQuantity;
import cz.dfi.datamodel.series.TimeStampArray;

/**
 * Encapsulates a set of altitude values.
 * @author Tomas Prochazka
 * 24.12.2015
 */
public class AltitudeWrapper extends DoubleQuantity {

        public AltitudeWrapper(double[] values, String unit, TimeStampArray timeStamps) {
            super(values, "Altitude", unit, timeStamps);
        }
    }