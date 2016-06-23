/*
 */
package cz.dfi.trajectorycomp;

import cz.dfi.recorddataprovider.FileLookup;
import cz.dfi.trajectory.Trajectory;
import java.util.Collection;
import java.util.Iterator;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//cz.dfi.trajectorycomp//Trajectory//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "TrajectoryTopComponent",
        iconBase = "cz/dfi/trajectorycomp/iconmonstr-paper-plane-6-16.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "output", openAtStartup = true)
@ActionID(category = "Window", id = "cz.dfi.trajectorycomp.TrajectoryTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_TrajectoryAction",
        preferredID = "TrajectoryTopComponent"
)
@Messages({
    "CTL_TrajectoryAction=Trajectory",
    "CTL_TrajectoryTopComponent=Trajectory Window",
    "HINT_TrajectoryTopComponent=This is a Trajectory window"
})
public final class TrajectoryTopComponent extends TopComponent {

    private static final long serialVersionUID = 1L;
    private final PointSeriesDataset dataset = new PointSeriesDataset();
    private final Lookup.Result<Trajectory> availableTrajectories;
    private final LookupListener trajectoriesChangeListener = (LookupEvent ev) -> {
        feedDataset();
    };

    public TrajectoryTopComponent() {
        initComponents();
        setName(Bundle.CTL_TrajectoryTopComponent());
        setToolTipText(Bundle.HINT_TrajectoryTopComponent());
        JFreeChart scatterPlot = ChartFactory.createScatterPlot("Trajectory", null, null, dataset, PlotOrientation.VERTICAL,/*legend*/ true, false, false);
        final XYPlot plot = scatterPlot.getXYPlot();
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesLinesVisible(0, true);
        plot.setDomainPannable(true);
        plot.setRangePannable(true);
        
        ChartPanel panel = new ChartPanel(scatterPlot);
        add(panel);

        availableTrajectories = FileLookup.getDefault().lookupResult(Trajectory.class);

    }

    private void feedDataset() {
        Collection<? extends Trajectory> trajectories = availableTrajectories.allInstances();
        int n = trajectories.size();
        double[][] x = new double[n][];
        double[][] y = new double[n][];
        String[] names = new String[n];
        int i = 0;
        for (Trajectory trajectory : trajectories) {
            x[i] = trajectory.getxValues();
            y[i] = trajectory.getyValues();
            names[i] = trajectory.getName();
            i++;
        }
        dataset.setSeries(x, y, names);
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
    @Override
    public void componentOpened() {
        availableTrajectories.addLookupListener(trajectoriesChangeListener);
    }

    @Override
    public void componentClosed() {
        availableTrajectories.removeLookupListener(trajectoriesChangeListener);
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

}