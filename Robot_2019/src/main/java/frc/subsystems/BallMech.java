package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The robot mechanism for the picking up and scoring of balls.
 */
public class BallMech
{

	public enum ArmPosition {
		STARTING, LOADING_FLOOR, LOADING_WALL, SCORING, CLIMBING;
	}
	
	private static CANSparkMax armMotor1 = new CANSparkMax(RobotMap.BALLMECH_LEFTARM_ID, MotorType.kBrushless);
	private static CANSparkMax armMotor2 = new CANSparkMax(RobotMap.BALLMECH_RIGHTARM_ID, MotorType.kBrushless);
	private static CANSparkMax intakeMotor = new CANSparkMax(RobotMap.BALLMECH_INTAKE_ID, MotorType.kBrushed);
	private static CANEncoder armEncoder1 = armMotor1.getEncoder();
	private double armEncVal, armDifference;
	private final double maxArmVal = 87;
	private final double minArmVal = 6;


	
	AnalogInput ultrasonicAnalog = new AnalogInput(0);
	DigitalInput bumper1 = new DigitalInput(0);
	DigitalInput bumper2 = new DigitalInput(1);
	double cm;



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



	public BallMech()
	{
		armMotor2.setInverted(true);
		armMotor2.follow(armMotor1);
	}

	public void setIntake(double speed)
	{
		intakeMotor.set(speed);
	}

	public void setArm(ArmPosition position)
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

	public void intakeOutake()
	{
		if(!bumper1.get() && !bumper2.get())
		{
			System.out.println("Ball detected!  Turn motor off!\r\n");
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
					armMotor1.set( armDifference / 20);
				}
				else if(armEncVal > targetVal)
				{
					armMotor1.set(0.3);
				}
				else if(armEncVal < targetVal)
				{
					armMotor1.set(-0.3);
				}
				return false;
			}
			else
			{
				System.out.println("Finished turning");
				armMotor1.set(0.0);
				return true;
			}
		}
		else
		{
			System.out.println("Arm is outside of safety zone.");
			armMotor1.set(0.0);
			return true;
		}

	}
}