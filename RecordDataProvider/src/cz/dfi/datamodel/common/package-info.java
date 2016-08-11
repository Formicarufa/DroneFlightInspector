/**
 * A set of commonly used quantities.
 * <p>
 * Commonly used quantities have their special wrapper so that they can be easily found in the lookup.
* <p> 
* Many visualization components rely on the presence of a certain special wrapper in
* the lookup. For example, the window that display 3D orientation is dependent
* on the presence of {@link RotationWrapper} in the lookup.
* <p>
* 
* When importing a new file, make sure that you use the special quantities 
*  whenever it is possible.
* <p> 
* In your own module, you can also create your own special quantities and use them in your modules.
*  Special quantities are usually subclasses of {@link DoubleQuantity} or
* {@link TopLevelSeriesGroupWrapper} .
 */
package cz.dfi.datamodel.common;
