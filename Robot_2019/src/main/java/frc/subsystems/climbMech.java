package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANEncoder;
import frc.robot.RobotMap;


public class climbMech
{
	private static CANSparkMax leftClimbMotor = new CANSparkMax(RobotMap.CLIMBMECH_MOTOR_LEFT, MotorType.kBrushed);
	private static CANSparkMax rightClimbMotor = new CANSparkMax(RobotMap.CLIMBMECH_MOTOR_RIGHT, MotorType.kBrushed);

    public climbMech()
    {
        rightClimbMotor.follow(leftClimbMotor, true);
    }

    public void setClimbMotors(double speed)
    {
        leftClimbMotor.set(speed);
    }

}