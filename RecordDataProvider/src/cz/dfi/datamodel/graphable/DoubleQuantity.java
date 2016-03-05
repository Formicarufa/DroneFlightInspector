/*
 */

package cz.dfi.datamodel.graphable;

import cz.dfi.datamodel.TimeStampType;
import cz.dfi.datamodel.series.SeriesWrapper;
import cz.dfi.datamodel.series.TimeStampArray;
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

    @Override
    public Collection<SeriesWrapper> getChildren() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ValueWrapper getValue(long time, TimeStampType timeType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
