package frc.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
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
    private static DoubleSolenoid hatchEjector = new DoubleSolenoid(RobotMap.PCM1, 6, 1);
    private static Timer pickupTimer = new Timer();
    private static Timer Timer = new Timer();
    public int hatchMechState = 0;
    public int hatchScoreState = 0, hatchMechPickupState = 0, hatchRetrieveState = 0, counter = 0;
    private boolean clamp;

    public boolean hatchPickupSequence()
    {
        //System.out.println("Hatch State: " + hatchMechState);
        switch (hatchMechPickupState)
        {
            case 0:
            if (!Robot.sigmaSight.isValidTarget())
            {
                Robot.sigmaSight.seekTarget();
                //System.out.println("No target detected");
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
            //hatchExtender.set(Value.kForward);
            
            // try {Thread.sleep(300);}
            // catch (InterruptedException e) {}

            hatchMechPickupState = 4;
            break;

            case 4:

       
            
                hatchClamp.set(Value.kForward); //hatch clamp
               
               hatchMechPickupState = 5;
            
            break;

            case 5:

        //   try {Thread.sleep(1000);}
        //   catch (InterruptedException e) {}
         //   {
                //hatchExtender.set(Value.kReverse);
                hatchMechPickupState = 6;
          //  }
            break;

            case 6:
            {
               // Robot.drivetrain.moveState = 0;           
            }
            return true;
        }
        //System.out.println("Trackstate: " + hatchMechState);
        return false;
    }


    public boolean hatchSequence() //scores hatch
    {
        //System.out.println("Hatch State: " + hatchMechState);
        switch (hatchMechState)
        {
            case 0:
            if (!Robot.sigmaSight.isValidTarget())
            {
                Robot.sigmaSight.seekTarget();
                //System.out.println("No target detected");
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
            }
            break;

            case 3:
            hatchExtender.set(Value.kForward);
            
            try {Thread.sleep(100);}
            catch (InterruptedException e) {}

      //      if(counter(100))
            {
                //hatchClamp.set(Value.kReverse);
             //   Robot.hatchMech.hatchExtender();
             hatchMechState = 4;
            }
            break;

            case 4:
        //    if(counter(100))
        try {Thread.sleep(100);} //delay
        catch (InterruptedException e) {}

            {
                hatchClamp.set(Value.kReverse); //hatch clamp
               // Robot.hatchMech.hatchEjector();
               hatchMechState = 5;
            }
            break;

            case 5:
          //  if(counter(1000))

          try {Thread.sleep(100);}
          catch (InterruptedException e) {}
            {
                hatchEjector.set(Value.kForward);
                
                hatchMechState = 6;
            }
            break;
         
            case 6:
          //  if(counter(1000))

          try {Thread.sleep(500);}
          catch (InterruptedException e) {}
            {
                hatchEjector.set(Value.kReverse); //hatch ejecter again
                hatchExtender.set(Value.kReverse); //retract hatch panel
                hatchClamp.set(Value.kReverse);
                hatchMechState = 7;
            }
            break;

            case 7:
    
            break;
        }
        //System.out.println("Trackstate: " + hatchMechState);
        return false;
    }

    public void hatchEjector()
    {
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
        // }
        // else
        // {
        //     hatchEjector.set(Value.kForward);
        // }
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

    public boolean scoreHatch()
    {
        switch (hatchScoreState)
        {
            // case 0:
            // //hatchExtender.set(Value.kForward);
            // Robot.climbMech.liftRobot(true);
            // if(counter(1000))
            // {
            //     //hatchClamp.set(Value.kReverse);
            //  //   Robot.hatchMech.hatchExtender();
            //     hatchScoreState = 1;
            // }
            // break;

            // case 1:
            // if(counter(1000))
            // {
            //     hatchEjector.set(Value.kForward);
            //    // Robot.hatchMech.hatchEjector();
            //     hatchScoreState = 2;
            // }
            // break;

            // case 2:
            // if(counter(1000))
            // {
            //     //hatchEjector.set(Value.kReverse);
            //     hatchExtender.set(Value.kForward);
            //     hatchScoreState = 3;
            // }
            // break;
         
            // case 3:
            // if(counter(1000))
            // {
            //     hatchExtender.set(Value.kReverse);
            //     Robot.climbMech.liftRobot(false);
            // }
            // hatchScoreState = 0;
            // return true;
        }
        return false;
    }

    public boolean retrieveHatch()
    {
        switch (hatchRetrieveState)
        {
            case 0:
            //hatchExtender.set(Value.kForward);
            if(counter(1000))
            {
                hatchRetrieveState = 1;
            }
            break;

            case 1:
            //hatchClamp.set(Value.kForward);
            Robot.hatchMech.hatchEjector();
            if(counter(1000))
            {
                hatchRetrieveState = 2;
            }
            break;

            case 2:
            //hatchExtender.set(Value.kReverse);
            Robot.climbMech.liftRobot(false);
            hatchRetrieveState = 0;
            return true;
        }
        return false;
    }
    
    public boolean counter(int ms)
    {
     System.out.println(counter + "\r\n");
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

    public void resetHatchState()
    {
        hatchMechState = 0;
    }
}