/*
 */
package cz.dfi.datamodel;

import cz.dfi.datamodel.series.SeriesGroupWrapper;
import cz.dfi.datamodel.series.SeriesWrapper;
import cz.dfi.datamodel.series.TimeStampArray;
import cz.dfi.datamodel.values.TimeInterval;
import cz.dfi.datamodel.values.TimeStamp;
import cz.dfi.datamodel.values.ValueWrapper;
import cz.dfi.datamodel.values.ValuesGroupWrapper;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Tomas Prochazka 8.12.2015
 */
public class ImageRecordsWrapper implements SeriesWrapper,TimeSelectionLayer {

    List<ImageDataRecord> records;
    TimeStampArray timeStamps=null;
    public ImageRecordsWrapper(List<ImageDataRecord> images) {
        this.records = images;
    }


    @Override
    public String getName() {
        return "Video stream";
    }

  
   

    @Override
    public SeriesGroupWrapper getParent() {
        return null;
    }

    @Override
    public void setParent(SeriesGroupWrapper parent) {
        throw new UnsupportedOperationException("Not supported."); 
    }

    @Override
    public Collection<SeriesWrapper> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public ValueWrapper getValue(long time, TimeStampType timeType) {
        final TimeStamp t =getTimeStamps().getClosestTimeStamp(time, timeType);
        return new ValueWrapper() {
            @Override
            public String getName() {
                return "image";
            }

            @Override
            public TimeStamp getTimeStamp() {
                return t;
            }

            @Override
            public TimeInterval getTimeInterval() {
                return null;
            }

            @Override
            public ValuesGroupWrapper getParent() {
                return null;
            }

            @Override
            public Collection<ValueWrapper> getChildren() {
                return Collections.emptyList();
            }

            @Override
            public String getValueAsString() {
                return null;
            }

            @Override
            public void setParent(ValuesGroupWrapper groupWrapper) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }

    @Override
    public Collection<ValueWrapper> getIntervalSummary(long t1, long t2, TimeStampType timeType) {
        return Collections.emptyList();
    }

    @Override
    public TimeStampArray getTimeStamps() {
        if (timeStamps==null) {
            long[] recordedTimeValues = new long[records.size()];
            for (int i = 0; i < records.size(); i++) {
                recordedTimeValues[i] = records.get(i).time;
            }
            timeStamps=new TimeStampArray(recordedTimeValues, TimeStampType.TimeOfRecord, true);
        }
        return timeStamps;
    }

}
