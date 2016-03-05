/*
 */

package cz.dfi.datamodel.values;

/**
 * Group which has a parent on which it can redirect questions on
 * time.
 */
public class NestedValuesGroupWrapper extends ValuesGroupWrapper {

    
    public NestedValuesGroupWrapper(String name) {
        super(name);
    }
    @Override
    public TimeStamp getTimeStamp() {
        return this.parent.getTimeStamp();
    }
    @Override
    public TimeInterval getTimeInterval() {
        return this.parent.getTimeInterval();
    }
}
