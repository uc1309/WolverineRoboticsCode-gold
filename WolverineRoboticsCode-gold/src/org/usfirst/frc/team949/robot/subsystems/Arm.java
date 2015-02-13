
package org.usfirst.frc.team949.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Talon;

import org.usfirst.frc.team949.robot.RobotMap;
import org.usfirst.frc.team949.robot.commands.ArmControl;

/**
 * A great amount of psychological trauma occurred when writing this subsystem.
 */
public class Arm extends Subsystem {
	
	private Talon armMotor;
	
	public Arm() {
		armMotor = new Talon(RobotMap.armMotor);
	}
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new ArmControl());
    }
    
    public void setSpeed(double speed) {
    	armMotor.set(speed);
    }
    
    
}

