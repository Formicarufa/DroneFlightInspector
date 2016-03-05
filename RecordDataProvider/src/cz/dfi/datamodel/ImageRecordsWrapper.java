/*
 */
package cz.dfi.datamodel;

import cz.dfi.datamodel.series.SeriesGroupWrapper;
import cz.dfi.datamodel.series.SeriesWrapper;
import cz.dfi.datamodel.series.TimeStampArray;
import cz.dfi.datamodel.values.ValueWrapper;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Tomas Prochazka 8.12.2015
 */
public class ImageRecordsWrapper implements SeriesWrapper {

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setParent(SeriesGroupWrapper parent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<SeriesWrapper> getChildren() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ValueWrapper getValue(long time, TimeStampType timeType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<ValueWrapper> getIntervalSummary(long t1, long t2, TimeStampType timeType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
