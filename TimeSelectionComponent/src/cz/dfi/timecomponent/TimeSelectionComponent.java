/*
 */
package cz.dfi.timecomponent;

import cz.dfi.datafileimplementation.RecordDataFileInfo;
import cz.dfi.datamodel.RecordsWrapper;
import cz.dfi.fileimporertinterface.FileImporter;
import cz.dfi.recorddataprovider.CurrentFileLookupProvider;
import cz.dfi.recorddataprovider.OpenedFilesManager;
import java.awt.BorderLayout;
import java.io.File;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import jtimeselector.JTimeSelector;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.MultiDataObject;
import org.openide.util.Lookup;
import org.openide.util.lookup.InstanceContent;
import org.openide.windows.CloneableTopComponent;
import org.openide.windows.TopComponent;

/**
 *
 * @author Tomas Prochazka
 */
@ConvertAsProperties(
        dtd = "-//cz.dfi.timecomponent//TimeSelectionComponent//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "TimeSelectionComponent",
        iconBase = "cz/dfi/rosbagsupport/icon3.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
public class TimeSelectionComponent extends CloneableTopComponent {

    private final RecordDataFileInfo fileInfo;
    private File file;
    private String path;
    private FileImporter fileImporter;
    private String importerName;
    private InstanceContent fileContent;
    private boolean selected = false;
    private boolean layersLoaded =false;

    /**
     * Creates new form TimeSelectionComponent
     */
    public TimeSelectionComponent() {
        initComponents();
        fileInfo = new RecordDataFileInfo();
        OpenedFilesManager fileManager = Lookup.getDefault().lookup(OpenedFilesManager.class);
        fileContent = fileManager.newFileOpened(fileInfo);
        createTimeline();
    }

    public TimeSelectionComponent(MultiDataObject obj, FileImporter fileImporter) {
        this();
        this.file = FileUtil.toFile(obj.getPrimaryFile());
        this.path = file.getPath();
        this.fileImporter = fileImporter;
        this.importerName = fileImporter.getName();
    }

    private void createTimeline() {
        jPanel1.setLayout(new BorderLayout());
        jTimeSelector = new JTimeSelector();
        jPanel1.add(jTimeSelector);
    }
    private JTimeSelector jTimeSelector;
    @Override
    protected void componentActivated() {
        super.componentActivated(); 
        fileInfo.fileSelected();
        
        if (!layersLoaded) loadTimelineLayers();
        selected = true;
    }

    @Override
    protected void componentClosed() {
        super.componentClosed(); 
        fileInfo.fileClosed();
    }

    @Override
    protected void componentOpened() {
        selected=true;
        super.componentOpened();
        if (file == null) {
            file = new File(path);
        }
        setDisplayName(file.getName());
        new Runnable() {

            @Override
            public void run() {
                fileImporter = getFileImporter();
                if (fileImporter == null) {
                    TimeSelectionComponent.this.close();
                    return;
                }
                if (!fileImporter.loadRecords(file, fileContent)) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "File importer with the name {0} was unable to load the file {1}", new String[]{importerName, file.getName()});
                }
                if (TimeSelectionComponent.this.selected==true) {
                    fileInfo.fileSelected();
                    TimeSelectionComponent.this.loadTimelineLayers();
                }
            }
        }.run();

    }

    @Override
    protected void componentHidden() {
        super.componentHidden(); //To change body of generated methods, choose Tools | Templates.
        selected = false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    private FileImporter getFileImporter() {
        if (fileImporter != null) {
            return fileImporter;
        }
        Collection<? extends FileImporter> allImporters = Lookup.getDefault().lookupAll(FileImporter.class);
        for (FileImporter importer : allImporters) {
            if (importer.getName().equals(importerName)) {
                return importer;
            }
        }
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "File importer with the name {0} not found.", importerName);
        return null;
    }

    void writeProperties(java.util.Properties p) {
        p.setProperty("path", path);
        p.setProperty("importer", importerName);
    }

    void readProperties(java.util.Properties p) {
        path = p.getProperty("path");
        importerName = p.getProperty("importer");

    }

    private void loadTimelineLayers() {
        CurrentFileLookupProvider provider = Lookup.getDefault().lookup(CurrentFileLookupProvider.class);
        Lookup lookup = provider.getCurrentFileLookup();
        if (lookup==null) return;
        Collection<? extends RecordsWrapper> lookupAll = lookup.lookupAll(RecordsWrapper.class);
        for (RecordsWrapper w : lookupAll) {
            jTimeSelector.addTimeValuesLayer(w.getName(), w.getTimeOfRecordValues());
        }
        layersLoaded=true;
        
    }
}