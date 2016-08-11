/*
 */
package cz.dfi.multiplegraphscomponent;

import cz.dfi.recorddataprovider.TimeToStringConverter;
import cz.dfi.graphsselectioncomponent.GraphedQuantity;
import cz.dfi.recorddataprovider.FileLookup;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Collection;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Contains the graphing component which displays the plots selected in the
 * Graphs Selection Window.
 * <p>
 * Tracks the instances of {@link GraphedQuantity} in the {@link FileLookup}
 * and notifies the {@link MultipleModifiableGraphsDataset} if a change occurs.
 */
@ConvertAsProperties(
        dtd = "-//cz.dfi.multiplegraphscomponent//MultipleGraphs//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "MultipleGraphsTopComponent",
        iconBase = "cz/dfi/multiplegraphscomponent/iconmonstr-bar-chart-4-icon-16.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "output", openAtStartup = true)
@ActionID(category = "Window", id = "cz.dfi.multiplegraphscomponent.MultipleGraphsTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_MultipleGraphsAction",
        preferredID = "MultipleGraphsTopComponent"
)
@Messages({
    "CTL_MultipleGraphsAction=MultipleGraphs",
    "CTL_MultipleGraphsTopComponent=MultipleGraphs Window",
    "HINT_MultipleGraphsTopComponent=Displays plots of the quantities selected in the Graphs Selection Window"
})
public final class MultipleGraphsTopComponent extends TopComponent {
    private static final long serialVersionUID = 1L;
    protected final JFreeChart chart;
    protected final MultipleModifiableGraphsDataset dataset;
    private final Lookup.Result<GraphedQuantity> quantitiesSearch;
    LookupListener listener = this::resultChanged;
    private final SelectionHighlighter highlighter;

    public MultipleGraphsTopComponent() {
        initComponents();
        setName(Bundle.CTL_MultipleGraphsTopComponent());
        setToolTipText(Bundle.HINT_MultipleGraphsTopComponent());
        Lookup fileLookup = FileLookup.getDefault();
        quantitiesSearch = fileLookup.lookupResult(GraphedQuantity.class);
        quantitiesSearch.addLookupListener(listener);
        dataset = new MultipleModifiableGraphsDataset();
        //chart = ChartFactory.createXYLineChart(null, "time", "values", dataset, PlotOrientation.VERTICAL, false, true, false);
        chart = ChartFactory.createTimeSeriesChart(null, "time", "values", dataset, true, true, false);
        ChartPanel p = new ChartPanel(chart);
        DateAxis axis = (DateAxis) ((XYPlot) chart.getPlot()).getDomainAxis();
        final XYPlot plot = chart.getXYPlot();
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        TimeToStringConverter converter = TimeToStringConverter.get();
        final DateFormat timeFormat = converter.getRecordingTimeGraphFormat();
        axis.setDateFormatOverride(timeFormat);
        add(p);
        p.setMouseWheelEnabled(true);
        highlighter = SelectionHighlighter.create(plot);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    void writeProperties(java.util.Properties p) {
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
    }

    public void resultChanged(LookupEvent ev) {
        removeChangeListeners();
        allInstances = quantitiesSearch.allInstances();
        addChangeListeners();
        setColors();
        dataset.resultChanged(allInstances);

    }

    private void setColors() {
        XYPlot plot = (XYPlot) chart.getPlot();
        XYItemRenderer renderer = plot.getRenderer();
        int i = 0;
        for (GraphedQuantity q : allInstances) {
            if (q.getColor() != null) {
                renderer.setSeriesPaint(i, q.getColor());
            }
            i++;
        }
    }
    private Collection<? extends GraphedQuantity> allInstances;

    private void removeChangeListeners() {
        if (allInstances==null) return;
        for (GraphedQuantity q : allInstances) {
            q.removePropertyChangedListener(propertyListener);
        }
    }

    private void addChangeListeners() {
        for (GraphedQuantity q : allInstances) {
            q.addPropertyChangedListener(propertyListener);
        }
    }
    PropertyChangeListener propertyListener = (PropertyChangeEvent evt) -> {
        if (evt.getPropertyName().equals("color")) {
            setColors();
        }
        MultipleGraphsTopComponent.this.dataset.graphPropertyChanged();
    };
}
