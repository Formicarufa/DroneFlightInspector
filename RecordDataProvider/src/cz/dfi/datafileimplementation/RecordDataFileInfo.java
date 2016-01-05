/*
 */
package cz.dfi.datafileimplementation;

import cz.dfi.recorddataprovider.DataFileInfo;
import cz.dfi.recorddataprovider.FileStateChangedListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the DataFileInfo interface.
 *
 * @author Tomas Prochazka 2.10.2015
 */
public class RecordDataFileInfo implements DataFileInfo {

    private static int idCounter = 0;
    private final int id;
    private final List<FileStateChangedListener> fileChangeListeners = new ArrayList<>();

    /**
     * {@inheritDoc }
     */
    @Override
    public void addFileStateChangedListener(FileStateChangedListener l) {
        fileChangeListeners.add(l);
    }

    public RecordDataFileInfo() {
        this.id = idCounter++;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void removeFileStateChangedListener(FileStateChangedListener l) {
        fileChangeListeners.remove(l);
    }

    private void notifyFileClosed() {
        List<FileStateChangedListener> copy = new ArrayList(fileChangeListeners);
        copy.stream().forEach((FileStateChangedListener fileClosedListener) -> {
            fileClosedListener.fileClosed(this);
        });
    }

    private void notifyFileSelected() {
            List<FileStateChangedListener> copy = new ArrayList(fileChangeListeners);
            copy.stream().forEach((FileStateChangedListener fileClosedListener) -> {
            fileClosedListener.fileSelected(this);
        });
    }
    /**
     * This method should be called by the owner of this FileInfo class instance,
     * when the file receives focus.
     */
    public void fileSelected(){
        notifyFileSelected();
    }
      /**
     * This method should be called by the owner of this FileInfo class,
     * when the file is closed.
     */
    public void fileClosed(){
        notifyFileClosed();
    }

    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RecordDataFileInfo other = (RecordDataFileInfo) obj;
        return this.id == other.id;
    }
   

}
