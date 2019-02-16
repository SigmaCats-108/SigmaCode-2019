package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANEncoder;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.RobotMap;

/**
 * The robot mechanism for the picking up and scoring of balls.
 */
public class BallMech
{

	private static CANSparkMax armMotor1 = new CANSparkMax(RobotMap.BALLMECH_LEFTARM, MotorType.kBrushed);
	private static CANSparkMax armMotor2 = new CANSparkMax(RobotMap.BALLMECH_RIGHTARM, MotorType.kBrushed);
	private static CANSparkMax intakeMotor = new CANSparkMax(RobotMap.BALLMECH_INTAKE, MotorType.kBrushed);
	private static CANEncoder armEncoder1 = armMotor1.getEncoder();

	private AnalogInput ultrasonicAnalog = new AnalogInput(0);
	private DigitalInput bumper1 = new DigitalInput(0);
	private DigitalInput bumper2 = new DigitalInput(1);

	private double armEncVal, armDifference;
	private final double maxArmVal = 9900;
	private final double minArmVal = -10;
	private final double armKp = 0.91;
	private int armState = 0;
	private double armDiff = 0;

	public BallMech()
	{
		armMotor1.setIdleMode(IdleMode.kBrake);
		armMotor2.setIdleMode(IdleMode.kBrake);
		intakeMotor.setIdleMode(IdleMode.kBrake);

		armMotor2.follow(armMotor1, true);
	}

	public int getUltrasonicSensor()
	{
		return ultrasonicAnalog.getValue();
		//System.out.println(cm);
	}

	public void intake(double speed)
	{
		if((bumper1.get() /*&& bumper2.get()*/))
		{
			intakeMotor.set(speed);
		}
		else
		{
			intakeMotor.set(0.0);
		}
	}

	public void outake(double speed)
	{
		intakeMotor.set(-speed);
	}

	public boolean setArm(RobotMap.ArmPosition position)
	{
		switch(position)
		{
			case STARTING: return turnArm(80);

			case LOADING_FLOOR: return turnArm(70);
			
			case LOADING_WALL: return turnArm(60);
			
			case SCORING: return turnArm(50);
			
			case CLIMBING: return turnArm(40);

			default:
			return false;
		}
	}

	private boolean turnArm(double targetVal)
	{
		armEncVal = armEncoder1.getPosition();

		armDifference = targetVal - armEncVal;

		if(!(armEncVal > maxArmVal || armEncVal < minArmVal))
		{
			if(armEncVal > targetVal + 2 || armEncVal < targetVal -2)
			{
				if(Math.abs(armDifference) < 20)
				{
					armMotor1.set( (armDifference / 20) * armKp);
					armMotor2.set( -(armDifference / 20) * armKp);
				}
				else if(armEncVal > targetVal)
				{
					armMotor1.set(1.0 * (armKp));
					armMotor2.set(-1.0 * (armKp));
				}
				else if(armEncVal < targetVal)
				{
					armMotor1.set(1.0 * (armKp));
					armMotor2.set(-1.0 * (armKp));
				}
				return false;
			}
			else
			{
				System.out.println("Finished turning");
				armMotor1.set(0.0);
				armMotor2.set(0.0);
				return true;
			}
		}
		else
		{
			System.out.println("Arm is outside of safety zone.");
			armMotor1.set(0.0);
			armMotor2.set(0.0);
			return true;
		}

	}

	private boolean turnArm2(double targetVal)
	{
		armDiff = targetVal - armEncoder1.getPosition();

		switch(armState)
		{
			case 1:
			double speed = armDiff * armKp;
			armMotor1.set(speed);
			if(Math.abs(armDiff) < 2)
			{
				armState = 2;
			}
			break;

			case 2:
			stopArm();
			System.out.println("Arm turn finished!");
			return true;
		}
		System.out.println("armState: " + armState);
		return false;
	}

	public void spinArm(double speed)
	{
		armMotor1.set(speed);
	}

	public void stopArm()
	{
		armMotor1.set(0.0);
	}

	public double testArmEnc()
	{
		System.out.println(armEncoder1.getPosition());
		return armEncoder1.getPosition();
	}
}