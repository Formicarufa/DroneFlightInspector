/*
 */
package cz.dfi.timecomponent;

import cz.dfi.datamodel.TimeSelectionLayer;
import cz.dfi.fileimporertinterface.FileImporter;
import cz.dfi.recorddataprovider.FileLoadingRequestProcessor;
import cz.dfi.recorddataprovider.OpenedFilesManager;
import cz.dfi.recorddataprovider.RecordFile;
import java.awt.BorderLayout;
import java.io.File;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import jtimeselector.JTimeSelector;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.MultiDataObject;
import org.openide.util.Lookup;
import org.openide.util.LookupListener;
import org.openide.windows.CloneableTopComponent;
import org.openide.windows.TopComponent;
import cz.dfi.datamodel.series.SeriesWrapper;

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

    private RecordFile fileInfo;
    private File file;
    private String path;
    private FileImporter fileImporter;
    private String importerName;
    private boolean selected = false;
    private boolean loaded=false;
    private OpenedFilesManager filesManager;
    private SelectionProvider selectionProvider;
    

    /**
     * Creates new form TimeSelectionComponent
     */
    public TimeSelectionComponent() {
        initComponents();
        
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
        selectionProvider = new SelectionProvider();
        jTimeSelector.addTimeSelectionChangedListener(selectionProvider);
        jPanel1.add(jTimeSelector);
    }
    private JTimeSelector jTimeSelector;

    @Override
    protected void componentActivated() {
        super.componentActivated();
        if (loaded) filesManager.fileSelected(fileInfo);
        selected = true;
    }

    @Override
    protected void componentClosed() {
        super.componentClosed();
        selected=false;
        if (lookupResult!= null) lookupResult.removeLookupListener(changeListener);
        filesManager.fileClosed(fileInfo);
    }

    @Override
    protected void componentOpened() {
        selected = true;
        super.componentOpened();
        if (file == null) {
            file = new File(path);
        }
        
        filesManager = OpenedFilesManager.getDefault();
        fileInfo = filesManager.newFileOpened(file.getName());
        fileInfo.getLookupContent().add(fileInfo);
        setDisplayName(file.getName());
        Runnable runnable = () -> {
             final ProgressHandle progr =ProgressHandle.createHandle("Loading file " + file.getName());
             progr.start();
            fileImporter = getFileImporter();
            if (fileImporter == null) {
                TimeSelectionComponent.this.close();
                return;
            }
            if (!fileImporter.loadRecords(file, fileInfo.getLookupContent())) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "File importer with the name {0} was unable to load the file {1}", new String[]{importerName, file.getName()});
            }
            loaded=true;
            if (TimeSelectionComponent.this.selected == true) {
                filesManager.fileSelected(fileInfo);
            }
            TimeSelectionComponent.this.loadTimelineLayers();
            jTimeSelector.setTimeToStringConverter((long l) -> {
                cz.dfi.recorddataprovider.TimeToStringConverter c = cz.dfi.recorddataprovider.TimeToStringConverter.get();
                Date d = new Date(l/1_000_000);
                DateFormat f = c.getRecordingTimeGraphFormat();
                return f.format(d);
             });
            progr.finish();
        };
        FileLoadingRequestProcessor.getDefault().post(runnable);

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
        lookupResult = fileInfo.getLookup().lookupResult(TimeSelectionLayer.class);
       lookupResult.addLookupListener(changeListener);
        Collection<? extends SeriesWrapper> lookupAll = lookupResult.allInstances();
        for (SeriesWrapper w : lookupAll) {
            jTimeSelector.addTimeValuesLayer(w.getName(), w.getTimeStamps().getRecorderValues());
        }
        jTimeSelector.requireRepaint();
    }
    private Lookup.Result<TimeSelectionLayer> lookupResult;
    LookupListener changeListener = (evt ) -> {
        jTimeSelector.removeAllGraphLayers();
        this.loadTimelineLayers();
    };
    
}
