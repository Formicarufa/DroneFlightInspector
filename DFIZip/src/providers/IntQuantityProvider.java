/*
 */
package providers;

import cz.dfi.dfizip.constructors.IntQuantityConstructor;
import cz.dfi.dfizip.constructors.SingleSeriesConstructor;
import org.openide.util.lookup.ServiceProvider;
import org.w3c.dom.Element;
/**
 * @see ConstructorProvider
 * @author Tomas Prochazka
 */
@ServiceProvider(service = ConstructorProvider.class)
public class IntQuantityProvider implements ConstructorProvider {

    @Override
    public String getNodeName() {
        return "intQuantity";
    }

    @Override
    public SingleSeriesConstructor getConstructor(Element node) {
        String name = node.getAttribute("name");
        String unit = node.getAttribute("unit");
        return new IntQuantityConstructor(name, unit);
    }

}
