/*
 */
package cz.dfi.graphs;

import cz.dfi.datamodel.graphable.GraphableQuantity;
import cz.dfi.datamodel.series.SeriesGroupWrapper;
import cz.dfi.datamodel.series.SeriesWrapper;
import cz.dfi.recorddataprovider.TimeToStringConverter;
import java.text.DateFormat;
import java.util.Collection;
import java.util.stream.Collectors;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;

/**
 * Contains common code for the graph plotting top components.
 *
 * @author Tomas Prochazka 23.11.2015
 */
public class GraphsCommon {
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
