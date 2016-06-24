/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.dfi.datamodel;

import cz.dfi.datamodel.values.TimeInterval;
import cz.dfi.datamodel.values.TimeStamp;
import org.netbeans.api.annotations.common.NullUnknown;

/**
 * If a component wants to change the currently selected time it should issue
 * an instance of this class (by putting it into the file lookup).
 * It is possible to select single time value as well as time interval.
 * 24.6.2016
 * @author Tomas Prochazka
 */
public class SetTimeRequest {
    private TimeStamp time=null;
    private TimeInterval interval=null;

    public SetTimeRequest(TimeStamp time) {
        this.time = time;
    }

    public SetTimeRequest(TimeInterval interval) {
        this.interval = interval;
    }

    /**
     * Method might return null, 
     * but one of the two getters will always return non-null value.
     * @return 
     */
    @NullUnknown public TimeStamp getTime() {
        return time;
    }
    /**
     * Method might return null, 
     * but one of the two getters will always return non-null value.
     * @return 
     */
    @NullUnknown public TimeInterval getInterval() {
        return interval;
    }
    
}
