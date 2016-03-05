/*
 */

package cz.dfi.datamodel.series;

import java.util.Collection;
import org.netbeans.api.annotations.common.CheckForNull;

/**
 *
 * @author Tomas Prochazka
 * 27.2.2016
 */
public interface SingleSeriesWrapper extends SeriesWrapper{
    @Override
    default @CheckForNull Collection<SeriesWrapper> getChildren(){
        return null;
    }
}
