package org.usfirst.frc.team949.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnToTote extends FindYellowTotes {

	
	@Override
	protected void execute() {
		super.execute();
		if (!SmartDashboard.getBoolean("IsTote")) {
//			Robot.driveTrain.drive.mecanumDrive_Cartesian(0, 0, SmartDashboard.getNumber("Auto Turn Force"), 0);
		}
	}
	
	@Override
	protected boolean isFinished() {
		return SmartDashboard.getBoolean("IsTote");
	}	

}
