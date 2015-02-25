package org.usfirst.frc.team949.robot.subsystems;

//import org.usfirst.frc.team949.robot.RobotMap;
import org.usfirst.frc.team949.robot.commands.JoystickDrive;

import static org.usfirst.frc.team949.robot.RobotMap.*;
import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.Gyro;
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

	public StableRobotDrive drive;

	private float rotateNerf;
	private float forwardNerf;
	private float shiftNerf;

	public DriveTrain() {
		drive = new StableRobotDrive(frontLeft, backLeft, frontRight, backRight, 0, 1, 2, 3, 4, 5, 6, 7);
//		drive = new RobotDrive(frontLeft, backLeft, frontRight, backRight);

		drive.setInvertedMotor(MotorType.kFrontLeft, true);
		drive.setInvertedMotor(MotorType.kRearRight, true);
		updateNerfValues();
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new JoystickDrive());
	}

	public void driveForward() {
		updateNerfValues();
		drive.mecanumDrive_Polar(5, 0, 0);
	}

	public void mechanumDrive(Joystick joy) {
		updateNerfValues();
		drive.mecanumDrive_Cartesian(-joy.getZ() * rotateNerf, joy.getY() * forwardNerf, -joy.getX() * shiftNerf, joy.getTwist());
	}

	public void mechanumFullDrive(Joystick joy) {
		updateNerfValues();
		drive.mecanumDrive_Cartesian(-joy.getZ(), joy.getY(), -joy.getX(), joy.getTwist());
	}

	public void mechanumDrive(float x, float y, float twist, float gyro) {
		updateNerfValues();
		drive.mecanumDrive_Cartesian(x, y, twist, gyro);
	}

	public void stop() {
		drive.mecanumDrive_Cartesian(0, 0, 0, 0);
	}

	public void updateNerfValues() {
		rotateNerf = (float) SmartDashboard.getNumber("ROTATE CONTROL NERF");
		forwardNerf = (float) SmartDashboard.getNumber("FORWARD CONTROL NERF");
		shiftNerf = (float) SmartDashboard.getNumber("SHIFT CONTROL NERF");
	}

}
