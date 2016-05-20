/*
 */

package cz.dfi.dfizip.special;

import cz.dfi.datamodel.graphable.DoubleQuantity;
import cz.dfi.datamodel.series.TimeStampArray;

/**
 * Creates a specific subclass of DoubleQuantity class for quantities 
 * with a certain name. Such quantities can then be more easily found in 
 * the lookup.
 * @author Tomas Prochazka
 * 15.5.2016
 */
public interface SpecialDoubleQuantProvider {
    String getName();
    DoubleQuantity getQuantity(String unit, double[] values, TimeStampArray timeStamps);
}
