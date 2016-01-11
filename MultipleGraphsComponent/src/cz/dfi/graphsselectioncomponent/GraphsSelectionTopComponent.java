/*
 */
package cz.dfi.graphsselectioncomponent;

import cz.dfi.recorddataprovider.FileLookup;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.propertysheet.PropertySheetView;
import org.openide.explorer.view.OutlineView;
import org.openide.nodes.AbstractNode;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//cz.dfi.graphsselectioncomponent//GraphsSelection//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "GraphsSelectionTopComponent",
        iconBase = "cz/dfi/graphsselectioncomponent/iconmonstr-bar-chart-4-icon-16.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "properties", openAtStartup = true)
@ActionID(category = "Window", id = "cz.dfi.graphsselectioncomponent.GraphsSelectionTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_GraphsSelectionAction",
        preferredID = "GraphsSelectionTopComponent"
)
@Messages({
    "CTL_GraphsSelectionAction=GraphsSelection",
    "CTL_GraphsSelectionTopComponent=GraphsSelection Window",
    "HINT_GraphsSelectionTopComponent=This is a GraphsSelection window"
})
public final class GraphsSelectionTopComponent extends TopComponent implements ExplorerManager.Provider {

    private final ExplorerManager em = new ExplorerManager();
    private final PropertySheetView graphOptionsSheetView;
    private final OutlineView listView;
    QuantitiesListModelProvider listOfGraphables;
    private final RootNode rootNode;

    public GraphsSelectionTopComponent() {
        initComponents();
        setName(Bundle.CTL_GraphsSelectionTopComponent());
        setToolTipText(Bundle.HINT_GraphsSelectionTopComponent());
        graphOptionsSheetView = new PropertySheetView();
        jPanel4.add(graphOptionsSheetView);
        listView = new OutlineView();
        associateLookup(ExplorerUtils.createLookup(em, getActionMap()));
        jPanel3.add(listView);
        listOfGraphables = new QuantitiesListModelProvider();
        rootNode = new RootNode(listOfGraphables);
        em.setRootContext(rootNode);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();

        setMinimumSize(new java.awt.Dimension(50, 50));
        setName(""); // NOI18N
        setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(-1);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(0.6);
        jSplitPane1.setToolTipText(org.openide.util.NbBundle.getMessage(GraphsSelectionTopComponent.class, "GraphsSelectionTopComponent.jSplitPane1.toolTipText")); // NOI18N

        jPanel3.setLayout(new java.awt.BorderLayout());
        jSplitPane1.setTopComponent(jPanel3);

        jPanel4.setLayout(new java.awt.BorderLayout());
        jSplitPane1.setRightComponent(jPanel4);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSplitPane jSplitPane1;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
    }


    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return em;
    }

    public static class RootNode extends AbstractNode {

        public RootNode(QuantitiesListModelProvider children) {
            super(children);
            setDisplayName("Available graphs:");
        }
    }

}
