/*
 */

package providers;

import cz.dfi.dfizip.constructors.DoubleQuantityConstructor;
import org.openide.util.lookup.ServiceProvider;
import org.w3c.dom.Element;

@ServiceProvider(service = ConstructorProvider.class)
public class DoubleQuantityConstructorProvider implements ConstructorProvider {

    @Override
    public String getNodeName() {
        return "doubleQuantity";
    }

    @Override
    public DoubleQuantityConstructor getConstructor(Element node) {
        String name = node.getAttribute("name");
        String unit = node.getAttribute("unit");
        return new DoubleQuantityConstructor(name, unit);
    }

}
