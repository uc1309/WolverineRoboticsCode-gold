package org.usfirst.frc.team949.robot;

import java.util.Comparator;
import java.util.Vector;

<<<<<<< HEAD
import org.usfirst.frc.team949.robot.subsystems.Arm;
import org.usfirst.frc.team949.robot.subsystems.DriveTrain;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.ShapeMode;

=======
>>>>>>> FETCH_HEAD
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
<<<<<<< HEAD
=======

import org.usfirst.frc.team949.robot.commands.*;
import org.usfirst.frc.team949.robot.subsystems.*;
>>>>>>> FETCH_HEAD

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.ShapeMode;

import java.lang.Math;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to each mode, as described in the IterativeRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource directory.
 */
public class Robot extends IterativeRobot {
	
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
	Image frame;
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

	public class ParticleReport implements Comparator<ParticleReport>, Comparable<ParticleReport> {
		double PercentAreaToImageArea;
		double Area;
		double ConvexHullArea;
		double BoundingRectLeft;
		double BoundingRectTop;
		double BoundingRectRight;
		double BoundingRectBottom;

		public int compareTo(ParticleReport r)
		{
			return (int) (r.Area - this.Area);
		}

		public int compare(ParticleReport r1, ParticleReport r2)
		{
			return (int) (r1.Area - r2.Area);
		}
	};

	// Structure to represent the scores for the various tests used for target identification
	public class Scores {
		double Trapezoid;
		double LongAspect;
		double ShortAspect;
		double AreaToConvexHullArea;
	};

	public static Arm arm;
	public static DriveTrain driveTrain;
	public static OI oi;
<<<<<<< HEAD
	Command autonomousCommand;

	// Images
	Image frame;
	Image binaryFrame;
	int imaqError;
=======
	int session;
>>>>>>> FETCH_HEAD

