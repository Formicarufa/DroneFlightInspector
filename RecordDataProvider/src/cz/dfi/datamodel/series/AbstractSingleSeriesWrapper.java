/*
 */

package cz.dfi.datamodel.series;

import cz.dfi.datamodel.TimeStampType;

/**
 * Represents a series which has a parent: a group to which it belongs
 * and therefore it does not need its own time information.
 * (If the time information is requested the corresponding parent method is called.)
 * It follows that each instance needs to be put into the group.
 * It is advised to make constructor of each inheritor class protected
 * and for creating of an instance use rather a static method 'create' which also
 * puts the object into a given group.
 * 
 * @author Tomas Prochazka
 * 28.2.2016
 */
public abstract class AbstractSingleSeriesWrapper implements SingleSeriesWrapper{
    protected SeriesGroupWrapper parent;
    protected final String name;

    public AbstractSingleSeriesWrapper(String name) {
        this.name = name;
    }
    

    @Override
    public void setParent(SeriesGroupWrapper parent) {
        this.parent=null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public SeriesGroupWrapper getParent() {
        return parent;
    }    
}
