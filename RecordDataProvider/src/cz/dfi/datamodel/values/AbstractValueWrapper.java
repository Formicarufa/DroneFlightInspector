/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dfi.datamodel.values;

import java.util.Collection;

/**
 *
 * @author Tomas Prochazka
 * @see ValueWrapper
 */
public abstract class AbstractValueWrapper implements ValueWrapper{
    protected final String name;
    protected final TimeStamp timeStamp;
    protected final TimeInterval timeInterval;
    protected ValuesGroupWrapper parent;

    public AbstractValueWrapper(String name, TimeInterval timeInterval) {
        this.name = name;
        this.timeStamp = null;
        this.timeInterval = timeInterval;
    }

    public AbstractValueWrapper(String name, TimeStamp timeStamp) {
        this.name = name;
        this.timeStamp = timeStamp;
        this.timeInterval=null;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public TimeStamp getTimeStamp() {
        return timeStamp;
    }

    @Override
    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    @Override
    public ValuesGroupWrapper getParent() {
        return parent;
    }

    @Override
    public Collection<ValueWrapper> getChildren() {
        return null;
    }


    @Override
    public void setParent(ValuesGroupWrapper groupWrapper) {
        this.parent=groupWrapper;
    }
    
}
