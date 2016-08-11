/*
 */
package cz.dfi.datamodel.series;

import cz.dfi.datamodel.TimeStampType;
import cz.dfi.datamodel.graphable.DoubleQuantity;
import cz.dfi.datamodel.graphable.DoubleValueWrapper;
import cz.dfi.datamodel.values.StringValueWrapper;
import cz.dfi.datamodel.values.TimeInterval;
import cz.dfi.datamodel.values.ValueWrapper;
import cz.dfi.datamodel.values.ValuesGroupWrapper;
import cz.dfi.recorddataprovider.TimeToStringConverter;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import org.netbeans.api.annotations.common.CheckForNull;

/**
 * Series group wrapper which contains the time information.
 * To create a series use the {@link #create(java.lang.String, cz.dfi.datamodel.series.SeriesGroupWrapper) }
 * method or the {@link #create(java.lang.String, cz.dfi.datamodel.series.TimeStampArray) } method.
 * <p>
 * This class is suitable for being extended if the user wants to create a special
 * group wrapper.
 * <p>
 * The other
 * implementation of {@link SeriesGroupWrapper}  the {@link  NestedSeriesGroupWrapper}
 * does not contain the time
 * information itself. However, it has to have a parent which is able to provide
 * the time information when needed. 
 * <p>
 * Instance of this class does not have to
 * have parent but it has to have the time information.
 * 
 *
 * @author Tomas Prochazka 28.2.2016
 */
public class TopLevelSeriesGroupWrapper extends SeriesGroupWrapper {

    private final TimeStampArray timeStamps;

    /**
     * Creates a new group which joins multiple series of values together.
     *
     * @param name Name of the group
     * @param timeStamps Array of time stamps associated with the values of
     * series subsumed into this group
     */
    protected TopLevelSeriesGroupWrapper(String name, TimeStampArray timeStamps) {
        super(name);
        this.timeStamps = timeStamps;
    }

    @Override
    public TimeStampArray getTimeStamps() {
        return timeStamps;
    }

    @Override
    public ValueWrapper getValue(long time, TimeStampType timeType) {
        ValuesGroupWrapper groupWrapper = createValuesGroupWrapper(time, timeType);
        addChildrenToValuesGroup(time, timeType, groupWrapper);
        return groupWrapper;
    }

    /**
     * Creates a default group for storing the values of children. If an
     * inheritor wants to store the values in some more specific subclass of
     * ValuesGroupWrapper, this method can be overridden.
     *
     * @param time
     * @param timeType
     * @return
     */
    protected ValuesGroupWrapper createValuesGroupWrapper(long time, TimeStampType timeType) {
        ValuesGroupWrapper groupWrapper = ValuesGroupWrapper.create(name, timeStamps.getClosestTimeStamp(time, timeType));
        return groupWrapper;
    }

    protected void addChildrenToValuesGroup(long time, TimeStampType timeType, ValuesGroupWrapper groupWrapper) {
        for (SeriesWrapper seriesWrapper : getChildren()) {
            ValueWrapper value = seriesWrapper.getValue(time, timeType);
            if (value != null) {
                groupWrapper.addChild(value);
            }
        }
    }

    @Override
    public Collection<ValueWrapper> getIntervalSummary(long t1, long t2, TimeStampType timeType) {
        final TimeInterval timeInterval = getTimeStamps().getTimeInterval(t1, t2, timeType);
        if (timeInterval == null) {
            return Collections.emptyList();
        }
        ValuesGroupWrapper groupWrapper = createValuesGroupWrapper(timeInterval);
        for (SeriesWrapper seriesWrapper : getChildren()) {
            Collection<ValueWrapper> intervalSummary = seriesWrapper.getIntervalSummary(t1, t2, timeType);
            for (ValueWrapper valueWrapper : intervalSummary) {
                groupWrapper.addChild(valueWrapper);
            }
        }
        if (groupWrapper.getChildren().isEmpty()) {
            return Collections.emptyList();
        } else {
            int first = getTimeStamps().getFirstGreaterThanOrEqualIndex(t1, timeType);
            int last = getTimeStamps().getLastLessThanOrEqualIndex(t2, timeType);
            TimeToStringConverter converter = TimeToStringConverter.get();
            String time;
            Date d = new Date((t2 - t1) / 1_000_000); //to millis conversion
            DateFormat format;
            if (timeType == TimeStampType.TimeOfRecord) {
                format = converter.getRecordingTimeGraphFormat();
            } else {
                format = converter.getOnboardTimeGraphFormat();
            }
            if (getParent() == null) {
                groupWrapper.addChild(new StringValueWrapper("Length", timeInterval, format.format(d)));
                groupWrapper.addChild(new DoubleValueWrapper("Count", timeInterval, last - first + 1, "#"));
            }
            return Collections.singleton(groupWrapper);
        }

    }

    /**
     * Creates a default group for storing the values of children. If an
     * inheritor wants to store the values in some more specific subclass of
     * ValuesGroupWrapper, this method can be overridden.
     *
     * @param timeInterval
     * @return
     */
    protected ValuesGroupWrapper createValuesGroupWrapper(final TimeInterval timeInterval) {
        ValuesGroupWrapper groupWrapper = ValuesGroupWrapper.create(name, timeInterval);
        return groupWrapper;
    }

    /**
     * Searches the children quantities for a double quantity with a name which
     * contains the given substring.
     *
     * @param val lowercase substring
     * @return
     */
    protected @CheckForNull
    DoubleQuantity getChildWithNameContaining(String val) {
        for (SeriesWrapper x : getChildren()) {
            if (x.getName().toLowerCase().contains(val)) {
                if (x instanceof DoubleQuantity) {
                    return (DoubleQuantity) x;
                }
            }
        }
        return null;
    }

    /**
     * Searches the children quantities for a double quantity with a name such
     * that it contains one of the given substrings.
     *
     * @param substrings lowercase substring
     * @return
     */
    protected @CheckForNull
    DoubleQuantity getChildWithNameContaining(String... substrings) {
        for (SeriesWrapper x : getChildren()) {
            String lowercaseName = x.getName().toLowerCase();
            for (String val : substrings) {
                if (lowercaseName.contains(val)) {
                    if (x instanceof DoubleQuantity) {
                        return (DoubleQuantity) x;
                    }
                }
            }
        }
        return null;
    }

}
