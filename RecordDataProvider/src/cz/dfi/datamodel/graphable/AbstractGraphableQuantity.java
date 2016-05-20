/*
 */
package cz.dfi.datamodel.graphable;

import cz.dfi.datamodel.TimeStampType;
import cz.dfi.datamodel.TimeValuesConverter;
import cz.dfi.datamodel.series.AbstractSingleSeriesWrapper;
import cz.dfi.datamodel.series.TimeStampArray;
import cz.dfi.datamodel.values.ValueWrapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author Tomas Prochazka 21.12.2015
 */
public abstract class AbstractGraphableQuantity extends AbstractSingleSeriesWrapper implements GraphableQuantity {
    protected String unit;
    private final TimeStampArray timeStamps;


    public AbstractGraphableQuantity(String name, String unit, TimeStampArray timeStamps) {
        super(name);
        this.unit=unit;
        this.timeStamps=timeStamps;
    }

    @Override
    public TimeStampArray getTimeStamps() {
        return timeStamps;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getUnit() {
        return unit;
    }

    @Override
    public Collection<ValueWrapper> getIntervalSummary(long t1, long t2, TimeStampType timeType) {
        return Collections.emptyList();
    }
    
    

}
