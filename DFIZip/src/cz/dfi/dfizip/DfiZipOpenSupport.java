/*
 */

package cz.dfi.dfizip;

import cz.dfi.timecomponent.TimeSelectionComponent;
import org.openide.cookies.OpenCookie;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.OpenSupport;
import org.openide.windows.CloneableTopComponent;

/**
 *
 * @author Tomas Prochazka
 * 4.5.2016
 */
class DfiZipOpenSupport extends OpenSupport implements OpenCookie{

    public DfiZipOpenSupport(MultiDataObject.Entry primaryEntry) {
        super(primaryEntry);
    }

    @Override
    protected CloneableTopComponent createCloneableTopComponent() {
        MultiDataObject o = entry.getDataObject();
        TimeSelectionComponent window = new TimeSelectionComponent(o,new DfiZipImporter());
        window.setDisplayName(o.getName());
        return window;
    }

}
