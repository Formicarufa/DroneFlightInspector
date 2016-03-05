/*
 */

package cz.dfi.datamodel;

import cz.dfi.recorddataprovider.FileLookup;
import java.util.Collection;
import org.netbeans.api.annotations.common.CheckForNull;
import org.openide.util.Lookup;

/**
 *
 * @author Tomas Prochazka
 * 21.12.2015
 */
public interface TimeValuesConverter {
    /**
     * For the given message time stamp returns the estimate of time, when the
     * message was sent from/ received on the board of the drone.
     * Also: if the time format is different, the returned time 
     * is in the format of the drone's time.
     * (And input is expected in the time format of the computer/recorder machine)
     * @param time
     * @param outcoming
     * @return 
     */
    Long boardTimeToRecordTime(long time, boolean outcoming);
    
    default long[] onboardTimeToRecordTime(long[] times, boolean outcoming) {
        long[] res = new long[times.length];
        for (int i = 0; i < times.length; i++) {
            Long l = boardTimeToRecordTime(times[i], outcoming);
            if (l==null) return null;
            res[i]=l;
        }
        return res;
    }
    /**
     * For the given message time stamp which was added to the message on the board of the drone,
     * returns the estimate of time, when the
     * message was sent from/ received by the recording device.
     * Also: if the time format is different, the returned time 
     * is in the format of the recorded device.
     * (And input is expected in the time format of the drone)
     * @param time
     * @param outcoming
     * @return 
     */
    Long recordTimeToOnboardTime(long time, boolean outcoming);
    default long[] recordTimeToBoardTime(long[] times, boolean outcoming){
        long[] res = new long[times.length];
        for (int i = 0; i < times.length; i++) {
            Long l = recordTimeToOnboardTime(times[i], outcoming);
            if (l==null) return null;
            res[i]=l;
        }
        return res;
    }
    /**
     * Finds a converter inside of the FileLookup which is capable of converting
     * the values and uses it for the conversion.
     * @param recorderTimeValues list of time values included by the recorder device
     * @param incoming true: messages were sent by the drone to the recorder
     *                 / false: conversely, sent by the recorder to the drone
     * @return converted values(estimated onboard time values) or null if no suitable converter exists
     */
    static  @CheckForNull long[] convertRecorderValuesToOnboard(long[] recorderTimeValues, boolean incoming) {
        long[] res;
        Collection<? extends TimeValuesConverter> converters = findConverters();
        for (TimeValuesConverter converter : converters) {
            res = converter.recordTimeToBoardTime(recorderTimeValues, !incoming);
            if (res != null) {
                return res;
            }
        }
        return null;
    }
        /**
     * Finds a converter inside of the FileLookup which is capable of converting
     * the values and uses it for the conversion.
     * @param onboardTimeValues list of time values included on board of the drone
     * @param incoming true: messages were sent by the drone to the recorder
     *                 false: conversely, sent by the recorder to the drone
     * @return converted values(estimated onboard time values) or null if no suitable converter exists
     */
    static @CheckForNull long[] convertOnboardValuesToRecorderValues(long[] onboardTimeValues, boolean incoming) {
       long[] res;
        Collection<? extends TimeValuesConverter> converters = findConverters();
        for (TimeValuesConverter converter : converters) {
            res = converter.onboardTimeToRecordTime(onboardTimeValues, !incoming);
            if (res != null) {
                return res;
            }
        }
        return null;
    }

    static Collection<? extends TimeValuesConverter> findConverters() {
        Collection<? extends TimeValuesConverter> converters;
        final Lookup currentFileLookup = FileLookup.getDefault();
        converters = currentFileLookup.lookupAll(TimeValuesConverter.class);
        return converters;
    }
     /**
    * Finds a converter inside of the FileLookup which is capable of converting
     * the value and uses it for the conversion.
     * @param onboardTime  list of time values included on board the drone
     * @param incoming true: message was sent by the drone to the recorder
     *                 false: conversely, sent by the recorder to the drone
     * @return converted value(estimated on-board time value) or null if no suitable converter exists
     */
    static @CheckForNull Long convertOnboardValueToRecorderValue(long onboardTime, boolean incoming) {
        Collection<? extends TimeValuesConverter> converters = findConverters();
        for (TimeValuesConverter converter : converters) {
            Long res = converter.boardTimeToRecordTime(onboardTime, incoming);
            if (res!=null) return res;
        }
        return null;
    }
    /**
     * Finds a converter inside of the FileLookup which is capable of converting
     * the value and uses it for the conversion.
     * @param recorderTime list of time values included by the recorder device
     * @param incoming true: messages were sent by the drone to the recorder
     *                 / false: conversely, sent by the recorder to the drone
     * @return converted values(estimated on-board time values) or null if no suitable converter exists
     */
    static @CheckForNull Long convertRecorderValueToOnboardValue(long recorderTime, boolean incoming) {
        Collection<? extends TimeValuesConverter> converters = findConverters();
        for (TimeValuesConverter converter : converters) {
            Long res = converter.boardTimeToRecordTime(recorderTime, incoming);
            if (res!=null) return res;
        }
        return null;
    }

    
}
