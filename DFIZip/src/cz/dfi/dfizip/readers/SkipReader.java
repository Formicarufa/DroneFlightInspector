/*
 */

package cz.dfi.dfizip.readers;

/**
 * Reader which ignores the read value.
 * @author Tomas Prochazka
 */
public class SkipReader implements ValueReader {

    @Override
    public void read(String str) {
    }

    @Override
    public Object getValues() {
        throw new UnsupportedOperationException("SkipReader saves no values.");
    }

}
