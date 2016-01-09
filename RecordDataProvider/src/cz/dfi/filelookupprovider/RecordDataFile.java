/*
 */
package cz.dfi.filelookupprovider;

import cz.dfi.recorddataprovider.RecordFile;
import cz.dfi.recorddataprovider.FileStateChangedListener;
import java.util.ArrayList;
import java.util.List;
import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 * Class representing an opened record file.
 * It stores information about the file and
 * provides access to file lookup.
 * File lookup contains data loaded from the file and all other file-related data.
 *l
 * @author Tomas Prochazka 2.10.2015
 */
public class RecordDataFile extends RecordFile {

    private static int idCounter = 0;
    private final int id;
    private final List<FileStateChangedListener> fileChangeListeners = new ArrayList<>();
    private final String name;
    private final Lookup lookup;
    private final InstanceContent content;
    

    /**
     * Creates a representation of opened file.
     * @param name Name of the file
     */
    public RecordDataFile(String name) {
        this.id = idCounter++;
        this.name = name;
        content = new InstanceContent();
        lookup = new AbstractLookup(content);
        
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public void addFileStateChangedListener(FileStateChangedListener l) {
        fileChangeListeners.add(l);
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
        List<FileStateChangedListener> copy = new ArrayList<>(fileChangeListeners);
        copy.stream().forEach((FileStateChangedListener fileClosedListener) -> {
            fileClosedListener.fileClosed(this);
        });
    }

    private void notifyFileSelected() {
            List<FileStateChangedListener> copy = new ArrayList<>(fileChangeListeners);
            copy.stream().forEach((FileStateChangedListener fileClosedListener) -> {
            fileClosedListener.fileSelected(this);
        });
    }
    /**
     * Notifies the class that the file it represents has been selected.
     * The RecordFile propagates this information to the listeners.
     */
    protected void fileSelected(){
        notifyFileSelected();
    }
    /**
     * Notifies the class that the file it represents has been closed.
     * The RecordFile propagates this information to the listeners.
     */
    protected void fileClosed(){
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
        final RecordDataFile other = (RecordDataFile) obj;
        return this.id == other.id;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc }
     */
    
    @Override
    public Lookup getLookup() {
        return lookup;
    }
    /**
     * {@inheritDoc }
     */

    @Override
    public InstanceContent getLookupContent() {
        return content;
    }
   

}
