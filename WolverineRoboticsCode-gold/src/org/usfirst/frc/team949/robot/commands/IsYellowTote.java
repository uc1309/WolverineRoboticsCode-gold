package org.usfirst.frc.team949.robot.commands;

import java.util.Vector;
import org.usfirst.frc.team949.robot.Robot;
import org.usfirst.frc.team949.robot.subsystems.Camera.ParticleReport;
import com.ni.vision.NIVision;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class IsYellowTote extends Command {

    public IsYellowTote() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.camera);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	boolean isTote = false;
    	boolean isLong = false;
    	
    	//read file in from disk. For this example to run you need to copy image20.jpg from the SampleImages folder to the
		//directory shown below using FTP or SFTP: http://wpilib.screenstepslive.com/s/4485/m/24166/l/282299-roborio-ftp
		NIVision.imaqReadFile(Robot.camera.frame, "/home/lvuser/SampleImages/image20.jpg");

		//Update threshold values from SmartDashboard. For performance reasons it is recommended to remove this after calibration is finished.
		Robot.camera.TOTE_HUE_RANGE.minValue = (int)SmartDashboard.getNumber("Tote hue min", Robot.camera.TOTE_HUE_RANGE.minValue);
		Robot.camera.TOTE_HUE_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote hue max", Robot.camera.TOTE_HUE_RANGE.maxValue);
		Robot.camera.TOTE_SAT_RANGE.minValue = (int)SmartDashboard.getNumber("Tote sat min", Robot.camera.TOTE_SAT_RANGE.minValue);
		Robot.camera.TOTE_SAT_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote sat max", Robot.camera.TOTE_SAT_RANGE.maxValue);
		Robot.camera.TOTE_VAL_RANGE.minValue = (int)SmartDashboard.getNumber("Tote val min", Robot.camera.TOTE_VAL_RANGE.minValue);
		Robot.camera.TOTE_VAL_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote val max", Robot.camera.TOTE_VAL_RANGE.maxValue);

		//Threshold the image looking for yellow (tote color)
		NIVision.imaqColorThreshold(Robot.camera.binaryFrame, Robot.camera.frame, 255, NIVision.ColorMode.HSV, Robot.camera.TOTE_HUE_RANGE, Robot.camera.TOTE_SAT_RANGE, Robot.camera.TOTE_VAL_RANGE);

		//Send particle count to dashboard
		int numParticles = NIVision.imaqCountParticles(Robot.camera.binaryFrame, 1);
		SmartDashboard.putNumber("Masked particles", numParticles);

		//Send masked image to dashboard to assist in tweaking mask.
		CameraServer.getInstance().setImage(Robot.camera.binaryFrame);

		//filter out small particles
		float areaMin = (float)SmartDashboard.getNumber("Area min %", Robot.camera.AREA_MINIMUM);
		Robot.camera.criteria[0].lower = areaMin;
		Robot.camera.imaqError = NIVision.imaqParticleFilter4(Robot.camera.binaryFrame, Robot.camera.binaryFrame, Robot.camera.criteria, Robot.camera.filterOptions, null);

		//Send particle count after filtering to dashboard
		numParticles = NIVision.imaqCountParticles(Robot.camera.binaryFrame, 1);
		SmartDashboard.putNumber("Filtered particles", numParticles);

		if(numParticles > 0)
		{
			//Measure particles and sort by particle size
			Vector<ParticleReport> particles = new Vector<ParticleReport>();
			for(int particleIndex = 0; particleIndex < numParticles; particleIndex++)
			{
				ParticleReport par = Robot.camera.new ParticleReport();
				par.PercentAreaToImageArea = NIVision.imaqMeasureParticle(Robot.camera.binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
				par.Area = NIVision.imaqMeasureParticle(Robot.camera.binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA);
				par.ConvexHullArea = NIVision.imaqMeasureParticle(Robot.camera.binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_CONVEX_HULL_AREA);
				par.BoundingRectTop = NIVision.imaqMeasureParticle(Robot.camera.binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
				par.BoundingRectLeft = NIVision.imaqMeasureParticle(Robot.camera.binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
				par.BoundingRectBottom = NIVision.imaqMeasureParticle(Robot.camera.binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
				par.BoundingRectRight = NIVision.imaqMeasureParticle(Robot.camera.binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
				particles.add(par);
			}
			particles.sort(null);

			//This example only scores the largest particle. Extending to score all particles and choosing the desired one is left as an exercise
			//for the reader. Note that the long and short side scores expect a single tote and will not work for a stack of 2 or more totes.
			//Modification of the code to accommodate 2 or more stacked totes is left as an exercise for the reader.
			Robot.camera.scores.Trapezoid = Robot.camera.TrapezoidScore(particles.elementAt(0));
			SmartDashboard.putNumber("Trapezoid", Robot.camera.scores.Trapezoid);
			Robot.camera.scores.LongAspect = Robot.camera.LongSideScore(particles.elementAt(0));
			SmartDashboard.putNumber("Long Aspect", Robot.camera.scores.LongAspect);
			Robot.camera.scores.ShortAspect = Robot.camera.ShortSideScore(particles.elementAt(0));
			SmartDashboard.putNumber("Short Aspect", Robot.camera.scores.ShortAspect);
			Robot.camera.scores.AreaToConvexHullArea = Robot.camera.ConvexHullAreaScore(particles.elementAt(0));
			SmartDashboard.putNumber("Convex Hull Area", Robot.camera.scores.AreaToConvexHullArea);
			isTote = Robot.camera.scores.Trapezoid > Robot.camera.SCORE_MIN && (Robot.camera.scores.LongAspect > Robot.camera.SCORE_MIN || Robot.camera.scores.ShortAspect > Robot.camera.SCORE_MIN) && Robot.camera.scores.AreaToConvexHullArea > Robot.camera.SCORE_MIN;
			isLong = Robot.camera.scores.LongAspect > Robot.camera.scores.ShortAspect;

			//Send distance and tote status to dashboard. The bounding rect, particularly the horizontal center (left - right) may be useful for rotating/driving towards a tote
			SmartDashboard.putBoolean("IsTote", isTote);
			SmartDashboard.putNumber("Distance", Robot.camera.computeDistance(Robot.camera.binaryFrame, particles.elementAt(0), isLong));
		} else {
			SmartDashboard.putBoolean("IsTote", false);
		}
		
		if (isTote == true) {
			Robot.driveTrain.driveForward();
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
