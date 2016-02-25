/*
 */

package cz.dfi.datamodel.graphable;

/**
 *
 * @author Tomas Prochazka
 * 22.12.2015
 */
public class DoubleQuantity  extends AbstractGraphableQuantity{
    protected double[] values;

    public DoubleQuantity(double[] values, String name, String unit, long[] onBoardTimeValues) {
        super(name, unit, onBoardTimeValues);
        this.values = values;
    }

    public DoubleQuantity(double[] values, String name, String unit, long[] onBoardTimeValues, boolean isMessageIncoming) {
        super(name, unit, onBoardTimeValues, isMessageIncoming);
        this.values = values;
    }

    public DoubleQuantity(double[] values, String name, String unit, long[] onBoardTimeValues, long[] recorderTimeValues) {
        super(name, unit, onBoardTimeValues, recorderTimeValues);
        this.values = values;
    }

    public DoubleQuantity(double[] values, String name, String unit, long[] onBoardTimeValues, long[] recorderTimeValues, boolean isMessageIncoming) {
        super(name, unit, onBoardTimeValues, recorderTimeValues, isMessageIncoming);
        this.values = values;
    }

    public DoubleQuantity(double[] values, long[] recorderTimeValues, String name, String unit) {
        super(recorderTimeValues, name, unit);
        this.values = values;
    }

    public DoubleQuantity(double[] values, long[] recorderTimeValues, String name, String unit, boolean isMessageIncoming) {
        super(recorderTimeValues, name, unit, isMessageIncoming);
        this.values = values;
    }

    
    
    @Override
    public double[] getValuesAsDoubles() {
        return values;
    }

}
