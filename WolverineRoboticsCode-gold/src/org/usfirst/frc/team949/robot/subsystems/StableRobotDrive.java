/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package org.usfirst.frc.team949.robot.subsystems;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary.tInstances;
import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary.tResourceType;
import edu.wpi.first.wpilibj.communication.UsageReporting;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Utility class for handling Robot drive based on a definition of the motor configuration. The robot drive class handles basic driving for a robot. Currently, 2 and 4 motor tank and mecanum drive
 * trains are supported. In the future other drive types like swerve might be implemented. Motor channel numbers are supplied on creation of the class. Those are used for either the drive function
 * (intended for hand created drive code, such as autonomous) or with the Tank/Arcade functions intended to be used for Operator Control driving.
 */
public class StableRobotDrive extends RobotDrive {

	protected float frontLeftNerf;
	protected float rearLeftNerf;
	protected float frontRightNerf;
	protected float rearRightNerf;

	public Encoder encFrontLeft;
	public Encoder encFrontRight;
	public Encoder encBackRight;
	public Encoder encBackLeft;

	 public StableRobotDrive(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor, int frontLeftAEnc, int frontLeftBEnc, int rearLeftAEnc, int rearLeftBEnc,
	 int frontRightAEnc, int frontRightBEnc, int rearRightAEnc, int rearRightBEnc) {
	 super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
	 encFrontLeft = new Encoder(frontLeftAEnc, frontLeftBEnc, false, Encoder.EncodingType.k4X);
	 encFrontRight = new Encoder(frontRightAEnc, frontRightBEnc, true, Encoder.EncodingType.k4X);
	 encBackRight = new Encoder(rearRightAEnc, rearRightBEnc, false, Encoder.EncodingType.k4X);
	 encBackLeft = new Encoder(rearLeftAEnc, rearLeftBEnc, true, Encoder.EncodingType.k4X);
	 encBackLeft.setDistancePerPulse(25.132741229/4);
	 encFrontRight.setDistancePerPulse(25.132741229/4);
	 encBackRight.setDistancePerPulse(25.132741229/4);
	 encBackLeft.setDistancePerPulse(25.132741229/4);
	 updateSmartValues();
	 }

//	@Deprecated
//	public StableRobotDrive(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor, int frontLeftAEnc, int frontLeftBEnc, int rearLeftAEnc, int rearLeftBEnc, int
//			frontRightAEnc, int frontRightBEnc, int rearRightAEnc, int rearRightBEnc) {
//		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
//
//	}

	public static class MotorType {

		/**
		 * The integer value representing this enumeration
		 */
		public final int value;
		static final int kFrontLeft_val = 0;
		static final int kFrontRight_val = 1;
		static final int kRearLeft_val = 2;
		static final int kRearRight_val = 3;
		/**
		 * motortype: front left
		 */
		public static final MotorType kFrontLeft = new MotorType(kFrontLeft_val);
		/**
		 * motortype: front right
		 */
		public static final MotorType kFrontRight = new MotorType(kFrontRight_val);
		/**
		 * motortype: rear left
		 */
		public static final MotorType kRearLeft = new MotorType(kRearLeft_val);
		/**
		 * motortype: rear right
		 */
		public static final MotorType kRearRight = new MotorType(kRearRight_val);

		private MotorType(int value) {
			this.value = value;
		}
	}

	@Override
	public void mecanumDrive_Cartesian(double x, double y, double rotation, double gyroAngle) {
		updateSmartValues();
		if (!kMecanumCartesian_Reported) {
			UsageReporting.report(tResourceType.kResourceType_RobotDrive, getNumMotors(), tInstances.kRobotDrive_MecanumCartesian);
			kMecanumCartesian_Reported = true;
		}
		double xIn = x;
		double yIn = y;
		// Negate y for the joystick.
		yIn = -yIn;
		// Compenstate for gyro angle.
		double rotated[] = rotateVector(xIn, yIn, gyroAngle);
		xIn = rotated[0];
		yIn = rotated[1];

		double wheelSpeeds[] = new double[kMaxNumberOfMotors];
		wheelSpeeds[MotorType.kFrontLeft_val] = (xIn + yIn + rotation) * frontLeftNerf;
		wheelSpeeds[MotorType.kFrontRight_val] = (-xIn + yIn - rotation) * frontRightNerf;
		wheelSpeeds[MotorType.kRearLeft_val] = (-xIn + yIn + rotation) * rearLeftNerf;
		wheelSpeeds[MotorType.kRearRight_val] = (xIn + yIn - rotation) * rearRightNerf;

		normalize(wheelSpeeds);
		m_frontLeftMotor.set(wheelSpeeds[MotorType.kFrontLeft_val] * m_invertedMotors[MotorType.kFrontLeft_val] * m_maxOutput, m_syncGroup);
		m_frontRightMotor.set(wheelSpeeds[MotorType.kFrontRight_val] * m_invertedMotors[MotorType.kFrontRight_val] * m_maxOutput, m_syncGroup);
		m_rearLeftMotor.set(wheelSpeeds[MotorType.kRearLeft_val] * m_invertedMotors[MotorType.kRearLeft_val] * m_maxOutput, m_syncGroup);
		m_rearRightMotor.set(wheelSpeeds[MotorType.kRearRight_val] * m_invertedMotors[MotorType.kRearRight_val] * m_maxOutput, m_syncGroup);

		if (m_syncGroup != 0) {
			CANJaguar.updateSyncGroup(m_syncGroup);
		}

		if (m_safetyHelper != null)
			m_safetyHelper.feed();
	}

	public void updateSmartValues() {
		frontLeftNerf = (float) SmartDashboard.getNumber("frontLeftNerf");
		rearLeftNerf = (float) SmartDashboard.getNumber("rearLeftNerf");
		frontRightNerf = (float) SmartDashboard.getNumber("frontRightNerf");
		rearRightNerf = (float) SmartDashboard.getNumber("rearRightNerf");

		 SmartDashboard.putNumber("Front Left wheel RPS", encFrontLeft.getRate());
		 SmartDashboard.putNumber("Front Right wheel RPS", encFrontRight.getRate());
		 SmartDashboard.putNumber("Rear Left wheel RPS", encBackLeft.getRate());
		 SmartDashboard.putNumber("Rear Right wheel RPS", encBackRight.getRate());

	}

}
