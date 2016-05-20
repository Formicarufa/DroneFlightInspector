/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.dfi.ardronerosbag;

import cz.dfi.datamodel.ImportHelper;
import cz.dfi.datamodel.TimeStampType;
import cz.dfi.datamodel.graphable.DoubleQuantity;
import cz.dfi.datamodel.series.SeriesGroupWrapper;
import cz.dfi.datamodel.series.TimeStampArray;
import cz.dfi.datamodel.series.TopLevelSeriesGroupWrapper;
import java.util.List;
import org.openide.util.lookup.InstanceContent;

/**
 * 7.3.2016
 * @author Tomas Prochazka
 */
public class ArdroneCommandsModel {

    double[] pitch;
    double[]roll;
    double[]yaw;
    double[] vertical;
    private final TimeStampArray timeStamps;
    
    public ArdroneCommandsModel(List<Double>pitch, List<Long> recorderTimeValues, List<Double> roll, List<Double> yaw, List<Double> vertical) {
      
        this.pitch = pitch.stream().mapToDouble(x->x).toArray();
        this.roll = roll.stream().mapToDouble(x->x).toArray();
        this.yaw=yaw.stream().mapToDouble(x->x).toArray();
        this.vertical=vertical.stream().mapToDouble(x->x).toArray();
        long[] timeVals = recorderTimeValues.stream().mapToLong(x->x).toArray();
        timeStamps = new TimeStampArray(timeVals, TimeStampType.TimeOfRecord, false);
    }
    void construct(InstanceContent content) {
        SeriesGroupWrapper commands = SeriesGroupWrapper.create_timelineLayer("Commands ", timeStamps);
        commands.addChildren(
                new DoubleQuantity(pitch, "Pitch (forward/backward tilt) command", "-1...1", timeStamps),
                new DoubleQuantity(roll, "Roll (left/right tilt) command", "-1 ... +1", timeStamps),
                new DoubleQuantity(yaw, "Yaw (rotation about z) command", "-1...1", timeStamps),
                new DoubleQuantity(vertical, "Vertical speed command", "-1...1", timeStamps)
        );
        ImportHelper.addTreeToLookup(commands, content);
    }
    
}
