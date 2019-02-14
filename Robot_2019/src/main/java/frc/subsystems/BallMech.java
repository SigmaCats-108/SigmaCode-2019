package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANEncoder;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The robot mechanism for the picking up and scoring of balls.
 */
public class BallMech
{

	private static CANSparkMax armMotor1 = new CANSparkMax(RobotMap.BALLMECH_LEFTARM, MotorType.kBrushless);
	private static CANSparkMax armMotor2 = new CANSparkMax(RobotMap.BALLMECH_RIGHTARM, MotorType.kBrushless);
	private static CANSparkMax intakeMotor = new CANSparkMax(RobotMap.BALLMECH_INTAKE, MotorType.kBrushed);
	private static CANEncoder armEncoder1 = armMotor1.getEncoder();
	private double armEncVal, armDifference;
	private double armKp = 0.91;
	private final double maxArmVal = 9900;
	private final double minArmVal = -10;

	AnalogInput ultrasonicAnalog = new AnalogInput(0);
	DigitalInput bumper1 = new DigitalInput(0);
	DigitalInput bumper2 = new DigitalInput(1);
	double cm;

	public BallMech()
	{
		armMotor1.setIdleMode(IdleMode.kBrake);
		armMotor2.setIdleMode(IdleMode.kBrake);

		armMotor2.follow(armMotor1, true);
		
		
	}

	public void updateBallMech()
	{
		//testEncVal1 = testEncoder1.getPosition();
		cm = ultrasonicAnalog.getValue();
		//System.out.println(cm);
		if(cm > 60){

            SmartDashboard.putBoolean("inTheAir", true);

		}
		else
		{
			SmartDashboard.putBoolean("inTheAir", false);
		}
	}

	public void setIntake(double speed)
	{
		intakeMotor.set(speed);
	}

	public void setArm(RobotMap.ArmPosition position)
	{
		switch(position)
		{
			case STARTING: turnArm(80);
			break;
			
			case LOADING_FLOOR: turnArm(70);
			break;

			case LOADING_WALL: turnArm(60);
			break;

			case SCORING: turnArm(50);
			break;

			case CLIMBING: turnArm(40);
			break;
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

	public void spinArm(double speed)
	{
		armMotor1.set(speed);
	}

	public void stopArm()
	{
		armMotor1.set(0.0);
	}

	public void testArmEnc()
	{
		System.out.println(armEncoder1.getPosition());
	}
}