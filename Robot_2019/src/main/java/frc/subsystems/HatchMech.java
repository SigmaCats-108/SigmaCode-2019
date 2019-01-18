package frc.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Robot mechanism for the picking up and scoring of hatches.
 */
public class HatchMech
{
    private static DoubleSolenoid hatchIntake = new DoubleSolenoid(3, 0);

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