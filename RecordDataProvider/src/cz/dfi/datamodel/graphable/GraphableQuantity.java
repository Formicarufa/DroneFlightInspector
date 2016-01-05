/*
 */

package cz.dfi.datamodel.graphable;

import cz.dfi.datamodel.RecordsWrapper;

/**
 *Represents a set of records that can be plotted on a graph.
 * If an instance of a class implementing this interface is
 * put to the Lookup, it  appears in the list of quantities that 
 * can be plotted on the Multiple Graphs Window.
 * (Another requirement is that the {@link RecordsWrapper#getOnBoardTimeValues() 
 * method does not return null.)
 * @author Tomas Prochazka
 * 19.12.2015
 */
public interface GraphableQuantity extends RecordsWrapper {
    /**
     * Values to be plotted.
     * Order of the items must be such that the n-th item
     * in the array corresponds to the n-th item in the array returned by {@link RecordsWrapper#getOnBoardTimeValues() }.
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
