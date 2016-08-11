/*
 */

package cz.dfi.recorddataprovider;

import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;

/**
 * Register a class implementing this interface as a ServiceProvider and
 * it will be called for every new opened file.
 * <p>
 * The callback can add new data to the lookup based on the data already present.
 * @author Tomas Prochazka
 * 16.6.2016
 */
public interface DataLoadedCallback {
    void dataLoaded(Lookup data, InstanceContent content);
}
