package org.usfirst.frc.team949.robot.commands;

import org.usfirst.frc.team949.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ArmControl extends Command {

	private double speed = 0.2;
	private double joystickThreshhold = 0.5;

	public ArmControl() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.arm);

	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (Robot.oi.joystickAssist.getRawButton(5)) {
			speed = SmartDashboard.getNumber("TURBO");
		}
		else if (Robot.oi.joystickAssist.getRawButton(3)) {
			speed = SmartDashboard.getNumber("NORMAL");
		}
		else if (Robot.oi.joystickAssist.getRawButton(6)) {
			speed = SmartDashboard.getNumber("SLOW");
		}
		else if (Robot.oi.joystickAssist.getRawButton(4)) {
			speed = SmartDashboard.getNumber("PRECISION");
		}

		if (Robot.oi.joystickAssist.getY() >= joystickThreshhold) {
			Robot.arm.setSpeed(speed);
		} else if (Robot.oi.joystickAssist.getY() <= -joystickThreshhold) {
			Robot.arm.setSpeed(-speed);
		} else {
			Robot.arm.setSpeed(0);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
