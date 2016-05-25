/*
 */

package cz.dfi.unitconverters;

import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = DegreeConverter.class,position = Integer.MAX_VALUE-5)
public class Deg2DegConverter implements DegreeConverter {

    @Override
    public boolean canConvert(String unit) {
        switch (unit) {
            case "deg":
            case "degree":
            case "degrees":
            case "°":
                return true;
            default:
                return false;
        }
    }

    @Override
    public double convert(double value, String unit) {
        return value;
    }

}
