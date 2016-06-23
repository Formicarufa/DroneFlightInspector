/*
 */

package cz.dfi.trajectorycomp;

/**
 *
 * @author Tomas Prochazka
 * 12.6.2016
 */
public interface TrajectoryDisplay {
    void clearPaths();
    void drawPath(double[] xValues, double[] yValues);
    void removePathCache();
}
 