	// Constants
	NIVision.Range TOTE_HUE_RANGE = new NIVision.Range(24, 49); // Default hue range for yellow tote
	NIVision.Range TOTE_SAT_RANGE = new NIVision.Range(67, 255); // Default saturation range for yellow tote
	NIVision.Range TOTE_VAL_RANGE = new NIVision.Range(49, 255); // Default value range for yellow tote
	double AREA_MINIMUM = 0.5; // Default Area minimum for particle as a percentage of total image area
	double LONG_RATIO = 2.22; // Tote long side = 26.9 / Tote height = 12.1 = 2.22
	double SHORT_RATIO = 1.4; // Tote short side = 16.9 / Tote height = 12.1 = 1.4
	double SCORE_MIN = 75.0; // Minimum score to be considered a tote
	double VIEW_ANGLE = 49.4; // View angle fo camera, set to Axis m1011 by default, 64 for m1013, 51.7 for 206, 52 for HD3000 square, 60 for HD3000 640x480
	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0, 0, 1, 1);
	Scores scores = new Scores();

	int session;

	/**
	 * This function is run when the robot is first started up and should be used for any initialization code.
	 */
	public void robotInit() {

		// the camera name (ex "cam0") can be found through the roborio web interface
		session = NIVision.IMAQdxOpenCamera("cam0",
				NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		NIVision.IMAQdxConfigureGrab(session);

		// create images
		frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA,
				AREA_MINIMUM, 100.0, 0, 0);

		// Put default values to SmartDashboard so fields will appear
		SmartDashboard.putNumber("Tote hue min", TOTE_HUE_RANGE.minValue);
		SmartDashboard.putNumber("Tote hue max", TOTE_HUE_RANGE.maxValue);
		SmartDashboard.putNumber("Tote sat min", TOTE_SAT_RANGE.minValue);
		SmartDashboard.putNumber("Tote sat max", TOTE_SAT_RANGE.maxValue);
		SmartDashboard.putNumber("Tote val min", TOTE_VAL_RANGE.minValue);
		SmartDashboard.putNumber("Tote val max", TOTE_VAL_RANGE.maxValue);
		SmartDashboard.putNumber("Area min %", AREA_MINIMUM);

<<<<<<< HEAD
		oi = new OI();
		// instantiate the command used for the autonomous period
		// autonomousCommand = new AutonomousCommand();
		driveTrain = new DriveTrain();
		arm = new Arm();
	}

	public void operatorControl() {
		NIVision.IMAQdxStartAcquisition(session);

		/**
		 * grab an image, draw the circle, and provide it for the camera server which will in turn send it to the dashboard.
		 */
		NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);

		while (isOperatorControl() && isEnabled()) {

			NIVision.IMAQdxGrab(session, frame, 1);
			NIVision.imaqDrawShapeOnImage(frame, frame, rect,
					DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);

			CameraServer.getInstance().setImage(frame);

			/** robot code here! **/
			Timer.delay(0.005); // wait for a motor update time
		}
		NIVision.IMAQdxStopAcquisition(session);
	}

	public void test() {
	}

	public void autonomous() {
		while (isAutonomous() && isEnabled())
		{
			// read file in from disk. For this example to run you need to copy image20.jpg from the SampleImages folder to the
			// directory shown below using FTP or SFTP: http://wpilib.screenstepslive.com/s/4485/m/24166/l/282299-roborio-ftp
			NIVision.imaqReadFile(frame, "/home/lvuser/SampleImages/image20.jpg");

			// Update threshold values from SmartDashboard. For performance reasons it is recommended to remove this after calibration is finished.
			TOTE_HUE_RANGE.minValue = (int) SmartDashboard.getNumber("Tote hue min", TOTE_HUE_RANGE.minValue);
			TOTE_HUE_RANGE.maxValue = (int) SmartDashboard.getNumber("Tote hue max", TOTE_HUE_RANGE.maxValue);
			TOTE_SAT_RANGE.minValue = (int) SmartDashboard.getNumber("Tote sat min", TOTE_SAT_RANGE.minValue);
			TOTE_SAT_RANGE.maxValue = (int) SmartDashboard.getNumber("Tote sat max", TOTE_SAT_RANGE.maxValue);
			TOTE_VAL_RANGE.minValue = (int) SmartDashboard.getNumber("Tote val min", TOTE_VAL_RANGE.minValue);
			TOTE_VAL_RANGE.maxValue = (int) SmartDashboard.getNumber("Tote val max", TOTE_VAL_RANGE.maxValue);

			// Threshold the image looking for yellow (tote color)
			NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSV, TOTE_HUE_RANGE,
					TOTE_SAT_RANGE, TOTE_VAL_RANGE);

			// Send particle count to dashboard
			int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
			SmartDashboard.putNumber("Masked particles", numParticles);

			// Send masked image to dashboard to assist in tweaking mask.
			CameraServer.getInstance().setImage(binaryFrame);

			// filter out small particles
			float areaMin = (float) SmartDashboard.getNumber("Area min %", AREA_MINIMUM);
			criteria[0].lower = areaMin;
			imaqError = NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, criteria, filterOptions, null);

			// Send particle count after filtering to dashboard
			numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
			SmartDashboard.putNumber("Filtered particles", numParticles);

			if (numParticles > 0)
			{
				// Measure particles and sort by particle size
				Vector<ParticleReport> particles = new Vector<ParticleReport>();
				for (int particleIndex = 0; particleIndex < numParticles; particleIndex++)
				{
					ParticleReport par = new ParticleReport();
					par.PercentAreaToImageArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0,
							NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
					par.Area = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0,
							NIVision.MeasurementType.MT_AREA);
					par.ConvexHullArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0,
							NIVision.MeasurementType.MT_CONVEX_HULL_AREA);
					par.BoundingRectTop = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0,
							NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
					par.BoundingRectLeft = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0,
							NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
					par.BoundingRectBottom = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0,
							NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
					par.BoundingRectRight = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0,
							NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
