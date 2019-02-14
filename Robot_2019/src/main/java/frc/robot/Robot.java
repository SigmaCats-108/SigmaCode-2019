package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.autos.Autonomous;
import frc.autos.Follower;
import frc.inputs.NavX;
import frc.vision.SigmaSight;
import frc.subsystems.BallMech;
import frc.subsystems.Drivetrain;
import frc.subsystems.HatchMech;
import frc.subsystems.climbMech;


public class Robot extends TimedRobot {

    public static Autonomous autonomous;
    public static Follower follower;
    public static NavX navX;
    public static SigmaSight sigmaSight;
    public static BallMech ballMech;
    public static climbMech climbMech;    
    public static Drivetrain drivetrain;
    public static HatchMech hatchMech;
    

    @Override
    public void robotInit() 
    {
        autonomous = new Autonomous();
        follower = new Follower();
        navX = new NavX();
        sigmaSight = new SigmaSight();
        ballMech = new BallMech();
        climbMech = new climbMech();
        drivetrain = new Drivetrain();
        hatchMech = new HatchMech();
        

    }

    @Override
    public void robotPeriodic()
    {
        navX.updateAHRS();
        //navX.testAngle();
        sigmaSight.updateValues();
        sigmaSight.testValues();
        ballMech.updateBallMech();
        ballMech.testArmEnc();
    }
    
    @Override
    public void autonomousInit() 
    {
        //follower.setupFollower();
    }

    @Override
    public void autonomousPeriodic()
    {
        //autonomous.runAutonomous();
    }

    @Override
    public void teleopInit() { }

    @Override
    public void teleopPeriodic()
    {
        IO.UpdateControllers();

        drivetrain.sigmaDrive(IO.m_leftAnalogY, IO.m_rightAnalogY);
        IO.ProcessControllers();
    }

    @Override
    public void testInit() { }

    @Override
    public void testPeriodic()
    { 
    }
}