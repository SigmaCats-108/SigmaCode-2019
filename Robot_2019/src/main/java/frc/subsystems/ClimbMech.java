package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Robot;
import frc.robot.RobotMap;


public class ClimbMech
{
	private static CANSparkMax leftClimbMotor = new CANSparkMax(RobotMap.CLIMBMECH_MOTOR_LEFT, MotorType.kBrushed);
	private static CANSparkMax rightClimbMotor = new CANSparkMax(RobotMap.CLIMBMECH_MOTOR_RIGHT, MotorType.kBrushed);
	private static DoubleSolenoid liftPistons = new DoubleSolenoid(RobotMap.PCM2, RobotMap.HABLIFT_PISTON_FWD, RobotMap.HABLIFT_PISTON_REV);
	private static DoubleSolenoid smallLiftPistons = new DoubleSolenoid(RobotMap.PCM2, RobotMap.SMALL_HABLIFT_PISTON_FWD, RobotMap.SMALL_HABLIFT_PISTON_REV);
    
    private int climbState = 0;

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

    public void level2Lift()
	{
        if(smallLiftPistons.get() == Value.kForward)
        {
            smallLiftPistons.set(Value.kReverse);
        }
        else
        {
            smallLiftPistons.set(Value.kForward);
        }
    }

    /**
     * 1 - Lift arm all the way up (this should be done before reaching the HAB zone)
     * 2 - Start pushing arm down & Engage pistons
     * 3 - Once arm is all the way down, begin driving forward
     * 4 - Stop driving once the HAB platform is detected and retract the pistons
     * 5 - Continue driving for X inches
     * 6 - Stop all motors
     * 7 - HAB Climb Sequence Complete
     */
    public boolean climb()
    {
        switch(climbState)
        {
            case 0:
            //move arm down(until limit switch)
            setClimbMotors(0.3);
            liftPistons.set(Value.kForward);
            Robot.ballMech.spinArm(-0.5);
            if(Robot.ballMech.armSwitchRight.get())
            climbState = 1;
            break;

            case 1:
            Robot.ballMech.stopArm();
            Robot.drivetrain.sigmaDrive(0.3, 0.3);

            // if(ultrasonicSensor.get detects floor)
            // {
            //     Robot.drivetrain.sigmaDrive(0.0, 0.0);
            //     setClimbMotors(0.0);
            //     liftPistons.set(Value.kReverse);
            //     climbstate = 2;
            // } 
            break;

            case 2:
            try 
            {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {}
            
            setClimbMotors(0.3);
            // if(Robot.drivetrain.driveStraight(12))
            // {
            //     setClimbMotors(0.0);
            //     Robot.drivetrain.sigmaDrive(0.0, 0.0);
            //     climbState = 3;
            //     return true;
            // }
            break;
        }
        return false;
    }
}