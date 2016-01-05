/*
 */

package cz.dfi.rosbagimporter;

import cz.dfi.fileimporertinterface.FileImporter;

/**
 *This interface needs to be implemented in order to add a new way of loading ROS Bag files.
 * The implementing class also needs to be put into the lookup by annotating it with:
 * \@ServiceProvider(
                service = cz.dfi.rosbagimporter.RosbagImporter.class
        )
 * @author Tomas Prochazka
 * 20.11.2015
 */
public interface RosbagImporter extends FileImporter{
    
}
