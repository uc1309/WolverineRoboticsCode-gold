package org.usfirst.frc.team949.robot.subsystems;

import org.usfirst.frc.team949.robot.commands.CameraUpdate;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */

public class Camera extends Subsystem {

	public int session = NIVision.IMAQdxOpenCamera("cam0",
			NIVision.IMAQdxCameraControlMode.CameraControlModeController);
	public Image frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

	public int numCommands = 0;

	@Override
	protected void initDefaultCommand() {
//		NIVision.imaqSetImageSize(frame, width, height);
		setDefaultCommand(new CameraUpdate());
	}
}
