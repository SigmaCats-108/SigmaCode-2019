package frc.team108;

import edu.wpi.first.wpilibj.IterativeRobot;
import frc.team108.IO;
import frc.team108.autos.Autonomous;
import frc.team108.inputs.NavX;
import frc.team108.inputs.Encoders;
import frc.team108.subsystems.Drivetrain;

public class Robot extends IterativeRobot {

    public static NavX navX;
    public static Encoders encoders;
    public static Drivetrain drivetrain;

    @Override
    public void robotInit() 
    {
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
    public void disabledInit() { }

    @Override
    public void disabledPeriodic() { }

    @Override
    public void autonomousInit() { }

    @Override
    public void autonomousPeriodic()
    {
        Autonomous.runAutonomous();
    }

    @Override
    public void teleopInit() { }

    @Override
    public void teleopPeriodic()
    {
        IO.ProcessControllers();
        drivetrain.tankDrive(IO.leftAnalogY, IO.rightAnalogY);
    }

    @Override
    public void testInit() { }

    @Override
    public void testPeriodic() { }
}