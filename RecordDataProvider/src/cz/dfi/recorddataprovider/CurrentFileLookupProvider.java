
package cz.dfi.recorddataprovider;

import org.netbeans.api.annotations.common.CheckForNull;
import org.openide.util.Lookup;

/**
 * Provides a lookup of the currently selected file.
 * Notifies listeners, if the selected file is changed.
 *  A singleton instance of the implementation of this interface 
 * is available as a service through the application's global lookup.
 * @author Tomas Prochazka
 */
@Deprecated
public interface CurrentFileLookupProvider {
    /**
     * Returns the lookup of the selected file.
     * @return lookup of the selected file
     */
    @CheckForNull Lookup getCurrentFileLookup();
    /**
     * Returns information about the currently selected (focused) file.
     * @return 
     */
    @CheckForNull RecordFile getCurrentFileInfo();
    /**
     * Adds the given listener to the collection of objects,
     * that are notified if the selected file is changed.
     * @param l Object which will be notified of the change
     */
    void addFileSelectionChangedListener(FileSelectionChangedListener l);
    /**
     * Removes the given listener from the collection of objects,
     * that are notified if the selected file is changed.
     * Nothing will happen if the object was not present in the collection of listeners.
     * @param l Object that should be removed.
     */
    void removeFileSelectionChangedListener(FileSelectionChangedListener l);
    
}
