/*
 */
package cz.dfi.datamodel.series;

import cz.dfi.datamodel.TimeStampType;
import cz.dfi.datamodel.TimeValuesConverter;
import cz.dfi.datamodel.values.TimeInterval;
import cz.dfi.datamodel.values.TimeStamp;
import jtimeselector.TimeSearch;
import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.api.annotations.common.CheckReturnValue;
import org.netbeans.api.annotations.common.NonNull;

/**
 * Stores an array of time values. Includes information about their origin.
 * (Whether the time stamp was included by the drone or by the
 * recorder/controller device and whether the message itself was incoming = sent
 * from the drone to the recorder device e.g. information about altitude, or
 * outgoing: sent from the recorder to the drone - e.g. commands to the drone)
 *
 * @author Tomas Prochazka 28.2.2016 *
 */
public class TimeStampArray {

    private long[] recorderValues = null;
    private long[] onboardValues = null;
    private final TimeStampType source;
    private final boolean incoming;

    public TimeStampArray(@NonNull long[] timeValues, TimeStampType source, boolean incoming) {
        this.incoming = incoming;
        this.source = source;
        switch (source) {
            case OnboardTime:
                onboardValues = timeValues;
                break;
            case TimeOfRecord:
                recorderValues = timeValues;
                break;
            case Both:
                throw new IllegalArgumentException("Unexpected TimeStampType.Both, "
                        + "source should contain other value "
                        + "or different constructor should be used. ");
            default:
                throw new IllegalArgumentException("Invalid TimeStampType value.");
        }
    }

    /**
     * Creates an object which encapsulates both recorder time stamps and
     * on-board time stamp as original (not obtained by conversion) time values.
     *
     * @param recorderValues array of time stamps included by the recorder
     * device
     * @param onboardValues array of time stamps included on board the drone
     */
    public TimeStampArray(@NonNull long[] recorderValues, @NonNull long[] onboardValues) {
        this.recorderValues = recorderValues;
        this.onboardValues = onboardValues;
        this.source = TimeStampType.Both;
        this.incoming = true; // information not needed, most likely incoming (as we have both recorder and onboard time values available).
    }

    /**
     * Gets a list of time stamps, that have been added to some message at the
     * moment when it was saved to the file. If such values are not available,
     * they could be estimated in the same manner as those of {@link #getOnboardValues()
     * } method. Typically, though, there is no reason these values should not
     * be included.
     *
     * @return list of time values in the ascending order, might also return
     * null
     */
    public @CheckForNull
    long[] getRecorderValues() {

        if (source == TimeStampType.TimeOfRecord || source == TimeStampType.Both) {
            return recorderValues;
        }
        if (recorderValues == null) {
            recorderValues = TimeValuesConverter.convertOnboardValuesToRecorderValues(onboardValues, incoming);
        }
        return recorderValues;
    }

    /**
     * Returns an array of time stamps such that they have been added to the
     * message on board of the drone. therefore the values and the time
     * information shall shall correspond precisely. (No delay should have
     * occurred.) If such data are not present, estimated values may be
     * returned. (Estimated data should be calculated by the TimeValueConvertor
     * which, if it exists, is present in the Lookup) The user can ensure
     * whether the data are original or estimated by calling the {@link #getSource()
     * } method.
     *
     * @return list of time values in the ascending order, might also return
     * null
     */
    public @CheckForNull
    long[] getOnboardValues() {
        if (source == TimeStampType.OnboardTime || source == TimeStampType.Both) {
            return onboardValues;
        }
        if (onboardValues == null) {
            onboardValues = TimeValuesConverter.convertRecorderValuesToOnboard(recorderValues, incoming);
        }
        return onboardValues;
    }

    /**
     * The time information of each record can come from two sources: Either the
     * time stamp is added to the message on board the drone or it is added by
     * computer when the message is recorded to file. Some messages may even
     * contain both these time stamps.
     *
     * @return Type of the time stamp that was present in the original recorded
     * data.
     */
    public TimeStampType getSource() {
        return source;
    }

    /**
     * Gets the index of the time value inside of the array which is closest to
     * the given value.
     *
     * @param time time value
     * @param timeType type of the given time value
     * @return -1 if the values for the given TimeStampType are not available or
     * the array is empty. Otherwise returns the index of the time value which
     * is closest to the argument.
     */
    public @CheckReturnValue
    int getIndexOfClosest(long time, TimeStampType timeType) {
        long[] timeValues = getValuesForType(timeType);
        if (arrayEmpty(timeValues)) {
            return -1;
        }
        return jtimeselector.TimeSearch.indexOfClosest(timeValues, time);
    }

