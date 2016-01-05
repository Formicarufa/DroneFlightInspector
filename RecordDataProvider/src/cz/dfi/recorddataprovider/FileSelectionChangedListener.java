
package cz.dfi.recorddataprovider;

import org.netbeans.api.annotations.common.NullAllowed;
import org.openide.util.Lookup;

/**
 *Interface through the users of the CurrentFileLookupProvider are informed
 * that the selected file was changed.
 * There can be always only one selected file at a time,
 * therefore selecting one means deselecting the other.
 * Using this interface, objects can get data of the currently selected file.
 * All file data are stored in the lookup, which is passed as a parameter.
 * @author Tomas Prochazka
 */
public interface FileSelectionChangedListener {
    /**
     * This method is called by CurrentFileLookupProvider
     * if the file selection changed.
     * Lookup which contains all the selected file's data is passed.
     * @param l Lookup that contains the selected file's data, might be null.
     * @param file File that has been selected, might be null.
     */
    void selectedFileChanged(@NullAllowed Lookup l, @NullAllowed DataFileInfo file);
}
