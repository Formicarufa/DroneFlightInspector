/*
 */

package cz.dfi.dfizip.readers;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts the given string values into doubles, returns the values at once on
 * demand.
 * @author Tomas Prochazka
 * 12.5.2016
 */
public class DoubleReader implements  ValueReader{
    List<Double> values= new ArrayList<>();
    @Override
    public void read(String str) {
        values.add(Double.parseDouble(str));
    }

    @Override
    public double[] getValues() {
        return values.stream().mapToDouble(d->d).toArray();
    }

}
