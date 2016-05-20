/*
 */
package cz.dfi.ardronerosbag;

import cz.dfi.datamodel.common.AltitudeWrapper;
import cz.dfi.datamodel.FlightDataRecord;
import cz.dfi.datamodel.ImportHelper;
import cz.dfi.datamodel.common.AccelerationWrapper;
import cz.dfi.datamodel.common.BatteryPercentWrapper;
import cz.dfi.datamodel.common.MagnetometerWrapper;
import cz.dfi.datamodel.common.MotorsWrapper;
import cz.dfi.datamodel.common.RotationWrapper;
import cz.dfi.datamodel.common.SpeedWrapper;
import cz.dfi.datamodel.graphable.DoubleQuantity;
import cz.dfi.datamodel.series.SeriesGroupWrapper;
import cz.dfi.datamodel.series.SeriesWrapper;
import cz.dfi.datamodel.series.TimeStampArray;
import java.util.Collection;
import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author Tomas Prochazka 22.12.2015
 */
public class ArdroneNavdataModel {

    List<FlightDataRecord> records;

    public ArdroneNavdataModel(List<FlightDataRecord> records) {
        this.records = records;
    }

    void construct(InstanceContent content) {
        long[] recorderTimeValues = getLongArray(r -> r.time);
        // conversion from microsecons (float) to nanoseconds (long):
        long[] droneTimeValues = getLongArray(r -> Math.round(r.droneTime * 1_000.0));
        TimeStampArray timeStamps = new TimeStampArray(recorderTimeValues, droneTimeValues);
        double[] altitudes = getDoubleArray(r -> r.altitude);
        double[] motor1Powers = getDoubleArray(r -> r.motor1);
        double[] motor2Powers = getDoubleArray(r -> r.motor2);
        double[] motor3Powers = getDoubleArray(r -> r.motor3);
        double[] motor4Powers = getDoubleArray(r -> r.motor4);
        double[] speedX = getDoubleArray(r -> r.vx);
        double[] speedY = getDoubleArray(r -> r.vy);
        double[] speedZ = getDoubleArray(r -> r.vz);
        double[] batteryLevels = getDoubleArray(r -> r.batteryPercent);
        double[] magX = getDoubleArray(r -> r.magX);
        double[] magY = getDoubleArray(r -> r.magY);
        double[] magZ = getDoubleArray(r -> r.magZ);
        double[] pressure = getDoubleArray(r -> r.pressure);
        double[] temperatures = getDoubleArray(r -> r.temp);
        double[] rotX = getDoubleArray(r -> r.rotX);
        double[] rotY = getDoubleArray(r -> r.rotY);
        double[] rotZ = getDoubleArray(r -> r.rotZ);
        double[] accX = getDoubleArray(r -> r.ax);
        double[] accY = getDoubleArray(r -> r.ay);
        double[] accZ = getDoubleArray(r -> r.az);

        SeriesGroupWrapper navdata = SeriesGroupWrapper.create_timelineLayer("Navigation data", timeStamps);

        final AltitudeWrapper altitudeWrapper = new AltitudeWrapper(altitudes, "cm", timeStamps);
        final BatteryPercentWrapper batteryWrapper = new BatteryPercentWrapper(batteryLevels, timeStamps);
        final MotorsWrapper motorsWrapper = new MotorsWrapper(timeStamps, motor1Powers, motor2Powers, motor3Powers, motor4Powers);
        final SpeedWrapper speedWrapper = new SpeedWrapper("mm/sec", timeStamps, speedX, speedY, speedZ);
        final MagnetometerWrapper magWrapper = new MagnetometerWrapper("??", timeStamps, magX, magY, magZ);
        final DoubleQuantity pressureWrapper = new DoubleQuantity(pressure, "Pressure", "Pa", timeStamps);
        DoubleQuantity tempWrapper = new DoubleQuantity(temperatures, "Temperature", "??", timeStamps);
        SeriesWrapper windCond = getWindConditions(timeStamps);
        RotationWrapper rotationWrapper = new RotationWrapper("degrees", timeStamps, rotX, rotY, rotZ);
        AccelerationWrapper accelerationWrapper = new AccelerationWrapper("g", timeStamps, accX, accY, accZ);
        navdata.addChildren(
                altitudeWrapper, batteryWrapper,
                speedWrapper, motorsWrapper,
                magWrapper, pressureWrapper,
                tempWrapper, windCond,
                rotationWrapper, accelerationWrapper);
        content.add(records);
        ImportHelper.addTreeToLookup(navdata, content);
    }


    double[] getDoubleArray(ToDoubleFunction<FlightDataRecord> f) {
        double[] res = new double[records.size()];
        for (int i = 0; i < records.size(); i++) {
            res[i] = f.applyAsDouble(records.get(i));
        }
        return res;
    }

    long[] getLongArray(ToLongFunction<FlightDataRecord> f) {
        long[] res = new long[records.size()];
        for (int i = 0; i < records.size(); i++) {
            res[i] = f.applyAsLong(records.get(i));
        }
        return res;
    }

    SeriesWrapper getWindConditions(TimeStampArray timeStamps) {
        SeriesGroupWrapper windConditions = SeriesGroupWrapper.create("Wind conditions", timeStamps);
        double[] windSpeed = getDoubleArray(r -> r.wind_speed);
        double[] windAngle = getDoubleArray(r -> r.wind_angle);
        double[] windCompAngle = getDoubleArray(r -> r.wind_comp_angle);
        windConditions.addChildren(
                new DoubleQuantity(windSpeed, "Wind speed", "??", timeStamps),
                new DoubleQuantity(windAngle, "Wind angle", "??", timeStamps),
                new DoubleQuantity(windCompAngle, "Wind compensation angle", "??", timeStamps)
        );
        return windConditions;
    }

}
