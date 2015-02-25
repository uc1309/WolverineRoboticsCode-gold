package org.usfirst.frc.team949.robot.commands.groups;

import org.usfirst.frc.team949.robot.Robot;
import org.usfirst.frc.team949.robot.commands.CameraUpdate;
import org.usfirst.frc.team949.robot.commands.DriveForward;
import org.usfirst.frc.team949.robot.commands.DriveShift;
import org.usfirst.frc.team949.robot.commands.FindYellowTotes;
import org.usfirst.frc.team949.robot.commands.LiftArm;
import org.usfirst.frc.team949.robot.commands.TurnToTote;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonomousCommand extends CommandGroup {

	public AutonomousCommand() {
		this("adf");
	}

	public AutonomousCommand(String name) {
		requires(Robot.driveTrain);
		requires(Robot.arm);
		requires(Robot.grab);

		addSequential(new DriveForward(100));
		addSequential(new GrabTote(1000));
		addSequential(new LiftArm(1000));
		addSequential(new DriveShift(100, 0.5f));
	}

}
