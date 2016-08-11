/**
 * Contains currently only converters of angles to degrees.
 * <p>
 * This package has been created because Drone Flight Inspector
 * does not specify the required units of quantities (except for time values that
 * have to be in nanoseconds).
 * <p>
 * As we need to know the real angle size, we use {@link DegreeConverter} to
 * convert other common units to degrees. 
 * <p>
 * If the developer wanted to use any nonstandard unit (other than degrees, millidegrees,
 * radians) he would have to create own DegreeConverter implementation and 
 * register it as a service in the global action lookup.
 * @see UnitConverter
 * @see DegreeConverter
 */
package cz.dfi.unitconverters;
