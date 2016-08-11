/*
 */
package cz.dfi.dfizip.special;

import cz.dfi.datamodel.common.VelocityWrapper;
import cz.dfi.datamodel.series.SeriesGroupWrapper;
import cz.dfi.datamodel.series.TimeStampArray;
import org.openide.util.lookup.ServiceProvider;

/**
 * @see SpecialGroupProvider
 * @author Tomas Prochazka
 */
@ServiceProvider(service = SpecialGroupProvider.class)
public class VelocityWrapperProvider implements SpecialGroupProvider {

    @Override
    public String getName() {
        return VelocityWrapper.NAME;
    }

    @Override
    public SeriesGroupWrapper getGroup(TimeStampArray timeStamps) {
        return new VelocityWrapper(timeStamps);
    }

}
