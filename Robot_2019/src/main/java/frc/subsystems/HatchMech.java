package frc.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Robot;

/**
 * Robot mechanism for the picking up and scoring of hatches.
 */
public class HatchMech
{
    private static DoubleSolenoid hatchIntake = new DoubleSolenoid(3, 0);

    private int hatchMechState = 0;
    
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
            
            Robot.drivetrain.sigmaDrive(0.4, 0.4);

            break;

        }
    }

    public void hatchIntake()
	{
        if (hatchIntake.get() == Value.kForward)
        {
            hatchIntake.set(Value.kReverse);
        }
        else
        {
            hatchIntake.set(Value.kForward);
        }
        //hatchClamps.set(DoubleSolenoid.Value.kReverse);
		//hatchClamps.set(!hatchClamps.get());
    }
    
    public void resetHatchState()
    {
        hatchMechState = 0;
    }
}