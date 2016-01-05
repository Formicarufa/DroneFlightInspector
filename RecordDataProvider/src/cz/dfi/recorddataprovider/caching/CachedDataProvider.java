/*
 */

package cz.dfi.recorddataprovider.caching;

import cz.dfi.recorddataprovider.CurrentFileLookupProvider;
import cz.dfi.recorddataprovider.DataFileInfo;
import cz.dfi.recorddataprovider.FileSelectionChangedListener;
import java.util.HashMap;
import java.util.Map;
import org.openide.util.Lookup;

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
public class CachedDataProvider<T> implements FileSelectionChangedListener{

    private final Map<DataFileInfo,T> cachedData = new HashMap<>();
    private final CachedDataReceiver<T> receiver;
    /**
     * Once the CachedDataProvider is instantiated, no other interaction
     * with it is required.
     * @param <T> Type of the saved data.
     * @param receiver Interface, that enables provider to get and provide the data. 
     * @return  
     */
    public  static  <T>  CachedDataProvider<T> create(CachedDataReceiver<T> receiver) {
        CachedDataProvider<T> c = new CachedDataProvider<>(receiver);
        CurrentFileLookupProvider fileProvider = getFileLookupProvider();
        DataFileInfo currentFileInfo = fileProvider.getCurrentFileInfo();
        Lookup context = fileProvider.getCurrentFileLookup();
        fileProvider.addFileSelectionChangedListener(c);
        c.selectedFileChanged(context, currentFileInfo);
        return c;
    }

    protected static CurrentFileLookupProvider getFileLookupProvider() {
        CurrentFileLookupProvider fileProvider = Lookup.getDefault().lookup(CurrentFileLookupProvider.class);
        return fileProvider;
    }
    
    private CachedDataProvider(CachedDataReceiver<T> receiver)  {
        this.receiver=receiver;
    }
/**
 *  Using this method the cache provider is notified that the file selection
 * has changed.
 * @param l Lookup containing the data context of this file.
 * @param file Information about the file itself (contains its ID)
 */
    @Override
    public final void selectedFileChanged(Lookup l, DataFileInfo file) {
        if (file==null) { //iff no file is opened
            receiver.setCurrentData(null);
            return;
        }
        if (!cachedData.containsKey(file)) {
            T data = receiver.getDataForStoring(l);
            cachedData.put(file, data);
            file.addFileStateChangedListener(cachedData::remove);
            
        }
        receiver.setCurrentData(cachedData.get(file));
    }
    public void deleteData() {
        for (DataFileInfo file : cachedData.keySet()) {
            file.removeFileStateChangedListener(cachedData::remove);
        }
        getFileLookupProvider().removeFileSelectionChangedListener(this);
        cachedData.clear();
    }
    
    
}
