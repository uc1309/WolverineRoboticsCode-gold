package org.usfirst.frc.team949.robot;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    public static final int frontRight = 0;
    public static final int frontLeft = 1;
    public static final int backRight = 2;
    public static final int backLeft = 3;
    public static final int gyro = 4;
    
    public static Talon frontRightMotor = new Talon(RobotMap.frontRight);
    public static Talon frontLeftMotor = new Talon(RobotMap.frontLeft);
    public static Talon backRightMotor = new Talon(RobotMap.backRight);
    public static Talon backLeftMotor = new Talon(RobotMap.backLeft); 
    
    public static Gyro driveGyro = new Gyro(RobotMap.gyro);
    
    public static RobotDrive drive = new RobotDrive(frontLeftMotor, backLeftMotor, frontRightMotor, frontLeftMotor);	
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
}
