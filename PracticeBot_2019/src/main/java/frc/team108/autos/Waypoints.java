package frc.team108.autos;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;
import jaci.pathfinder.followers.EncoderFollower;
import frc.team108.inputs.NavX;
import frc.team108.robot.Robot;
import frc.team108.subsystems.Drivetrain;
//import navx

public class Waypoints
{
	Waypoint[] points = new Waypoint[] {
		new Waypoint(0, 0, 0),
		new Waypoint(2, 1, 0),
	};

	Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.02, 4.8, 2.0, 60.0);
	Trajectory trajectory = Pathfinder.generate(points, config);

	TankModifier tankModifier = new TankModifier(trajectory).modify(0.5);

	Trajectory leftTraj  = tankModifier.getLeftTrajectory();       // Get the Left Side
	Trajectory rightTraj = tankModifier.getRightTrajectory();      // Get the Right Side

	EncoderFollower left = new EncoderFollower(leftTraj);
	EncoderFollower right = new EncoderFollower(rightTraj);

	left.configureEncoder(Drivetrain.getLeftEncoder(), 1000, 0.1524);
	left.configurePIDVA(1.0, 0.0, 0.0, 1 / 4.8, 0);

	double l = left.calculate(Drivetrain.getLeftEncoder());
	double r = right.calculate(Drivetrain.getRightEncoder());

	//double gyro_heading = /*NavX Angle*/ ;	// Assuming the gyro is giving a value in degrees
	double desired_heading = Pathfinder.r2d(left.getHeading());  // Should also be in degrees

	double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
	double turn = 0.8 * (-1.0/80.0) * angleDifference;

	setLeftMotors(l + turn);
	setRightMotors(r - turn);

}