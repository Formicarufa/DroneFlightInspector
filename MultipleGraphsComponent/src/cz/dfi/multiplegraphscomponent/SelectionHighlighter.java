/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.dfi.multiplegraphscomponent;

import cz.dfi.recorddataprovider.FileLookup;
import cz.dfi.timecomponent.TimeIntervalSelectionImpl;
import cz.dfi.timecomponent.TimeValueSelectionImpl;
import cz.dfi.timecomponent.selection.TimeIntervalSelection;
import cz.dfi.timecomponent.selection.TimeSelection;
import cz.dfi.timecomponent.selection.TimeValueSelection;
import java.awt.Color;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 * 7.3.2016
 * @author Tomas Prochazka
 */
public final class SelectionHighlighter implements LookupListener{

    private static Lookup.Result<TimeSelection> result;
    private final XYPlot plot;

    protected SelectionHighlighter(XYPlot plot) {
        this.plot = plot;
        
    }
    public static SelectionHighlighter create(XYPlot plot){
        SelectionHighlighter highlighter = new SelectionHighlighter(plot);
        Lookup lkp = FileLookup.getDefault();
        result = lkp.lookupResult(TimeSelection.class);
        result.addLookupListener(highlighter);
        return highlighter;
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Lookup l = FileLookup.getDefault();
        TimeValueSelection value = l.lookup(TimeValueSelection.class);
        plot.clearDomainMarkers();
        if (value!=null) {                                                                      //To millis conversion
            ValueMarker valueMarker = new ValueMarker(value.getSelectedValue().getRecorderValue()/1_000_000); //!Hardcoded time stamp type 
            plot.addDomainMarker(valueMarker);
            valueMarker.setPaint(ORANGE);
            return;
        } 
        TimeIntervalSelection interval = l.lookup(TimeIntervalSelection.class);
        if (interval!=null) {
            long l1 = interval.getSelectedInterval().t1.getRecorderValue();//!Hardcoded time stamp type
            long l2 = interval.getSelectedInterval().t2.getRecorderValue();//!Hardcoded time stamp type
            IntervalMarker marker = new IntervalMarker(l1/1_000_000, l2/1_000_000); //To millis conversion
            marker.setPaint(ORANGE);
            marker.setAlpha(.3f);
            plot.addDomainMarker(marker);
        }
        
    }
    public static final Color ORANGE = new Color(255,115,0);
    
}
