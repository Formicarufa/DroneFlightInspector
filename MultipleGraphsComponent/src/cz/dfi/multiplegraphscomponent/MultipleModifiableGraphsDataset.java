/*
 */

package cz.dfi.multiplegraphscomponent;

import org.jfree.data.DomainOrder;
import org.jfree.data.xy.AbstractXYDataset;
import org.openide.util.Lookup;

/**
 *
 * @author Tomas Prochazka
 * 23.12.2015
 */
public class MultipleModifiableGraphsDataset extends AbstractXYDataset{

    Lookup l;

    public MultipleModifiableGraphsDataset(Lookup l) {
        this.l = l;
    }
    
    
    @Override
    public int getSeriesCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Comparable getSeriesKey(int series) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getItemCount(int series) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Number getX(int series, int item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Number getY(int series, int item) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DomainOrder getDomainOrder() {
        return DomainOrder.ASCENDING;
    }
    
}
