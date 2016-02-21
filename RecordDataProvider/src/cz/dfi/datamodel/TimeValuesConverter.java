/*
 */

package cz.dfi.datamodel;

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
    Long boardTimeToRecordTime(double time, boolean outcoming);
    
    default long[] boardTimeToRecordTime(double[]times, boolean outcoming) {
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
    Double recordTimeToBoardTime(double time, boolean outcoming);
    default double[] recordTimeToBoardTime(long[] times, boolean outcoming){
        double[] res = new double[times.length];
        for (int i = 0; i < times.length; i++) {
            Double d = recordTimeToBoardTime(times[i], outcoming);
            if (d==null) return null;
            res[i]=d;
        }
        return res;
    }

    
}
