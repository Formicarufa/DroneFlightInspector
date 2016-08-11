/*
 */
package cz.dfi.trajectory;

import cz.dfi.datamodel.common.RotationWrapper;
import cz.dfi.datamodel.common.VelocityWrapper;
import cz.dfi.datamodel.graphable.DoubleQuantity;
import cz.dfi.datamodel.series.TimeStampArray;
import cz.dfi.datamodel.values.TimeStamp;
import cz.dfi.unitconverters.DegreeConverter;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

/**
 * Default algorithm for computing the trajectory.
 * In order to compute the trajectory the lookup has to contain an instance of
 * {@link VelocityWrapper} and {@link RotationWrapper} classes. Moreover, both
 * these series have to have the same time stamps.
 *
 * @author Tomas Prochazka 17.6.2016
 */
@ServiceProvider(service = TrajectoryAlgorithm.class)
public class TrajectoryNoFilter implements TrajectoryAlgorithm {

    @Override
    public Trajectory computeTrajectory(Lookup inputData) {
        VelocityWrapper speed = inputData.lookup(VelocityWrapper.class);
        RotationWrapper rotation = inputData.lookup(RotationWrapper.class);
        TimeStampArray timeStamps = speed.getTimeStamps();
        double[] xValues = new double[timeStamps.size()];
        double[] yValues = new double[timeStamps.size()];
        double[] speedX = speed.getSpeedX().getValuesAsDoubles();
        double[] speedY = speed.getSpeedY().getValuesAsDoubles();
        DoubleQuantity yawQuantity = rotation.getYaw();
        double[] yawValues = yawQuantity.getValuesAsDoubles();
        String unit = yawQuantity.getUnit();
        DegreeConverter toDeg = DegreeConverter.getForUnit(unit);
        long[] timeVals = timeStamps.getOnboardValues();
        if (timeVals == null) {
            // onboard values are prefered, but recorder values can also be used
            timeStamps.getRecorderValues();
        }
        Trajectory result = new Trajectory(timeStamps, xValues, yValues, "Trajectory with removed navdata gaps");
        if (yawValues.length == 0) {
            return result;
        }
        double sx, sy, yaw, lastdx, lastdy, dx, dy, sin, cos, dt;
        long t, lastt;

        //Calculate the initial velocity in x and y:
        sx = speedX[0];
        sy = speedY[0];
        yaw = -Math.toRadians(toDeg.convert(yawValues[0], unit));
        sin = Math.sin(yaw);
        cos = Math.cos(yaw);
        //Recalculate the x and y speed from the local frame to the global frame.
        lastdx = sx * cos - sy * sin;
        lastdy = sx * sin + sy * cos;
        xValues[0] = 0;
        yValues[0] = 0;
        lastt = timeVals[0];
        for (int i = 1; i < yawValues.length; i++) {
            sx = speedX[i];
            sy = -speedY[i];
            t = timeVals[i];
            yaw = -Math.toRadians(toDeg.convert(yawValues[i], unit));
            sin = Math.sin(yaw);
            cos = Math.cos(yaw);
            //Recalculate the x and y speed from the local frame to the global frame.
            dx = sx * cos - sy * sin;
            dy = sx * sin + sy * cos;
            // Take the average value, assuming that the velocity in each direction has been changing as a linear function.
            dt = TimeStamp.deltaSec(lastt, t);
            if (dt < .02) {
                xValues[i] = xValues[i - 1] + (dx + lastdx) / 2 * dt;
                yValues[i] = yValues[i - 1] + (dy + lastdy) / 2 * dt;
            } else {
                //ignore if we have no data available
                xValues[i] = xValues[i - 1] + (dx + lastdx) / 2 * .020;
                yValues[i] = yValues[i - 1] + (dy + lastdy) / 2 * .020;
            }

            lastdx = dx;
            lastdy = dy;
            lastt = t;
        }
        return result;
    }

    @Override
    public boolean canComputeTrajectory(Lookup inputData) {
        VelocityWrapper speed = inputData.lookup(VelocityWrapper.class);
        if (speed == null) {
            return false;
        }
        RotationWrapper rotation = inputData.lookup(RotationWrapper.class);
        if (rotation == null) {
            return false;
        }
        final DoubleQuantity yaw = rotation.getYaw();
        return speed.getTimeStamps() == rotation.getTimeStamps()
                && speed.getSpeedX() != null
                && speed.getSpeedY() != null
                && speed.getSpeedZ() != null
                && yaw != null
                && DegreeConverter.getForUnit(yaw.getUnit()) != null;
    }

}
