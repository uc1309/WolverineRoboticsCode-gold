package org.usfirst.frc.team949.robot.subsystems;

import org.usfirst.frc.team949.robot.commands.CameraUpdate;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */

public class Camera extends Subsystem {

	public int session = NIVision.IMAQdxOpenCamera("cam0",
			NIVision.IMAQdxCameraControlMode.CameraControlModeController);
	public Image frame;
	

	public int numCommands = 0;

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new CameraUpdate());
	}
	
	public Camera() {
		super();
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		NIVision.imaqSetImageSize(frame, (int)SmartDashboard.getNumber("Camera Width Res"), (int)SmartDashboard.getNumber("Camera Height Res"));
	}
}
