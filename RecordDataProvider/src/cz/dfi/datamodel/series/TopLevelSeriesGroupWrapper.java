/*
 */
package cz.dfi.datamodel.series;

import cz.dfi.datamodel.TimeStampType;
import cz.dfi.datamodel.values.TimeInterval;
import cz.dfi.datamodel.values.ValueWrapper;
import cz.dfi.datamodel.values.ValuesGroupWrapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Series group wrapper which contains the time information. The other
 * implementation of {@link SeriesGroupWrapper} does not contain the time
 * information itself. However, it has to have parent which is able to provide
 * the time information when needed. Instance of this class does not have to
 * have parent. Class is instantiated through static method
 *
 * @author Tomas Prochazka 28.2.2016
 */
class TopLevelSeriesGroupWrapper extends SeriesGroupWrapper {

    private final TimeStampArray timeStamps;

    /**
     * Creates a new group which joins multiple series of values together.
     *
     * @param name Name of the group
     * @param timeStamps Array of time stamps associated with the values of
     * series subsumed into this group
     */
    TopLevelSeriesGroupWrapper(String name, TimeStampArray timeStamps) {
        super(name);
        this.timeStamps = timeStamps;
    }

    @Override
    public TimeStampArray getTimeStamps() {
        return timeStamps;
    }

    @Override
    public ValueWrapper getValue(long time, TimeStampType timeType) {
        ValuesGroupWrapper groupWrapper = ValuesGroupWrapper.create(name, timeStamps.getClosestTimeStamp(time, timeType));
        for (SeriesWrapper seriesWrapper : getChildren()) {
            ValueWrapper value = seriesWrapper.getValue(time, timeType);
            groupWrapper.addChild(value);
        }
        return groupWrapper;
    }

    @Override
    public Collection<ValueWrapper> getIntervalSummary(long t1, long t2, TimeStampType timeType) {
        ValuesGroupWrapper groupWrapper = ValuesGroupWrapper.create(name, getTimeStamps().getTimeInterval(t1, t2, timeType));
        for (SeriesWrapper seriesWrapper : getChildren()) {
            Collection<ValueWrapper> intervalSummary = seriesWrapper.getIntervalSummary(t1, t2, timeType);
            for (ValueWrapper valueWrapper : intervalSummary) {
                groupWrapper.addChild(valueWrapper);
            }
        }
        if (groupWrapper.getChildren().isEmpty()) {
            return Collections.EMPTY_LIST;
        } else {
            return Collections.singleton(groupWrapper);
        }

    }

}
