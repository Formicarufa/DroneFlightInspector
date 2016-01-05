/*
 */

package cz.dfi.recorddataprovider.caching;

import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.api.annotations.common.NullAllowed;
import org.openide.util.Lookup;

/**
 * Interface of the class which uses CachedDataProvider to store
 * data for each of the opened files.
 * @author Tomas Prochazka
 * 22.11.2015
 * @param <T>
 */
public interface CachedDataReceiver<T> {
    /**
     * Creates data for the current file based on its context.
     * This method is called by CachedDataProvider when a new file opens.
     * CachedDataProvider then stores the data and keeps them saved even when
     * another file gets selected. After "the current file" is opened again, the CachedDataProvider passes the data
     *  back to this instance by calling "setCurrentData".
     * @param currentContext 
     * @return 
     */
    @CheckForNull T getDataForStoring(Lookup currentContext);
    /**
     * Passes the cached data of the currently opened file to this instance.
     * This method is called by CachedDataProvider when some file is selected
     * and there are cached data for that file stored in its memory.
     * @param data null iff no file is opened
     */
    void setCurrentData(@NullAllowed T data);
}
