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
public class SimpleTimeDataset extends AbstractXYDataset {

    private static final long serialVersionUID = 1L;

    long[] timeValues = new long[0];
    double[] values = new double[0];

    @Override
    public int getSeriesCount() {
        return 1;
    }

    @Override
    public Integer getSeriesKey(int series) {
        return 0;
    }

    @Override
    public int getItemCount(int series) {
        return values.length;
    }

    @Override
    public Number getX(int series, int item) {
        final long t = timeValues[item];
        final double res = t / 1_000_000.0; // time is converted to millis
        return res;
    }

    @Override
    public Number getY(int series, int item) {
        return values[item];
    }

    @Override
    public DomainOrder getDomainOrder() {
        return DomainOrder.ASCENDING;
    }

    public void resultChanged(long[] timeValues, double[] values) {

        this.timeValues = timeValues;
        this.values = values;
        if (timeValues == null) {
            clear();
        }
        notifyListeners(new DatasetChangeEvent(this, this));

    }

    private void clear() {
        timeValues = new long[0];
        values = new double[0];
    }
}
