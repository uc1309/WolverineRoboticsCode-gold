package org.usfirst.frc.team949.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Talon;

import org.usfirst.frc.team949.robot.RobotMap;
import org.usfirst.frc.team949.robot.commands.GrabCommand;

public class Grab extends Subsystem{

	private Talon grabMotor;
	
	public Grab(){
		grabMotor = new Talon(RobotMap.grabMotor);
	}
	@Override
	protected void initDefaultCommand() {
        setDefaultCommand(new GrabCommand());
	}
	public void setSpeed(double speed){
		grabMotor.set(speed);
	}

}
