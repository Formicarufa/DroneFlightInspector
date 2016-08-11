/*
 */

package cz.dfi.datamodel.values;

/**
 * Group of values, which contains time information.
 * @see cz.dfi.datamodel.series.TopLevelSeriesGroupWrapper
 */
public class DefaultValuesGroupWrapper extends ValuesGroupWrapper {

    private final TimeStamp timeStamp;
    private final TimeInterval timeInterval;
    public DefaultValuesGroupWrapper(String name, TimeStamp timeStamp) {
        super(name);
        this.timeStamp=timeStamp;
        this.timeInterval=null;
    }

    public DefaultValuesGroupWrapper(String name, TimeInterval timeInterval) {
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
