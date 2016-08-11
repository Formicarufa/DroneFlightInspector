/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dfi.timecomponent.selectionimpl;

import cz.dfi.timeselection.SetTimeRequest;
import cz.dfi.datamodel.values.TimeInterval;
import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jtimeselector.JTimeSelector;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.lookup.InstanceContent;

/**
 * 24.6.2016
 *This class processes the set time requests that other Drone
 * Flight Inspector modules put into the lookup.
 * The set time request change the selection on the JTimeSelector.
 * As a result, the time selector generates update event and
 * time selection change is handled by {@link SelectionProvider}
 * @author Tomas Prochazka
 */
public class SetTimeRequestsReceiver implements LookupListener {

    private final JTimeSelector timeSelector;
    private final Lookup fileLookup;
    private final Lookup.Result<SetTimeRequest> requests;
    private final InstanceContent lookupContent;

    public SetTimeRequestsReceiver(JTimeSelector timeSelector, Lookup fileLookup, InstanceContent lookupContent) {
        this.timeSelector = timeSelector;
        this.fileLookup = fileLookup;
        this.lookupContent = lookupContent;
        requests = this.fileLookup.lookupResult(SetTimeRequest.class);
        requests.addLookupListener(this);
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        SetTimeRequest request = fileLookup.lookup(SetTimeRequest.class);
        if (request == null) {
            return;
        }
        setTime(request);
        lookupContent.remove(request);
    }

    private void setTime(SetTimeRequest request) {
        final TimeInterval interval = request.getInterval();
        if (interval != null) {
            //Hardcoded time stamp type: it is presumed that the Time selection component uses the recorder time stamps
            Long t1 = interval.t1.getRecorderValue();
            Long t2 = interval.t2.getRecorderValue();
            if (t1 == null || t2 == null) {
                Logger.getLogger(SetTimeRequestsReceiver.class.getName()).log(Level.SEVERE, "Unable to set time interval on the time selection component (from outside), recorder time value is not available.");
                return;
            }
            selectIntervalOnEventThread(t1, t2);
        } else {
            Long t1 = request.getTime().getRecorderValue();
            if (t1 == null) {
                Logger.getLogger(SetTimeRequestsReceiver.class.getName()).log(Level.SEVERE, "Unable to set time on the time selection component (from outside), recorder time value is not available.");
                return;
            }
            selectTimeOnEventThread(t1);

        }
    }

    private void selectIntervalOnEventThread(Long t1, Long t2) {
        if (EventQueue.isDispatchThread()) {
            timeSelector.selectTimeInterval(t1, t2);
        } else {
            try {
                EventQueue.invokeAndWait(() -> {
                    timeSelector.selectTimeInterval(t1, t2);
                });
            } catch (InterruptedException | InvocationTargetException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

    private void selectTimeOnEventThread(Long t1) {
        if (EventQueue.isDispatchThread()) {
            timeSelector.selectTime(t1);
        } else {
            try {
                //We wait because we do not want to flood the EventQueue
                // with too many requests.
                EventQueue.invokeAndWait(() -> {
                    timeSelector.selectTime(t1);
                });
            } catch (InterruptedException | InvocationTargetException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
    }

}
