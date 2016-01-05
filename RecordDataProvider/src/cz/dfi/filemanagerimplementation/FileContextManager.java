package cz.dfi.filemanagerimplementation;

import cz.dfi.recorddataprovider.CurrentFileLookupManager;
import cz.dfi.recorddataprovider.CurrentFileLookupProvider;
import cz.dfi.recorddataprovider.DataFileInfo;
import cz.dfi.recorddataprovider.FileSelectionChangedListener;
import cz.dfi.recorddataprovider.FileStateChangedListener;
import cz.dfi.recorddataprovider.OpenedFilesManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.AbstractLookup.Content;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 * This service provides the data of the currently selected record file to the
 * modules which need it. There is always at most one file selected. If at least
 * one file opened, there is always a selected file. Selected file is the one of
 * which Time Selection Component had focus last time some time selection
 * component had focus.
 *
 * @author Tomas Prochazka 29.9.2015
 */
@ServiceProviders({
    @ServiceProvider(
            service = cz.dfi.recorddataprovider.CurrentFileLookupManager.class
    ),
    @ServiceProvider(
            service = cz.dfi.recorddataprovider.CurrentFileLookupProvider.class
    ),
    @ServiceProvider(
            service = cz.dfi.recorddataprovider.OpenedFilesManager.class
    )
})
public class FileContextManager implements CurrentFileLookupManager, CurrentFileLookupProvider, OpenedFilesManager, FileStateChangedListener {

    Map<DataFileInfo, LookupAndContent> lookups = new HashMap<>();
    List<FileSelectionChangedListener> selectedFileChangeListeners = new ArrayList<>();
    private Lookup currentLookup;
    private DataFileInfo currentDataFile;
    private InstanceContent currentLookupContent;
    private int filesCount;

    @Override
    public InstanceContent getCurrentFileLookupContent() {
        return currentLookupContent;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Lookup getCurrentFileLookup() {
        return currentLookup;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int getOpenedFilesCount() {
        return filesCount;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void fileSelected(DataFileInfo file) {
        if (currentDataFile != null && currentDataFile.getId() == file.getId()) {
            return;
        }
        LookupAndContent lac = lookups.get(file);
        currentDataFile = file;
        currentLookup = lac.lookup;
        currentLookupContent = lac.content;
        notifyFileSelectionChanged(file);

    }

    /**
     * {@inheritDoc }
     */
    @Override
    public InstanceContent newFileOpened(DataFileInfo file) {
        InstanceContent c = new InstanceContent();
        filesCount++;
        lookups.put(file, new LookupAndContent(new AbstractLookup(c), c));
        file.addFileStateChangedListener(this);
        return c;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void addFileSelectionChangedListener(FileSelectionChangedListener l) {
        selectedFileChangeListeners.add(l);
    }

    /**
     * {@inheritDoc }
     *
     */
    @Override
    public void removeFileSelectionChangedListener(FileSelectionChangedListener l) {
        selectedFileChangeListeners.remove(l);
    }

    /**
     * Invokes the selectedFileChanged methods of all observers of the file
     * selection change event.
     */
    private void notifyFileSelectionChanged(DataFileInfo f) {
        selectedFileChangeListeners.stream().forEach((selectedFileChangeListener) -> {
            selectedFileChangeListener.selectedFileChanged(currentLookup,f);
        });
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void fileClosed(DataFileInfo dataFile) {
        lookups.remove(dataFile);
        dataFile.removeFileStateChangedListener(this);
        filesCount--;
        if (filesCount == 0) {
            currentDataFile = null;
            currentLookup = null;
            currentLookupContent = null;
            notifyFileSelectionChanged(null);
        }
        // else {some new file opens and rewrites "current..." fields.}

    }

    @Override
    public DataFileInfo getCurrentFileInfo() {
        return currentDataFile;
    }

    /**
     * A wrapper for a hash map value. Stores the lookup and its content
     * together.
     */
    private static class LookupAndContent {

        private final Lookup lookup;
        private final InstanceContent content;

        public LookupAndContent(Lookup lookup, InstanceContent content) {
            this.lookup = lookup;
            this.content = content;
        }

    }

}
