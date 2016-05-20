/*
 */

package cz.dfi.datamodel.enumeration;

import cz.dfi.datamodel.values.AbstractValueWrapper;
import cz.dfi.datamodel.values.TimeStamp;


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
