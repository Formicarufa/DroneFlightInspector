/*
 */

package cz.dfi.datamodel.series;

import java.util.ArrayList;
import java.util.Collection;
import org.netbeans.api.annotations.common.NonNull;

/**
 * Represent a group of series which share the time stamps.
 * The preferred approach of creating groups is to use the 
 * static methods {@link #create(java.lang.String, cz.dfi.datamodel.series.SeriesGroupWrapper) 
 * or {@link #create(java.lang.String, cz.dfi.datamodel.series.TimeStampArray) }
 * @author Tomas Prochazka
 * @see cz.dfi.datamodel
 * 27.2.2016
 */
public abstract class SeriesGroupWrapper implements SeriesWrapper{
    protected final String name;
    private final ArrayList<SeriesWrapper> children = new ArrayList<>();
    protected SeriesGroupWrapper parent=null;

    public SeriesGroupWrapper(String name) {
        this.name = name;
    }
    
    
    /**
     * Adds a member to the group.
     * Child's parent is updated automatically.
     * @param child a new member of the group
     */
    public void addChild(SeriesWrapper child) {
        children.add(child);
        child.setParent(this);
    }

  

    @Override
    public String getName() {
        return name;
    }

  
    @Override
    public SeriesGroupWrapper getParent() {
        return parent;
    }

    @Override
    public Collection<SeriesWrapper> getChildren() {
        return children;
    }

    @Override
    public void setParent(SeriesGroupWrapper parent) {
        this.parent=parent;
    }

        /**
     * Creates a new group which may contain multiple value series with a common time header.
     * @param name Name of the group
     * @param timeStamps Array of time values associated with the values of the members of the group.
     * @return A new group
     */
    public static SeriesGroupWrapper create(String name,TimeStampArray timeStamps) {
        return new TopLevelSeriesGroupWrapper(name, timeStamps);
    }
            /**
     * Creates a new group which may contain multiple value series with a common time header.
     * The group is displayed on the TimeSelection component as a separate layer.
     * @param name Name of the group
     * @param timeStamps Array of time values associated with the values of the members of the group.
     * @return A new group
     */
    public static SeriesGroupWrapper create_timelineLayer(String name,TimeStampArray timeStamps) {
        return new SelectionLayerGroupWrapper(name, timeStamps);
    }
    /**
   * Creates a new group which may contain multiple value series with a common time header.
   * The group is inserted as a child into the given group.
   * Time information does not have to be provided, because it can be obtained from parent.
     * @param name Name of the new group.
     * @param parent Parent group
     * @return  A new group
     */
    public static SeriesGroupWrapper create(String name, @NonNull SeriesGroupWrapper parent) {
        SeriesGroupWrapper result = new NestedSeriesGroupWrapper(name);
        parent.addChild(result);
        return result;
    }
    public void addChildren(SeriesWrapper... children) {
        for (SeriesWrapper seriesWrapper : children) {
            addChild(seriesWrapper);
        }
    }

    
}
