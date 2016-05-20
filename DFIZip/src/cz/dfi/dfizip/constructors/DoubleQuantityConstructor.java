/*
 */
package cz.dfi.dfizip.constructors;

import cz.dfi.datamodel.graphable.DoubleQuantity;
import cz.dfi.datamodel.series.SeriesGroupWrapper;
import cz.dfi.datamodel.series.SeriesWrapper;
import cz.dfi.dfizip.readers.DoubleReader;
import cz.dfi.dfizip.readers.ValueReader;
import cz.dfi.dfizip.special.SpecialDoubleQuantProvider;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.openide.util.Lookup;

public class DoubleQuantityConstructor implements SingleSeriesConstructor {

    DoubleReader reader = new DoubleReader();

    @Override
    public ValueReader getReader() {
        return reader;
    }
    String name;
    String unit;

    public DoubleQuantityConstructor(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

    @Override
    public SeriesWrapper createSeries(SeriesGroupWrapper parentGroup) {
        DoubleQuantity result = getSpecialQuantity(parentGroup);
        if (result == null) {
            result = new DoubleQuantity(reader.getValues(), name, unit, parentGroup.getTimeStamps());
        }
        return result;
    }

    public  DoubleQuantity getSpecialQuantity(SeriesGroupWrapper parentGroup) {
        DoubleQuantity result = null;
        SpecialDoubleQuantProvider p = DoubleQuantityConstructor.getSpecialQuantities().get(name);
        if (p != null) {
            result = p.getQuantity(unit, reader.getValues(), parentGroup.getTimeStamps());
        }
        return result;
    }

    private static Map<String, SpecialDoubleQuantProvider> specialQuantities = null;

    public static Map<String, SpecialDoubleQuantProvider> getSpecialQuantities() {
        if (specialQuantities == null) {
            specialQuantities = new HashMap<>();
            Collection<? extends SpecialDoubleQuantProvider> provs
                    = Lookup.getDefault().lookupAll(SpecialDoubleQuantProvider.class);
            for (SpecialDoubleQuantProvider prov : provs) {
                specialQuantities.put(prov.getName(), prov);
            }
        }
        return specialQuantities;
    }

}
