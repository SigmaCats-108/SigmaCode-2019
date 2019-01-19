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

    private int visionState = 0;
    
    public void scoreHatch()
    {
        switch (visionState)
        {
            case 0:

            if(Robot.sigmaSight.aimandrange())
                visionState ++;

            case 1:

            Robot.drivetrain.sigmaDrive(0.6, 0.6);
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
}