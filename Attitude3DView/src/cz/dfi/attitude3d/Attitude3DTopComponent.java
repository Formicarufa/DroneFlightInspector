/*
 */
package cz.dfi.attitude3d;

import cz.dfi.datamodel.common.RotationValuesWrapper;
import cz.dfi.datamodel.graphable.DoubleValueWrapper;
import cz.dfi.recorddataprovider.FileLookup;
import cz.dfi.unitconverters.DegreeConverter;
import java.awt.BorderLayout;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays the 3D Window with attitude of the drone.
 * Contains a JFX panel which is capable of displaying JavaFX inside of Swing.
 * Gets the {@link RotationValuesWrapper} from the file lookup. The Rotation Values
 * Wrapper contains the required roll, pitch and yaw. 
 */
@ConvertAsProperties(
        dtd = "-//cz.dfi.attitude3d//Attitude3D//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "Attitude3DTopComponent",
        iconBase = "cz/dfi/attitude3d/iconmonstr-eye-6-16.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "bottomleftview", openAtStartup = true)
@ActionID(category = "Window", id = "cz.dfi.attitude3d.Attitude3DTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_Attitude3DAction",
        preferredID = "Attitude3DTopComponent"
)
@Messages({
    "CTL_Attitude3DAction=Attitude 3D",
    "CTL_Attitude3DTopComponent=Attitude 3D Window",
    "HINT_Attitude3DTopComponent=Attitude 3D Window displays the pose of the drone in the selected time."
})
public final class Attitude3DTopComponent extends TopComponent implements LookupListener{

    private static final long serialVersionUID = 1L;
    private JFXPanel fxPanel = new JFXPanel();
    private Attitude3DView view;
    private Lookup.Result<RotationValuesWrapper> result;

    public Attitude3DTopComponent() {
        initComponents();
        init();
    }

    private void init() {
        setName(Bundle.CTL_Attitude3DTopComponent());
        setToolTipText(Bundle.HINT_Attitude3DTopComponent());
        setLayout(new BorderLayout());
        add(fxPanel, BorderLayout.CENTER);
        Platform.setImplicitExit(false);
        Platform.runLater(() -> {
            createContent();
        });
    }

    private void createContent() {
        view = new Attitude3DView();
        fxPanel.setScene(view.createScene());
        result = FileLookup.getDefault().lookupResult(RotationValuesWrapper.class);
        result.addLookupListener(this);
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
    }

    @Override
    public void componentClosed() {
    }

    void writeProperties(java.util.Properties p) {
        p.setProperty("version", "1.0");
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        Collection<? extends RotationValuesWrapper> wrappers = result.allInstances();
            Optional<? extends RotationValuesWrapper> first = wrappers.stream().findFirst();
        if (!first.isPresent()) {
            Platform.runLater(()->{
                 view.hide(); 
            });
        } else {
            RotationValuesWrapper rotation = first.get();
            DoubleValueWrapper pitchW = rotation.getPitch();
            DoubleValueWrapper rollW = rotation.getRoll();
            DoubleValueWrapper yawW = rotation.getYaw();
            if (pitchW== null || rollW == null || yawW==null) {
                LOGGER.log(Level.WARNING, "Rotation wrapper does not contain roll, pitch or yaw.");
                return;
            }
            Double pitch = convertToDeg(pitchW);
            Double roll = convertToDeg(rollW);
            Double yaw = convertToDeg(yawW);
            if (pitch==null || roll == null || yaw == null) {
                LOGGER.log(Level.WARNING, "Pitch / roll / yaw value cannot be converted to degrees.");
                return;
            }
            Platform.runLater(()->{
                view.show();
                view.setEulerAngleRotation(pitch, roll, yaw);
            });
                    
        }
    }
    private static final Logger LOGGER = Logger.getLogger(Attitude3DTopComponent.class.getName());
    
    private Double convertToDeg(DoubleValueWrapper wrapper) {
        return DegreeConverter.convertToDegrees(wrapper.getValue(), wrapper.getUnit());
    }

}
