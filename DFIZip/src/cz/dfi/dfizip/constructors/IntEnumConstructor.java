/*
 */

package cz.dfi.dfizip.constructors;

import cz.dfi.datamodel.enumeration.IntEnumerationSeries;
import cz.dfi.datamodel.series.SeriesGroupWrapper;
import cz.dfi.datamodel.series.SeriesWrapper;
import cz.dfi.dfizip.readers.IntReader;
import cz.dfi.dfizip.readers.ValueReader;
import java.util.Map;


public class IntEnumConstructor implements SingleSeriesConstructor {
    IntReader r = new IntReader();
    private final String name;
    Map<Integer, String> valuesMap;

    public IntEnumConstructor(String name, Map<Integer, String> valuesMap) {
        this.name = name;
        this.valuesMap = valuesMap;
    }
    
    @Override
    public ValueReader getReader() {
        return r;
    }

    @Override
    public SeriesWrapper createSeries(SeriesGroupWrapper parentGroup) {
        return new IntEnumerationSeries(name, parentGroup.getTimeStamps(), r.getValues(), valuesMap);
    }

}
