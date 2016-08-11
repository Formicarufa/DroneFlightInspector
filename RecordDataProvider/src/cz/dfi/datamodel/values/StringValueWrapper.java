/*
 */

package cz.dfi.datamodel.values;

/**
 * Wrapper for a string name and a string value with a time stamp.
 * @author Tomas Prochazka
 */
public class StringValueWrapper extends AbstractValueWrapper {

    String value;
    public StringValueWrapper(String name, TimeInterval timeInterval, String value) {
        super(name, timeInterval);
        this.value = value;
    }

    public StringValueWrapper(String name, TimeStamp timeStamp, String value) {
        super(name, timeStamp);
        this.value = value;
    }

    @Override
    public String getValueAsString() {
        return value;
    }

}
