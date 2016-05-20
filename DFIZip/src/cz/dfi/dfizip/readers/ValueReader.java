/*
 */

package cz.dfi.dfizip.readers;

/**
 * An interface of all classes which provide ability to read a value of certain type
 * from string. Value reader stores the values it reads and than returns them at 
 * once as an array of values.
 * @author Tomas Prochazka
 * 5.5.2016
 */
public interface ValueReader {
    void read(String str);
    Object getValues();

}
