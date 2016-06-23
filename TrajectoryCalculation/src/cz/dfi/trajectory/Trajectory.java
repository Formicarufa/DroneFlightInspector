/*
 */
package cz.dfi.trajectory;

import cz.dfi.datamodel.TimeStampType;
import cz.dfi.datamodel.series.AbstractSingleSeriesWrapper;
import cz.dfi.datamodel.series.TimeStampArray;
import cz.dfi.datamodel.values.TimeStamp;
import cz.dfi.datamodel.values.ValueWrapper;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author Tomas Prochazka 13.6.2016
 */
public class Trajectory extends AbstractSingleSeriesWrapper {

    TimeStampArray timeStamps;
    double[] xValues;
    double[] yValues;

    public Trajectory(TimeStampArray timeStamps, double[] xValues, double[] yValues, String name) {
        super(name);
        this.timeStamps = timeStamps;
        this.xValues = xValues;
        this.yValues = yValues;
    }

    @Override
    public ValueWrapper getValue(long time, TimeStampType timeType) {
        int indexOfClosest = getTimeStamps().getIndexOfClosest(time, timeType);
        if (indexOfClosest == -1) {
            return null;
        }
        TimeStamp t = getTimeStamps().getClosestTimeStamp(time, timeType);
        return new TrajectoryPoint(name, t, xValues[indexOfClosest], yValues[indexOfClosest]);
    }

    @Override
    public Collection<ValueWrapper> getIntervalSummary(long t1, long t2, TimeStampType timeType) {
        return Collections.emptyList();
    }

    @Override
    public TimeStampArray getTimeStamps() {
        return timeStamps;
    }

    public double[] getxValues() {
        return xValues;
    }

    public double[] getyValues() {
        return yValues;
    }
    

}
