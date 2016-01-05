/*
 */

package cz.dfi.rosbagsupport;

import cz.dfi.rosbagimporter.RosbagImporter;
import cz.dfi.fileimporertinterface.FileImporter;
import java.io.File;
import java.util.Collection;
import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Tomas Prochazka
 * 20.11.2015
 */
@ServiceProvider(service = cz.dfi.fileimporertinterface.FileImporter.class)
public class RosBagGenericImporter  implements FileImporter{

    @Override
    public boolean loadRecords(File data,InstanceContent content) {
        Collection<? extends RosbagImporter> rosbagImporters = Lookup.getDefault().lookupAll(RosbagImporter.class);
        for (RosbagImporter rosbagImporter : rosbagImporters) {
            if (rosbagImporter.loadRecords(data,content)) {
                return true;
            }
        }
        return false;
    }

}
