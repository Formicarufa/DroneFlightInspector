/*
 */

package cz.dfi.datamodel.series;

/**
 *
 * @author Tomas Prochazka
 * 28.2.2016
 */
public interface TimeSeries {
        /**
     * Gets the name of the series that can be displayed to the user of the application.
     * @return Name of the series
     */
    String getName();
    
    /** 
     * Gets the list of time stamps with which the values of the series are
     * associated.
     * @return list of time values
     */
    TimeStampArray getTimeStamps();
}
