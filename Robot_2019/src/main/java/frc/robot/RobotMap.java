package frc.robot;

public class RobotMap
{

	// Robot Stat Values
	public static final double ROBOT_TIME_STEP = 0.02; // 20ms
	public static final double ROBOT_MAX_VELOCITY = 3.2624; // meters per second
	public static final double ROBOT_MAX_ACCELERATION = 0.2;
	public static final double ROBOT_MAX_JERK = 0.5;
	public static final int ROBOT_WHEEL_DIAMETER = 2;


	// Drivetrain Motor Controller Ports
	public static final int DRIVETRAIN_LEFT1 = 1;
	public static final int DRIVETRAIN_LEFT2 = 2;
	public static final int DRIVETRAIN_LEFT3 = 3;
	public static final int DRIVETRAIN_RIGHT1 = 4;
	public static final int DRIVETRAIN_RIGHT2 = 5;
	public static final int DRIVETRAIN_RIGHT3 = 6;

	public static final double DRIVETRAIN_LEFT_PGAIN = .986;


	// Vision System Constants
	public static final double HATCH_VISION_TURN_PGAIN = -0.03;
	public static final double HATCH_VISION_DISTANCE_PGAIN = 0.049;
	public static final double HATCH_VISION_MIN_AIM_COMMAND = 0.0;
	public static final double HATCH_VISION_AREA_TO_DISTANCE_CONSTANT = 1.7;
}