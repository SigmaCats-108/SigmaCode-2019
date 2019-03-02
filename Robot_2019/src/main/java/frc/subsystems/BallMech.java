package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANEncoder;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.IO;
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
	private static DoubleSolenoid armClutch = new DoubleSolenoid(RobotMap.PCM2, RobotMap.ARM_CLUTCH_FWD, RobotMap.ARM_CLUTCH_REV);
	private AnalogInput ultrasonicAnalog = new AnalogInput(0);
	private DigitalInput bumper1 = new DigitalInput(0);
	public DigitalInput armSwitchRight = new DigitalInput(1);
	public DigitalInput upperArmSwitchRight = new DigitalInput(3);
	public boolean rumble = false;
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
	}

	public void intake(double speed)
	{
		if(bumper1.get())
		{
			intakeMotor.set(speed);
			IO.mainController.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
		}
		else
		{
			intakeMotor.set(0.15);
			IO.mainController.setRumble(GenericHID.RumbleType.kLeftRumble, 0.5);
		}
	}

	public void outake(double speed)
	{
		IO.mainController.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
		if(!bumper1.get() && !IO.m_rightBumper)
		{
			intakeMotor.set(0.15);
		}
		else
		{
			intakeMotor.set(-speed);
		}
	}

	public void spinArm(double speed)
	{
		armClutch.set(Value.kReverse);
		
		armMotor1.set(speed);
		if(speed == 0)
		{
			armClutch.set(Value.kForward);
		} 
		else
		{
			armClutch.set(Value.kReverse);
		}
	}

	public void stopArm()
	{
		armMotor1.set(0.0);
		armClutch.set(Value.kForward);
	}

	public double testArmEnc()
	{
		System.out.println(armEncoder1.getPosition());
		return armEncoder1.getPosition();
	}
}