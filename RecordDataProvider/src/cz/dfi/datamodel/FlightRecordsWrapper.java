/*
 */
package cz.dfi.datamodel;

import java.util.List;

/**
 *
 * @author Tomas Prochazka 22.11.2015
 */
public class FlightRecordsWrapper implements RecordsWrapper {

    private List<FlightDataRecord> records;
    private double[] timeValues;
    private double[] recordedTimeValues;

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
     *
     * @return
     */
    public double[][] getAltitudeInTime() {
        double[][] table;
        table = new double[2][];
        table[0] = getOnBoardTimeValues();
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
    public double[] getOnBoardTimeValues() {
        if (timeValues == null) {
            timeValues = new double[records.size()];
            for (int i = 0; i < records.size(); i++) {
                timeValues[i] = records.get(i).droneTime / 1_000_000;
            }
        }
        return timeValues;
    }

    /**
     * Returns the table of the motors performance (in percent) in time (sec).
     * Format: time t1 t2 t3 motor 1 [0][1] [0][2] [0][3] ... motor 2 [1][1]
     * [1][2] [1][3] ... motor 3 [2][1] [2][2] [2][3] ... motor 4 [3][1] [3][2]
     * [3][3] ...
     *
     * @return double[5][#_of_records]
     */
    public double[][] getMotorsPower() {
        double[][] table = new double[5][];
        table[0] = getOnBoardTimeValues();
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
     * @return
     */
    public double[][] getVelocities() {
        double[][] table = new double[4][];
        table[0] = getOnBoardTimeValues();
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
    public double[] getTimeOfRecordValues() {
        if (recordedTimeValues == null) {
            recordedTimeValues = new double[records.size()];
            for (int i = 0; i < records.size(); i++) {
                recordedTimeValues[i] = records.get(i).time/1_000_000.0;
            }
        }
        return recordedTimeValues;
    }

    @Override
    public TimeStampType getOriginalTimeStampSource() {
        return TimeStampType.Both;
    }

}
