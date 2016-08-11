/*
 */
package cz.dfi.graphs;

import org.jfree.data.DomainOrder;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.xy.AbstractXYDataset;

/**
 * Stores multiple series of values.
 * When passing the values to the graphing component,
 * the time values are converted from the nanoseconds (DFI convention) to milliseconds.
 * @author Tomas Prochazka 15.5.2016
 */
public class MultipleValuesDataset extends AbstractXYDataset {

    private static final long serialVersionUID = 1L;

    long[] timeValues = new long[0];
    double[][] values = new double[0][];
    String[] keys = new String[0];

    @Override
    public int getSeriesCount() {
        return values.length;
    }

    @Override
    public String getSeriesKey(int series) {
        return keys[series];
    }

    @Override
    public int getItemCount(int series) {
        return timeValues.length;
    }

    @Override
    public Number getX(int series, int item) {
        final long t = timeValues[item];
        final double res = t / 1_000_000.0; // time is converted to millis
        return res;
    }

    @Override
    public Number getY(int series, int item) {
        return values[series][item];
    }

    @Override
    public DomainOrder getDomainOrder() {
        return DomainOrder.ASCENDING;
    }

    public void valuesChanged(long[] timeValues, double[][] values, String[] keys) {

        this.timeValues = timeValues;
        this.values = values;
        this.keys = keys;
        if (timeValues== null) {
            clear();
        }
        notifyListeners(new DatasetChangeEvent(this, this));
    }

    public void clear() {
        timeValues = new long[0];
        values = new double[0][];
        keys = new String[0];
    }
}
