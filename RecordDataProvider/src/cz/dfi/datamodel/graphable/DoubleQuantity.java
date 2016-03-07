/*
 */

package cz.dfi.datamodel.graphable;

import cz.dfi.datamodel.TimeStampType;
import cz.dfi.datamodel.series.SeriesWrapper;
import cz.dfi.datamodel.series.TimeStampArray;
import cz.dfi.datamodel.values.TimeStamp;
import cz.dfi.datamodel.values.ValueWrapper;
import java.util.Collection;

/**
 *
 * @author Tomas Prochazka
 * 22.12.2015
 */
public class DoubleQuantity  extends AbstractGraphableQuantity{
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
     * @return null
     */
    @Override
    public Collection<SeriesWrapper> getChildren() {
        return null;
    }

    @Override
    public ValueWrapper getValue(long time, TimeStampType timeType) {
        int indexOfClosest = getTimeStamps().getIndexOfClosest(time, timeType);
        if (indexOfClosest==-1) {
            return null;
        }
        TimeStamp t = getTimeStamps().getClosestTimeStamp(time, timeType);
        return new DoubleValueWrapper(name, t, values[indexOfClosest]);
    }

}
