/*
 */

package cz.dfi.datamodel.series;

import cz.dfi.datamodel.TimeStampType;
import cz.dfi.datamodel.values.ValueWrapper;
import java.util.Collection;
import org.netbeans.api.annotations.common.CheckForNull;

/**
 * This is an interface for classes which wrap a set of
 * loaded values.
 * <p>
 * The interface provides method to get the name of the type of data,
 * methods to get array of points in time for which the values are present.
 * <p>
 * However, it does not provide access to the data itself.
 * The reason is, the loaded data can be of different types, including
 * primitive types, which can't be represented by generics.
 * (And I do not want to box and unbox them because of performance.)
 * Despite that, this interface provides enough information to be
 * used by, for example,  TimeSelectionComponent.
 * @author Tomas Prochazka
 * 8.12.2015
 */
public interface SeriesWrapper extends TimeSeries{

    /**
     * Gets a group which contains this series.
     * Each series or group of series might be be present inside of a group.
     * @return group which directly s this series or null if the series is not contained in a group
     */
    @CheckForNull SeriesGroupWrapper getParent();
        /**
     * Sets a group which contains this series.
     * Each series or group of series might be be present inside of a group.
     */
    void setParent(SeriesGroupWrapper parent);
    /**
     * In case the wrapper wraps a group of objects, returns the members of the group,
     * otherwise returns null
     * @return members of the group or null if the wrapper is a single series wrapper.
     */
    @CheckForNull Collection<SeriesWrapper> getChildren();
    /**
     * Gets the value from the series whose timestamp is closest to a given time value.
     * 
     * @param time time for which the value should be found
     * @param timeType type of the time argument
     * @return Wrapper of the value closest to a given time or null if the wrapper is a group wrapper
    **/
    @CheckForNull ValueWrapper getValue(long time, TimeStampType timeType);
    /**
     * When a time interval is selected the quantity represented by the TimeSeries
 might provide some kind of summary of the values inside of the interval.
     * SeriesWrapper can provide any number of summaries, including none (returning null)
     * @param t1 lower bound of the interval
     * @param t2 upper bound of the interval
     * @param timeType how the lower and upper bound are expressed
     * @return Collection of ValueWrappers or null.
     */
    @CheckForNull Collection<ValueWrapper> getIntervalSummary(long t1, long t2, TimeStampType timeType);
}
 