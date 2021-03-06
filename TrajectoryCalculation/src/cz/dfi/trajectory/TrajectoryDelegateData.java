/*
 */

package cz.dfi.trajectory;

import org.openide.util.Lookup;

/**
 *A storage for data that are converted to {@link Trajectory} by 
 * {@link TrajectoryDelegate}. 
 * @author Tomas Prochazka
 * 14.6.2016
 */
public class TrajectoryDelegateData {
    public TrajectoryAlgorithm algorithm;
    public Lookup data;
    public final int id;
    public static int ID = 0;
    public TrajectoryDelegateData(TrajectoryAlgorithm algorithm, Lookup data) {
        this.algorithm = algorithm;
        this.data = data;
        this.id = ID++;
    }    
}
