/*
 */

package cz.dfi.dfizip.special;

import cz.dfi.datamodel.common.RotationWrapper;
import cz.dfi.datamodel.series.SeriesGroupWrapper;
import cz.dfi.datamodel.series.TimeStampArray;
import org.openide.util.lookup.ServiceProvider;
/**
 * @see SpecialGroupProvider
 * @author Tomas Prochazka
 */
@ServiceProvider(service = SpecialGroupProvider.class)
public class RotationWrapperProvider implements SpecialGroupProvider {

    @Override
    public String getName() {
        return RotationWrapper.NAME;
    }

    @Override
    public SeriesGroupWrapper getGroup(TimeStampArray timeStamps) {
        return new RotationWrapper(timeStamps);
    }

}
