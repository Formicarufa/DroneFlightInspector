/*
 */
package cz.dfi.multiplegraphscomponent;

import cz.dfi.graphsselectioncomponent.GraphedQuantity;
import java.util.Collection;
import org.jfree.data.DomainOrder;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.xy.AbstractXYDataset;

/**
 * Dataset that applies transformations on  numbers before passing them
 * to the graph drawing library.
 * If the set of quantities that should be graphed changes, this dataset
 * has to be notified by calling the {@link #resultChanged(java.util.Collection) }
 * @author Tomas Prochazka 23.12.2015
 */
public class MultipleModifiableGraphsDataset extends AbstractXYDataset {

    private static final long serialVersionUID = 1L;

    GraphedQuantity[] quantities;
    long[][] timeValues;
    double[][] values;
 
    private int seriesCount;
    

    public MultipleModifiableGraphsDataset() {
        

    }

    @Override
    public int getSeriesCount() {
        return seriesCount;
    }

    @Override
    public String getSeriesKey(int series) {
        return quantities[series].getQuantityName();
    }

    @Override
    public int getItemCount(int series) {
        return timeValues[series].length;
    }

    @Override
    public Number getX(int series, int item) {
        GraphedQuantity q = quantities[series];
        final long[] t = timeValues[series];
        final long t0 = t[0];
        final double res = (t0+(t[item]-t0) * q.getScaleX())/1_000_000 + q.getTranslationX(); // time is converted to millis
        return res;
    }

    @Override
    public Number getY(int series, int item) {
        GraphedQuantity q = quantities[series];
        return values[series][item]*q.getScaleY()+q.getTranslationY();
    }

    @Override
    public DomainOrder getDomainOrder() {
        return DomainOrder.ASCENDING;
    }

    public void resultChanged( Collection<? extends GraphedQuantity> quantitiesList) {
        seriesCount = quantitiesList.size();
        quantities = new GraphedQuantity[seriesCount];
        timeValues = new long[seriesCount][];
        values = new double[seriesCount][];
        int i = 0;
        for (GraphedQuantity q : quantitiesList) {
            quantities[i] = q;
            timeValues[i] = q.quantity.getTimeStamps().getRecorderValues();
            values[i] = q.quantity.getValuesAsDoubles();
            i++;
        }
        notifyListeners(new DatasetChangeEvent(this, this));

    }
    public void graphPropertyChanged() {
        notifyListeners(new DatasetChangeEvent(this,this));
    }

}
