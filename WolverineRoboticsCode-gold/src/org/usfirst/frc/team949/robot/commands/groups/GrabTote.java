package org.usfirst.frc.team949.robot.commands.groups;

import java.util.Date;

import org.usfirst.frc.team949.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class GrabTote extends Command {

	float startTime;
	float duration;
	
	public GrabTote(float duration) {
		super();
		this.duration = duration;
	}
	
	@Override
	protected void initialize() {
		startTime = new Date().getTime();
	}

	@Override
	protected void execute() {
		Robot.grab.setSpeed(1);
	}

	@Override
	protected boolean isFinished() {
		return true;
//		return duration < new Date().getTime() - startTime;
	}

	@Override
	protected void end() {
		Robot.grab.setSpeed(0);
	}

	@Override
	protected void interrupted() {
		// TODO Auto-generated method stub

	}

}
