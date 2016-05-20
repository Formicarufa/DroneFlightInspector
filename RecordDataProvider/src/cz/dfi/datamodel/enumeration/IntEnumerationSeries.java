/*
 */

package cz.dfi.datamodel.enumeration;

import cz.dfi.datamodel.TimeStampType;
import cz.dfi.datamodel.series.AbstractSingleSeriesWrapper;
import cz.dfi.datamodel.series.TimeStampArray;
import cz.dfi.datamodel.values.TimeStamp;
import cz.dfi.datamodel.values.ValueWrapper;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;


public class IntEnumerationSeries extends AbstractSingleSeriesWrapper {

    private final TimeStampArray timeStamps;
    private final int[] values;
    private final Map<Integer, String> valueNames;
    /**
     * 
     * @param name
     * @param timeStamps
     * @param valueNames A map which assigns a String name to each Integer value.
     */
    public IntEnumerationSeries(String name, TimeStampArray timeStamps, int[] values, Map<Integer,String> valueNames) {
        super(name);
        this.timeStamps=timeStamps;
        this.values = values;
        this.valueNames=valueNames;
    }
    
    @Override
    public ValueWrapper getValue(long time, TimeStampType timeType) {
        int i = getTimeStamps().getIndexOfClosest(time, timeType);
        if (i==-1) {
            return null;
        }
        TimeStamp t = getTimeStamps().getClosestTimeStamp(time, timeType);
        return new IntEnumValueWrapper(name, t, valueNames.get(values[i]));
    }

    @Override
    public Collection<ValueWrapper> getIntervalSummary(long t1, long t2, TimeStampType timeType) {
        return Collections.emptyList();
    }

    @Override
    public TimeStampArray getTimeStamps() {
        return timeStamps;
    }

}
