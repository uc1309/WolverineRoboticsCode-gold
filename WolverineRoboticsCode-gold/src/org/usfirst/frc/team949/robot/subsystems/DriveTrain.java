package org.usfirst.frc.team949.robot.subsystems;

import org.usfirst.frc.team949.robot.RobotMap;
import org.usfirst.frc.team949.robot.commands.JoystickDrive;

//import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {
	
	private RobotDrive drive = RobotMap.drive;
	//private Gyro gyro = RobotMap.driveGyro;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new JoystickDrive());
    }
    
    public void driveForward() {
    	drive.mecanumDrive_Polar(5, 0, 0);
    }
    
	public void mechanumDrive(Joystick joy) {
    	drive.mecanumDrive_Cartesian(joy.getX(), joy.getY(), joy.getZ(), joy.getTwist());
    }
    
    public void stop() {
    	drive.mecanumDrive_Cartesian(0, 0, 0, 0);
    }
}