    /**
     * Returns time stamp from the array which is closest to a given time.
     *
     * @param time time value
     * @param timeType type of the given time value
     * @return null if the values for the given TimeStampType are not available
     * or the array is empty.
     */
    public TimeStamp getClosestTimeStamp(long time, TimeStampType timeType) {
        int indexOfClosest = getIndexOfClosest(time, timeType);
        if (indexOfClosest == -1) {
            return null;
        }
        if (source == TimeStampType.Both || source != timeType) {
            return new TimeStamp(recorderValues[indexOfClosest], onboardValues[indexOfClosest], incoming);
        } else {
            long[] valuesForType = getValuesForType(timeType);
            return new TimeStamp(valuesForType[indexOfClosest], timeType, incoming);
        }
    }

    /**
     * Gets index of the last time value in the array which is less than or
     * equal to a given value.
     *
     * @param time time value
     * @param timeType type of the given time value
     * @return index to the array or -1 if array is empty or not available for
     * the given TimeStampType or does not contain any element satisfying the
     * condition
     */
    public @CheckReturnValue
    int getLastLessThanOrEqualIndex(long time, TimeStampType timeType) {
        long[] timeValues = getValuesForType(timeType);
        if (arrayEmpty(timeValues)) {
            return -1;
        }
        return TimeSearch.lastLessThanOrEqual(timeValues, time);
    }

    /**
     * Gets the last time value in the array which is less than or equal to a
     * given value.
     *
     * @param time time value
     * @param timeType type of the given time value
     * @return null if array is empty or not available for the given
     * TimeStampType or does not contain any element satisfying the condition
     */
    public @CheckForNull
    TimeStamp getLastLessThanOrEqual(long time, TimeStampType timeType) {
        int index = getLastLessThanOrEqualIndex(time, timeType);
        if (index == -1) {
            return null;
        }
        if (source == TimeStampType.Both || timeType != source) {
            return new TimeStamp(recorderValues[index], onboardValues[index], incoming);
        } else {
            long[] values = getValuesForType(timeType);
            return new TimeStamp(values[index], timeType, incoming);
        }

    }

    /**
     * Gets the index of the first element in the array of time stamps that is
     * greater than or equal to a given time.
     *
     * @param time time value
     * @param timeType type of the given time value
     * @return index to the array or 0 if the array is empty or not available
     * for the given TimeStampType, "array length" if no element satisfying the
     * condition exists.
     */
    public @CheckReturnValue
    int getFirstGreaterThanOrEqualIndex(long time, TimeStampType timeType) {
        long[] timeValues = getValuesForType(timeType);
        if (arrayEmpty(timeValues)) {
            return 0;
        }
        return TimeSearch.firstGreaterThanOrEqual(timeValues, time);
    }
 /**
     * Gets the first element in the array of time stamps that is
     * greater than or equal to a given time.
     *
     * @param time time value
     * @param timeType type of the given time value
     * @return null if the array is empty or not available
     * for the given TimeStampType or if no element satisfying the
     * condition exists.
     */
    TimeStamp getFirstGreaterThanOrEqual(long time, TimeStampType timeType) {
        long[] values = getValuesForType(timeType);
        if (values == null) {
            return null;
        }
        int index = getFirstGreaterThanOrEqualIndex(time, timeType);
        if (index >= values.length) {
            return null;
        }
        if (source == TimeStampType.Both || source != timeType) {
            return new TimeStamp(recorderValues[index], onboardValues[index], incoming);
        } else {
            return new TimeStamp(values[index],timeType,incoming);
        }

    }
    /**
     * For a given time interval [t1,t2] returns a representation
     * of the interval with respect to this instance data. 
     * (Trims the parts where there are no data, 
     *  if a conversion is needed, it is done with respect to 
     * the data origin.)
     * @param t1
     * @param t2
     * @param timeType
     * @return 
     */
    public TimeInterval getTimeInterval(long t1, long t2, TimeStampType timeType){
        TimeStamp stamp1 = getFirstGreaterThanOrEqual(t1, timeType);
        TimeStamp stamp2 = getLastLessThanOrEqual(t2, timeType);
        if (stamp1==null || stamp2 == null) return null;
        if (stamp1.getTimeForType(timeType)>stamp2.getTimeForType(timeType)) {
            return null;
        }
        return new TimeInterval(stamp1, stamp2);
        
    }

    private static boolean arrayEmpty(long[] timeValues) {
        return timeValues == null || timeValues.length == 0;
    }

    private long[] getValuesForType(TimeStampType timeType) throws IllegalArgumentException {
        long[] timeValues = null;
        switch (timeType) {
            case OnboardTime:
                timeValues = getOnboardValues();
                break;
            case TimeOfRecord:
                timeValues = getRecorderValues();
                break;
            case Both:
            default:
                throw new IllegalArgumentException("Unexpected TimeStampType.");
        }
        return timeValues;
    }
}
