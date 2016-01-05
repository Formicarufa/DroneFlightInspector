package cz.dfi.recorddataprovider;

import org.openide.util.lookup.InstanceContent;

/**
 *Interface of a service, which is able to provide a current file's lookup content,
 * so that objects can be added to it or removed from it by other parts of the code.
 * A singleton instance of the implementation of this interface 
 * is available as a service through the application's global lookup.
 * @author Tomas Prochazka
 */
public interface CurrentFileLookupManager {
    /**
     * Gets the lookup content of the currently selected file.
     * @return Lookup content of the file which is selected.
     */
        InstanceContent getCurrentFileLookupContent();
    
}
