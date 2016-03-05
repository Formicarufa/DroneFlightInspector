/*
 */

package cz.dfi.datamodel.series;

import cz.dfi.datamodel.TimeStampType;
import cz.dfi.datamodel.values.NestedValuesGroupWrapper;
import cz.dfi.datamodel.values.ValueWrapper;
import cz.dfi.datamodel.values.ValuesGroupWrapper;
import java.util.Collection;

/**
 *
 * @author Tomas Prochazka
 * 28.2.2016
 */
public class NestedSeriesGroupWrapper extends SeriesGroupWrapper {

    public NestedSeriesGroupWrapper(String name) {
        super(name);
    }

    @Override
    public TimeStampArray getTimeStamps() {
        return getParent().getTimeStamps();
    }

    /**
     * Ensure that returned ValueWrapper is put into the parent ValueGroupWrapper!
     * @param time
     * @param timeType
     * @return 
     */
    @Override
    public ValueWrapper getValue(long time, TimeStampType timeType) {
         //Nested group needs to have parent. However, we do not know it, yet.
        ValuesGroupWrapper groupWrapper = new NestedValuesGroupWrapper(name);
        
        for (SeriesWrapper seriesWrapper : getChildren()) {
            ValueWrapper value = seriesWrapper.getValue(time, timeType);
            groupWrapper.addChild(value);
        }
        return groupWrapper;
    }

    @Override
    public Collection<ValueWrapper> getIntervalSummary(long t1, long t2, TimeStampType timeType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



}
