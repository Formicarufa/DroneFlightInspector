/*
 */

package cz.dfi.datamodel;

import cz.dfi.datamodel.series.SeriesWrapper;
import java.util.Collection;
import org.openide.util.lookup.InstanceContent;

/**
 * 
 * @author Tomas Prochazka
 * 15.5.2016
 */
public class ImportHelper {

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
