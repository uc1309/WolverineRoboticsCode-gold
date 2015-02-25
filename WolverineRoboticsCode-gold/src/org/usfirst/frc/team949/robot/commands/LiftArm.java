package org.usfirst.frc.team949.robot.commands;

import java.util.Date;

import org.usfirst.frc.team949.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LiftArm extends Command {

	float startTime;
	float duration;
	
	public LiftArm(float duration) {
		super();
		this.duration = duration;
	}
	
	@Override
	protected void initialize() {
		startTime = new Date().getTime();
	}

	@Override
	protected void execute() {
		Robot.arm.setSpeed(0.3);
	}

	@Override
	protected boolean isFinished() {
		return true;
//		return duration < new Date().getTime() - startTime;
	}

	@Override
	protected void end() {
		Robot.arm.setSpeed(0);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
