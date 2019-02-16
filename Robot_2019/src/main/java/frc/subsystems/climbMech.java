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
	private static DoubleSolenoid liftPistons = new DoubleSolenoid(RobotMap.PCM1, RobotMap.HABLIFT_PISTON_FWD, RobotMap.HABLIFT_PISTON_REV);

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

    /**
     * 1 - Lift arm all the way up (this should be done before reaching the HAB zone)
     * 2 - Start pushing arm down & Engage pistons
     * 3 - Once arm is all the way down, begin driving forward
     * 4 - Stop driving once the HAB platform is detected and retract the pistons
     * 5 - Continue driving for X inches
     * 6 - HAB Climb Sequence Complete
     */
    public boolean climb()
    {

        switch(climbState)
        {
            case 0:
            //move arm down(until limit switch)
            liftPistons.set(Value.kForward);
            setClimbMotors(0.3);
            Robot.ballMech.spinArm(-0.5);

            /*
            if(limitSwitch)
            climbState = 1;
            */
            break;

            case 1:
            Robot.ballMech.stopArm();
            Robot.drivetrain.sigmaDrive(0.3, 0.3);

            /*
            if(ultrasonicSensor detects floor)
            {
                Robot.drivetrain.sigmaDrive(0.0, 0.0);
                setClimbMotors(0.0);
                liftPistons.set(Value.kReverse);
                climbstate = 2;
            }
            */
            break;

            case 2:
            
            setClimbMotors(0.3);
            if(!Robot.drivetrain.driveStraight(20))
            {
                climbState = 3;
            }
            break;

            
            

            

        }
        return false;
    }
}