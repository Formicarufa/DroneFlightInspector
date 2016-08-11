/*
 */

package cz.dfi.dfizip.special;

import cz.dfi.datamodel.common.AltitudeWrapper;
import cz.dfi.datamodel.graphable.DoubleQuantity;
import cz.dfi.datamodel.series.TimeStampArray;
import org.openide.util.lookup.ServiceProvider;
/**
 * @see SpecialGroupProvider
 * @author Tomas Prochazka
 */
@ServiceProvider(service = SpecialDoubleQuantProvider.class)
public class AltitudeWrapperProvider implements SpecialDoubleQuantProvider {

    @Override
    public String getName() {
        return AltitudeWrapper.NAME;
    }

    @Override
    public DoubleQuantity getQuantity(String unit, double[] values, TimeStampArray timeStamps) {
        return new AltitudeWrapper(values,unit,timeStamps);
    }

}
