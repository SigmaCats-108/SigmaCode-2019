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
    private int hatchScoreState = 0, counter = 0;
    
    public void scoreHatch()
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
                hatchMechState++;
            }
            break;

            case 1:
            
            if(Robot.sigmaSight.aimAndRange())
            {
                hatchMechState++;
            }
            break;

            case 2:
            
            Robot.drivetrain.sigmaDrive(0.2, 0.2);

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

    public void hatchScore()
    {
        switch (hatchScoreState)
        {
            case 0:
            
        }



    }
    
    public void resetHatchState()
    {
        hatchMechState = 0;
    }
}