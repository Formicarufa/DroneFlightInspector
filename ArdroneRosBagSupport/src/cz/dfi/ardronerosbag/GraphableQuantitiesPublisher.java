/*
 */
package cz.dfi.ardronerosbag;

import cz.dfi.datamodel.graphable.AltitudeWrapper;
import cz.dfi.datamodel.FlightDataRecord;
import cz.dfi.datamodel.graphable.MotorsWrapper;
import cz.dfi.datamodel.graphable.SpeedWrapper;
import java.util.List;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author Tomas Prochazka 22.12.2015
 */
public class GraphableQuantitiesPublisher {

    List<FlightDataRecord> records;

    public GraphableQuantitiesPublisher(List<FlightDataRecord> records) {
        this.records = records;
    }

    void addQuantitiesToLookup(InstanceContent content) {
        double[] recorderTimeValues = getRecorderTimeValues();
        double[] droneTimeValues = getDroneTimeValues();
        double[] altitudes = getAltitudes();
        double[] motor1Powers = getMotor1Powers();
        double[] motor2Powers = getMotor2Powers();
        double[] motor3Powers = getMotor3Powers();
        double[] motor4Powers = getMotor4Powers();
        double[] xVelocities = getXVelocities();
        double[] yVelocities = getYVelocities();
        double[] zVelocities = getZVelocities();
        content.add(new AltitudeWrapper(altitudes,"cm", droneTimeValues, recorderTimeValues));
        content.add(new MotorWrapper(motor1Powers, 1, droneTimeValues, recorderTimeValues));
        content.add(new MotorWrapper(motor2Powers, 2, droneTimeValues, recorderTimeValues));
        content.add(new MotorWrapper(motor3Powers, 3, droneTimeValues, recorderTimeValues));
        content.add(new MotorWrapper(motor4Powers, 4, droneTimeValues, recorderTimeValues));
        content.add(new SpeedDirectionWrapper(xVelocities, "x", droneTimeValues, recorderTimeValues));
        content.add(new SpeedDirectionWrapper(yVelocities, "y", droneTimeValues, recorderTimeValues));
        content.add(new SpeedDirectionWrapper(zVelocities, "z", droneTimeValues, recorderTimeValues));
    }

    double[] getRecorderTimeValues() {
        double[] res = new double[records.size()];
        for (int i = 0; i < records.size(); i++) {
            res[i] = records.get(i).time/ 1_000_000;
        }
        return res;
    }

    double[] getDroneTimeValues() {
        double[] res = new double[records.size()];
        for (int i = 0; i < records.size(); i++) {
            res[i] = records.get(i).droneTime;
        }
        return res;
    }

    double[] getAltitudes() {
        double[] res = new double[records.size()];
        for (int i = 0; i < records.size(); i++) {
            res[i] = records.get(i).altitude;
        }
        return res;
    }

    double[] getXVelocities() {
        double[] res = new double[records.size()];
        for (int i = 0; i < records.size(); i++) {
            res[i] = records.get(i).vx;
        }
        return res;
    }

    double[] getYVelocities() {
        double[] res = new double[records.size()];
        for (int i = 0; i < records.size(); i++) {
            res[i] = records.get(i).vy;
        }
        return res;
    }

    double[] getZVelocities() {
        double[] res = new double[records.size()];
        for (int i = 0; i < records.size(); i++) {
            res[i] = records.get(i).vz;
        }
        return res;
    }

    double[] getMotor1Powers() {
        double[] res = new double[records.size()];
        for (int i = 0; i < records.size(); i++) {
            res[i] = records.get(i).motor1;
        }
        return res;
    }

    double[] getMotor2Powers() {
        double[] res = new double[records.size()];
        for (int i = 0; i < records.size(); i++) {
            res[i] = records.get(i).motor2;
        }
        return res;
    }

    double[] getMotor3Powers() {
        double[] res = new double[records.size()];
        for (int i = 0; i < records.size(); i++) {
            res[i] = records.get(i).motor3;
        }
        return res;
    }

    double[] getMotor4Powers() {
        double[] res = new double[records.size()];
        for (int i = 0; i < records.size(); i++) {
            res[i] = records.get(i).motor4;
        }
        return res;
    }





    public static class MotorWrapper extends MotorsWrapper {
        public MotorWrapper(double[] values, int motorNumber, double[] onBoardTimeValues, double[] recorderTimeValues) {
            super(values, "Motor " + motorNumber + " power", onBoardTimeValues, recorderTimeValues);
        }
    }



    public static class SpeedDirectionWrapper extends SpeedWrapper {
        public SpeedDirectionWrapper(double[] values, String direction, double[] onBoardTimeValues, double[] recorderTimeValues) {
            super(values, "Speed in the " + direction + " direction", "mm / sec", onBoardTimeValues, recorderTimeValues);
        }

    }

}