=======
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {

        // the camera name (ex "cam0") can be found through the roborio web interface
        session = NIVision.IMAQdxOpenCamera("cam0",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
        
        // create images
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
        
		oi = new OI();
        // instantiate the command used for the autonomous period
        autonomousCommand = new AutonomousCommand();
        driveTrain = new DriveTrain();
        arm = new Arm();
    }
    
    public void operatorControl() {
        NIVision.IMAQdxStartAcquisition(session);

        /**
         * grab an image, draw the circle, and provide it for the camera server
         * which will in turn send it to the dashboard.
         */
        NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);

        while (isOperatorControl() && isEnabled()) {

            NIVision.IMAQdxGrab(session, frame, 1);
            NIVision.imaqDrawShapeOnImage(frame, frame, rect,
                    DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);
            
            CameraServer.getInstance().setImage(frame);

            /** robot code here! **/
            Timer.delay(0.005);		// wait for a motor update time
        }
        NIVision.IMAQdxStopAcquisition(session);
	}

    public void test() {
    }
    
    public void autonomous() {
		while (isAutonomous() && isEnabled())
		{
			//read file in from disk. For this example to run you need to copy image20.jpg from the SampleImages folder to the
			//directory shown below using FTP or SFTP: http://wpilib.screenstepslive.com/s/4485/m/24166/l/282299-roborio-ftp
			NIVision.imaqReadFile(frame, "/home/lvuser/SampleImages/image20.jpg");

			//Update threshold values from SmartDashboard. For performance reasons it is recommended to remove this after calibration is finished.
			TOTE_HUE_RANGE.minValue = (int)SmartDashboard.getNumber("Tote hue min", TOTE_HUE_RANGE.minValue);
			TOTE_HUE_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote hue max", TOTE_HUE_RANGE.maxValue);
			TOTE_SAT_RANGE.minValue = (int)SmartDashboard.getNumber("Tote sat min", TOTE_SAT_RANGE.minValue);
			TOTE_SAT_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote sat max", TOTE_SAT_RANGE.maxValue);
			TOTE_VAL_RANGE.minValue = (int)SmartDashboard.getNumber("Tote val min", TOTE_VAL_RANGE.minValue);
			TOTE_VAL_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote val max", TOTE_VAL_RANGE.maxValue);

			//Threshold the image looking for yellow (tote color)
			NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSV, TOTE_HUE_RANGE, TOTE_SAT_RANGE, TOTE_VAL_RANGE);

			//Send particle count to dashboard
			int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
			SmartDashboard.putNumber("Masked particles", numParticles);

			//Send masked image to dashboard to assist in tweaking mask.
			CameraServer.getInstance().setImage(binaryFrame);

			//filter out small particles
			float areaMin = (float)SmartDashboard.getNumber("Area min %", AREA_MINIMUM);
			criteria[0].lower = areaMin;
			imaqError = NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, criteria, filterOptions, null);

			//Send particle count after filtering to dashboard
			numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
			SmartDashboard.putNumber("Filtered particles", numParticles);

			if(numParticles > 0)
			{
				//Measure particles and sort by particle size
				Vector<ParticleReport> particles = new Vector<ParticleReport>();
				for(int particleIndex = 0; particleIndex < numParticles; particleIndex++)
				{
					ParticleReport par = new ParticleReport();
					par.PercentAreaToImageArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
					par.Area = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA);
					par.ConvexHullArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_CONVEX_HULL_AREA);
					par.BoundingRectTop = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
					par.BoundingRectLeft = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
					par.BoundingRectBottom = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
					par.BoundingRectRight = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
>>>>>>> FETCH_HEAD
					particles.add(par);
				}
				particles.sort(null);

<<<<<<< HEAD
				// This example only scores the largest particle. Extending to score all particles and choosing the desired one is left as an exercise
				// for the reader. Note that the long and short side scores expect a single tote and will not work for a stack of 2 or more totes.
				// Modification of the code to accommodate 2 or more stacked totes is left as an exercise for the reader.
=======
				//This example only scores the largest particle. Extending to score all particles and choosing the desired one is left as an exercise
				//for the reader. Note that the long and short side scores expect a single tote and will not work for a stack of 2 or more totes.
				//Modification of the code to accommodate 2 or more stacked totes is left as an exercise for the reader.
