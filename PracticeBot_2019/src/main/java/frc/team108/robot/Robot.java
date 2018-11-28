package frc.team108.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import frc.team108.inputs.*;
import frc.team108.subsystems.Drivetrain;

public class Robot extends IterativeRobot {

    Controllers mainController;
    Encoders encoders;
    NavX mainNavX;

    @Override
    public void robotInit() 
    {
        mainController = new Controllers();
        Drivetrain.initializeDrivetrain();
        encoders = new Encoders();
        mainNavX = new NavX();
    }

    @Override
    public void disabledInit() { }

    @Override
    public void disabledPeriodic() { }

    @Override
    public void autonomousInit() { }

    @Override
    public void autonomousPeriodic() { }

    @Override
    public void teleopInit() { }

    @Override
    public void teleopPeriodic()
    {
        mainController.setMainContollerValues();
        encoders.setEncoderValues();
        Drivetrain.tankDrive(mainController.leftAnalogY, mainController.rightAnalogY);
    }

    @Override
    public void testInit() { }

    @Override
    public void testPeriodic() { }
}