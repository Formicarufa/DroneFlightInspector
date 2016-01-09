/*
 */
package cz.dfi.recorddataprovider;

import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;

/**
 * FileLookupProvider consists of few static methods, that
 * enable users to add content to the lookup of the 
 * selected file({@link #getCurrentFileLookupContent()}) and get information about the file.
 * 
 * @author Tomas Prochazka 6.1.2016
 */
public abstract class FileLookupProvider implements Lookup.Provider {

    /**
     * Gets the InstanceContent of the lookup of the currently selected 
     * file, through which the user can insert data to the lookup.
     * Inserted data will be available from FileLookup anytime the file is selected.
     * @return 
     */
    public abstract InstanceContent getCurrentFileLookupContent();
    /**
     * Gets the information about the selected file.
     * Through the returned objects it is possible to get 
     * to the name of the file as well as to its Lookup content
     * and keep reference to it even after another file gets selected.
     * @return 
     */
    public abstract RecordFile getCurrentFile();
    
        /**
     * Gets the InstanceContent of the lookup of the currently selected 
     * file, through which the user can insert data to the lookup.
     * Inserted data will be available from FileLookup anytime the file is selected.
     * @return 
     */
   public static InstanceContent getSelectedFileLookupContent() {
       return getDefault().getCurrentFileLookupContent();
   }
       /**
     * Gets the information about the selected file.
     * Through the returned objects it is possible to get 
     * to the name of the file as well as to its Lookup content
     * and keep reference to it even after another file gets selected.
     * @return 
     */
    public static RecordFile getSelectedFile() {
        return getDefault().getCurrentFile();
    }
    /**
     * Gets the lookup which provides access to the data of the 
     * currently selected file. 
     * When the selection changes
     * @return 
     */
    @Override
    public abstract Lookup getLookup();

    /**
     * Finds an instance of FileLookupProvider service in the
     * default lookup and returns it.
     * (There should always be an instance of {@link cz.dfi.filelookupprovider.DefaultFileLookupProvider} available.)
     * @return
     */
    protected static FileLookupProvider getDefault() {
        return Lookup.getDefault().lookup(FileLookupProvider.class);
    }
}
