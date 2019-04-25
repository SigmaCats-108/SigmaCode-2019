package frc.robot;

public class RobotMap
{
	// Robot Stat Values
	public static final double ROBOT_TIME_STEP = 0.02; // 20ms
	public static final double ROBOT_MAX_VELOCITY = 3.2624; // meters per second
	public static final double ROBOT_MAX_ACCELERATION = 0.2;
	public static final double ROBOT_MAX_JERK = 0.5;
	public static final int ROBOT_WHEEL_DIAMETER = 2;

	// Drivetrain ID Values
	public static final int DRIVETRAIN_LEFT1 = 1;
	public static final int DRIVETRAIN_LEFT2 = 2;
	public static final int DRIVETRAIN_LEFT3 = 3;
	public static final int DRIVETRAIN_RIGHT1 = 4;
	public static final int DRIVETRAIN_RIGHT2 = 5;
	public static final int DRIVETRAIN_RIGHT3 = 6;
	public static final double DRIVETRAIN_LEFT_PGAIN = .986;

	// Pneumatics ID Values
	public static final int PCM1 = 0;
	public static final int DRIVETRAIN_SHIFTER_FWD = 0;
	public static final int DRIVETRAIN_SHIFTER_REV = 0;

	// ENUMS
	public enum EnumTemplate
	{
		VALUE1, VALUE2, VALUE3;
	}
}