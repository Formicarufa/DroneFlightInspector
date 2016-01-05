/*
 */

package cz.dfi.recorddataprovider;

/**
 * Provides information about the data file.
 *  Propagates the information, that the file was selected or closed.
 * @author Tomas Prochazka
 * 29.9.2015
 */
public interface DataFileInfo {

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
    void addFileStateChangedListener(FileStateChangedListener l);

    /**
     * Returns the unique identification number of the file.
     * @return 
     */
    int getId();
    /**
     * Removes the given listener from the collection of objects,
     * that are notified if the file is selected or closed.
     * If the listener was not registered, nothing will happen.
     * @param l Object that will be removed from the listeners collection.
     */
    void removeFileStateChangedListener(FileStateChangedListener l);

}
