 /*
 */

package cz.dfi.trajectory;

import org.openide.util.lookup.InstanceContent;

/**
 * Enables lazy computation of the trajectory data.
 * Data are not computed until requested.
 * @author Tomas Prochazka
 * 14.6.2016
 */
public class TrajectoryDelegate implements InstanceContent.Convertor<TrajectoryDelegateData, Trajectory>{

    @Override
    public Trajectory convert(TrajectoryDelegateData obj) {
        return obj.algorithm.computeTrajectory(obj.data);
    }

    @Override
    public Class<? extends Trajectory> type(TrajectoryDelegateData obj) {
        return Trajectory.class;
    }

    @Override
    public String id(TrajectoryDelegateData obj) {
        return "trajectory "+ obj.id;
    }

    @Override
    public String displayName(TrajectoryDelegateData obj) {
        return "Trajectory delegate";
    }

}
