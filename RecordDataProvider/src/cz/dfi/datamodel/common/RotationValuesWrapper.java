/*
 */
package cz.dfi.datamodel.common;

import cz.dfi.datamodel.graphable.DoubleValueWrapper;
import cz.dfi.datamodel.values.DefaultValuesGroupWrapper;
import cz.dfi.datamodel.values.TimeInterval;
import cz.dfi.datamodel.values.TimeStamp;
import cz.dfi.datamodel.values.ValueWrapper;
import org.netbeans.api.annotations.common.CheckForNull;

/**
 *
 * @author Tomas Prochazka 24.5.2016
 */
public class RotationValuesWrapper extends DefaultValuesGroupWrapper {

    public RotationValuesWrapper(String name, TimeInterval timeInterval) {
        super(name, timeInterval);
    }

    public RotationValuesWrapper(String name, TimeStamp timeStamp) {
        super(name, timeStamp);
    }

    public @CheckForNull DoubleValueWrapper getPitch() {
        return getChildWithNameContaining("pitch");
    }

    public @CheckForNull DoubleValueWrapper getRoll() {
        return getChildWithNameContaining("roll");
    }
    public @CheckForNull DoubleValueWrapper getYaw() {
        return getChildWithNameContaining("yaw");
    }

    /**
     *
     * @param val lowercase substring
     * @return
     */
    protected @CheckForNull DoubleValueWrapper getChildWithNameContaining(String val) {
        for (ValueWrapper x : getChildren()) {
            if (x.getName().toLowerCase().contains(val)) {
                if (x instanceof DoubleValueWrapper) {
                    return (DoubleValueWrapper) x;
                }
            }
        }
        return null;
    }

}
