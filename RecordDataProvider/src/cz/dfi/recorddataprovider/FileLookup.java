/*
 */
package cz.dfi.recorddataprovider;

import org.openide.util.Lookup;

/**
 *This class provides an easy way to get to the lookup that contains the
 * content of the currently selected file
 * through the static method {@link #getDefault() }. 
 * @author Tomas Prochazka 5.1.2016
 */

public class FileLookup {

    /**
     * Gets the lookup which contains the data of the currently selected file.
     * 
     * @return Lookup which contains the file data.
     */
    public static Lookup getDefault() {
        return FileLookupProvider.getDefault().getLookup();
    }
  

  
    


}
