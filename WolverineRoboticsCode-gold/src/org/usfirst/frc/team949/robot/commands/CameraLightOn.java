package org.usfirst.frc.team949.robot.commands;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Command;

public class CameraLightOn extends Command {
	
	final int CAMERA_RELAY_PIN = 2;
	
	private Relay cameraLight = new Relay(CAMERA_RELAY_PIN);
	
    public CameraLightOn() {
    	 cameraLight.set(Relay.Value.kForward);
    }
    
    public void turnOff(){
    	cameraLight.set(Relay.Value.kReverse);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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