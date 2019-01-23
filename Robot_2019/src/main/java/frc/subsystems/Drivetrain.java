package frc.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.RobotMap;
import frc.inputs.NavX;


public class Drivetrain
{
	// Motor Controller Declarations
	private static WPI_TalonSRX leftSRX = new WPI_TalonSRX(RobotMap.DRIVETRAIN_LEFT_SRX);
	private static WPI_VictorSPX leftSPX1 = new WPI_VictorSPX(RobotMap.DRIVETRAIN_LEFT_SPX1);
	private static WPI_VictorSPX leftSPX2 = new WPI_VictorSPX(RobotMap.DRIVETRAIN_LEFT_SPX2);
	private static WPI_VictorSPX rightSPX = new WPI_VictorSPX(RobotMap.DRIVETRAIN_RIGHT_SRX);
	private static WPI_VictorSPX rightSPX1 = new WPI_VictorSPX(RobotMap.DRIVETRAIN_RIGHT_SPX1);
	private static WPI_VictorSPX rightSPX2 = new WPI_VictorSPX(RobotMap.DRIVETRAIN_RIGHT_SPX2);
	
	private static DoubleSolenoid gearShifter = new DoubleSolenoid(5, 7);

	private DifferentialDrive drive;

	public Drivetrain()
	{
		// Assign DifferentialDrive Motors
		drive = new DifferentialDrive(leftSRX, rightSPX);

		// Set up followers
		leftSPX1.follow(leftSRX);
		leftSPX2.follow(leftSRX);
		rightSPX1.follow(rightSPX);
		rightSPX2.follow(rightSPX);

		// Sets drivetrain deadband, default is 0.02
		drive.setDeadband(0.03);
	}

	public void sigmaDrive(double leftSpeed, double rightSpeed)
	{
		drive.tankDrive(-leftSpeed /** RobotMap.DRIVETRAIN_LEFT_PGAIN*/, -rightSpeed, false);
	}

	/**
	 * Enables / disables the drivetrain's highGear mode
	 * 
	 * @param gearState True to enable highGear, and False to disable it
	 */
	public void highGear(boolean gearState)
	{
		if(gearState)
			gearShifter.set(Value.kForward);
		else
			gearShifter.set(Value.kReverse);
	}

	/**
	 * Uses angle heading to drive in a straight line
	 * 
	 * @param yValue Joystick value to be used
	 * @param robotHeading The robot's current heading
	 * @param desiredAngle Angle at which the robot will drive
	 */
	public void driveStraight(double yValue, double robotHeading, double desiredAngle)
	{
		double constant;
		double difference = desiredAngle - robotHeading;
		if(difference != 0)
		{
			if(difference > 0)
			{
				constant = 1 - (.02 * difference);
				sigmaDrive(yValue, yValue * constant);
				// System.out.println("Left speed: " + yValue + "; Right speed: " + yValue * constant);
			}
			else 
			{
				difference = Math.abs(difference);
				constant = 1 - (.02 * (difference));
				sigmaDrive(yValue * constant, yValue);
				// System.out.println("Left speed: " + yValue  * constant + "; Right speed: " + yValue);
			}
		}
		else 
		{
			sigmaDrive(yValue, yValue);
		}
	}

	/**
	 * Turns the robot to a desired angle
	 * 
	 * @param robotHeading The robot's current heading
	 * @param desiredAngle Angle that the robot will rotate towards
	 * @param rotationRate Rate at which the robot will turn
	 * @param tolerance Angle deadzone to prevent overshooting
	 * @return Lets the code know when the robot is within the target range
	 */
	public boolean toAngle(double targetAngle, double rotationRate, double tolerance, double robotHeading)
	{
		double output;
		double currentAngle = -robotHeading;
		double angleDifference = targetAngle - currentAngle;
		boolean turnFinished;

		if(angleDifference > tolerance)
		{
			turnFinished = false;
			
			if(angleDifference > 10 || angleDifference < -10)
			{
				output = 0.7*rotationRate;
			}
			else
			{
				output = 0.5*rotationRate;
			}

			if(angleDifference > 0)
			{
				sigmaDrive(-output, output);
			}
			else
			{
				sigmaDrive(output, -output);
			}
		}
		else
		{
			turnFinished = true;
		}

		return turnFinished;
	}

	double turn_kp = 0.98; // change this to calibrate to avoid overshooting
	double leftCommand, rightCommand, motor_command;

	public void turn (double desired_angle, NavX navX, double leftDriveValue, double rightDriveValue)
	{
		double angle_error = desired_angle - navX.yaw;
		motor_command = angle_error * (1/180) * turn_kp;
		leftCommand = motor_command;
		rightCommand = -motor_command;
		sigmaDrive(leftCommand, rightCommand);
	}

	public int getLeftEncoder()
	{
		return leftSRX.getSelectedSensorPosition(0);
	}
	public int getRightEncoder()
	{
		return rightSPX.getSelectedSensorPosition(0);
	}

	public void stop()
	{
		rightSPX.stopMotor();
		leftSRX.stopMotor();
	}

	public boolean stopped()
	{
		if(rightSPX.getSelectedSensorVelocity(0) == 0 && leftSRX.getSelectedSensorVelocity(0) == 0)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}

	public void testSpeed()
	{
		System.out.println("Left Motor Speed: " + leftSRX.get());
		System.out.println("Right Motor Speed: " + rightSPX.get());
	}
	
	public void setToBrake()
	{
		leftSRX.setNeutralMode(NeutralMode.Brake);
		rightSPX.setNeutralMode(NeutralMode.Brake);
		leftSPX1.setNeutralMode(NeutralMode.Brake);
		leftSPX2.setNeutralMode(NeutralMode.Brake);
		rightSPX1.setNeutralMode(NeutralMode.Brake);
		rightSPX2.setNeutralMode(NeutralMode.Brake);
	}
	
	public void setToCoast()
	{
		leftSRX.setNeutralMode(NeutralMode.Coast);
		rightSPX.setNeutralMode(NeutralMode.Coast);
		leftSPX1.setNeutralMode(NeutralMode.Coast);
		leftSPX2.setNeutralMode(NeutralMode.Coast);
		rightSPX1.setNeutralMode(NeutralMode.Coast);
		rightSPX2.setNeutralMode(NeutralMode.Coast);
	}

}