package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.RobotMap;

/**
 * The robot mechanism for the picking up and scoring of balls.
 */
public class BallMech
{

	private static CANSparkMax armMotor1 = new CANSparkMax(RobotMap.BALLMECH_LEFTARM_ID, MotorType.kBrushless);
	private static CANSparkMax armMotor2 = new CANSparkMax(RobotMap.BALLMECH_RIGHTARM_ID, MotorType.kBrushless);
	private static CANSparkMax intakeMotor = new CANSparkMax(RobotMap.BALLMECH_INTAKE_ID, MotorType.kBrushed);
	private static CANEncoder armEncoder1 = armMotor1.getEncoder();
	private double armEncVal, armDifference;
	private final double maxArmVal = 87;
	private final double minArmVal = 6;

	public BallMech()
	{
		armMotor2.setInverted(true);
		armMotor2.follow(armMotor1);
	}

	public void setIntake(double speed)
	{
		intakeMotor.set(speed);
	}

	public void setArm(int position)
	{
		switch(position)
		{
			case 1: turnArm(80);
			break;
			
			case 2: turnArm(70);
			break;

			case 3: turnArm(60);
			break;

			case 4: turnArm(50);
			break;

			case 5: turnArm(40);
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