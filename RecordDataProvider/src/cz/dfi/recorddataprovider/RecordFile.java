/*
 */

package cz.dfi.recorddataprovider;

import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;

/**
 * Provides information about the data file.
 *  Propagates the information, that the file was selected or closed.
 * @author Tomas Prochazka
 * 29.9.2015
 */
public abstract class RecordFile implements Lookup.Provider {

    /**
     * Adds the given listener to the collection of objects,
     * that are notified if the file is selected or closed.
     * Does not share the information that the data file was deselected.
     * When the file is deselected, another file is selected,
     * therefore this information can be obtained by listening
     * to other file's stateChangedListeners as well.
     * (Only exception is when the last file is closed.)
     * @param l Object which will be notified of the change
     */
    public abstract void addFileStateChangedListener(FileStateChangedListener l);

    /**
     * Returns the unique identification number of the file.
     * @return file unique identifier
     */
    public abstract int getId();
    /**
     * Removes the given listener from the collection of objects,
     * that are notified if the file is selected or closed.
     * If the listener was not registered, nothing will happen.
     * @param l Object that will be removed from the listeners collection.
     */
    public abstract void removeFileStateChangedListener(FileStateChangedListener l);
    /**
     * Gets the name of the file.
     * @return name of the file
     */
    public abstract String getName();
    /**
     * Gets the content of the lookup for insertion of new file-related data.
     * @return content of the file lookup
     */
    public abstract InstanceContent getLookupContent();
    /**
     * Gets the lookup that is associated with this record file.
     * All data related to this file should be stored inside it.
     * @return Lookup containing file data
     */
    @Override
    public abstract Lookup getLookup();
    

}