>>>>>>> FETCH_HEAD
				scores.Trapezoid = TrapezoidScore(particles.elementAt(0));
				SmartDashboard.putNumber("Trapezoid", scores.Trapezoid);
				scores.LongAspect = LongSideScore(particles.elementAt(0));
				SmartDashboard.putNumber("Long Aspect", scores.LongAspect);
				scores.ShortAspect = ShortSideScore(particles.elementAt(0));
				SmartDashboard.putNumber("Short Aspect", scores.ShortAspect);
				scores.AreaToConvexHullArea = ConvexHullAreaScore(particles.elementAt(0));
				SmartDashboard.putNumber("Convex Hull Area", scores.AreaToConvexHullArea);
<<<<<<< HEAD
				boolean isTote = scores.Trapezoid > SCORE_MIN
						&& (scores.LongAspect > SCORE_MIN || scores.ShortAspect > SCORE_MIN)
						&& scores.AreaToConvexHullArea > SCORE_MIN;
				boolean isLong = scores.LongAspect > scores.ShortAspect;

				// Send distance and tote status to dashboard. The bounding rect, particularly the horizontal center (left - right) may be useful for rotating/driving towards a tote
=======
				boolean isTote = scores.Trapezoid > SCORE_MIN && (scores.LongAspect > SCORE_MIN || scores.ShortAspect > SCORE_MIN) && scores.AreaToConvexHullArea > SCORE_MIN;
				boolean isLong = scores.LongAspect > scores.ShortAspect;

				//Send distance and tote status to dashboard. The bounding rect, particularly the horizontal center (left - right) may be useful for rotating/driving towards a tote
>>>>>>> FETCH_HEAD
				SmartDashboard.putBoolean("IsTote", isTote);
				SmartDashboard.putNumber("Distance", computeDistance(binaryFrame, particles.elementAt(0), isLong));
			} else {
				SmartDashboard.putBoolean("IsTote", false);
			}

<<<<<<< HEAD
			Timer.delay(0.005); // wait for a motor update time
		}
	}

=======
			Timer.delay(0.005);				// wait for a motor update time
		}
	}
	
