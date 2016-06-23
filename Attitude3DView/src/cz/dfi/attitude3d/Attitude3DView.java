 /*
 */
package cz.dfi.attitude3d;

import com.interactivemesh.jfx.importer.col.ColModelImporter;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import org.openide.modules.InstalledFileLocator;

/**
 *
 * @author Tomas Prochazka 22.5.2016
 */
public class Attitude3DView {

    private static final String MESH_NAME = "ardrone_centered.dae";
    private Group root;
    private Scene scene;
    private Group droneModel;

    public Scene createScene() {
        root = createSceneContent();
        scene = new Scene(root,0,0,true);
        scene.setFill(Color.SKYBLUE);
        PerspectiveCamera camera = new PerspectiveCamera(true);
        scene.setCamera(camera);
        camera.setFarClip(200);
        camera.setFieldOfView(60);
        camera.setTranslateZ(-2);
        //root.setTranslateX(300);        
        //root.setTranslateY(300);
        return scene;
    }

    private static Node[] loadDroneMesh() {
        //http://wiki.netbeans.org/DevFaqInstalledFileLocator
        File file = InstalledFileLocator.getDefault().locate(
                MESH_NAME,
                "cz.dfi.attitude3d",
                false);
        if (file == null) {
            Logger.getLogger(Attitude3DView.class.getName()).log(Level.SEVERE, "Could not locate the drone 3D model " + MESH_NAME);
            return null;
        }
        ColModelImporter importer = new ColModelImporter();
        importer.read(file);
        return importer.getImport();
    }
    /**
     *  The rotations will be applied in the following order:
     * roll -> pitch -> yaw
     * Unit: degrees.
     * Has to be called from the JavaFX thread.
     * Use {@link Platform#runLater(java.lang.Runnable) }
     * @param pitch THETA
     * @param roll  PHI
     * @param yaw PSI
     */
    public void setEulerAngleRotation(double pitch, double roll, double yaw) {
        ObservableList<Transform> transforms = droneModel.getTransforms();
        transforms.clear();
        //PHI - THETA - PSI ... roll - pitch - yaw
        Point3D rollAxis = new Point3D(0, 0, 1);
        Point3D pitchAxis = new Point3D(1,0,0);
        Point3D yawAxis = new Point3D(0,1,0);
        
        Rotate rollRot = new Rotate(roll, rollAxis);
        Point3D pitchAxis1 = rollRot.transform(pitchAxis);
        Point3D yawAxis1 = rollRot.transform(yawAxis);
        transforms.add(rollRot);
        
        Rotate pitchRot = new Rotate( pitch,pitchAxis1);
        Point3D yawAxis2 = pitchRot.transform(yawAxis1);
        transforms.add(pitchRot);
        
        Rotate yawRot = new Rotate(yaw,yawAxis2);
        transforms.add(yawRot);
    }
        
    private Group createSceneContent() {
        Node[] droneMesh = loadDroneMesh();
        droneModel = new Group(droneMesh);
        droneModel.setScaleX(.25);
        droneModel.setScaleZ(.25);
        droneModel.setScaleY(.25);
        droneModel.setTranslateZ(40);
        droneModel.setRotationAxis(new Point3D(0,1,0));
        //droneModel.setRotate(180);
//        Timeline anim = new Timeline(new KeyFrame(Duration.millis(200), (ActionEvent event) -> {
//             droneModel.setRotate(droneModel.getRotate()+2);
//        }));
//        anim.setCycleCount(Timeline.INDEFINITE);
//        anim.play();
        Group sceneRoot = new Group(droneModel);
        ObservableList<Node> children = sceneRoot.getChildren();
        AmbientLight ambientLight = new AmbientLight();
        //ambientLight.
       children.add(ambientLight);
        return sceneRoot;
    }
    /**
     * Has to be called from the JavaFX thread.
     * use {@link Platform#runLater(java.lang.Runnable) }
     */
    void hide() {
        droneModel.setVisible(false);
    }
    /**
     * Has to be called from the JavaFX thread.
     * Use {@link Platform#runLater(java.lang.Runnable) }
     */
    void show() {
        droneModel.setVisible(true);
    }
}
