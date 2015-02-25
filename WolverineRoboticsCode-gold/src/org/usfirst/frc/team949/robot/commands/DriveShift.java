package org.usfirst.frc.team949.robot.commands;

import org.usfirst.frc.team949.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveShift extends Command {
	
	float distance;
	float zSpeed;
	
	public DriveShift(float distance, float zSpeed) {
		super();
		this.distance = distance;
		this.zSpeed = zSpeed;
	}
	
	@Override
	protected void initialize() {
		Robot.driveTrain.drive.encBackLeft.reset();
		Robot.driveTrain.drive.encBackRight.reset();
		Robot.driveTrain.drive.encFrontLeft.reset();
		Robot.driveTrain.drive.encFrontRight.reset();
	}

	@Override
	protected void execute() {
		Robot.driveTrain.mechanumDrive(0, 0, zSpeed, 0);;
//		SmartDashboard.putNumber("Enc Distance", Robot.driveTrain.drive.encFrontLeft.getDistance());
	}

	@Override
	protected boolean isFinished() {
		return false;
//		return Robot.driveTrain.drive.encFrontLeft.getDistance() >= distance;
	}

	@Override
	protected void end() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
