/*
 */
package cz.dfi.trajectorycomp;

import org.jfree.data.DomainOrder;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.xy.AbstractXYDataset;

/**
 *
 * @author Tomas Prochazka 21.6.2016
 */
public class PointSeriesDataset extends AbstractXYDataset {

    private static final long serialVersionUID = 1L;

    double[][] seriesXVals = new double[0][];
    double[][] seriesYVals = new double[0][];
    String[] keys = new String[0];

    /**
     *
     * @param seriesXValues array of series - 1 series = 1 array of x values
     * @param seriesYValues array of series - 1 series = 1 array of y values
     * @param keys A name for each series
     */
    public void setSeries(double[][] seriesXValues, double[][] seriesYValues, String[] keys) {
        this.seriesXVals = seriesXValues;
        this.seriesYVals = seriesYValues;
        this.keys = keys;
        notifyListeners(new DatasetChangeEvent(this, this));
    }

    @Override
    public int getSeriesCount() {
        return seriesXVals.length;
    }

    @Override
    public String getSeriesKey(int series) {
        return keys[series];
    }

    @Override
    public int getItemCount(int series) {
        return seriesXVals[series].length;
    }

    @Override
    public Number getX(int series, int item) {
        return seriesXVals[series][item];
    }

    @Override
    public Number getY(int series, int item) {
        return seriesYVals[series][item];
    }

    @Override
    public DomainOrder getDomainOrder() {
        return DomainOrder.NONE;
    }
    public void clear() {
        seriesXVals = new double[0][];
        seriesYVals = new double[0][];
        keys = new String[0];
    }

}
