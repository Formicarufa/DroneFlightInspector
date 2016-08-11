/*
 */

package cz.dfi.datamodel.values;

/**
 *A wrapper for two time stamps.
 * @author Tomas Prochazka
 * 4.3.2016
 */
public class TimeInterval {
    public TimeStamp t1;
    public TimeStamp t2;

    public TimeInterval(TimeStamp t1, TimeStamp t2) {
        this.t1 = t1;
        this.t2 = t2;
    }
    
}
