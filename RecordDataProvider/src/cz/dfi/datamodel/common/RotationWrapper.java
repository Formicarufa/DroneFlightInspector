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
        this(timeStamps);
        this.addChildren(
                new DoubleQuantity(rotX, "Roll (left-right tilt)", unit, timeStamps),
                new DoubleQuantity(rotY, "Pitch (forward-backward tilt)", unit, timeStamps),
                new DoubleQuantity(rotZ, "Yaw (z rotation)", unit, timeStamps)
        );
    }

        /**
     * Make sure that double quantities representing rotation values in x (roll), y(pitch), z (yaw) are added
     * into this container using {@link #addChild} or 
     * {@link #addChildren(cz.dfi.datamodel.series.SeriesWrapper...) } methods.
     * To do that automatically, use the 
     * {@link #MagnetometerWrapper(java.lang.String, cz.dfi.datamodel.series.TimeStampArray, double[], double[], double[]) other constructor}.
     * @param timeStamps 
     */
    public RotationWrapper(TimeStampArray timeStamps) {
        super(NAME, timeStamps);
    }
    public static final String NAME = "Rotation";

}
