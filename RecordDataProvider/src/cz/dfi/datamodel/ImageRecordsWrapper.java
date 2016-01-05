/*
 */
package cz.dfi.datamodel;

import java.util.List;

/**
 *
 * @author Tomas Prochazka 8.12.2015
 */
public class ImageRecordsWrapper implements RecordsWrapper {

    List<ImageDataRecord> records;

    public ImageRecordsWrapper(List<ImageDataRecord> images) {
        this.records = images;
    }

    @Override
    public double[] getOnBoardTimeValues() {
        return null;
    }

    @Override
    public String getName() {
        return "Video stream";
    }
    double[] recordedTimeValues;

    @Override
    public double[] getTimeOfRecordValues() {
        if (recordedTimeValues == null) {
            recordedTimeValues = new double[records.size()];
            for (int i = 0; i < records.size(); i++) {
                recordedTimeValues[i] = records.get(i).time / 1_000_000.0;
            }
        }
        return recordedTimeValues;
    }

    @Override
    public TimeStampType getOriginalTimeStampSource() {
        return TimeStampType.Both;
    }

}
