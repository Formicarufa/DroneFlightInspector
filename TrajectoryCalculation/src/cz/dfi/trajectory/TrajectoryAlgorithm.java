/*
 */

package cz.dfi.trajectory;

import org.openide.util.Lookup;

/**
 * An algorithm that can compute trajectory based on the content of the file lookup.
 * The implementations of this interface should be registered as service providers.
 * If they are registered, they are used automatically.
 * @author Tomas Prochazka
 * 14.6.2016
 */
public interface TrajectoryAlgorithm {
    /**
     * 
     * @param inputData The lookup which contains the velocity and orientation data.
     * @return the computed trajectory
     */
    Trajectory computeTrajectory(Lookup inputData);
    /**
     * Check whether there are the required data available in the lookup.
     * The method call should not be time-consuming
     * (in compare to the {@link #computeTrajectory(org.openide.util.Lookup) }).
     * @param inputData
     * @return 
     */
    boolean canComputeTrajectory(Lookup inputData);
}
