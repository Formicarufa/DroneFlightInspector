/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dfi.datamodel.graphable;

import cz.dfi.datamodel.values.AbstractValueWrapper;
import cz.dfi.datamodel.values.TimeInterval;
import cz.dfi.datamodel.values.TimeStamp;

/**
 *
 * @author Tomas Prochazka
 */
public class DoubleValueWrapper extends AbstractValueWrapper{
    double value;
    public DoubleValueWrapper(String name, TimeStamp timeStamp, double value) {
        super(name, timeStamp);
        this.value=value;
    }
    public DoubleValueWrapper(String name, TimeInterval timeInterval, double value) {
        super(name, timeInterval);
        this.value=value;
    }

    @Override
    public String getValueAsString() {
        return Double.toString(value);
    }

    
    
}
