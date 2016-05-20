/*
 */

package cz.dfi.dfizip.special;

import cz.dfi.datamodel.common.MotorsWrapper;
import cz.dfi.datamodel.series.SeriesGroupWrapper;
import cz.dfi.datamodel.series.TimeStampArray;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = SpecialGroupProvider.class)
public class MotorsWrapperProvider implements SpecialGroupProvider {

    @Override
    public String getName() {
        return MotorsWrapper.NAME;
    }

    @Override
    public SeriesGroupWrapper getGroup(TimeStampArray timeStamps) {
        return new MotorsWrapper(timeStamps);
    }

}
