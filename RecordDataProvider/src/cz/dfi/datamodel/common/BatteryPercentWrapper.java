/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dfi.datamodel.common;

import cz.dfi.datamodel.graphable.DoubleQuantity;
import cz.dfi.datamodel.series.TimeStampArray;

/**
 * Encapsulates a set of battery level values in range from 0 to 100.
 * @author Tomas Prochazka
 */
public class BatteryPercentWrapper extends DoubleQuantity {
    
    public BatteryPercentWrapper(double[] values, TimeStampArray timeStamps) {
        super(values, "Battery", "percent", timeStamps);
    }
    
}
