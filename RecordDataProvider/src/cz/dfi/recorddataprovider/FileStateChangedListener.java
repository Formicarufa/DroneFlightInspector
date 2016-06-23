/*
 */

package cz.dfi.recorddataprovider;

/**
 * Objects that want to register as listeners with the RecordFile
 have to implement this interface.
 * RecordFile calls fileClosed and fileSelected methods if the
 file that RecordFile represent was selected or closed.
 * @author Tomas Prochazka
 * 29.9.2015
 */
public interface FileStateChangedListener {
    /**
     * This method is called by RecordFile if the file it represents is closed.
     * @param recordFile RecordFile of the file that was closed
     */
    void fileClosed(RecordFile recordFile);
        /**
     * This method is called by RecordFile when the file is selected.
     * The components and extensions that use data from the file
     * usually DO NOT HAVE TO use this method. 
     * The change of the data can be detected by listening for a change of the required object
     * on the  lookup returned by {@link cz.dfi.recorddataprovider.FileLookup#getDefault() }.
     * @param recordFile  RecordFile of the file that was selected
     */
    default void fileSelected(RecordFile recordFile) {
        //Empty
    };
}
