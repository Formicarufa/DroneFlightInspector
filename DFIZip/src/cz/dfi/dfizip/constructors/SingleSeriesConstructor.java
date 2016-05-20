/*
 */

package cz.dfi.dfizip.constructors;

import cz.dfi.dfizip.readers.ValueReader;

/**
 *
 * @author Tomas Prochazka
 * 12.5.2016
 */
public interface SingleSeriesConstructor extends SeriesConstructor {

    /**
     * Gets the reader which should be used to load the values from file.
     * @return
     */
    ValueReader getReader();
    

}
