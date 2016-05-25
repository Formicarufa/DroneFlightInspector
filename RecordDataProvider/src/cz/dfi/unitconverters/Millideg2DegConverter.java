/*
 */
package cz.dfi.unitconverters;

import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = DegreeConverter.class)
public class Millideg2DegConverter implements DegreeConverter {

    @Override
    public boolean canConvert(String unit) {
        switch (unit) {
            case "millidegree":
            case "millideg":
            case "milli-degree":
            case "milli-degrees":
            case "millidegrees":
            case "mdeg":
            case "milli-degees": //typo in my recorded files...
                return true;
            default:
                return false;
        }
    }

    @Override
    public double convert(double value, String unit) {
        return value / 1000;
    }

}
