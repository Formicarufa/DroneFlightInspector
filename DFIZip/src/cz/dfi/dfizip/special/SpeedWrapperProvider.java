/*
 */

package cz.dfi.dfizip.special;

import cz.dfi.datamodel.common.SpeedWrapper;
import cz.dfi.datamodel.series.SeriesGroupWrapper;
import cz.dfi.datamodel.series.TimeStampArray;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = SpecialGroupProvider.class)
public class SpeedWrapperProvider implements SpecialGroupProvider {

    @Override
    public String getName() {
        return SpeedWrapper.NAME;
    }

    @Override
    public SeriesGroupWrapper getGroup(TimeStampArray timeStamps) {
        return new SpeedWrapper(timeStamps);
    }

}
