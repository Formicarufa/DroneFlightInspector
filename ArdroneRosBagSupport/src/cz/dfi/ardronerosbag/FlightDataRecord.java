/*
 */
package cz.dfi.ardronerosbag;

/**
 * Stores the message received from AR Drone and stored by
 * ardrone_autonomy in ROS as /ardrone/navdata 
 * @author Tomas Prochazka 21.11.2015
 */
public class FlightDataRecord {

    /**
     * Time in nanoseconds since epoch (1970)
     * (at the moment when the     
 message was recorded.)
     */
    public long time;
    public float batteryPercent;
    public long state;
    public int magX;
    public int magY;
    public int magZ;    
    public int pressure;
    public int temp;
    public float wind_speed;
    public float wind_angle;
    public float wind_comp_angle;
    /** left/right tilt in degrees **/
    public float rotX;
    /** forward/backward tilt in degrees **/

    public float rotY;
    /**
     * rotation about the Z axis in degrees
     */
    public float rotZ;
    /**  Estimated altitude in cm*/
    public int altitude;
    public float vx, vy, vz, ax, ay, az;
    /**  unsigned integer: 0..255 */
    public int motor1;
    /**  unsigned integer: 0..255 */
    public int motor2;
   /**   unsigned integer: 0..255 */
    public int motor3;
    /**  unsigned integer: 0..255 */
    public int motor4;
    
    /**
     * Timestamp of the drone:
     * im milliseconds 
     */
    public float droneTime;
    
}
