package org.usfirst.frc.team949.robot.commands;

import org.usfirst.frc.team949.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveForward extends Command {
	
	float distance;
	
	public DriveForward(float distance) {
		super();
		this.distance = distance;
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
		Robot.driveTrain.mechanumDrive(0, -0.5f, 0, 0);;
//		SmartDashboard.putNumber("Enc Distance", Robot.driveTrain.drive.encFrontLeft.getDistance());
	}

	@Override
	protected boolean isFinished() {
		return Robot.driveTrain.drive.encFrontLeft.getDistance() >= distance;
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
