/*
 */

package cz.dfi.unitconverters;

import java.util.Collection;
import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.api.annotations.common.NullUnknown;
import org.openide.util.Lookup;

/**
 * Classes implementing this interface perform a conversion
 * of a given value to degrees.
 * <p>
 * To convert to degrees, use the static method 
 * {@link #convertToDegrees(double, java.lang.String) 
 * <p>
 * The converters are registered as services. It is possible to register
 * a new converters.
 * 
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
        unit = unit.trim().toLowerCase();
        for (DegreeConverter converter : converters) {
            if (converter.canConvert(unit)) {
                return converter.convert(value, unit);
            }
        }
        return null;
    }
    public static @NullUnknown DegreeConverter getForUnit(String unit) {
        Collection<? extends DegreeConverter> converters = Lookup.getDefault().lookupAll(DegreeConverter.class);
        unit = unit.trim().toLowerCase();
        for (DegreeConverter converter : converters) {
            if (converter.canConvert(unit)) {
                return converter;
            }
        }
        return null;
    }
}
