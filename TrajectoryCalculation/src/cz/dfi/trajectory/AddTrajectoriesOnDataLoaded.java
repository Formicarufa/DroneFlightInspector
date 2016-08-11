/*
 */
package cz.dfi.trajectory;

import cz.dfi.recorddataprovider.DataLoadedCallback;
import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ServiceProvider;

/**
 * Puts trajectory data into the lookup.
 * <p> 
 * More precisely, only a delegate is put
 * into the lookup which calls the trajectory evaluation algorithm the first
 * time the result is requested. 
 * <p>
 * Multiple algorithms for trajectory evaluation
 * can exist, all of them are added into the lookup. This class is registered as
 * a service which is called for each newly opened file.
 *
 * @author Tomas Prochazka 17.6.2016
 */
@ServiceProvider(service = DataLoadedCallback.class, position = Integer.MAX_VALUE - 20)
public class AddTrajectoriesOnDataLoaded implements DataLoadedCallback {

    @Override
    public void dataLoaded(Lookup data, InstanceContent content) {
        TrajectoryDelegate trajectoryDelegate = new TrajectoryDelegate();
        Lookup.getDefault().lookupAll(TrajectoryAlgorithm.class).forEach((x) -> {
            if (x.canComputeTrajectory(data)) {
                content.add(new TrajectoryDelegateData(x, data), trajectoryDelegate);
            }
        });

    }

}
