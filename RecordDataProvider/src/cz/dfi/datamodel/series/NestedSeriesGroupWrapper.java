/*
 */

package cz.dfi.datamodel.series;

import cz.dfi.datamodel.TimeStampType;
import cz.dfi.datamodel.values.NestedValuesGroupWrapper;
import cz.dfi.datamodel.values.ValueWrapper;
import cz.dfi.datamodel.values.ValuesGroupWrapper;
import java.util.Collection;
import java.util.Collections;

/**
 * A group of series that has a parent group.
 * Use {@link #create(java.lang.String, cz.dfi.datamodel.series.SeriesGroupWrapper) }.
 * or {@link #create(java.lang.String, cz.dfi.datamodel.series.TimeStampArray)  to 
 * create a group.
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

    /**
     * Returns a collection containing one ValuesGroupWrapper which contains ValueWrappers
     * of children values. If children provide no values, returns an empty collection, instead.
     * Ensure that returned ValueWrapper is put into the parent ValuesGroupWrapper.
     * @param t1
     * @param t2
     * @param timeType
     * @return 1 element collection or an empty collection 
     */
    @Override
    public Collection<ValueWrapper> getIntervalSummary(long t1, long t2, TimeStampType timeType) {
        ValuesGroupWrapper groupWrapper = new NestedValuesGroupWrapper(name);
        for (SeriesWrapper seriesWrapper : getChildren()) {
            Collection<ValueWrapper> intervalSummary = seriesWrapper.getIntervalSummary(t1, t2, timeType);
            for (ValueWrapper valueWrapper : intervalSummary) {
                groupWrapper.addChild(valueWrapper);
            }
        }
        if (groupWrapper.getChildren().isEmpty()) {
            return Collections.emptyList();
        } 
        return Collections.singleton(groupWrapper);
    }



}
