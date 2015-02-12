package org.usfirst.frc.team949.robot.commands;

import org.usfirst.frc.team949.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class JoystickDrive extends Command {

	private double joystickThreshhold = 0.02;
	
    public JoystickDrive() {
		// Use requires() here to declare subsystem dependencies
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.oi.joystickDriver.getX() <= joystickThreshhold && Robot.oi.joystickDriver.getZ() <= joystickThreshhold) {
    		Robot.driveTrain.correctMotor(Robot.oi.getJoystickDriver());
    	}
    	
    	if (Robot.oi.joystickDriver.getRawButton(1)) {
    		if (Robot.oi.joystickDriver.getY() >= joystickThreshhold || Robot.oi.joystickDriver.getX() >= joystickThreshhold ||
    				Robot.oi.joystickDriver.getZ() >= joystickThreshhold) {
    			Robot.driveTrain.mechanumFullDrive(Robot.oi.getJoystickDriver());
    		}	
    	}
    	else {
    		if (Robot.oi.joystickDriver.getY() >= joystickThreshhold || Robot.oi.joystickDriver.getX() >= joystickThreshhold ||
    				Robot.oi.joystickDriver.getZ() >= joystickThreshhold) {
    			Robot.driveTrain.mechanumDrive(Robot.oi.getJoystickDriver());
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
