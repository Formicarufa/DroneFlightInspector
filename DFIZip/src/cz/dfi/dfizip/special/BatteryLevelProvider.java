/*
 */

package cz.dfi.dfizip.special;

import cz.dfi.datamodel.common.BatteryPercentWrapper;
import cz.dfi.datamodel.graphable.DoubleQuantity;
import cz.dfi.datamodel.series.TimeStampArray;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = SpecialDoubleQuantProvider.class)
public class BatteryLevelProvider implements SpecialDoubleQuantProvider {

    @Override
    public String getName() {
        return BatteryPercentWrapper.NAME;
    }

    @Override
    public DoubleQuantity getQuantity(String unit, double[] values, TimeStampArray timeStamps) {
        return new BatteryPercentWrapper(values,timeStamps, unit);
    }

}
