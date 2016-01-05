/*
 */

package cz.dfi.recorddataprovider;

/**
 * Objects that want to register as listeners with the DataFileInfo
 * have to implement this interface.
 * DataFileInfo calls fileClosed and fileSelected methods if the
 * file that DataFileInfo represent was selected or closed.
 * @author Tomas Prochazka
 * 29.9.2015
 */
public interface FileStateChangedListener {
    /**
     * This method is called by DataFileInfo if the file it represents is closed.
     * @param dataFile DataFileInfo of the file that was closed
     */
    void fileClosed(DataFileInfo dataFile);
        /**
     * This method is called by DataFileInfo if the file it represents is selected.
     * @param dataFile  DataFileInfo of the file that was selected
     */
    default void fileSelected(DataFileInfo dataFile) {
        //Empty
    };
}
