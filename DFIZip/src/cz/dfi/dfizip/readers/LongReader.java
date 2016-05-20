/*
 */

package cz.dfi.dfizip.readers;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts the given string values to long integers, returns the values at once on
 * demand.
 *
 * @author Tomas Prochazka
 */
public class LongReader implements ValueReader {
    
    List<Long> values = new ArrayList<>();
    @Override
    
    public void read(String str) {
        long i = Long.parseLong(str);
        values.add(i);
    }

    @Override
    public long[] getValues() {
        return values.stream().mapToLong(i->i).toArray();
    }

}
