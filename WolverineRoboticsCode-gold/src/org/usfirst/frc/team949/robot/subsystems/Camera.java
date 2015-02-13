package org.usfirst.frc.team949.robot.subsystems;

import org.usfirst.frc.team949.robot.commands.CameraUpdate;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
<<<<<<< HEAD
public class Camera extends Subsystem {
	
	public class ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport>{
		double PercentAreaToImageArea;
		double Area;
		double ConvexHullArea;
		double BoundingRectLeft;
		double BoundingRectTop;
		double BoundingRectRight;
		double BoundingRectBottom;
		
		public int compareTo(ParticleReport r)
		{
			return (int)(r.Area - this.Area);
		}
		
		public int compare(ParticleReport r1, ParticleReport r2)
		{
			return (int)(r1.Area - r2.Area);
		}
	};

	//Structure to represent the scores for the various tests used for target identification
	public class Scores {
		double Trapezoid;
		double LongAspect;
		double ShortAspect;
		double AreaToConvexHullArea;
	};

	//Images
	public Image frame;
	Image binaryFrame;
	int imaqError;

	//Constants
	NIVision.Range TOTE_HUE_RANGE = new NIVision.Range(24, 49);	//Default hue range for yellow tote
	NIVision.Range TOTE_SAT_RANGE = new NIVision.Range(67, 255);	//Default saturation range for yellow tote
	NIVision.Range TOTE_VAL_RANGE = new NIVision.Range(49, 255);	//Default value range for yellow tote
	double AREA_MINIMUM = 0.5; //Default Area minimum for particle as a percentage of total image area
	double LONG_RATIO = 2.22; //Tote long side = 26.9 / Tote height = 12.1 = 2.22
	double SHORT_RATIO = 1.4; //Tote short side = 16.9 / Tote height = 12.1 = 1.4
	double SCORE_MIN = 75.0;  //Minimum score to be considered a tote
	double VIEW_ANGLE = 49.4; //View angle fo camera, set to Axis m1011 by default, 64 for m1013, 51.7 for 206, 52 for HD3000 square, 60 for HD3000 640x480
	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);
	Scores scores = new Scores();
	
	public Camera() {
		frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
	    binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
	    criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MINIMUM, 100.0, 0, 0);
	    
	  //Put default values to SmartDashboard so fields will appear
        SmartDashboard.putNumber("Tote hue min", TOTE_HUE_RANGE.minValue);
        SmartDashboard.putNumber("Tote hue max", TOTE_HUE_RANGE.maxValue);
     	SmartDashboard.putNumber("Tote sat min", TOTE_SAT_RANGE.minValue);
     	SmartDashboard.putNumber("Tote sat max", TOTE_SAT_RANGE.maxValue);
     	SmartDashboard.putNumber("Tote val min", TOTE_VAL_RANGE.minValue);
     	SmartDashboard.putNumber("Tote val max", TOTE_VAL_RANGE.maxValue);
     	SmartDashboard.putNumber("Area min %", AREA_MINIMUM);
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new RobotVision());
    }
    
	//Comparator function for sorting particles. Returns true if particle 1 is larger
	public static boolean CompareParticleSizes(ParticleReport particle1, ParticleReport particle2)
	{
		//we want descending sort order
		return particle1.PercentAreaToImageArea > particle2.PercentAreaToImageArea;
	}

	/**
	 * Converts a ratio with ideal value of 1 to a score. The resulting function is piecewise
	 * linear going from (0,0) to (1,100) to (2,0) and is 0 for all inputs outside the range 0-2
	 */
	public double ratioToScore(double ratio)
	{
		return (Math.max(0, Math.min(100*(1-Math.abs(1-ratio)), 100)));
	}
=======
>>>>>>> parent of 068b5c5... Changed one variable for Camera

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
