package frc.robot;

public class RobotMap
{

	// Robot Stat Values
	public static final double ROBOT_TIME_STEP = 0.02; // 20ms
	public static final double ROBOT_MAX_VELOCITY = 3.2624; // meters per second
	public static final double ROBOT_MAX_ACCELERATION = 0.2;
	public static final double ROBOT_MAX_JERK = 0.5;
	public static final int ROBOT_WHEEL_DIAMETER = 2;


	// Drivetrain Motor Controller CAN IDs
	public static final int DRIVETRAIN_LEFT1 = 1;
	public static final int DRIVETRAIN_LEFT2 = 2;
	public static final int DRIVETRAIN_LEFT3 = 3;
	public static final int DRIVETRAIN_RIGHT1 = 44;
	public static final int DRIVETRAIN_RIGHT2 = 45;
	public static final int DRIVETRAIN_RIGHT3 = 46;

	public static final double DRIVETRAIN_LEFT_PGAIN = .986;

	// BallMech Constants
	public static final int BALLMECH_LEFTARM_ID = 4;
	public static final int BALLMECH_RIGHTARM_ID = 5;
	public static final int BALLMECH_INTAKE_ID = 17;

	// TestMech Constants
	public static final int BALLMECH_TEST1 = 0;

	// Vision System Constants
	public static final double HATCH_VISION_TURN_PGAIN = -0.007;
	public static final double HATCH_VISION_DISTANCE_PGAIN = 0.2;
	public static final double HATCH_VISION_MIN_AIM_COMMAND = 0.0;
	public static final double HATCH_VISION_DESIRED_TARGET_AREA = 4.0;
}