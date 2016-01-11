/*
 */

package cz.dfi.recorddataprovider;

import org.openide.util.RequestProcessor;

/**
 * Source of the request processor to be used when opening files.
 * @author Tomas Prochazka
 * 10.1.2016
 */
public class FileLoadingRequestProcessor {
    /**
     * At most 2 data files are loaded at the same time.
     */
    private static final int MAX_THREADS = 2;

    private static RequestProcessor fileLoaderProcessor =
            new RequestProcessor("Request processor for opening data files.", MAX_THREADS);
    /**
     * Gets the request processor which is used for opening the data
     * files in the background.
     * 
     * @return 
     */
    public static RequestProcessor getDefault() {
        return fileLoaderProcessor;
    }
}
