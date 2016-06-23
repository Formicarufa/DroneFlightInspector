/*
 */
package cz.dfi.datamodel.common;

import cz.dfi.datamodel.graphable.DoubleQuantity;
import cz.dfi.datamodel.series.TimeStampArray;
import cz.dfi.datamodel.series.TopLevelSeriesGroupWrapper;
import org.netbeans.api.annotations.common.CheckForNull;
import org.netbeans.api.annotations.common.NullUnknown;

/**
 * Unit convention: arbitrary unit PER SECOND (this is assumed by the trajectory algorithm.)
 * @author Tomas Prochazka 24.12.2015
 */
public class SpeedWrapper extends TopLevelSeriesGroupWrapper {

    private DoubleQuantity speedx;
    private DoubleQuantity speedy;
    private DoubleQuantity speedz;

    public SpeedWrapper(String unit, TimeStampArray timeStamps, double[] xValues, double[] yValues, double[] zValues) {
        this(timeStamps);
        this.addChildren(
                new DoubleQuantity(xValues, "Speed in x", unit, timeStamps),
                new DoubleQuantity(yValues, "Speed in y", unit, timeStamps),
                new DoubleQuantity(zValues, "Speed in z", unit, timeStamps)
        );

    }

    /**
     * Make sure that double quantities representing speed values in x, y, z are
     * added into this container using {@link #addChild} or 
     * {@link #addChildren(cz.dfi.datamodel.series.SeriesWrapper...) } methods.
     * To do that automatically, use the
     * {@link #SpeedWrapper(java.lang.String, cz.dfi.datamodel.series.TimeStampArray, double[], double[], double[])  other constructor}.
     *
     * @param timeStamps
     */
    public SpeedWrapper(TimeStampArray timeStamps) {
        super(NAME, timeStamps);
    }
    public static final String NAME = "Velocity";

    @NullUnknown
    public DoubleQuantity getSpeedX() {
        if (speedx == null) {
            speedx = getChildWithNameContaining(" x", "x ");
        }
        return speedx;
    }

    @NullUnknown
    public DoubleQuantity getSpeedY() {
        if (speedy == null) {
            speedy = getChildWithNameContaining(" y", "y ");
        }
        return speedy;
    }
    @NullUnknown
    public DoubleQuantity getSpeedZ() {
        if (speedz == null) {
            speedz = getChildWithNameContaining(" z", "z ");
        }
        return speedz;
    }
}
