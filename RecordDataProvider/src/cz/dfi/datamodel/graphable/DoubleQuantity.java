/*
 */
package cz.dfi.datamodel.graphable;

import cz.dfi.datamodel.TimeStampType;
import cz.dfi.datamodel.series.SeriesWrapper;
import cz.dfi.datamodel.series.TimeStampArray;
import cz.dfi.datamodel.values.TimeInterval;
import cz.dfi.datamodel.values.TimeStamp;
import cz.dfi.datamodel.values.ValueWrapper;
import cz.dfi.datamodel.values.ValuesGroupWrapper;
import java.util.Collection;
import java.util.Collections;

/**
 *The most important {@link SeriesWrapper}. 
 * Double quantity is a {@link GraphableQuantity}, which contains a vector of doubles.
 * Almost all data recorded by drones can be stored as double quantities.
 * @author Tomas Prochazka 22.12.2015
 */
public class DoubleQuantity extends AbstractGraphableQuantity {

    protected double[] values;

    public DoubleQuantity(double[] values, String name, String unit, TimeStampArray timeStamps) {
        super(name, unit, timeStamps);
        this.values = values;
    }

    @Override
    public double[] getValuesAsDoubles() {
        return values;
    }

    /**
     * Has no children.
     *
     * @return null
     */
    @Override
    public Collection<SeriesWrapper> getChildren() {
        return null;
    }

    @Override
    public ValueWrapper getValue(long time, TimeStampType timeType) {
        int indexOfClosest = getTimeStamps().getIndexOfClosest(time, timeType);
        if (indexOfClosest == -1) {
            return null;
        }
        TimeStamp t = getTimeStamps().getClosestTimeStamp(time, timeType);
        return new DoubleValueWrapper(name, t, values[indexOfClosest], unit);
    }

    @Override
    public Collection<ValueWrapper> getIntervalSummary(long t1, long t2, TimeStampType timeType) {
        int first = getTimeStamps().getFirstGreaterThanOrEqualIndex(t1, timeType);
        int last = getTimeStamps().getLastLessThanOrEqualIndex(t2, timeType);
        if (last < first) {
            return Collections.emptyList();
        }
        final TimeInterval timeInterval = getTimeStamps().getTimeInterval(t1, t2, timeType);
        ValuesGroupWrapper group = ValuesGroupWrapper.create(name, timeInterval);
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        double sum = 0;
        for (int i = first; i <= last; i++) {
            double value = values[i];
            if (value < min) {
                min = value;
            }
            if (value > max) {
                max = value;
            }
            sum += value;
        }
        if (min != max) {
            double avg = sum / (last - first + 1);
            group.addChild(new DoubleValueWrapper("Average", timeInterval, avg, unit));
            group.addChild(new DoubleValueWrapper("Min", timeInterval, min, unit));
            group.addChild(new DoubleValueWrapper("Max", timeInterval, max, unit));
            return Collections.singleton(group);
        }
        return Collections.singleton(new DoubleValueWrapper(name, timeInterval, min, unit));

    }

}
