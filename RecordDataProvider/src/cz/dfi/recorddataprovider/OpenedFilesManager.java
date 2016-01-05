package cz.dfi.recorddataprovider;

import org.openide.util.lookup.InstanceContent;

/**
 * Interface of the opened files management service.
 * New files register by the manager when they are opened by using this interface.
 * The manager also shares the number of opened files.
 * singleton instance of the implementation of this interface 
 * is available as a service through the application's global lookup.
 * @author Tomas Prochazka
 */
public interface OpenedFilesManager {
    /**
     * Gets the number of files, that were registered by calling the newFileOpened method
     * and haven't been closed.
     * @return the number of currently opened files.
     */
    int getOpenedFilesCount();
    /**
     * Informs the manager that a new file was opened.
     * @param file information about the newly opened file
     * @return The content of the lookup of the new file to be filled with data
     */
    InstanceContent newFileOpened(DataFileInfo file);
}
