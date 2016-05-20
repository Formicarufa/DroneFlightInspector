/*
 */
package cz.dfi.dfizip.constructors;

import cz.dfi.datamodel.series.SeriesGroupWrapper;
import cz.dfi.datamodel.series.TimeStampArray;

public class TimelineLayerGroupConstructor extends GroupConstructor {

    public TimelineLayerGroupConstructor(String groupName) {
        super(groupName);
    }

    @Override
    public SeriesGroupWrapper createSeries(SeriesGroupWrapper parentGroup) {
        TimeStampArray stamps = createTimeStampArray();
        if (stamps == null) {
            stamps = parentGroup.getTimeStamps();
        }
        if (stamps != null) {
            final SeriesGroupWrapper group = SeriesGroupWrapper.create_timelineLayer(name, stamps);
            addChildren(group);
            return group;
        }
        throw new IllegalStateException("Timeline layer needs to contain time information.");
    }

}
