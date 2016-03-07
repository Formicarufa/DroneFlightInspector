/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dfi.timecomponent;

import cz.dfi.datamodel.TimeStampType;
import cz.dfi.datamodel.series.SeriesWrapper;
import cz.dfi.datamodel.values.TimeInterval;
import cz.dfi.datamodel.values.TimeStamp;
import cz.dfi.datamodel.values.ValueWrapper;
import cz.dfi.recorddataprovider.FileLookupProvider;
import cz.dfi.recorddataprovider.RecordFile;
import cz.dfi.timecomponent.selection.TimeSelection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import jtimeselector.JTimeSelector;
import jtimeselector.LongRange;
import jtimeselector.TimeSelectionListener;
import jtimeselector.TimeSelectionType;
import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;

/**
 * Reacts on jTimSelector's timeSelectionChanged event. When some time value is
 * selected, the SelectionProvider puts all values that are associated with this
 * time into the lookup, so that all application components can use it. 6.3.2016
 *
 * @author Tomas Prochazka
 */
class SelectionProvider implements TimeSelectionListener {

    List<ValueWrapper> providedValues = new ArrayList<>();
    Long selectedTime = null;
    TimeSelectionType type = TimeSelectionType.None;
    LongRange selectedInterval = null;
    TimeSelection s = null;

    public SelectionProvider() {
    }

    @Override
    public void timeSelectionChanged(JTimeSelector jts) {
        RecordFile selectedFile = FileLookupProvider.getSelectedFile();
        if (!checkChange(jts)) {
            return;
        }
        final InstanceContent content = selectedFile.getLookupContent();
        Lookup lkp = selectedFile.getLookup();
        removeOldValues(content);
        if (s != null) {
            content.remove(s);
        }
        switch (jts.getTimeSelectionType()) {
            case SingleValue:
                selectedTime = jts.getSelectedTime();
                addNewValues(selectedTime, lkp, content);
                s = new TimeValueSelectionImpl(new TimeStamp(selectedTime, TimeStampType.TimeOfRecord, true)); //!Hardcoded time stamp type
                content.add(s);
                break;
            case Interval:
                selectedInterval = jts.getSelectedTimeInterval();
                addNewValues(selectedInterval, lkp, content);
                TimeInterval timeInterval = new TimeInterval(new TimeStamp(selectedInterval.a, TimeStampType.TimeOfRecord, true), //!Hardcoded time stamp type
                        new TimeStamp(selectedInterval.b, TimeStampType.TimeOfRecord, true));                                      //!Hardcoded time stamp type
                s = new TimeIntervalSelectionImpl(timeInterval);
                content.add(s);
                break;
            case None:
                break;
            default:
                throw new AssertionError();
        }

    }

    private boolean checkChange(JTimeSelector jts) {
        TimeSelectionType timeSelectionType = jts.getTimeSelectionType();
        if (type != timeSelectionType) {
            return true;
        }
        switch (timeSelectionType) {
            case SingleValue:
                if (!Objects.equals(jts.getSelectedTime(), selectedTime)) {
                    return true;
                }
                return false;
            case Interval:
                if (!rangeEquals(jts.getSelectedTimeInterval(), selectedInterval)) {
                    return true;
                }
            case None:
            default:
                return false;
        }
    }

    private boolean rangeEquals(LongRange first, LongRange second) {
        if (first == null) {
            return second == null;
        }
        if (second == null) {
            return false;
        }
        return first.a == second.a && first.b == second.b;
    }

    private void removeOldValues(InstanceContent lookupContent) {
        if (providedValues.isEmpty()) {
            return;
        }
        for (ValueWrapper providedValue : providedValues) {
            removeTree(providedValue, lookupContent);
        }
    }

    private void removeTree(ValueWrapper providedValue, InstanceContent lookupContent) {
        lookupContent.remove(providedValue);
        if (providedValue.getChildren() != null) {
            for (ValueWrapper valueWrapper : providedValue.getChildren()) {
                removeTree(valueWrapper, lookupContent);
            }
        }

    }

    private void addNewValues(Long selectedTime, Lookup lkp, InstanceContent content) {
        if (selectedTime == null) {
            throw new IllegalStateException("Selected time can't be null.");
        }
        Collection<? extends SeriesWrapper> series = lkp.lookupAll(SeriesWrapper.class);
        series.stream()
                .filter((s) -> (s.getParent() == null)) //work only with root elements (without parent)
                .forEach((s) -> {
                    addSeriesTree(s, content, selectedTime);
                });
    }

    private void addNewValues(LongRange selectedInterval, Lookup lkp, InstanceContent content) {
        if (selectedInterval == null) {
            throw new IllegalStateException("Selected time interval can't be null.");
        }
        Collection<? extends SeriesWrapper> seriesWrappers = lkp.lookupAll(SeriesWrapper.class);
        seriesWrappers.stream().filter((s) -> (s.getParent() == null))
                .forEach((s) -> {
                    addSeriesTree(s, content, selectedInterval);
                });
    }

    private void addSeriesTree(SeriesWrapper s, InstanceContent cont, long l) {
        ValueWrapper value = s.getValue(l, TimeStampType.TimeOfRecord); //!Hardcoded time stamp type.
        addTreeToLookup(value, cont);
        providedValues.add(value);
    }

    private void addSeriesTree(SeriesWrapper s, InstanceContent cont, LongRange l) {
        Collection<ValueWrapper> intervalSummary = s.getIntervalSummary(l.a, l.b, TimeStampType.TimeOfRecord); //!Hardcoded time stamp type.
        for (ValueWrapper summary : intervalSummary) {
            addTreeToLookup(summary, cont);
            providedValues.add(summary);
        }

    }

    private void addTreeToLookup(ValueWrapper value, InstanceContent cont) {
        cont.add(value);
        final Collection<ValueWrapper> children = value.getChildren();
        if (children != null) {
            for (ValueWrapper child : children) {
                addTreeToLookup(child, cont);
            }
        }
    }

}
