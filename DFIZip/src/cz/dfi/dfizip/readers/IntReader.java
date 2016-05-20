/*
 */
package cz.dfi.dfizip.readers;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts the given string values to integers, returns the values at once on
 * demand.
 *
 * @author Tomas Prochazka
 */
public class IntReader implements ValueReader {

    List<Integer> values = new ArrayList<>();

    @Override
    public void read(String str) {
        int i = Integer.parseInt(str);
        values.add(i);
    }

    @Override
    public int[] getValues() {
        return values.stream().mapToInt(i->i).toArray();
    }
    public double[] getValuesAsDoubles() {
        return values.stream().mapToDouble(i->i).toArray();
    }

}
