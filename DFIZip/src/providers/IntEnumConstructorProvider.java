/*
 */

package providers;

import cz.dfi.dfizip.DfiZipImporter;
import cz.dfi.dfizip.constructors.IntEnumConstructor;
import cz.dfi.dfizip.constructors.SingleSeriesConstructor;
import java.util.HashMap;
import java.util.Map;
import org.openide.util.lookup.ServiceProvider;
import org.w3c.dom.Element;

@ServiceProvider(service = ConstructorProvider.class)
public class IntEnumConstructorProvider implements ConstructorProvider {

    @Override
    public String getNodeName() {
        return "intEnumeration";
    }

    @Override
    public SingleSeriesConstructor getConstructor(Element node) {
        String name = node.getAttribute("name");
        Map<Integer,String> labels= new HashMap<>();
        DfiZipImporter.nodeChildren(node).forEach(x->{
            Element el = (Element)x;
            assert(el.getNodeName().equals("option"));
            String val = el.getAttribute("value");
            String label = el.getAttribute("label");
            int v = Integer.parseInt(val);
            labels.put(v, label);
        });
        return new IntEnumConstructor(name, labels);
    }

}
