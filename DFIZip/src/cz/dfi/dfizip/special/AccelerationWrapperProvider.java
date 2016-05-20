/*
 */

package cz.dfi.dfizip.special;

import cz.dfi.datamodel.common.AccelerationWrapper;
import cz.dfi.datamodel.series.SeriesGroupWrapper;
import cz.dfi.datamodel.series.TimeStampArray;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = SpecialGroupProvider.class)
public class AccelerationWrapperProvider implements SpecialGroupProvider {

    @Override
    public String getName() {
        return AccelerationWrapper.NAME;
    }

    @Override
    public SeriesGroupWrapper getGroup(TimeStampArray timeStamps) {
        return new AccelerationWrapper(timeStamps);
    }

}
