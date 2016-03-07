/*
 */

package cz.dfi.datamodel.values;

import java.util.Collection;
import org.netbeans.api.annotations.common.CheckForNull;

/**
 * Wraps a value selected from some series.
 * Does not provide access to the value itself except 
 * through the {@link #getValueAsString() } method.
 * @author Tomas Prochazka
 * 27.2.2016
 */
public interface ValueWrapper {
    /**
     * Gets the name of the type of the value.
     * @return 
     */
    String getName();

    /**
     * Each wrapped value has either single time value or time interval attached.
     * @return time information
     */
    TimeStamp getTimeStamp();
     /**
     * Each wrapped value has either single time value or time interval attached.
     * @return time interval
     */
    TimeInterval getTimeInterval();
    /**
     * Gets the parent of the value in the group hierarchy.
     * @return 
     */
    @CheckForNull ValuesGroupWrapper getParent();
    /**
     * If the wrapper wraps a group of values then the group can be
     * obtained by calling this method.
     * @return members of the group or null
     */
    @CheckForNull Collection<ValueWrapper> getChildren();
    /**
     * Returns the value converted to string which can be displayed
     * to the user. If the value is not convertible to string
     * in a readable format, returns null.
     * @return 
     */
    @CheckForNull String getValueAsString();
    /**
     * Sets the parent of the value in the group hierarchy.
     */
    void setParent(ValuesGroupWrapper groupWrapper);
}
