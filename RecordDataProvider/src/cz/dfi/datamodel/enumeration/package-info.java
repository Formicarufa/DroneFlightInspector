/**
 * Series Wrapper for a series of enumeration values.
 * Often used for representation of a state.
 * Series contains integers and a map which maps these numbers
 * to String descriptions. 
 * <p>
 * 
 * e.g: 0-> "OK",
 *     1-> "Error", 2-> "Unknown"
 * <p>
 * {@link IntEnumerationSeries } generates {@link IntEnumValueWrapper} for 
 * a single value. When the value is converted to string using {@link cz.dfi.datamodel.values.ValueWrapper#getValueAsString()}
 * the string enumeration state is returned instead of the integer.
 * 
 */
package cz.dfi.datamodel.enumeration;
