/*
 */
package cz.dfi.graphs;

import cz.dfi.datamodel.FlightDataRecord;
import cz.dfi.datamodel.FlightRecordsWrapper;
import java.util.List;
import org.openide.util.Lookup;

/**
 * Contains common code for the graph plotting top components.
 * Creating some inheritance hierarchy is 
 * complicated, because of auto-generated code.
 * @author Tomas Prochazka 23.11.2015
 */
public class GraphsCommon {

    public static List<FlightDataRecord> getFlightRecords(Lookup currentContext) throws IllegalStateException {
        FlightRecordsWrapper dataRecords = currentContext.lookup(FlightRecordsWrapper.class);
        if (dataRecords == null) {
            throw new IllegalStateException("Reading data of not yet loaded file.");
        }
        List<FlightDataRecord> r = dataRecords.getRecords();
        return r;
    }
    public static FlightRecordsWrapper getRecordsWrapper(Lookup currentContext) {
        FlightRecordsWrapper dataRecords = currentContext.lookup(FlightRecordsWrapper.class);
        if (dataRecords == null) {
            throw new IllegalStateException("Reading data of not yet loaded file.");
        }
        return dataRecords;
    }
}
