/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.dfi.datamodel.common;

import cz.dfi.datamodel.graphable.DoubleQuantity;
import cz.dfi.datamodel.series.TimeStampArray;
import cz.dfi.datamodel.series.TopLevelSeriesGroupWrapper;

/**
 * 6.3.2016
 * @author Tomas Prochazka
 */
public class RotationWrapper extends TopLevelSeriesGroupWrapper{
    /**
     * 
     * @param unit  e.g. "degrees"
     * @param timeStamps
     * @param rotX Roll - left-right tilt
     * @param rotY Pitch - forward-backward tilt
     * @param rotZ Yaw - rotation about the z axis
     */
    public RotationWrapper(String unit, TimeStampArray timeStamps, double [] rotX, double [] rotY, double [] rotZ){
        super("Rotation ", timeStamps);
        this.addChildren(
                new DoubleQuantity(rotX, "Roll (left-right tilt)", unit, timeStamps),
                new DoubleQuantity(rotY, "Pitch (forward-backward tilt)", unit, timeStamps),
                new DoubleQuantity(rotZ, "Yaw (z rotation)", unit, timeStamps)
        );
    }

}
