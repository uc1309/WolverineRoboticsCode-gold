package org.usfirst.frc.team949.robot.commands;

import org.usfirst.frc.team949.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class GrabCommand extends Command {

	private double speed = -1;
	
	public GrabCommand() {
		requires(Robot.grab);
	}
	
	@Override
	protected void initialize() {		
	}

	@Override
	protected void execute() {
		if(Robot.oi.joystickAssist.getRawButton(2)){
			Robot.grab.setSpeed(speed);
		}
		else if(Robot.oi.joystickAssist.getRawButton((1))){
			Robot.grab.setSpeed(-speed);
		}
		else{
			Robot.grab.setSpeed(0);
		}
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
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
