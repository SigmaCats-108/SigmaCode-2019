package frc.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * Robot mechanism for the picking up and scoring of hatches.
 */
public class HatchMech
{
    private static DoubleSolenoid hatchClamp = new DoubleSolenoid(RobotMap.PCM1, RobotMap.HATCH_CLAMP_FWD, RobotMap.HATCH_CLAMP_REV);
    private static DoubleSolenoid hatchExtender = new DoubleSolenoid(RobotMap.PCM1, RobotMap.HATCH_PUSHER_FWD, RobotMap.HATCH_PUSHER_REV);
    private static DoubleSolenoid hatchEjector = new DoubleSolenoid(RobotMap.PCM1, 6, 1);
    public int hatchMechState = 0;
    public int hatchScoreState = 0, hatchMechPickupState = 0, hatchRetrieveState = 0, counter = 0;
    private boolean clamp;

    public boolean hatchPickupSequence()
    {
        switch (hatchMechPickupState)
        {
            case 0:
                if (!Robot.sigmaSight.isValidTarget())
                {
                    Robot.sigmaSight.seekTarget();
                }
                else
                {
                    hatchMechPickupState = 1;
                }
            break;

            case 1:
                if(Robot.sigmaSight.aimAndRange())
                {
                    hatchMechPickupState = 2;
                    Robot.drivetrain.sigmaDrive(0.0, 0.0);
                }
            break;

            case 2:
                if(Robot.drivetrain.driveStraight(44))
                {
                    hatchMechPickupState = 3;
                }
            break;

            case 3:
                hatchClamp.set(Value.kForward); //hatch clamp
                hatchMechPickupState = 4;
            break;

            case 4:
                
            break;            
        }
        return false;
    }

    public boolean hatchScoreSequence() //scores hatch
    {
        switch (hatchMechState)
        {
            case 0:
            if (!Robot.sigmaSight.isValidTarget())
            {
                Robot.sigmaSight.seekTarget();
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
                    Robot.drivetrain.sigmaDrive(0.0, 0.0);
                }
            break;

            case 2:
                if(Robot.drivetrain.driveStraight(36))
                {
                    hatchMechState = 3;
                    counter = 0;
                }
            break;

            case 3:
                hatchExtender.set(Value.kForward);
                    
                try {Thread.sleep(10);}
                catch (InterruptedException e) {}

                if(counter >= 10)
                {
                    hatchMechState = 4;
                    counter = 0;
                }
                counter++;
            break;

            case 4:
                // 100
                try {Thread.sleep(10);} //delay
                catch (InterruptedException e) {}

                if(counter >= 10)
                {
                    hatchClamp.set(Value.kReverse); //hatch clamp
                    hatchMechState = 5;
                    counter = 0;
                }

                counter++;
            break;

            case 5:
                // 100
                try {Thread.sleep(10);}
                catch (InterruptedException e) {}

                if (counter >= 10)
                {
                    hatchEjector.set(Value.kForward);
                    
                    hatchMechState = 6;
                    counter = 0;
                }
                counter++;
            break;
         
            case 6:
                // 500
                try {Thread.sleep(10);}
                catch (InterruptedException e) {}

                if (counter >= 50)
                {
                    hatchEjector.set(Value.kReverse); //hatch ejecter again
                    hatchExtender.set(Value.kReverse); //retract hatch panel
                    hatchClamp.set(Value.kReverse);
                    hatchMechState = 7;
                    counter = 0;
                }
                counter++;
            break;

            case 7:
    
            break;
        }
        return false;
    }

    public void hatchEjector()
    {
        hatchEjector.set(Value.kForward);
        try {Thread.sleep(300);} 
        catch (InterruptedException e) {}
        hatchEjector.set(Value.kReverse);
        try {Thread.sleep(100);} 
        catch (InterruptedException e) {}

        
        /*
         if(hatchEjector.get() == Value.kForward)
         {            
            hatchEjector.set(Value.kReverse);
            try {Thread.sleep(200);} 
            catch (InterruptedException e) {
                
            }
            hatchEjector.set(Value.kForward);
        } else
        {
            hatchEjector.set(Value.kForward);
            try {Thread.sleep(200);} 
            catch (InterruptedException e) {
                
            }
            hatchEjector.set(Value.kReverse);

        }
       */ 
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

    public void updateHatchMech()
    {
        if(hatchClamp.get() == Value.kForward)
        {
            clamp = true;
        }
        else
        {
            clamp = false;
        }
        SmartDashboard.putBoolean("ClampOpen", clamp);
    }
}