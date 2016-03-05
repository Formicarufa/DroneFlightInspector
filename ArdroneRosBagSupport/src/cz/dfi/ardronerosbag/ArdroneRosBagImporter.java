/*
 */
package cz.dfi.ardronerosbag;

import cz.dfi.datamodel.FlightDataRecord;
import cz.dfi.datamodel.FlightRecordsWrapper;
import cz.dfi.datamodel.ImageDataRecord;
import cz.dfi.datamodel.ImageRecordsWrapper;
import cz.dfi.rosbagimporter.RosbagImporter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.util.lookup.InstanceContent;
import org.openide.util.lookup.ServiceProvider;
import rosbagreader.RosMessageData;
import rosbagreader.RosStandardMessageHeader;
import rosbagreader.RosbagMessageDataParser;
import rosbagreader.RosbagReader;
import rosbagreader.Vector3;
import rosbagreader.exceptions.InvalidFieldValueRosbagException;
import rosbagreader.exceptions.InvalidRosbagFormatException;
import rosbagreader.exceptions.RequiredFieldMissingRosbagException;
import rosbagreader.exceptions.UnexpectedEndOfRosbagFileException;

/**
 *
 * @author Tomas Prochazka 20.11.2015
 */
@ServiceProvider(
        service = cz.dfi.rosbagimporter.RosbagImporter.class
)
public class ArdroneRosBagImporter implements RosbagImporter {

    @Override
    public boolean loadRecords(File data, InstanceContent content) {
        try (FileInputStream f = new FileInputStream(data)) {
            List<FlightDataRecord> records = new ArrayList<>();
            RosbagReader r = new RosbagReader(f);
            ArdroneRosBagMessageParser ardroneRosBagMessageParser = new ArdroneRosBagMessageParser(records);
            r.parseBag(ardroneRosBagMessageParser);
            content.add(new FlightRecordsWrapper(records));
            content.add(new ImageRecordsWrapper(ardroneRosBagMessageParser.images));
            GraphableQuantitiesPublisher publisher = new GraphableQuantitiesPublisher(records);
            publisher.addQuantitiesToLookup(content);
            Logger.getLogger(ArdroneRosBagImporter.class.getName()).log(Level.SEVERE, "Loaded total of {0} records from the ROSBag file.", records.size());

        } catch (IOException | UnexpectedEndOfRosbagFileException | InvalidRosbagFormatException | RequiredFieldMissingRosbagException | InvalidFieldValueRosbagException ex) {
            Logger.getLogger(ArdroneRosBagImporter.class.getName()).log(Level.SEVERE, "Error while loading ARDrone data from ROS Bag file. ", ex);
        }

        return true;

    }

    private static class ArdroneRosBagMessageParser implements RosbagMessageDataParser {

        private final List<FlightDataRecord> records;
        private final List<ImageDataRecord> images = new ArrayList<>();
        
        private ArdroneRosBagMessageParser(List<FlightDataRecord> records) {
            this.records = records;
        }

        @Override
        public void parseMessageData(RosMessageData rmd) throws IOException, UnexpectedEndOfRosbagFileException {
            if ("/ardrone/navdata".equals(rmd.getTopic())) {
                parseNavData(rmd);
            } else if ("/ardrone/image_raw".equals(rmd.getTopic())) {
                parseImage(rmd);
            } else if ("/geometry_msgs/Twist".equals(rmd.getTopic())) {
                parseCommands(rmd);
            }
        }

        private void parseNavData(RosMessageData rmd) throws IllegalStateException, UnexpectedEndOfRosbagFileException, IOException {
            FlightDataRecord r = new FlightDataRecord();
            RosStandardMessageHeader header = rmd.readMessageHeader();
            r.time=header.stamp.getTimeAsNanos();
            r.batteryPercent = rmd.readFloat();
            r.state = rmd.readUnsignedInt();
            r.magX = rmd.readInt();
            r.magY = rmd.readInt();
            r.magZ = rmd.readInt();
            r.pressure = rmd.readInt();
            r.temp = rmd.readInt();
            r.wind_speed = rmd.readFloat();
            r.wind_angle = rmd.readFloat();
            r.wind_comp_angle = rmd.readFloat();
            r.rotX = rmd.readFloat();
            r.rotY = rmd.readFloat();
            r.rotZ = rmd.readFloat();
            r.altitude = rmd.readInt();
            r.vx = rmd.readFloat();
            r.vy = rmd.readFloat();
            r.vz = rmd.readFloat();
            r.ax = rmd.readFloat();
            r.ay = rmd.readFloat();
            r.az = rmd.readFloat();
            r.motor1 = rmd.readByte();
            r.motor2 = rmd.readByte();
            r.motor3 = rmd.readByte();
            r.motor4 = rmd.readByte();

            //tags count
            rmd.readUnsignedInt();
            //tags_type
            rmd.readUnsignedIntArray();
            //tags_xc
            rmd.readUnsignedIntArray();
            //tags_yc
            rmd.readUnsignedIntArray();
            //tags_width
            rmd.readUnsignedIntArray();
            //tags_height
            rmd.readUnsignedIntArray();
            //tags_orientation
            rmd.readFloatArray();
            //tags_distance
            rmd.readFloatArray();
            r.droneTime = rmd.readFloat();
            if (rmd.getBytesLeft() != 0) {
                throw new IllegalStateException("Incorrect message size, left: " + rmd.getBytesLeft());
            }
            records.add(r);
        }

        private void parseImage(RosMessageData message) throws IOException, UnexpectedEndOfRosbagFileException {
            RosStandardMessageHeader header = message.readMessageHeader();
            
            long height = message.readUnsignedInt();
            long width = message.readUnsignedInt();
            String compression = message.readString();
            int isBigEndian = message.readByte();
            long step_row_length = message.readUnsignedInt();
            byte[] messageBytes = message.readBytes(message.getBytesLeft());
            BufferedImage i = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int pos = y * (int) step_row_length + x * 3;
                    int rgb = messageBytes[pos + 2];
                    rgb = (rgb << 8) + messageBytes[pos + 1];
                    rgb = (rgb << 8) + messageBytes[pos];
                    i.setRGB(x, y, rgb);
                }
            }
            images.add(new ImageDataRecord(header.stamp.getTimeAsNanos(),i));
        }
        /**
         * Forward/backward movement.
         */
        private List<Double> pitch= new ArrayList<>();
        /**
         * Leftward/rightward movement.
         */
        private List<Double> roll = new ArrayList<>();
        /**
         * Rotation 
         */
        private List<Double> yaw= new ArrayList<>();
        private List<Double> vertical_speed = new ArrayList<>();
        
        private void parseCommands(RosMessageData rmd) throws IOException, UnexpectedEndOfRosbagFileException {
            Vector3 linear = rmd.readVector3();
            pitch.add(linear.x);
            roll.add(linear.y);
            vertical_speed.add(linear.z);
            Vector3 angular = rmd.readVector3();
            yaw.add(angular.z);
            
        }
    }

}
