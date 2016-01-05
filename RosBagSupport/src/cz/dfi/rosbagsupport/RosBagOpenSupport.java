/*
 */

package cz.dfi.rosbagsupport;

import cz.dfi.timecomponent.TimeSelectionComponent;
import org.openide.cookies.OpenCookie;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.OpenSupport;
import org.openide.windows.CloneableTopComponent;

/**
 *
 * @author Tomas Prochazka
 * 19.11.2015
 */
public class RosBagOpenSupport extends OpenSupport implements OpenCookie{

    public RosBagOpenSupport(MultiDataObject.Entry entry) {
        super(entry);
    }

    @Override
    protected CloneableTopComponent createCloneableTopComponent() {
        MultiDataObject obj=  entry.getDataObject();
        TimeSelectionComponent fileWindow = new TimeSelectionComponent(obj,new RosBagGenericImporter());
        fileWindow.setDisplayName(obj.getName());
        return fileWindow;
    }

}
