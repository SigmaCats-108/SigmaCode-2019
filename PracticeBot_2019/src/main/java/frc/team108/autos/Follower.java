package frc.team108.autos;

import jaci.pathfinder.Waypoint;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.modifiers.TankModifier;
import jaci.pathfinder.followers.EncoderFollower;
import frc.team108.Robot;
import frc.team108.RobotMap;

public class Follower
{
	double leftSpeed;
	double rightSpeed;

	private Trajectory.Config trajectoryConfig = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, RobotMap.ROBOT_TIME_STEP, RobotMap.ROBOT_MAX_VELOCITY, RobotMap.ROBOT_MAX_ACCELERATION, RobotMap.ROBOT_MAX_JERK);

	private Waypoint[] testRun = new Waypoint[] {
		new Waypoint(0, 9, 0),
		new Waypoint(10, 9, 0),
	};

	private Trajectory testRunTraj = Pathfinder.generate(testRun, trajectoryConfig);
	private TankModifier modifier = new TankModifier(testRunTraj).modify(0.5);

	private EncoderFollower leftFollower = new EncoderFollower(modifier.getLeftTrajectory());
	private EncoderFollower rightFollower = new EncoderFollower(modifier.getRightTrajectory());

	public void setupFollower()
	{
		leftFollower.configureEncoder(Robot.encoders.leftDrive, 1000, 4);
		rightFollower.configureEncoder(Robot.encoders.rightDrive, 1000, 4);

		leftFollower.configurePIDVA(1.0, 0.0, 0.0, 1 / RobotMap.ROBOT_MAX_VELOCITY, 0);
		rightFollower.configurePIDVA(1.0, 0.0, 0.0, 1 / RobotMap.ROBOT_MAX_VELOCITY, 0);
	}

	public void followPath()
	{
		leftSpeed = leftFollower.calculate(Robot.encoders.leftDrive);
		rightSpeed = rightFollower.calculate(Robot.encoders.rightDrive);

		Robot.drivetrain.sigmaDrive(leftSpeed, rightSpeed);
	}
	
}