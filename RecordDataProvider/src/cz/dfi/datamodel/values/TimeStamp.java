/*
 */

package cz.dfi.datamodel.values;

import cz.dfi.datamodel.TimeStampType;
import cz.dfi.datamodel.TimeValuesConverter;

/**
 * Represents a time stamp of an event in time. Contains the time stamp from
 * board the drone or time value added by the recorder or both, if available.
 * <p>
 * Unit convention: nanoseconds since an arbitrary time instant in the past 
 * (Usually: since 1970)
 * </p>
 * @author Tomas Prochazka
 * 4.3.2016
 */
public class TimeStamp {
    Long onboardValue=null;

    
    Long recorderValue=null;
    boolean incoming;
    TimeStampType source;

    public TimeStamp(Long timeValue, TimeStampType source, boolean incoming ) {
        this.source=source;
        switch (source) {
            case OnboardTime:
                onboardValue=timeValue;
                break;
            case TimeOfRecord:
                recorderValue=timeValue;
                break;
            case Both:
                throw new IllegalArgumentException("Time stamp type = both, but only 1 time value gived.");
            default:
                throw new IllegalArgumentException("Unknown time stamp type.");
        }
        this.incoming = incoming;
        this.source = source;
    }

    /**
     * Assumes that if you know both recorder and on-board time values the
     * message is incoming.
     * @param recorderValue
     * @param onboardValue 
     */
    public TimeStamp(long recorderValue, long onboardValue) {
        this(recorderValue,onboardValue,true);
    }

    
    public TimeStamp(long recorderValue, long onboardValue,boolean incoming) {
        this.incoming=incoming;
        this.recorderValue=recorderValue;
        this.onboardValue=onboardValue;
        source = TimeStampType.Both;
    }
    
    public Long getOnboardValue() {
        if (onboardValue==null) {
            onboardValue = TimeValuesConverter.convertRecorderValueToOnboardValue(recorderValue, incoming);
        }
        return onboardValue;
    }

    public Long getRecorderValue() {
       if (recorderValue==null){
           recorderValue= TimeValuesConverter.convertOnboardValueToRecorderValue(onboardValue, incoming);
       }
        return recorderValue;
    }

    public TimeStampType getSource() {
        return source;
    }
    public Long getTimeForType(TimeStampType timeType){
        switch (timeType) {
            case OnboardTime:
                return getOnboardValue();
            case TimeOfRecord:
                return  getRecorderValue();
            case Both:
            default:
                throw new IllegalArgumentException("Unexpected time stamp type.");
        }
    }
    
    /**
     * Computes the distance between two time stamps.
     * @param t1 time in nanoseconds
     * @param t2 time in nanoseconds
     * @return distance in seconds as a floating-point value
     */
    public static double deltaSec(long t1, long t2) {
        return (t2 - t1)/NANOSECS_TO_SEC;
    }
    public static final double NANOSECS_TO_SEC = 1_000_000_000.0;
    
}
