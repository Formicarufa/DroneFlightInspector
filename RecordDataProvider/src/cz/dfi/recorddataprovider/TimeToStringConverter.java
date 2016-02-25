/*
 */

package cz.dfi.recorddataprovider;

import cz.dfi.recorddataprovider.FileLookup;
import java.text.DateFormat;
import org.netbeans.api.annotations.common.NonNull;

/**
 *Defines the way how time (represented as a long integer : number of nanoseconds) is converted to string
 * and displayed to the user. The time might be saved as a number of nanoseconds since 1970 or 
 * it might be just number of nanoseconds since boot of the drone / start of the recording.
 * If the user wants to redefine the way how these numbers are interpreted he/she should
 * implement this interface, create an instance of the implementing class and put it into the file lookup.
 * (default is: number of nanoseconds since 1970 for both onboard time and recording time)
 * @author Tomas Prochazka
 * 25.2.2016
 */
public interface TimeToStringConverter {
       DateFormat getRecordingTimeGraphFormat();
       /**
        * Converts time so that it can be read by user.
        * @param time Time in nanoseconds
        * @return 
        */
       String getRecordingTimeFullString(long time);
       DateFormat getOnboardTimeGraphFormat();
       /**
        * Converts time so that it can be read by user.
        * @param time Time in nanoseconds
        * @return 
        */
       String getOnboardTimeFullString(long time);
       /**
        * Searches for an instance of TimeToStringConverter in the current file lookup and returns it.
        * If no instance is found, default converter is returned.
        * @return 
        */
       static @NonNull TimeToStringConverter get() {
           TimeToStringConverter converter = FileLookup.getDefault().lookup(TimeToStringConverter.class);
           return converter == null ? new DefaultTimeToStringConverter() : converter;
       }
}
