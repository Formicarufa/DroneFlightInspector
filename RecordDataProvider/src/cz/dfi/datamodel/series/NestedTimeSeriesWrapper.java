/*
 */

package cz.dfi.datamodel.series;

import cz.dfi.datamodel.TimeStampType;

/**
 * Series which implements this interface declares that it will be used as nested:
 * that means that it will inside of the Series hierarchy always be contained in some group.
 * The series does not have to take care of the time values,
 * because it can always obtain them from the parent.
 * Default implementation for this behavior is included in this interface.
 * @author Tomas Prochazka
 * 28.2.2016
 */
public interface NestedTimeSeriesWrapper extends SeriesWrapper{

    @Override
    public default TimeStampArray getTimeStamps() {
        SeriesGroupWrapper p = getParent();
        if (p == null) throw new IllegalStateException("Parent must not be null.");
        return p.getTimeStamps();
    }
      

}
