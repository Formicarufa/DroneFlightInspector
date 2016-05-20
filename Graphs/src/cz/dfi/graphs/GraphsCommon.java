/*
 */
package cz.dfi.graphs;

import cz.dfi.datamodel.FlightDataRecord;
import cz.dfi.datamodel.FlightRecordsWrapper;
import cz.dfi.datamodel.graphable.GraphableQuantity;
import cz.dfi.datamodel.series.SeriesGroupWrapper;
import cz.dfi.datamodel.series.SeriesWrapper;
import cz.dfi.recorddataprovider.TimeToStringConverter;
import java.text.DateFormat;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.openide.util.Lookup;

/**
 * Contains common code for the graph plotting top components.
 *
 * @author Tomas Prochazka 23.11.2015
 */
public class GraphsCommon {

    /**
     *
     * @param currentContext
     * @return
     * @throws IllegalStateException
     * @deprecated FlightRecordsWrappers are not used anymore.
     */
    @Deprecated
    public static List<FlightDataRecord> getFlightRecords(Lookup currentContext) throws IllegalStateException {
        FlightRecordsWrapper dataRecords = currentContext.lookup(FlightRecordsWrapper.class);
        if (dataRecords == null) {
            throw new IllegalStateException("Reading data of not yet loaded file.");
        }
        List<FlightDataRecord> r = dataRecords.getRecords();
        return r;
    }

    @Deprecated
    public static FlightRecordsWrapper getRecordsWrapper(Lookup currentContext) {
        FlightRecordsWrapper dataRecords = currentContext.lookup(FlightRecordsWrapper.class);
        if (dataRecords == null) {
            throw new IllegalStateException("Reading data of not yet loaded file.");
        }
        return dataRecords;
    }


    public static void setupChart(JFreeChart chart) {
        DateAxis axis = (DateAxis) ((XYPlot) chart.getPlot()).getDomainAxis();
        final XYPlot plot = chart.getXYPlot();
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        TimeToStringConverter converter = TimeToStringConverter.get();
        final DateFormat timeFormat = converter.getOnboardTimeGraphFormat();
        axis.setDateFormatOverride(timeFormat);
    }

    public static void putChildrenToDataset(SeriesGroupWrapper w, MultipleValuesDataset dataSet) {
        Collection<SeriesWrapper> children = w.getChildren().stream().filter((SeriesWrapper x) -> x instanceof GraphableQuantity).collect(Collectors.toList());
        int size = children.size();
        double[][] values = new double[size][];
        String[] keys = new String[size];
        int i = 0;
        for (SeriesWrapper child : children) {
            GraphableQuantity q = (GraphableQuantity) child;
            values[i] = q.getValuesAsDoubles();
            keys[i] = q.getName();
            i++;
        }
        dataSet.valuesChanged(w.getTimeStamps().getOnboardValues(), values, keys);
    }


}
