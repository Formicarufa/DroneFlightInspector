/*
 */
package cz.dfi.multiplegraphscomponent;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
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
    "HINT_MultipleGraphsTopComponent=This is a MultipleGraphs window"
})
public final class MultipleGraphsTopComponent extends TopComponent {

    protected final JFreeChart chart;
    protected final XYDataset dataset;
    public MultipleGraphsTopComponent() {
        initComponents();
        setName(Bundle.CTL_MultipleGraphsTopComponent());
        setToolTipText(Bundle.HINT_MultipleGraphsTopComponent());
        chart = ChartFactory.createXYLineChart(null, "time", "values", dataset,PlotOrientation.VERTICAL,false,true,false);
        

    }
 
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
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
