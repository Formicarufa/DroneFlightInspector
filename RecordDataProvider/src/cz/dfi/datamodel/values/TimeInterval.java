/*
 */

package cz.dfi.datamodel.values;

/**
 *
 * @author Tomas Prochazka
 * 4.3.2016
 */
public class TimeInterval {
    TimeStamp t1;
    TimeStamp t2;

    public TimeInterval(TimeStamp t1, TimeStamp t2) {
        this.t1 = t1;
        this.t2 = t2;
    }
    
}
