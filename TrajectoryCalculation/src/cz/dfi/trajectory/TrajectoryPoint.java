/*
 */

package cz.dfi.trajectory;

import cz.dfi.datamodel.values.AbstractValueWrapper;
import cz.dfi.datamodel.values.TimeStamp;

/**
 *A Value Wrapper that wraps a single point on a trajectory and 
 * is able to convert it to String.
 * @author Tomas Prochazka
 * 13.6.2016
 */
public class TrajectoryPoint extends AbstractValueWrapper {

    private final double x;
    private final double y;

    public TrajectoryPoint(String name, TimeStamp timeStamp, double x, double y) {
        super(name, timeStamp);
        this.x = x;
        this.y = y;
    }

    
    @Override
    public String getValueAsString() {
        return x + ", " + y; 
    }

}
