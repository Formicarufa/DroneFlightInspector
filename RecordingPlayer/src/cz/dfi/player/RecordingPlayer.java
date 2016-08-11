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
import cz.dfi.datamodel.values.TimeInterval;
import cz.dfi.datamodel.values.TimeStamp;
import cz.dfi.timeselection.TimeIntervalSelection;
import cz.dfi.timeselection.TimeValueSelection;
import java.util.Collection;
import java.util.stream.Stream;
import org.openide.util.*;
import org.openide.util.lookup.InstanceContent;

/**
 * 23.6.2016
 * An action that enables replay of the recording.
 * Runs on a separate thread, continuously moves the time selection.
 * <p>
 * Can be stopped by another thread by calling the {@link #stop() } method.
 * @author Tomas Prochazka
 */
public class RecordingPlayer implements Runnable {

    Lookup lookup;
    InstanceContent content;
    private TimeInterval selectedInterval;
    private TimeStamp from;
    private TimeStamp to;
    private volatile boolean stopped = false;
    private RewindCookie rewind;
    private StopCookie stopCookie;

    public RecordingPlayer(Lookup lookup, InstanceContent content) {
        this.lookup = lookup;
        this.content = content;
        stopCookie = new StopCookie(this);
    }

    public void stop() {
        stopped = true;
    }

    @Override
    public void run() {
        getFromAndTo();
        disablePlayAndRewindButtons();
        enableStopButton();
        play();
        disableStopButton();
        resetSelection();
        enablePlayAndRewindButtons();

    }

    private void getFromAndTo() {
        TimeIntervalSelection interval = lookup.lookup(TimeIntervalSelection.class);
        if (interval != null) {
            selectedInterval = interval.getSelectedInterval();
            from = selectedInterval.t1;
            to = selectedInterval.t2;
            return;
        }
        selectedInterval = null;
        Collection<? extends TimeSelectionLayer> layers = lookup.lookupAll(TimeSelectionLayer.class);
        Stream<TimeStampArray> timeStampArrays = layers.stream().map(x -> x.getTimeStamps());
        Long maxRecorderValue = TimeStampArray.getMaxRecorderValue(timeStampArrays);
        to = new TimeStamp(maxRecorderValue, TimeStampType.TimeOfRecord, true); //bool incoming is not used.
        //assuming time selection component uses recorder time values.
        TimeValueSelection singleSelection = lookup.lookup(TimeValueSelection.class);
        if (singleSelection != null) {
            from = singleSelection.getSelectedValue();
        } else {
            timeStampArrays = layers.stream().map(x -> x.getTimeStamps());
            Long minRecorderValue = TimeStampArray.getMinRecorderValue(timeStampArrays);
            from = new TimeStamp(minRecorderValue, TimeStampType.TimeOfRecord, true);
        }

    }

    private void disablePlayAndRewindButtons() {
        content.remove(this);

        rewind = lookup.lookup(RewindCookie.class);
        if (rewind != null) {
            content.remove(rewind);
        }
    }

    private void enableStopButton() {
        content.add(stopCookie);
    }
    public static final long TIME_STEP = 50;//ms

    private void play() {
        long time = from.getRecorderValue();
        long max = to.getRecorderValue();
        long lastTime = System.nanoTime();
        while (time < max) {
            long delay = (System.nanoTime() - lastTime) / 1_000_000;
            long waitTime = Math.max(TIME_STEP - delay, 10);
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException ex) {
                Exceptions.printStackTrace(ex);
            }
            if (stopped) {
                return;
            }
            time = time + (System.nanoTime() - lastTime);
            if (time > max) {
                time = max;
            }
            lastTime = System.nanoTime();
            SetTimeRequest request = new SetTimeRequest(new TimeStamp(time, TimeStampType.TimeOfRecord, true));
            content.add(request);
        }
    }

    private void disableStopButton() {
        content.remove(stopCookie);
    }

    private void resetSelection() {
        if (!stopped) {

            if (selectedInterval != null) {
                SetTimeRequest request = new SetTimeRequest(selectedInterval);
                content.add(request);
            } else {
                SetTimeRequest request = new SetTimeRequest(from);
                content.add(request);
            }
        }
    }

    private void enablePlayAndRewindButtons() {
        stopped = false;
        content.add(this);
        if (rewind != null) {
            content.add(rewind);
            rewind = null;
        }
    }

}
