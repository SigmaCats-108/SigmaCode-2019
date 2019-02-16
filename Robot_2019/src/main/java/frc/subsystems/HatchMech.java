package frc.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * Robot mechanism for the picking up and scoring of hatches.
 */
public class HatchMech
{
    //private static DoubleSolenoid hatchPunt = new DoubleSolenoid(RobotMap.PCM1, RobotMap.HATCH_CLAMP_FWD, RobotMap.HATCH_CLAMP_REV);
    private static DoubleSolenoid hatchClamp = new DoubleSolenoid(RobotMap.PCM1, RobotMap.HATCH_CLAMP_FWD, RobotMap.HATCH_CLAMP_REV);
    private static DoubleSolenoid hatchExtender = new DoubleSolenoid(RobotMap.PCM1, RobotMap.HATCH_PUSHER_FWD, RobotMap.HATCH_PUSHER_REV);
    private static DoubleSolenoid hatchEjector = new DoubleSolenoid(RobotMap.PCM1, 3, 4);

    private int hatchMechState = 0;
    private int hatchScoreState = 0, hatchRetrieveState = 0, counter = 0;
    private boolean isHatchOnRobot;
    
    public void hatchSequence()
    {
        //System.out.println("Hatch State: " + hatchMechState);
        switch (hatchMechState)
        {
            case 0:
            if (!Robot.sigmaSight.isValidTarget())
            {
                Robot.sigmaSight.seekTarget();
                System.out.println("No target detected");
            }
            else
            {
                hatchMechState = 1;
            }
            break;

            case 1:
            if(Robot.sigmaSight.aimAndRange())
            {
                hatchMechState = 2;
            }
            break;

            case 2:
            //Robot.drivetrain.sigmaDrive(0.2, 0.2);
            break;

        }
        System.out.println("Trackstate: " + hatchMechState);
    }

    public void hatchEjector()
    {
        if(hatchEjector.get() == Value.kForward)
        {
            hatchEjector.set(Value.kReverse);
        }
        else
        {
            hatchEjector.set(Value.kForward);
        }
    }

    public void hatchClamp()
	{
        if(hatchClamp.get() == Value.kForward)
        {
            hatchClamp.set(Value.kReverse);
        }
        else
        {
            hatchClamp.set(Value.kForward);
        }
    }

    public void hatchExtender()
	{
        if (hatchExtender.get() == Value.kForward)
        {
            hatchExtender.set(Value.kReverse);
        }
        else
        {
            hatchExtender.set(Value.kForward);
        }
    }

    public boolean hatchScore()
    {
        switch (hatchScoreState)
        {
            case 0:
            hatchExtender.set(Value.kForward);
            if(counter(1000))
            {
                hatchScoreState = 1;
            }
            break;

            case 1:
            hatchClamp.set(Value.kReverse);
            if(counter(1000))
            {
                hatchScoreState = 2;
            }
            break;

            case 2:
            hatchEjector.set(Value.kForward);
            if(counter(1000))
            {
                hatchScoreState = 3;
            }
            break;
            
            case 3:
            hatchEjector.set(Value.kReverse);
            hatchExtender.set(Value.kReverse);
            hatchScoreState = 0;
            return true;
        }
        return false;
    }

    public boolean hatchRetrieve()
    {
        switch (hatchRetrieveState)
        {
            case 0:
            hatchExtender.set(Value.kForward);
            if(counter(1000))
            {
                hatchRetrieveState = 1;
            }
            break;

            case 1:
            hatchClamp.set(Value.kForward);
            if(counter(1000))
            {
                hatchRetrieveState = 2;
            }
            break;

            case 2:
            hatchExtender.set(Value.kReverse);
            hatchRetrieveState = 0;
            return true;
        }
        return false;
    }
    
    public boolean counter(int ms)
    {
        if(counter < ms / 20)
        {
            counter++;
            return false;
        }
        else
        {
            counter = 0;
            return true;
        }
    }

    public void resetHatchState()
    {
        hatchMechState = 0;
    }
}