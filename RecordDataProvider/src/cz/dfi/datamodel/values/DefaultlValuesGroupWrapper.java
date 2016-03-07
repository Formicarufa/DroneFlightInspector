/*
 */

package cz.dfi.datamodel.values;

/**
 * Group of values which contains time information.
 */
public class DefaultlValuesGroupWrapper extends ValuesGroupWrapper {

    private final TimeStamp timeStamp;
    private final TimeInterval timeInterval;
    DefaultlValuesGroupWrapper(String name, TimeStamp timeStamp) {
        super(name);
        this.timeStamp=timeStamp;
        this.timeInterval=null;
    }

    public DefaultlValuesGroupWrapper(String name, TimeInterval timeInterval) {
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
