/*
 */

package cz.dfi.datamodel.graphable;

import cz.dfi.datamodel.graphable.AbstractGraphableQuantity;

/**
 *
 * @author Tomas Prochazka
 * 22.12.2015
 */
public class DoubleQuantity  extends AbstractGraphableQuantity{
    protected double[] values;

    public DoubleQuantity(double[] values, String name, String unit, double[] onBoardTimeValues) {
        super(name, unit, onBoardTimeValues);
        this.values = values;
    }

    public DoubleQuantity(double[] values, String name, String unit, double[] onBoardTimeValues, boolean isMessageIncoming) {
        super(name, unit, onBoardTimeValues, isMessageIncoming);
        this.values = values;
    }

    public DoubleQuantity(double[] values, String name, String unit, double[] onBoardTimeValues, double[] recorderTimeValues) {
        super(name, unit, onBoardTimeValues, recorderTimeValues);
        this.values = values;
    }

    public DoubleQuantity(double[] values, String name, String unit, double[] onBoardTimeValues, double[] recorderTimeValues, boolean isMessageIncoming) {
        super(name, unit, onBoardTimeValues, recorderTimeValues, isMessageIncoming);
        this.values = values;
    }

    public DoubleQuantity(double[] values, double[] recorderTimeValues, String name, String unit) {
        super(recorderTimeValues, name, unit);
        this.values = values;
    }

    public DoubleQuantity(double[] values, double[] recorderTimeValues, String name, String unit, boolean isMessageIncoming) {
        super(recorderTimeValues, name, unit, isMessageIncoming);
        this.values = values;
    }

    
    
    @Override
    public double[] getValuesAsDoubles() {
        return values;
    }

}
