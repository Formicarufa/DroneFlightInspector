/*
 */
package cz.dfi.dfizip.constructors;

import cz.dfi.datamodel.graphable.DoubleQuantity;
import cz.dfi.datamodel.series.SeriesGroupWrapper;
import cz.dfi.datamodel.series.SeriesWrapper;
import cz.dfi.dfizip.readers.IntReader;
import cz.dfi.dfizip.readers.ValueReader;
import cz.dfi.dfizip.special.SpecialDoubleQuantProvider;
/**
 * Creates a series of integer values.
 * See {@link SingleSeriesConstructor}
 * @author Tomas Prochazka
 */
public class IntQuantityConstructor implements SingleSeriesConstructor {

    IntReader reader = new IntReader();
    String name;
    String unit;

    public IntQuantityConstructor(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

    @Override
    public ValueReader getReader() {
        return reader;
    }

    @Override
    public SeriesWrapper createSeries(SeriesGroupWrapper parentGroup) {
        // Int quantity is stored as a double quantity.
        // (For graphing, the values would anyway have to be converted to doubles.) 
        DoubleQuantity result = getSpecialQuantity(parentGroup);
        if (result == null) {
            result = new DoubleQuantity(reader.getValuesAsDoubles(), name, unit, parentGroup.getTimeStamps());
        }
        return result;
    }

    public DoubleQuantity getSpecialQuantity(SeriesGroupWrapper parentGroup) {
        DoubleQuantity result = null;
        SpecialDoubleQuantProvider p = DoubleQuantityConstructor.getSpecialQuantities().get(name);
        if (p != null) {
            result = p.getQuantity(unit, reader.getValuesAsDoubles(), parentGroup.getTimeStamps());
        }
        return result;
    }

}
