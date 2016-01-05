/*
 */

package cz.dfi.fileimporertinterface;

import java.io.File;
import org.openide.util.lookup.InstanceContent;

/**
 * 
 * @author Tomas Prochazka
 * 20.11.2015
 */
public interface FileImporter {
    /**
     * This method should decide whether this File Importer is able to load the given file and load it if it can.
     * If yes, it should load the data, otherwise it should return false.
     * 
     * @param data
     * @param content
     * @return true: loaded successfully, false: not supported
     */
    boolean loadRecords(File data, InstanceContent content);
    
    /**
     * This method is used to identify the importer.
     * The name of the importer of the opened file is saved when the 
     * application exits and according to it the correct importer is 
     * found to reload the content after the application is reopened.
     * Default implementation returns the class name.
     * (There is no need to override it.)
     * @return unique FileImporter name
     */
    default String getName(){
        return getClass().getName();
    }
}
