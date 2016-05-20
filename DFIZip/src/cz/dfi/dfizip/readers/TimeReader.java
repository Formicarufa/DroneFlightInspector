/*
 */

package cz.dfi.dfizip.readers;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Reads the values as long integers and converts them
 * to nanoseconds since epoch.
 * @author Tomas Prochazka
 */
public class TimeReader implements ValueReader {
    LongReader reader = new LongReader();
    int mult=1;

    public TimeReader(String unit) {
        switch(unit) {
            case "nanoseconds":
                break;
            case "microseconds":
                mult=1_000;
                break;
            case "milliseconds":
                mult=1_000_000;
                break;
            case "seconds":
                mult = 1_000_000_000;
                break;
            default:
                Logger.getLogger(TimeReader.class.getName()).log(Level.INFO, "Timestamps have unknown unit.");
                break;
        }
    }
    
    
    @Override
    public void read(String str) {
        reader.read(str);
    }
    
    @Override
    public long[] getValues() {
        long[] values = reader.getValues();
        for (int i = 0; i < values.length; i++) {
            values[i]=values[i]*mult;
        }
        return values;
    }

}
