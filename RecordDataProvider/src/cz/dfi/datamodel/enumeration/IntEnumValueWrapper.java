/*
 */

package cz.dfi.datamodel.enumeration;

import cz.dfi.datamodel.values.AbstractValueWrapper;
import cz.dfi.datamodel.values.TimeStamp;

/**
 * Wraps a single enumeration value, that can printed as a String.
 * @see IntEnumerationSeries
 * @author Tomas Prochazka
 */
public class IntEnumValueWrapper extends AbstractValueWrapper {

    private final String value;

    public IntEnumValueWrapper(String name, TimeStamp timeStamp, String value) {
        super(name, timeStamp);
        this.value = value;
    }

    @Override
    public String getValueAsString() {
        return value;
    }

}
