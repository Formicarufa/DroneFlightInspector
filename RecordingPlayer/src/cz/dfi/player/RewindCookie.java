/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dfi.player;

import cz.dfi.timeselection.SetTimeRequest;
import cz.dfi.datamodel.TimeSelectionLayer;
import cz.dfi.datamodel.TimeStampType;
import cz.dfi.datamodel.series.TimeStampArray;
import cz.dfi.datamodel.values.TimeStamp;
import java.util.Collection;
import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;

/**
 *Class which is activated when clicked on a rewind button.
 * The selected time is set to minimal possible value.
 * @author Tomas Prochazka
 */
public class RewindCookie {

    Lookup lookup;
    InstanceContent instanceContent;

    public RewindCookie(Lookup lookup, InstanceContent instanceContent) {
        this.lookup = lookup;
        this.instanceContent = instanceContent;
    }

    public void rewind() {
        Collection<? extends TimeSelectionLayer> layers = lookup.lookupAll(TimeSelectionLayer.class);
        Long minRecorderValue = TimeStampArray.getMinRecorderValue(layers.stream().map(x -> x.getTimeStamps()));
        if (minRecorderValue==null) return;
        TimeStamp from = new TimeStamp(minRecorderValue, TimeStampType.TimeOfRecord, true);
        SetTimeRequest request = new SetTimeRequest(from);
        instanceContent.add(request);
    }
}
