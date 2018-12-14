package frc.team108;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.team108.autos.Autonomous;
import frc.team108.inputs.NavX;
import frc.team108.inputs.Encoders;
import frc.team108.subsystems.Drivetrain;

public class Robot extends TimedRobot {

    public static Autonomous autonomous;
    public static NavX navX;
    public static Encoders encoders;
    public static Drivetrain drivetrain;


    @Override
    public void robotInit() 
    {
        autonomous = new Autonomous();
        navX = new NavX();
        encoders = new Encoders();
        drivetrain = new Drivetrain();
    }

    @Override
    public void robotPeriodic() 
    {
        navX.updateAHRS();
        encoders.updateEncoders();
    }
    
    @Override
    public void autonomousInit() { }

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
        drivetrain.sigmaDrive(IO.leftAnalogY, IO.rightAnalogY);
        IO.ProcessControllers();
    }

    @Override
    public void testInit() { }

    @Override
    public void testPeriodic() { }
}