package org.usfirst.frc.team949.robot.subsystems;

//import org.usfirst.frc.team949.robot.RobotMap;
import org.usfirst.frc.team949.robot.commands.JoystickDrive;

import static org.usfirst.frc.team949.robot.RobotMap.*;
//import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {

	RobotDrive drive;

	// private Gyro gyro = RobotMap.driveGyro;

	public DriveTrain() {
		drive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);
		drive.setInvertedMotor(MotorType.kFrontLeft, true);
		drive.setInvertedMotor(MotorType.kRearRight, true);
		SmartDashboard.putNumber("ROTATE CONTROL NERF", (float) 1 / 3);
		SmartDashboard.putNumber("FORWARD CONTROL NERF", (float) 1 / 3);
		SmartDashboard.putNumber("SHIFT CONTROL NERF", (float) 1 / 3);

		SmartDashboard.putNumber("ROTATE FULL NERF", (float) 0.5);
		SmartDashboard.putNumber("FORWARD FULL NERF", (float) 1);
		SmartDashboard.putNumber("SHIFT FULL NERF", (float) 0.5);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new JoystickDrive());
	}

	public void driveForward() {
		drive.mecanumDrive_Polar(5, 0, 0);
	}

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
	}

	public void stop() {
		drive.mecanumDrive_Cartesian(0, 0, 0, 0);
	}
}