>>>>>>> FETCH_HEAD
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
<<<<<<< HEAD
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		// schedule the autonomous command (example)
		autonomousCommand.cancel();
	}

	/**
	 * This function is called when the disabled button is hit. You can use it to reset subsystems before shutting down.
	 */
	public void disabledInit() {

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

	// Comparator function for sorting particles. Returns true if particle 1 is larger
	static boolean CompareParticleSizes(ParticleReport particle1, ParticleReport particle2)
	{
		// we want descending sort order
=======
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
    
	//Comparator function for sorting particles. Returns true if particle 1 is larger
	static boolean CompareParticleSizes(ParticleReport particle1, ParticleReport particle2)
	{
		//we want descending sort order
>>>>>>> FETCH_HEAD
		return particle1.PercentAreaToImageArea > particle2.PercentAreaToImageArea;
	}

	/**
<<<<<<< HEAD
	 * Converts a ratio with ideal value of 1 to a score. The resulting function is piecewise linear going from (0,0) to (1,100) to (2,0) and is 0 for all inputs outside the range 0-2
	 */
	double ratioToScore(double ratio)
	{
		return (Math.max(0, Math.min(100 * (1 - Math.abs(1 - ratio)), 100)));
=======
	 * Converts a ratio with ideal value of 1 to a score. The resulting function is piecewise
	 * linear going from (0,0) to (1,100) to (2,0) and is 0 for all inputs outside the range 0-2
	 */
	double ratioToScore(double ratio)
	{
		return (Math.max(0, Math.min(100*(1-Math.abs(1-ratio)), 100)));
>>>>>>> FETCH_HEAD
	}

	/**
	 * Method to score convex hull area. This scores how "complete" the particle is. Particles with large holes will score worse than a filled in shape
	 */
	double ConvexHullAreaScore(ParticleReport report)
	{
<<<<<<< HEAD
		return ratioToScore((report.Area / report.ConvexHullArea) * 1.18);
	}

	/**
	 * Method to score if the particle appears to be a trapezoid. Compares the convex hull (filled in) area to the area of the bounding box. The expectation is that the convex hull area is about 95.4%
	 * of the bounding box area for an ideal tote.
	 */
	double TrapezoidScore(ParticleReport report)
	{
		return ratioToScore(report.ConvexHullArea
				/ ((report.BoundingRectRight - report.BoundingRectLeft)
						* (report.BoundingRectBottom - report.BoundingRectTop) * .954));
=======
		return ratioToScore((report.Area/report.ConvexHullArea)*1.18);
	}

	/**
	 * Method to score if the particle appears to be a trapezoid. Compares the convex hull (filled in) area to the area of the bounding box.
	 * The expectation is that the convex hull area is about 95.4% of the bounding box area for an ideal tote.
	 */
	double TrapezoidScore(ParticleReport report)
	{
		return ratioToScore(report.ConvexHullArea/((report.BoundingRectRight-report.BoundingRectLeft)*(report.BoundingRectBottom-report.BoundingRectTop)*.954));
>>>>>>> FETCH_HEAD
	}

	/**
	 * Method to score if the aspect ratio of the particle appears to match the long side of a tote.
	 */
	double LongSideScore(ParticleReport report)
	{
<<<<<<< HEAD
		return ratioToScore(((report.BoundingRectRight - report.BoundingRectLeft) / (report.BoundingRectBottom - report.BoundingRectTop))
				/ LONG_RATIO);
=======
		return ratioToScore(((report.BoundingRectRight-report.BoundingRectLeft)/(report.BoundingRectBottom-report.BoundingRectTop))/LONG_RATIO);
>>>>>>> FETCH_HEAD
	}

	/**
	 * Method to score if the aspect ratio of the particle appears to match the short side of a tote.
	 */
<<<<<<< HEAD
	double ShortSideScore(ParticleReport report) {
		return ratioToScore(((report.BoundingRectRight - report.BoundingRectLeft) / (report.BoundingRectBottom - report.BoundingRectTop))
				/ SHORT_RATIO);
	}

	/**
	 * Computes the estimated distance to a target using the width of the particle in the image. For more information and graphics showing the math behind this approach see the Vision Processing
	 * section of the ScreenStepsLive documentation.
	 *
	 * @param image
	 *            The image to use for measuring the particle estimated rectangle
	 * @param report
	 *            The Particle Analysis Report for the particle
	 * @param isLong
	 *            Boolean indicating if the target is believed to be the long side of a tote
	 * @return The estimated distance to the target in feet.
	 */
	double computeDistance(Image image, ParticleReport report, boolean isLong) {
=======
	double ShortSideScore(ParticleReport report){
		return ratioToScore(((report.BoundingRectRight-report.BoundingRectLeft)/(report.BoundingRectBottom-report.BoundingRectTop))/SHORT_RATIO);
	}

	/**
	 * Computes the estimated distance to a target using the width of the particle in the image. For more information and graphics
	 * showing the math behind this approach see the Vision Processing section of the ScreenStepsLive documentation.
	 *
	 * @param image The image to use for measuring the particle estimated rectangle
	 * @param report The Particle Analysis Report for the particle
	 * @param isLong Boolean indicating if the target is believed to be the long side of a tote
	 * @return The estimated distance to the target in feet.
	 */
	double computeDistance (Image image, ParticleReport report, boolean isLong) {
>>>>>>> FETCH_HEAD
		double normalizedWidth, targetWidth;
		NIVision.GetImageSizeResult size;

		size = NIVision.imaqGetImageSize(image);
<<<<<<< HEAD
		normalizedWidth = 2 * (report.BoundingRectRight - report.BoundingRectLeft) / size.width;
		targetWidth = isLong ? 26.0 : 16.9;

		return targetWidth / (normalizedWidth * 12 * Math.tan(VIEW_ANGLE * Math.PI / (180 * 2)));
=======
		normalizedWidth = 2*(report.BoundingRectRight - report.BoundingRectLeft)/size.width;
		targetWidth = isLong ? 26.0 : 16.9;

		return  targetWidth/(normalizedWidth*12*Math.tan(VIEW_ANGLE*Math.PI/(180*2)));
>>>>>>> FETCH_HEAD
	}
}

