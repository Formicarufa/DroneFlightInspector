/*
 */

package cz.dfi.datamodel.values;

import java.util.ArrayList;
import java.util.Collection;
import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.api.annotations.common.NonNull;

/**
 * ValuesGroupWrapper encapsulates a set of values that were sent by the drone
 * to the recorder (or by the recorder/controller to the drone) at a certain time.
 * Group is created using the static method {@link #create(java.lang.String, cz.dfi.datamodel.values.ValuesGroupWrapper)}
 * or {@link #create(java.lang.String, long, cz.dfi.datamodel.TimeStampType, boolean).
 * @author Tomas Prochazka
 * 27.2.2016
 */
public abstract class ValuesGroupWrapper implements ValueWrapper{
    private final String name;
    protected ValuesGroupWrapper parent;
    private final ArrayList<ValueWrapper> members= new ArrayList<>();
    /**
     * There are 
     * @param name 
     */
    ValuesGroupWrapper(String name) {
        this.name = name;
    }
    
    /**
     * Group has no value. Returns null.
     * @return  null
     */
    @Override
    public String getValueAsString() {
        return null;
    }
    
    /**
     * Gets the name of the group.
     * @return name of the group
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the group in which this group is contained.
     * @return  the group or null if this group is not inside any other group
     */
    @Override
    public @CheckForNull ValuesGroupWrapper getParent() {
        return parent;
    }
    /**
     * Adds a given ValueWrapper to the group.
     * Updates the child's parent.
     * @param child new member of the group.
     */
    public void addChild(ValueWrapper child){
        members.add(child);
        child.setParent(this);
    }
    /**
     * Gets the content of the group.
     * @return members of the group, returned collection might be empty.
     */
    @Override
    public @NonNull Collection<ValueWrapper> getChildren() {
        return members;
    }
    /**
     * Creates a new group as a subgroup of a given parent group.
     * @param name name of the new group
     * @param parent the group in which the new group will be placed.
     * @return a new group
     */
    public static ValuesGroupWrapper create(String name, @NonNull ValuesGroupWrapper parent) {
        ValuesGroupWrapper res = new NestedValuesGroupWrapper(name);
        parent.addChild(res);
        return res;
    }
    /**
     * Creates a new independent group. Time information needs to be specified.
     * @param name Name of the new group.
     * @param time Position of the value in time. Time frame
     * is specified by the next argument.
     * @return a new group
     */
    public static ValuesGroupWrapper create(String name, TimeStamp time) {
        ValuesGroupWrapper res;
        res = new DefaultValuesGroupWrapper(name, time);
        return res;
    }

/**
     * Creates a new independent group. Time information needs to be specified.
     * @param name Name of the new group.
     * @param timeInterval Time interval to which the stored values are related.
     * @return a new group
     */
    public static ValuesGroupWrapper create(String name, TimeInterval timeInterval) {
        ValuesGroupWrapper res;
        res = new DefaultValuesGroupWrapper(name, timeInterval);
        return res;
    }

    @Override
    public void setParent(ValuesGroupWrapper groupWrapper) {
        this.parent=groupWrapper;
    }
    
    
}
