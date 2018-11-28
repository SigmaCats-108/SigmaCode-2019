package frc.team108.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.team108.inputs.NavX;
import frc.team108.robot.Robot;

public class Drivetrain
{
	//Motor Controller Declarations
	//Ports 1,2,3
	private static WPI_TalonSRX  leftSRX = new WPI_TalonSRX(1);
	private static WPI_VictorSPX  leftSPX1 = new WPI_VictorSPX(2);
	private static WPI_VictorSPX  leftSPX2 = new WPI_VictorSPX(3);
	//Ports 4,5,6
	private static WPI_TalonSRX  rightSRX = new WPI_TalonSRX(4);
	private static WPI_VictorSPX  rightSPX1 = new WPI_VictorSPX(5);
	private static WPI_VictorSPX  rightSPX2 = new WPI_VictorSPX(6);
	
	private static DifferentialDrive drive = new DifferentialDrive(leftSRX, rightSRX);

	public static void initializeDrivetrain()
	{
		//Set up followers
		leftSPX1.follow(leftSRX);
		leftSPX2.follow(leftSRX);
		rightSPX1.follow(rightSRX);
		rightSPX2.follow(rightSRX);
		rightSRX.setInverted(true);
		rightSPX1.setInverted(true);
		rightSPX2.setInverted(true);

		// Sets drivetrain deadband, default is 0.02
		drive.setDeadband(0.03);
	}

	public static void tankDrive(double leftSpeed, double rightSpeed)
	{
		drive.tankDrive(leftSpeed * .986, rightSpeed, false);
	}

	// Uses angle heading to drive in a straight line
	public static void driveStraight(double yValue, double angle, double supposedAngle)
	{
		double constant;
		double difference = supposedAngle - angle;
		if(difference != 0)
		{
			if(difference > 0)
			{
				constant = 1 - (.02 * difference);
				tankDrive(yValue, yValue * constant);
				//System.out.println("Left speed: " + yValue + "; Right speed: " + yValue * constant);
			}
			else 
			{
				difference = Math.abs(difference);
				constant = 1 - (.02 * (difference));
				tankDrive(yValue * constant, yValue);
				//System.out.println("Left speed: " + yValue  * constant + "; Right speed: " + yValue);
			}
		}
		else 
		{
			tankDrive(yValue, yValue);
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
	public static boolean toAngle(double targetAngle, double rotationRate, double tolerance, NavX navX)
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
				tankDrive(-output, output);
			}
			else
			{
				tankDrive(output, -output);
			}
		}
		else
		{
			turnFinished = true;
		}

		return turnFinished;
	}

	static double turn_kp = 0.98; //change this to calibrate to avoid overshooting
	static double leftDriveValue, rightDriveValue, motor_command;

	public static void turn (double desired_angle, NavX navX, double leftDriveValue, double rightDriveValue)
	{
		double angle_error = desired_angle - navX.yaw;
		motor_command = angle_error * (1/180) * turn_kp;
		leftDriveValue += motor_command;
		rightDriveValue -= motor_command;
		tankDrive(leftDriveValue, rightDriveValue);
	}

	public static int getLeftEncoder()
	{
		return leftSRX.getSelectedSensorPosition(0);
	}
	public static int getRightEncoder()
	{
		return rightSRX.getSelectedSensorPosition(0);
	}

	public static void stop()
	{
		rightSRX.stopMotor();
		leftSRX.stopMotor();
	}

	public static boolean stopped()
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

	public static void testDrivetrainCurrent()
	{
		System.out.println("Left Motor Current: " + leftSRX.getOutputCurrent());
		System.out.println("Right Motor Current: " + rightSRX.getOutputCurrent());
	}

	public static void enableCurrentLimiting(double amps)
	{
		leftSRX.enableCurrentLimit(true);
		rightSRX.enableCurrentLimit(true);
	}
	
	public static void setToBrake()
	{
		leftSRX.setNeutralMode(NeutralMode.Brake);
		rightSRX.setNeutralMode(NeutralMode.Brake);
		leftSPX1.setNeutralMode(NeutralMode.Brake);
		leftSPX2.setNeutralMode(NeutralMode.Brake);
		rightSPX1.setNeutralMode(NeutralMode.Brake);
		rightSPX2.setNeutralMode(NeutralMode.Brake);
	}
	
	public static void setToCoast()
	{
		leftSRX.setNeutralMode(NeutralMode.Coast);
		rightSRX.setNeutralMode(NeutralMode.Coast);
		leftSPX1.setNeutralMode(NeutralMode.Coast);
		leftSPX2.setNeutralMode(NeutralMode.Coast);
		rightSPX1.setNeutralMode(NeutralMode.Coast);
		rightSPX2.setNeutralMode(NeutralMode.Coast);
	}

}