/*
 */

package cz.dfi.datamodel.series;

/**
 * Represents a series of values.
 *
 * @see cz.dfi.datamodel
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
        this.parent=parent;
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
