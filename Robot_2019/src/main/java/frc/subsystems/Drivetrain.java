package frc.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import frc.robot.RobotMap;
import frc.inputs.NavX;

public class Drivetrain
{
	// Motor Controller Declarations
	private static WPI_TalonSRX leftSRX = new WPI_TalonSRX(RobotMap.DRIVETRAIN_LEFT_SRX);
	private static WPI_VictorSPX leftSPX1 = new WPI_VictorSPX(RobotMap.DRIVETRAIN_LEFT_SPX1);
	private static WPI_VictorSPX leftSPX2 = new WPI_VictorSPX(RobotMap.DRIVETRAIN_LEFT_SPX2);
	private static WPI_TalonSRX rightSRX = new WPI_TalonSRX(RobotMap.DRIVETRAIN_RIGHT_SRX);
	private static WPI_VictorSPX rightSPX1 = new WPI_VictorSPX(RobotMap.DRIVETRAIN_RIGHT_SPX1);
	private static WPI_VictorSPX rightSPX2 = new WPI_VictorSPX(RobotMap.DRIVETRAIN_RIGHT_SPX2);
	
	private DifferentialDrive drive;

	public Drivetrain()
	{
		// Assign DifferentialDrive Motors
		drive = new DifferentialDrive(leftSRX, rightSRX);

		// Set up followers
		leftSPX1.follow(leftSRX);
		leftSPX2.follow(leftSRX);
		rightSPX1.follow(rightSRX);
		rightSPX2.follow(rightSRX);

		// Sets drivetrain deadband, default is 0.02
		drive.setDeadband(0.03);
	}

	public void sigmaDrive(double leftSpeed, double rightSpeed)
	{
		drive.tankDrive(-leftSpeed * RobotMap.DRIVETRAIN_RIGHT_PGAIN, -rightSpeed, false);
	}

	// Uses angle heading to drive in a straight line
	public void driveStraight(double yValue, double angle, double supposedAngle)
	{
		double constant;
		double difference = supposedAngle - angle;
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
	 * 
	 * @param navX NavX-MXP for yaw angle heading
	 * @param targetAngle Target angle for the toAngle function
	 * @param rotationRate Rate at which the robot will turn
	 * @param tolerance Angle deadzone to prevent overshooting
	 * @return Lets the code know when the robot is within the target range
	 */
	public boolean toAngle(double targetAngle, double rotationRate, double tolerance, NavX navX)
	{
		double output;
		double currentAngle = -navX.angle;
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
	double leftDriveValue, rightDriveValue, motor_command;

	public void turn (double desired_angle, NavX navX, double leftDriveValue, double rightDriveValue)
	{
		double angle_error = desired_angle - navX.yaw;
		motor_command = angle_error * (1/180) * turn_kp;
		leftDriveValue += motor_command;
		rightDriveValue -= motor_command;
		sigmaDrive(leftDriveValue, rightDriveValue);
	}

	public int getLeftEncoder()
	{
		return leftSRX.getSelectedSensorPosition(0);
	}
	public int getRightEncoder()
	{
		return rightSRX.getSelectedSensorPosition(0);
	}

	public void stop()
	{
		rightSRX.stopMotor();
		leftSRX.stopMotor();
	}

	public boolean stopped()
	{
		if(rightSRX.getSelectedSensorVelocity(0) == 0 && leftSRX.getSelectedSensorVelocity(0) == 0)
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
		System.out.println("Right Motor Speed: " + rightSRX.get());
	}
	
	public void setToBrake()
	{
		leftSRX.setNeutralMode(NeutralMode.Brake);
		rightSRX.setNeutralMode(NeutralMode.Brake);
		leftSPX1.setNeutralMode(NeutralMode.Brake);
		leftSPX2.setNeutralMode(NeutralMode.Brake);
		rightSPX1.setNeutralMode(NeutralMode.Brake);
		rightSPX2.setNeutralMode(NeutralMode.Brake);
	}
	
	public void setToCoast()
	{
		leftSRX.setNeutralMode(NeutralMode.Coast);
		rightSRX.setNeutralMode(NeutralMode.Coast);
		leftSPX1.setNeutralMode(NeutralMode.Coast);
		leftSPX2.setNeutralMode(NeutralMode.Coast);
		rightSPX1.setNeutralMode(NeutralMode.Coast);
		rightSPX2.setNeutralMode(NeutralMode.Coast);
	}

}