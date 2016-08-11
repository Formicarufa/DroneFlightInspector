/*
 */

package cz.dfi.dfizip.constructors;

import cz.dfi.dfizip.readers.ValueReader;

/**
 * Single series constructor creates a series based
 * on the values of its {@link ValueReader}
 * <p>
 * The importer code uses the method {@link #getReader()} to obtain the 
 * reader and feed the reader with data.
 * Then it calls the method {@link  #createSeries(cz.dfi.datamodel.series.SeriesGroupWrapper) }
 * which creates the series based on the content the {@link ValueReader} has read.
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
