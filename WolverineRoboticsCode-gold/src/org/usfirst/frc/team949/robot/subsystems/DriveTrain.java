package org.usfirst.frc.team949.robot.subsystems;

//import org.usfirst.frc.team949.robot.RobotMap;
import org.usfirst.frc.team949.robot.RobotMap;
import org.usfirst.frc.team949.robot.commands.JoystickDrive;

import static org.usfirst.frc.team949.robot.RobotMap.*;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {

	RobotDrive drive;
<<<<<<< HEAD
	Encoder encFrontLeft;
	Encoder encFrontRight;
	Encoder encBackRight;
	Encoder encBackLeft;
=======

	// private Gyro gyro = RobotMap.driveGyro;
>>>>>>> origin/master

	public DriveTrain() {
		drive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
		drive.setInvertedMotor(MotorType.kFrontLeft, true);
		drive.setInvertedMotor(MotorType.kRearRight, true);
<<<<<<< HEAD
		encFrontLeft = new Encoder(0, 1, true, Encoder.EncodingType.k4X);
		encFrontRight = new Encoder(2, 3, false, Encoder.EncodingType.k4X);
		encBackRight = new Encoder(4, 5, true, Encoder.EncodingType.k4X);
		encBackLeft = new Encoder(6, 7, false, Encoder.EncodingType.k4X);		
=======
>>>>>>> origin/master
		SmartDashboard.putNumber("ROTATE CONTROL NERF", (float) 1 / 3);
		SmartDashboard.putNumber("FORWARD CONTROL NERF", (float) 1 / 3);
		SmartDashboard.putNumber("SHIFT CONTROL NERF", (float) 1 / 3);

		SmartDashboard.putNumber("ROTATE FULL NERF", (float) 0.5);
		SmartDashboard.putNumber("FORWARD FULL NERF", (float) 1);
		SmartDashboard.putNumber("SHIFT FULL NERF", (float) 0.5);
<<<<<<< HEAD
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new JoystickDrive());
	}

	public void driveForward() {
		drive.mecanumDrive_Polar(5, 0, 0);
	}

=======
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new JoystickDrive());
	}

	public void driveForward() {
		drive.mecanumDrive_Polar(5, 0, 0);
	}

>>>>>>> origin/master
	public void mechanumDrive(Joystick joy) {
		float rotateNerf = (float) SmartDashboard.getNumber("ROTATE CONTROL NERF");
		float forwardNerf = (float) SmartDashboard.getNumber("FORWARD CONTROL NERF");
		float shiftNerf = (float) SmartDashboard.getNumber("SHIFT CONTROL NERF");

		drive.mecanumDrive_Cartesian(-joy.getZ() * rotateNerf, joy.getY() * forwardNerf, -joy.getX() * shiftNerf, joy.getTwist());
	}

	public void mechanumFullDrive(Joystick joy) {
		float rotateNerf = (float) SmartDashboard.getNumber("ROTATE FULL NERF");
		float forwardNerf = (float) SmartDashboard.getNumber("FORWARD FULL NERF");
		float shiftNerf = (float) SmartDashboard.getNumber("SHIFT FULL NERF");

		drive.mecanumDrive_Cartesian(-joy.getZ() * rotateNerf, joy.getY() * forwardNerf, -joy.getX() * shiftNerf, joy.getTwist());
<<<<<<< HEAD
	}

	public void stop() {
		drive.mecanumDrive_Cartesian(0, 0, 0, 0);
	}
    
    public void correctMotor(Joystick joy) {
    	double rateFrontLeft = Math.abs(encFrontLeft.getRate());
    	double rateFrontRight = Math.abs(encFrontRight.getRate());
    	double rateBackRight = Math.abs(encBackRight.getRate());
    	double rateBackLeft = Math.abs(encBackLeft.getRate());
    	Talon frontLeft = new Talon(RobotMap.frontLeft);
		Talon frontRight = new Talon(RobotMap.frontRight);
		Talon backLeft = new Talon(RobotMap.backLeft);
		Talon backRight = new Talon(RobotMap.backRight);
    	
    	double highest = rateFrontLeft;
    	if (highest < rateFrontRight) {
    		highest = rateFrontRight;
    		if (highest < rateBackRight) {
    			highest = rateBackRight;
    		}
    		if (highest < rateBackLeft) {
    			highest =  rateBackLeft;
    		}
    	}
    	if (highest < rateBackRight) {
    		highest = rateBackRight;
    		if (highest < rateBackLeft) {
    			highest = rateBackLeft;
    		}
    	}
    	if (highest < rateBackLeft) {
    		highest = rateBackLeft;
    	}
    	
    	if (rateFrontLeft == rateFrontRight && rateFrontLeft == rateBackRight && rateFrontLeft == rateBackLeft) {
    		System.out.println("I hate you Kevin");
    		SmartDashboard.putString("Encoder correction", "off");
    	}
    	else {
    		frontLeft.set(-highest * joy.getY());
    		frontRight.set(highest * joy.getY());
    		backLeft.set(highest * joy.getY());
    		backRight.set(-highest * joy.getY());
    		SmartDashboard.putString("Encoder correction", "on");
    	}
    }
}
=======
	}
>>>>>>> origin/master

	public void stop() {
		drive.mecanumDrive_Cartesian(0, 0, 0, 0);
	}
}
