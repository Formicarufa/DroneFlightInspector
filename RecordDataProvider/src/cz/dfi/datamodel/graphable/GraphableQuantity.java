/*
 */

package cz.dfi.datamodel.graphable;

import cz.dfi.datamodel.series.SeriesWrapper;
import cz.dfi.datamodel.series.TimeSeries;

/**
 *Represents a set of records that can be plotted on a graph.
 * If an instance of a class implementing this interface is
 * put to the Lookup, it  appears in the list of quantities that 
 * can be plotted on the Multiple Graphs Window.
 * (Another requirement is that the {@link SeriesWrapper#getOnBoardTimeValues() 
 * method does not return null.)
 * @author Tomas Prochazka
 * 19.12.2015
 */
public interface GraphableQuantity extends TimeSeries {
    /**
     * Values to be plotted.
     * Order of the items must be such that the n-th item
     * in the array corresponds to the n-th item in the array returned by {@link SeriesWrapper#getOnBoardTimeValues() }.
     * 
     * @return 
     */
    double[] getValuesAsDoubles();
    /**
     * String used only for information of the user.
     * @return 
     */
    String getUnit();
  

}
