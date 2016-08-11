/*
 */

package cz.dfi.dfizip.special;

import cz.dfi.datamodel.common.MagnetometerWrapper;
import cz.dfi.datamodel.series.SeriesGroupWrapper;
import cz.dfi.datamodel.series.TimeStampArray;
import org.openide.util.lookup.ServiceProvider;
/**
 * @see SpecialGroupProvider
 * @author Tomas Prochazka
 */
@ServiceProvider(service = SpecialGroupProvider.class)
public class MagnetoWrapperProvider implements SpecialGroupProvider {

    @Override
    public String getName() {
        return MagnetometerWrapper.NAME;
    }

    @Override
    public SeriesGroupWrapper getGroup(TimeStampArray timeStamps) {
        return new MagnetometerWrapper(timeStamps);
    }

}
