/*
 */

package cz.dfi.unitconverters;

/**
 * Converts values between different units.
 * @author Tomas Prochazka
 * 24.5.2016
 */
public interface UnitConverter {
    /**
     * 
     * @param unit the original unit of the value, TRIMMED, LOWERCASE
     * @return true if the converter is able to convert given unit
     */
    boolean canConvert(String unit);
    /**
     * Call this if {@link #canConvert(java.lang.String) } returns true.
     * @param value value to be converted
     * @param unit original unit
     * @return value converted to the unit of the converter.
     */
    double convert(double value, String unit);
}
