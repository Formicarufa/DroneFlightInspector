/*
 */
package cz.dfi.datamodel.graphable;

import cz.dfi.datamodel.TimeStampType;
import cz.dfi.datamodel.TimeValuesConverter;
import cz.dfi.recorddataprovider.CurrentFileLookupProvider;
import java.util.Collection;
import org.openide.util.Lookup;

/**
 *
 * @author Tomas Prochazka 21.12.2015
 */
public abstract class AbstractGraphableQuantity implements GraphableQuantity {

    protected String name;
    protected double[] onBoardTimeValues;
    protected double[] recorderTimeValues;
    protected TimeStampType originalTimeStamp;
    protected boolean isMessageIncoming;
    protected String unit;

    /**
     * Message default type is incoming: that means message is created by drone
     * and sent to the recorder device. If it is not, use constructor where type
     * can be specified.
     *
     * @param name
     * @param unit
     * @param onBoardTimeValues
     */
    public AbstractGraphableQuantity(String name, String unit, double[] onBoardTimeValues) {
        this.name = name;
        this.onBoardTimeValues = onBoardTimeValues;
        this.originalTimeStamp = TimeStampType.OnBoardTime;
        isMessageIncoming = true;
        this.unit=unit;
    }

    public AbstractGraphableQuantity(String name, String unit, double[] onBoardTimeValues, boolean isMessageIncoming) {
        this.name = name;
        this.onBoardTimeValues = onBoardTimeValues;
        this.isMessageIncoming = isMessageIncoming;
        this.originalTimeStamp = TimeStampType.OnBoardTime;
        this.unit=unit;        
    }

    /**
     * Message default type is incoming: that means message is created by drone
     * and sent to the recorder device. If it is not, use constructor where type
     * can be specified.
     *
     * @param name
     * @param unit
     * @param onBoardTimeValues
     * @param recorderTimeValues
     */
    public AbstractGraphableQuantity(String name, String unit, double[] onBoardTimeValues, double[] recorderTimeValues) {
        this.name = name;
        this.onBoardTimeValues = onBoardTimeValues;
        this.recorderTimeValues = recorderTimeValues;
        this.originalTimeStamp = TimeStampType.Both;
        isMessageIncoming = true;
        this.unit=unit;
    }

    public AbstractGraphableQuantity(String name, String unit, double[] onBoardTimeValues, double[] recorderTimeValues, boolean isMessageIncoming) {
        this.name = name;
        this.onBoardTimeValues = onBoardTimeValues;
        this.recorderTimeValues = recorderTimeValues;
        this.isMessageIncoming = isMessageIncoming;
        this.originalTimeStamp = TimeStampType.Both;
        this.unit=unit;
    }

    /**
     * Message default type is incoming: that means message is created by drone
     * and sent to the recorder device. If it is not, use constructor where type
     * can be specified.
     *
     * @param recorderTimeValues
     * @param name
     * @param unit
     */
    public AbstractGraphableQuantity(double[] recorderTimeValues, String name, String unit) {
        this.name = name;
        this.recorderTimeValues = recorderTimeValues;
        this.originalTimeStamp = TimeStampType.TimeOfRecord;
        this.unit=unit;
    }

    public AbstractGraphableQuantity(double[] recorderTimeValues, String name, String unit, boolean isMessageIncoming) {
        this.name = name;
        this.recorderTimeValues = recorderTimeValues;
        this.isMessageIncoming = isMessageIncoming;
        this.originalTimeStamp = TimeStampType.TimeOfRecord;
        this.unit=unit;
    }

    @Override
    public double[] getOnBoardTimeValues() {
        if (onBoardTimeValues != null) {
            return onBoardTimeValues;
        }
        //Try to convert.
        CurrentFileLookupProvider fileProvider = Lookup.getDefault().lookup(CurrentFileLookupProvider.class);
        Collection<? extends TimeValuesConverter> converters;
        final Lookup currentFileLookup = fileProvider.getCurrentFileLookup();
        if (currentFileLookup == null) {
            return null;
        }
        converters = currentFileLookup.lookupAll(TimeValuesConverter.class);
        for (TimeValuesConverter converter : converters) {
            recorderTimeValues = converter.recordTimeToBoardTime(onBoardTimeValues, !isMessageIncoming);
            if (recorderTimeValues != null) {
                return recorderTimeValues;
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double[] getTimeOfRecordValues() {
        if (recorderTimeValues != null) {
            return recorderTimeValues;
        }
        //Try to convert.
        CurrentFileLookupProvider fileProvider = Lookup.getDefault().lookup(CurrentFileLookupProvider.class);
        Collection<? extends TimeValuesConverter> converters;
        final Lookup currentFileLookup = fileProvider.getCurrentFileLookup();
        if (currentFileLookup == null) {
            return null;
        }
        converters = currentFileLookup.lookupAll(TimeValuesConverter.class);
        for (TimeValuesConverter converter : converters) {
            recorderTimeValues = converter.boardTimeToRecordTime(onBoardTimeValues, !isMessageIncoming);
            if (recorderTimeValues != null) {
                return recorderTimeValues;
            }
        }
        return null;
    }

    @Override
    public TimeStampType getOriginalTimeStampSource() {
        return originalTimeStamp;
    }

    @Override
    public String getUnit() {
        return unit;
    }
    

}
