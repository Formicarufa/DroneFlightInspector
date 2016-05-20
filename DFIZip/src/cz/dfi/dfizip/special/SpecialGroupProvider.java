/*
 */

package cz.dfi.dfizip.special;

import cz.dfi.datamodel.series.SeriesGroupWrapper;
import cz.dfi.datamodel.series.TimeStampArray;

/**
 * Instead of a normal group, special group provider can create an instance of
 * a different class specifically for a given content type (according to the String name of the group). 
 * I.e. for group called "Acceleration" the AccelerationWrapper class is used (which inherits from SeriesGroupWrapper).
 * The advantage of this approach is, that anyone who needs to get acceleration
 * can find it in the lookup by searching for AccelerationWrapper class.
 * @author Tomas Prochazka
 * 15.5.2016
 */
public interface SpecialGroupProvider {
    String getName();
    SeriesGroupWrapper getGroup(TimeStampArray timeStamps);

}
