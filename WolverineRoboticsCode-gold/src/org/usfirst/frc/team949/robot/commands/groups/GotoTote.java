package org.usfirst.frc.team949.robot.commands.groups;

import java.util.Date;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GotoTote extends CommandGroup {

	public GotoTote() {
		// TODO Auto-generated constructor stub
	}

	
	public GotoTote(String name) {
		super(name);
//		addSequential(new TurnToTote());
//		addSequential(new DriveToTote());
//		addSequential(new PickUpTote());
		
	}
//
//	long startTime = -1;
//	long gradTime = 2000;
//	void grab() {
//		startTime = new Date().getTime();
//	}
//
//	 boolean asdfisFinished() {
//		return new Date().getTime() - startTime > gradTime && startTime == -1;
//	}
}
