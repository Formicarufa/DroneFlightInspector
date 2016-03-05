/*
 */

package cz.dfi.datamodel.values;

/**
 * Group which has to contain time information because it has no parent
 * from which it could obtain this information.
 */
public class TopLevelValuesGroupWrapper extends ValuesGroupWrapper {

    private final TimeStamp timeStamp;
    private final TimeInterval timeInterval;
    TopLevelValuesGroupWrapper(String name, TimeStamp timeStamp) {
        super(name);
        this.timeStamp=timeStamp;
        this.timeInterval=null;
    }

    public TopLevelValuesGroupWrapper(String name, TimeInterval timeInterval) {
        super(name);
        this.timeStamp = null;
        this.timeInterval = timeInterval;
    }
    

    @Override
    public TimeStamp getTimeStamp() {
        return timeStamp;
    }

    @Override
    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    
    

}
