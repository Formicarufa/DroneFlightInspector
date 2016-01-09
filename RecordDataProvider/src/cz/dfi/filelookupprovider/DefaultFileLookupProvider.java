/*
 */
package cz.dfi.filelookupprovider;

import cz.dfi.recorddataprovider.FileLookupProvider;
import cz.dfi.recorddataprovider.OpenedFilesManager;
import cz.dfi.recorddataprovider.RecordFile;
import org.netbeans.api.annotations.common.CheckForNull;
import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(
        {
        @ServiceProvider(
                service = cz.dfi.recorddataprovider.FileLookupProvider.class)
        ,
         @ServiceProvider(
                service = cz.dfi.recorddataprovider.OpenedFilesManager.class)
        }
)
public class DefaultFileLookupProvider extends FileLookupProvider implements OpenedFilesManager{

    private final Lookup fileLookup;
    private RecordFile selectedFile;
    private final FileLookupProvider fileLookupProvider;
    private int filesCount=0;
    
    
   /**
     * {@inheritDoc }
     */
    public DefaultFileLookupProvider() {
        fileLookupProvider = new FileLookupProvider();
        fileLookup = Lookups.proxy(fileLookupProvider);
    }
   /**
     * {@inheritDoc }
     */
    @Override
    public @CheckForNull InstanceContent getCurrentFileLookupContent() {
        if (selectedFile==null) return null;
        return selectedFile.getLookupContent();
    }
   /**
     * {@inheritDoc }
     */
    @Override
    public @CheckForNull  RecordFile getCurrentFile() {
        return selectedFile;
    }
   /**
     * {@inheritDoc }
     */
    @Override
    public Lookup getLookup() {
        return fileLookup;
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
    public RecordFile newFileOpened(String name) {
        
        filesCount++;
        RecordFile f = new RecordDataFile(name);
        return f;
    }
   /**
     * {@inheritDoc }
     */
    @Override
    public void fileClosed(RecordFile recordFile) {
        filesCount--;
        if (filesCount == 0) {
            this.selectedFile = null;
        }
        ((RecordDataFile)recordFile).fileClosed();
    }

    /**
     *  {@inheritDoc }
     * @param recordFile 
     */
    @Override
    public void fileSelected(RecordFile recordFile) {
        if (!recordFile.equals(selectedFile)) {
            selectedFile=recordFile;
            fileLookup.lookup(Object.class); //Makes the lookup to notice that data have changed.
            ((RecordDataFile)recordFile).fileSelected();
        }
    }
    

    /**
     * FileLookupProvider class supplies ProxyLookup with the lookup of
     * the selected file. The returned value, of course, changes over time.
     */
    private class FileLookupProvider implements Lookup.Provider  {



        @Override
        public Lookup getLookup() {
            if (DefaultFileLookupProvider.this.selectedFile==null) return Lookup.EMPTY;
            Lookup currentLookup = DefaultFileLookupProvider.this.selectedFile.getLookup();
            return currentLookup != null ? currentLookup : Lookup.EMPTY;
        }

    }

}
