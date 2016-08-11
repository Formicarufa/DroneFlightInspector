/*
 */

package cz.dfi.recorddataprovider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Default way of converting time values (represented as a long integer -  number of nanoseconds since 1970) 
 * to string and displaying them to the user.
 * @author Tomas Prochazka
 * 25.2.2016
 */
public class DefaultTimeToStringConverter  implements TimeToStringConverter{

    final DateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");    
    final DateFormat full = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");

    public DefaultTimeToStringConverter() {
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override
    public DateFormat getRecordingTimeGraphFormat() {
        return format;
    }
    @Override
    public String getRecordingTimeFullString(long time) {
       long millis = time/1_000_000;
       Date d = new Date(millis);
       return full.format(d);
    }

    @Override
    public DateFormat getOnboardTimeGraphFormat() {
        return format;
    }

    @Override
    public String getOnboardTimeFullString(long time) {
       long millis = time/1_000_000;
       Date d = new Date(millis);
       return full.format(d);
    }
    
}
