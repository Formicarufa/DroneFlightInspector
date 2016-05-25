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
    private final double value;
    private final String unit;
    
    public DoubleValueWrapper(String name, TimeStamp timeStamp, double value, String unit) {
        super(name, timeStamp);
        this.value=value;
        this.unit = unit;
    }
    public DoubleValueWrapper(String name, TimeInterval timeInterval, double value, String unit) {
        super(name, timeInterval);
        this.value=value;
        this.unit = unit;
    }

    @Override
    public String getValueAsString() {
        return Double.toString(value);
    }

    public double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }
    
    
    
    
}
