/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dfi.datamodel.common;

import cz.dfi.datamodel.graphable.DoubleQuantity;
import cz.dfi.datamodel.series.TimeStampArray;

/**
 * Encapsulates a set of battery percentage values.
 *
 * @author Tomas Prochazka
 */
public class BatteryPercentWrapper extends DoubleQuantity {

    public BatteryPercentWrapper(double[] values, TimeStampArray timeStamps) {
        super(values, NAME, "percent", timeStamps);
    }

    public BatteryPercentWrapper(double[] values, TimeStampArray timeStamps, String unit) {
        super(values, NAME, unit, timeStamps);
    }
    public static final String NAME = "Battery level";

}
