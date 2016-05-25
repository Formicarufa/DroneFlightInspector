/*
 */

package cz.dfi.unitconverters;

import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = DegreeConverter.class)
public class Rad2DegConverter implements DegreeConverter {

    @Override
    public boolean canConvert(String unit) {
        switch (unit) {
            case "rad":
            case "radian":
            case "radians":
                return true;
            default:
                return false;
        }
    }

    @Override
    public double convert(double value, String unit) {
       return Math.toDegrees(value);
    }

}
