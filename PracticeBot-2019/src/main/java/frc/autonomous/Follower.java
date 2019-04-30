package frc.autonomous;

import jaci.pathfinder.Waypoint;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.modifiers.TankModifier;
import jaci.pathfinder.followers.EncoderFollower;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Follower
{
	private double leftSpeed, rightSpeed;

	private Trajectory.Config trajectoryConfig = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, RobotMap.ROBOT_TIME_STEP, RobotMap.ROBOT_MAX_VELOCITY, RobotMap.ROBOT_MAX_ACCELERATION, RobotMap.ROBOT_MAX_JERK);

	private Waypoint[] testRun = new Waypoint[] {
		new Waypoint(0, 9, 0),
		new Waypoint(10, 9, 0),
	};

	private Trajectory testRunTraj = Pathfinder.generate(testRun, trajectoryConfig);
	private TankModifier modifier = new TankModifier(testRunTraj).modify(RobotMap.ROBOT_DRIVEBASE_WIDTH);

	private EncoderFollower leftFollower = new EncoderFollower(modifier.getLeftTrajectory());
	private EncoderFollower rightFollower = new EncoderFollower(modifier.getRightTrajectory());

	public void setupFollower()
	{
		leftFollower.configureEncoder(Robot.drivetrain.getLeftEncoder(), RobotMap.ENCODER_TICKS_PER_REV, RobotMap.ROBOT_WHEEL_DIAMETER);
		rightFollower.configureEncoder(Robot.drivetrain.getRightEncoder(), RobotMap.ENCODER_TICKS_PER_REV, RobotMap.ROBOT_WHEEL_DIAMETER);

		leftFollower.configurePIDVA(1.0, 0.0, 0.0, 1 / RobotMap.ROBOT_MAX_VELOCITY, 0);
		rightFollower.configurePIDVA(1.0, 0.0, 0.0, 1 / RobotMap.ROBOT_MAX_VELOCITY, 0);
	}

	public void followPath()
	{
		leftSpeed = leftFollower.calculate(Robot.drivetrain.getLeftEncoder());
		rightSpeed = rightFollower.calculate(Robot.drivetrain.getRightEncoder());

		System.out.println("Left Speed: " + leftSpeed + ", Right Speed: " + rightSpeed);
		//Robot.drivetrain.sigmaDrive(leftSpeed, rightSpeed);
	}
	
}