package cz.dfi.recorddataprovider;

import org.openide.util.Lookup;

/**
 * Interface of the opened files management service. New files are registered by
 * the manager when they are opened using this interface. The manager also
 * shares the number of opened files. singleton instance of an implementation of
 * this interface is available as a service through the application's global
 * lookup. (And can be obtained by calling the {@link #getDefault() } static
 * method.)
 *
 * @author Tomas Prochazka
 */
public interface OpenedFilesManager {

    /**
     * Gets the number of files, that were registered by calling the
     * newFileOpened method and haven't been closed.
     *
     * @return the number of currently opened files.
     */
    int getOpenedFilesCount();

    /**
     * Informs the manager that a new file has been opened. Class with
     * information about file, Lookup and its InstanceContent (that needs to be
     * filled with data) is returned.
     *
     * @param name name of the file
     * @return representation of the new file
     */
    RecordFile newFileOpened(String name);

    /**
     * Gets the default opened files manager implementation.
     *
     * @return A class responsible for registering newly opened files
     */
    static OpenedFilesManager getDefault() {
        return Lookup.getDefault().lookup(OpenedFilesManager.class);
    }

    /**
     * Informs the OpenedFiles manager that the given file has been selected.
     *
     * @param file record file that has been selected
     */
    void fileSelected(RecordFile file);

    /**
     * Informs the OpenedFiles manager that the given file has been closed.
     *
     * @param file record file that has been closed
     */
    void fileClosed(RecordFile file);
}
