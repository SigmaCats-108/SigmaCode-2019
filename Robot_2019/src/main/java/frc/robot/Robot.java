package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.autos.Autonomous;
import frc.inputs.NavX;
import frc.vision.SigmaSight;
import frc.subsystems.BallMech;
import frc.subsystems.ClimbMech;
import frc.subsystems.Drivetrain;
import frc.subsystems.HatchMech;

public class Robot extends TimedRobot 
{

    public static Autonomous autonomous;
    public static NavX navX;
    public static SigmaSight sigmaSight;
    public static BallMech ballMech;
    public static ClimbMech climbMech;    
    public static Drivetrain drivetrain;
    public static HatchMech hatchMech;    

    @Override
    public void robotInit() 
    {
        autonomous = new Autonomous();
        navX = new NavX();
        sigmaSight = new SigmaSight();
        ballMech = new BallMech();
        climbMech = new ClimbMech();
        drivetrain = new Drivetrain();
        hatchMech = new HatchMech();
    }

    @Override
    public void robotPeriodic()
    {
        navX.updateAHRS();
        sigmaSight.updateValues();
        sigmaSight.testValues();
        ballMech.testArmEnc();
        drivetrain.update();
        hatchMech.updateHatchMech();
    }
    
    @Override
    public void autonomousInit() 
    {
    }

    @Override
    public void autonomousPeriodic()
    {
        IO.UpdateControllers();

        if(!IO.o_buttonB && !IO.o_buttonX)
        {
            drivetrain.sigmaDrive(IO.m_leftAnalogY, IO.m_rightAnalogY);
        }
        
        IO.ProcessControllers();
    }

    @Override
    public void teleopInit() { }

    @Override
    public void teleopPeriodic()
    {
        IO.UpdateControllers();

        if(!IO.o_buttonB && !IO.o_buttonX)
        {
            drivetrain.sigmaDrive(IO.m_leftAnalogY, IO.m_rightAnalogY);
        }
        
        IO.ProcessControllers();
    }

    @Override
    public void testInit() { }

    @Override
    public void testPeriodic()
    {
    }
}