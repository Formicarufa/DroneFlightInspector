/*
 */
package cz.dfi.filelookupprovider;

import cz.dfi.recorddataprovider.FileLookup;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.openide.util.ContextGlobalProvider;
import org.openide.util.Lookup;
import org.openide.util.Utilities;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.windows.TopComponent;

/**
 * Replaces the original ContextGlobalProvider.
 * <p>
 * The reason of the replacement is that we want to have all data from the
 * currently selected file accessible from the global context lookup.
 * (Global context lookup is obtained by calling the method {@link Utilities#actionsGlobalContext() }).
 * </p>
 * <p>
 * Despite being able to access  data of the selected file
 * through {@link FileLookup#getDefault() )
 * it's also handy to have them in the global action context because that enables us
 * to create actions that are bound to the currently selected file.
 * (With the original implementation, we could only create actions that are tied
 * to the currently selected TopComponent.)
 * </p>
 * <p>
 * Implementation remarks:
 * See <a href="http://wiki.netbeans.org/DevFaqAddGlobalContext"> DevFaqAddGlobalContext</a>
 * 
 * @author Tomas Prochazka 22.6.2016
 */
@ServiceProvider(
        service = ContextGlobalProvider.class,
        position = Integer.MAX_VALUE-50 // we want to precede the default implementation.
)
public class FileGlobalContextProvider implements ContextGlobalProvider, Lookup.Provider, PropertyChangeListener {

    Lookup fileLookup = FileLookup.getDefault();
    TopComponent.Registry registry = TopComponent.getRegistry();
    Lookup currentGlobal;
    public FileGlobalContextProvider() {
        registry.addPropertyChangeListener(this);
        setCurrentGlobalLookup();
    }

    @Override
    public Lookup createGlobalContext() {
        return Lookups.proxy(this);
    }

    @Override
    public Lookup getLookup() {
        return currentGlobal;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (TopComponent.Registry.PROP_ACTIVATED.equals(evt.getPropertyName())) {
            setCurrentGlobalLookup();
            org.openide.util.Utilities.actionsGlobalContext().lookup(javax.swing.ActionMap.class);
        }
    }

    private void setCurrentGlobalLookup() {
        TopComponent activated = registry.getActivated();
        if (activated== null) {
            currentGlobal=fileLookup;
        } else {
            currentGlobal = new ProxyLookup(fileLookup,activated.getLookup());
        }
    }
}
