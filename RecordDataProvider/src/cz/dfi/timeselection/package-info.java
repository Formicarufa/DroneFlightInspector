/**
 * A set of interfaces for sharing the information on the currently selected time and SetTimeRequest class.
 * <p>The classes {@link TimeIntervalSelection}, {@link TimeValueSelection} or
 * their ancestor {@link TimeSelection} can be searched in the lookup in
 * order to find out whether there currently exists a time selections.
 * <p> This package contains also the {@link SetTimeRequest} class.
 * If you want to change the current application-wide time selection,
 * instantiate the SetTimeRequest class and put it into the current file lookup.
 * @see SetTimeRequest
 */
package cz.dfi.timeselection;
