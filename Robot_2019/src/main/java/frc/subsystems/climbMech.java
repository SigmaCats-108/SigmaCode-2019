package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.RobotMap;


public class ClimbMech
{
	private static CANSparkMax leftClimbMotor = new CANSparkMax(RobotMap.CLIMBMECH_MOTOR_LEFT, MotorType.kBrushed);
	private static CANSparkMax rightClimbMotor = new CANSparkMax(RobotMap.CLIMBMECH_MOTOR_RIGHT, MotorType.kBrushed);
	private static DoubleSolenoid liftPistons = new DoubleSolenoid(RobotMap.PCM1, RobotMap.HABLIFT_PISTON_FWD, RobotMap.HABLIFT_PISTON_REV);

    public ClimbMech()
    {
        rightClimbMotor.follow(leftClimbMotor, true);
    }

    public void setClimbMotors(double speed)
    {
        leftClimbMotor.set(speed);
    }

    public void liftRobot(boolean lifting)
    {
        if(lifting)
        {
            liftPistons.set(Value.kForward);
        }
         else
        {
            liftPistons.set(Value.kReverse);
        }
    }

}