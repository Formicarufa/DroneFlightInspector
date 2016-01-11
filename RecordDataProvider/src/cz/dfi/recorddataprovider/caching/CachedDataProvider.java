/*
 */

package cz.dfi.recorddataprovider.caching;

import cz.dfi.recorddataprovider.FileLookup;
import cz.dfi.recorddataprovider.FileLookupProvider;
import cz.dfi.recorddataprovider.RecordFile;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * Cached data provider enables components to save/cache file specific data.
 * Whenever the file selection changes, the CachedDataProvider passes the
 * correct data to the component. If a new file is opened, the provider
 * first asks the component for the data that should be associated with this file. 
 * Once the file is closed, link to the data is removed.
 * @author Tomas Prochazka
 * 22.11.2015
 * @param <T> Type of the saved data.
 */
public class CachedDataProvider<T> implements LookupListener{

    private final Map<RecordFile,T> cachedData = new HashMap<>();
    private final CachedDataReceiver<T> receiver;
    private Lookup.Result<RecordFile> lookupResult;
    /**
     * Once the CachedDataProvider is instantiated, no other interaction
     * with it is required.
     * @param <T> Type of the saved data.
     * @param receiver Interface, that enables provider to get and provide the data. 
     * @return  
     */
    public  static  <T>  CachedDataProvider<T> create(CachedDataReceiver<T> receiver) {
        CachedDataProvider<T> c = new CachedDataProvider<>(receiver);
        RecordFile currentFileInfo = FileLookupProvider.getSelectedFile();
        Lookup context = FileLookup.getDefault();
        c.lookupResult = context.lookupResult(RecordFile.class);
        c.lookupResult.addLookupListener(c);
        c.selectedFileChanged(currentFileInfo);
        return c;
    }
    


    private CachedDataProvider(CachedDataReceiver<T> receiver)  {
        this.receiver=receiver;
    }
/**
 *  Using this method the cache provider is notified that the file selection
 * has changed.
 * @param file Information about the file itself (contains its ID)
 */
    public final void selectedFileChanged(RecordFile file) {
        if (file==null) { //iff no file is opened
            receiver.setCurrentData(null);
            return;
        }
        if (!cachedData.containsKey(file)) {
            T data = receiver.getDataForStoring(file.getLookup());
            cachedData.put(file, data);
            file.addFileStateChangedListener(cachedData::remove);
            
        }
        receiver.setCurrentData(cachedData.get(file));
    }
    public void deleteData() {
        for (RecordFile file : cachedData.keySet()) {
            file.removeFileStateChangedListener(cachedData::remove);
        }
        lookupResult.removeLookupListener(this);
        cachedData.clear();
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Collection<? extends RecordFile> allInstances = lookupResult.allInstances();
        Optional<? extends RecordFile> first = allInstances.stream().findFirst();
        selectedFileChanged(first.isPresent()? first.get() : null);
    }
    
    
}
