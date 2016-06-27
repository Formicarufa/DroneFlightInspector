/*
 */
package cz.dfi.dfizip.constructors;

import cz.dfi.datamodel.TimeStampType;
import cz.dfi.datamodel.series.NestedSeriesGroupWrapper;
import cz.dfi.datamodel.series.SeriesGroupWrapper;
import cz.dfi.datamodel.series.TimeStampArray;
import cz.dfi.dfizip.readers.TimeReader;
import cz.dfi.dfizip.special.SpecialGroupProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.netbeans.api.annotations.common.NullAllowed;
import org.openide.util.Lookup;

/**
 *
 * @author Tomas Prochazka 5.5.2016
 */
public class GroupConstructor implements SeriesConstructor {

    List<SeriesConstructor> children = new ArrayList<>();
    TimeReader recordTimeReader = null;
    TimeReader onboardTimeReader = null;
    String name;

    public GroupConstructor(String groupName) {
        name = groupName;
    }

    public void addChild(SeriesConstructor c) {
        children.add(c);
    }

    public void setRecordTimeReader(TimeReader recordTimeReader) {
        this.recordTimeReader = recordTimeReader;
    }

    public void setOnboardTimeReader(TimeReader onboardTimeReader) {
        this.onboardTimeReader = onboardTimeReader;
    }

    @Override
    public SeriesGroupWrapper createSeries(@NullAllowed SeriesGroupWrapper parentGroup) {
        SeriesGroupWrapper group;
        if (recordTimeReader != null || onboardTimeReader != null) {
            group = createGroupWithTimeStamps();
        } else {
            if (parentGroup == null) {
                throw new IllegalStateException("Either a time information or a parent group  must be set.");
            }
            SpecialGroupProvider p = getSpecials().get(name);
            if (p != null) {
                group = p.getGroup(parentGroup.getTimeStamps());
            } else {
                group = new NestedSeriesGroupWrapper(name);
                group.setParent(parentGroup);
            }
        }
        addChildren(group);
        return group;
    }

    protected void addChildren(SeriesGroupWrapper group) {
        for (SeriesConstructor c : children) {
            group.addChild(c.createSeries(group));
        }
    }

    private SeriesGroupWrapper createGroupWithTimeStamps() {
        TimeStampArray stamps = createTimeStampArray();
        if (stamps != null) {
            createGroup(stamps);
        }
        throw new IllegalStateException("Cannot create a group with time stamps: all timestamps are null.");
    }

    protected TimeStampArray createTimeStampArray() {
        if (recordTimeReader != null && onboardTimeReader != null) {
            return new TimeStampArray(recordTimeReader.getValues(), onboardTimeReader.getValues());
        }
        if (recordTimeReader != null) {
            //assuming the message is outcoming as we have only recorder time stamp available
            return new TimeStampArray(recordTimeReader.getValues(), TimeStampType.TimeOfRecord, false);
        }
        if (onboardTimeReader != null) {
            return new TimeStampArray(onboardTimeReader.getValues(), TimeStampType.OnboardTime, true);
        }
        return null;
    }

    private SeriesGroupWrapper createGroup(final TimeStampArray timeStamps) {
        SpecialGroupProvider p = getSpecials().get(name);
        if (p != null) {
            return p.getGroup(timeStamps);
        }
        return SeriesGroupWrapper.create(name, timeStamps);
    }
    private static Map<String, SpecialGroupProvider> specials = null;

    /**
     * Special containers are created for special quantities which are commonly
     * used in the application, such as Speed or Rotation.
     *
     * @return
     */
    private static Map<String, SpecialGroupProvider> getSpecials() {
        if (specials == null) {
            specials = new HashMap<>();
            Collection<? extends SpecialGroupProvider> res = Lookup.getDefault().lookupAll(SpecialGroupProvider.class);
            for (SpecialGroupProvider x : res) {
                specials.put(x.getName(), x);
            }
        }
        return specials;
    }

}
