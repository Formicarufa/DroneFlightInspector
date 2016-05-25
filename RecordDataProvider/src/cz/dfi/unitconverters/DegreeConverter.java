/*
 */

package cz.dfi.unitconverters;

import java.util.Collection;
import org.netbeans.api.annotations.common.CheckForNull;
import org.openide.util.Lookup;

/**
 * Classes implementing this interface perform a conversion
 * of a given value to degrees.
 * @author Tomas Prochazka
 * 24.5.2016
 */
public interface DegreeConverter extends UnitConverter{
    /**
     * Tries to convert the given value to degrees.
     * @param value the value to convert
     * @param unit the unit of the value
     * @return converted value or null if there is no converter which would know how to convert from the given unit.
     */
    public static @CheckForNull Double convertToDegrees(double value, String unit) {
        Collection<? extends DegreeConverter> converters = Lookup.getDefault().lookupAll(DegreeConverter.class);
        for (DegreeConverter converter : converters) {
            if (converter.canConvert(unit)) {
                return converter.convert(value, unit);
            }
        }
        return null;
    }
}
