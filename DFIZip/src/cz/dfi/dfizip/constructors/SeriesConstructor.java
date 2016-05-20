/*
 */

package cz.dfi.dfizip.constructors;

import cz.dfi.datamodel.series.SeriesGroupWrapper;
import cz.dfi.datamodel.series.SeriesWrapper;
import org.netbeans.api.annotations.common.NullAllowed;

/**
 *A class defining this interface is able to construct a Series (either a group or 
 * a series of values)
 * @author Tomas Prochazka
 * 5.5.2016
 */
public interface SeriesConstructor{
    SeriesWrapper createSeries(@NullAllowed SeriesGroupWrapper parentGroup);
    
}