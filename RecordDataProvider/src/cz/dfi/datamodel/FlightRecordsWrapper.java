/*
 */
package cz.dfi.datamodel;

import cz.dfi.datamodel.series.SeriesGroupWrapper;
import cz.dfi.datamodel.series.SeriesWrapper;
import cz.dfi.datamodel.series.TimeStampArray;
import cz.dfi.datamodel.values.ValueWrapper;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Tomas Prochazka 22.11.2015
 */
public class FlightRecordsWrapper implements SeriesWrapper {

    private final List<FlightDataRecord> records;
 
    TimeStampArray timeStamps=null;

   
    public FlightRecordsWrapper(List<FlightDataRecord> records) {
        this.records = records;
    }

    /**
     *
     * @return the records
     */
    public List<FlightDataRecord> getRecords() {
        return records;
    }

    /**
     * Gets two dimensional array (table) of altitude in time. The resulting
     * array is an array of type double[2][] where for each i double[0][i]
     * contains time value in seconds and double[1][i] contains altitude in time
     * "double[0][i]" in centimeters. Time values in the list are in the
     * ascending order.
     * @deprecated double not used to measure time anymore!
     * @return
     */
    @Deprecated
    public double[][] getAltitudeInTime() {
        double[][] table;
        table = new double[2][];
        table[0] = Arrays.stream(timeStamps.getOnboardValues()).mapToDouble((x)->{return (double)x;}).toArray();
        table[1] = new double[records.size()];
        for (int i = 0; i < records.size(); i++) {
            table[1][i] = records.get(i).altitude / 10.0;
        }
        return table;
    }

    /**
     * Gets the list of times (in seconds) for which there are some recorded
     * data in the FlightDataRecords in the ascending order.
     *
     * @return
     */
   
    @Override
     public TimeStampArray getTimeStamps() {
        if (timeStamps==null) {
            long[] timeValues = new long[records.size()];
            for (int i = 0; i < records.size(); i++) {
                // conversion from milisecons (float) to nanoseconds (long):
                timeValues[i] = Math.round(records.get(i).droneTime * 1_000_0000); 
            }
            long[] recordedTimeValues = new long[records.size()];
            for (int i = 0; i < records.size(); i++) {
                recordedTimeValues[i] = records.get(i).time;
            }
            timeStamps=new TimeStampArray(recordedTimeValues, timeValues);
        }
         return timeStamps;
    }
    /**
     * Returns the table of the motors performance (in percent) in time (sec).
     * Format: time t1 t2 t3 motor 1 [0][1] [0][2] [0][3] ... motor 2 [1][1]
     * [1][2] [1][3] ... motor 3 [2][1] [2][2] [2][3] ... motor 4 [3][1] [3][2]
     * [3][3] ...
     *@deprecated time no longer represented as double
     * @return double[5][#_of_records]
     */
    @Deprecated
    public double[][] getMotorsPower() {
        double[][] table = new double[5][];
        table[0] = Arrays.stream(timeStamps.getOnboardValues()).mapToDouble((x)->{return (double)x;}).toArray();
        table[1] = new double[records.size()];
        table[2] = new double[records.size()];
        table[3] = new double[records.size()];
        table[4] = new double[records.size()];
        for (int i = 0; i < records.size(); i++) {
            table[1][i] = records.get(i).motor1 * 100 / 255.0;
            table[2][i] = records.get(i).motor2 * 100 / 255.0;
            table[3][i] = records.get(i).motor3 * 100 / 255.0;
            table[4][i] = records.get(i).motor4 * 100 / 255.0;
        }
        return table;
    }

    /**
     * Returns the table of the drone estimated velocity (in cm/sec) in time
     * (sec). Format: time t1 t2 t3 velocity in x [0][1] [0][2] [0][3] ...
     * velocity in y [1][1] [1][2] [1][3] ... velocity in z [2][1] [2][2] [2][3]
     * ...
     *
     * @deprecated time no longer represented as double
     * @return
     */
    @Deprecated
    public double[][] getVelocities() {
        double[][] table = new double[4][];
        table[0] = Arrays.stream(timeStamps.getOnboardValues()).mapToDouble((x)->{return (double)x;}).toArray();
        table[1] = new double[records.size()];
        table[2] = new double[records.size()];
        table[3] = new double[records.size()];
        for (int i = 0; i < records.size(); i++) {
            table[1][i] = records.get(i).vx * 10;
            table[2][i] = records.get(i).vy * 10;
            table[3][i] = records.get(i).vz * 10;
        }
        return table;
    }

    @Override
    public String getName() {
        return "Navigation data";
    }

    @Override
    public SeriesGroupWrapper getParent() {
        return null;
    }

    @Override
    public void setParent(SeriesGroupWrapper parent) {
        throw new UnsupportedOperationException("Not supported."); 
    }

    @Override
    public Collection<SeriesWrapper> getChildren() {
        throw new UnsupportedOperationException("Not supported."); 
    }

    @Override
    public ValueWrapper getValue(long time, TimeStampType timeType) {
        throw new UnsupportedOperationException("Not supported."); 
    }

    @Override
    public Collection<ValueWrapper> getIntervalSummary(long t1, long t2, TimeStampType timeType) {
        throw new UnsupportedOperationException("Not supported.");
    }

   

}
