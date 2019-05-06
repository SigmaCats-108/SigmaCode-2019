package frc.robot;

public class RobotMap
{
	// Robot Stat Values
	public static final double ROBOT_TIME_STEP = 0.02; // Measured in seconds (20ms)
	public static final double ROBOT_MAX_VELOCITY = 4.572; // Measured in meters per second
	public static final double ROBOT_MAX_ACCELERATION = 0.2; // Measured in meters per second squared
	public static final double ROBOT_MAX_JERK = 0.5; // Measured in meters per second cubed
	public static final double ROBOT_WHEEL_DIAMETER = 2; // Measured in meters
	public static final double ROBOT_DRIVEBASE_WIDTH = 0.6985; // Measured in meters
	public static final int ENCODER_TICKS_PER_REV = 87000; // Encoder ticks per revolution

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