  package org.usfirst.frc.team949.robot;

import org.usfirst.frc.team949.robot.commands.FindYellowTotes;
import org.usfirst.frc.team949.robot.commands.groups.AutonomousCommand;
import org.usfirst.frc.team949.robot.subsystems.Arm;
import org.usfirst.frc.team949.robot.subsystems.Camera;
import org.usfirst.frc.team949.robot.subsystems.DriveTrain;
import org.usfirst.frc.team949.robot.subsystems.Grab;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	public static Arm arm; 
	public static DriveTrain driveTrain;
	public static OI oi;
	public static Camera camera;
	public static Grab grab;
	int session;

    Command autonomousCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	SmartDashboard.putNumber("Camera Width Res", 1920);
    	SmartDashboard.putNumber("Camera Height Res", 1080);
    	
    	SmartDashboard.putNumber("ROTATE CONTROL NERF", (float) 1 / 3);
    	SmartDashboard.putNumber("FORWARD CONTROL NERF", (float) 1 / 3);
    	SmartDashboard.putNumber("SHIFT CONTROL NERF", (float) 1 / 3);
    	SmartDashboard.putNumber("ROTATE FULL NERF", (float) 0.5);
    	SmartDashboard.putNumber("FORWARD FULL NERF", (float) 1);
    	SmartDashboard.putNumber("SHIFT FULL NERF", (float) 0.5);
    	
    	SmartDashboard.putNumber("TURBO", 1);
    	SmartDashboard.putNumber("NORMAL", 0.8);
    	SmartDashboard.putNumber("SLOW", 0.4);
    	SmartDashboard.putNumber("PRECISION", 0.2);
    	
    	SmartDashboard.putNumber("Auto Turn Force", 0.2);
    	SmartDashboard.putNumber("Auto Drive Force", 0.2);
    	
    	SmartDashboard.putNumber("frontLeftNerf", 1);
    	SmartDashboard.putNumber("rearLeftNerf", 1);
    	SmartDashboard.putNumber("frontRightNerf", 1);
    	SmartDashboard.putNumber("rearRightNerf", 1);
    	
		oi = new OI();
		driveTrain = new DriveTrain();
		arm = new Arm();
		grab = new Grab();
		camera = new Camera();
		autonomousCommand = new AutonomousCommand();
		
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
    	// instantiate the command used for the autonomous period
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}

