package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.cameraserver.CameraServer;
import frc.autos.Autonomous;
import frc.autos.Follower;
import frc.inputs.NavX;
import frc.vision.SigmaSight;
import frc.subsystems.BallMech;
import frc.subsystems.Drivetrain;
import frc.subsystems.HatchMech;
import frc.subsystems.TestMech;

public class Robot extends TimedRobot {

    public static Autonomous autonomous;
    public static Follower follower;
    public static NavX navX;
    public static SigmaSight sigmaSight;
    public static BallMech ballMech;
    public static Drivetrain drivetrain;
    public static HatchMech hatchMech;
    public static TestMech testMech;

    @Override
    public void robotInit() 
    {
        CameraServer.getInstance().startAutomaticCapture();
        autonomous = new Autonomous();
        follower = new Follower();
        navX = new NavX();
        sigmaSight = new SigmaSight();
        ballMech = new BallMech();
        drivetrain = new Drivetrain();
        hatchMech = new HatchMech();
        testMech = new TestMech();

    }

    @Override
    public void robotPeriodic()
    {
        navX.updateAHRS();
        //navX.testAngle();
        sigmaSight.updateValues();
        sigmaSight.testValues();
        //drivetrain.testSpeed();
    }
    
    @Override
    public void autonomousInit() 
    {
        //follower.setupFollower();
    }

    @Override
    public void autonomousPeriodic()
    {
        autonomous.runAutonomous();
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
        testMech.runTests();
    }
}