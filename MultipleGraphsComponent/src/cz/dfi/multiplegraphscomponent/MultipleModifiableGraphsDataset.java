/*
 */
package cz.dfi.multiplegraphscomponent;

import cz.dfi.graphsselectioncomponent.GraphedQuantity;
import cz.dfi.recorddataprovider.FileLookup;
import java.util.Collection;
import org.jfree.data.DomainOrder;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.xy.AbstractXYDataset;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 *
 * @author Tomas Prochazka 23.12.2015
 */
public class MultipleModifiableGraphsDataset extends AbstractXYDataset {

    GraphedQuantity[] quantities;
    double[][] timeValues;
    double[][] values;
 
    private int seriesCount;
    

    public MultipleModifiableGraphsDataset() {
        

    }

    @Override
    public int getSeriesCount() {
        return seriesCount;
    }

    @Override
    public Comparable getSeriesKey(int series) {
        return quantities[series].getQuantityName();
    }

    @Override
    public int getItemCount(int series) {
        return timeValues[series].length;
    }

    @Override
    public Number getX(int series, int item) {
        GraphedQuantity q = quantities[series];
        final double[] t = timeValues[series];
        final double t0 = t[0];
        return t0+(t[item]-t0) * q.getScaleX() + q.getTranslationX();
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
        timeValues = new double[seriesCount][];
        values = new double[seriesCount][];
        int i = 0;
        for (GraphedQuantity q : quantitiesList) {
            quantities[i] = q;
            //TODO: change if time on board is required
            timeValues[i] = q.quantity.getTimeOfRecordValues();
            values[i] = q.quantity.getValuesAsDoubles();
            i++;
        }
        notifyListeners(new DatasetChangeEvent(this, this));

    }

}
