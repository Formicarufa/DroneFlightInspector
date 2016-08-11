/*
 */

package cz.dfi.datamodel;

import cz.dfi.datamodel.series.SeriesWrapper;
import java.util.Collection;
import org.openide.util.lookup.InstanceContent;

/**
 * A storage for helper methods.
 * @author Tomas Prochazka
 * 15.5.2016
 */
public class ImportHelper {

    /**
     * This method adds the group and recursively all its children to the given lookup. 
     * If we are adding a group into the lookup, we usually want to place there
     * also all its children so that they can be directly found in lookup.
     * @param wrapper a group that should be added to the lookup
     * @param content the content of the lookup to which the group will be added
     */
    public static void addTreeToLookup(SeriesWrapper wrapper, InstanceContent content) {
        content.add(wrapper);
        final Collection<SeriesWrapper> children = wrapper.getChildren();
        if (children == null) {
            return;
        }
        for (SeriesWrapper child : children) {
            addTreeToLookup(child, content);
        }
    }

}
