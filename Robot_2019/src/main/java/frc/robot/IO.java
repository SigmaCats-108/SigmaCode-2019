package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class IO
{
    public static XboxController mainController = new XboxController(0);

    // Main Controller Variables
    public static boolean buttonA, buttonB, buttonX, buttonY, leftBumper, rightBumper, leftStick, rightStick;
    public static double leftTrigger, rightTrigger, leftAnalogX, leftAnalogY, rightAnalogX, rightAnalogY;

    public static void UpdateControllers()
    {
        buttonA = mainController.getRawButtonPressed(1);
        buttonB = mainController.getRawButton(2);
        buttonX = mainController.getRawButton(3);
        buttonY = mainController.getRawButton(4);
        leftBumper = mainController.getRawButton(5);
        rightBumper = mainController.getRawButton(6);
        leftStick = mainController.getRawButton(9);
        rightStick = mainController.getRawButton(10);
        leftTrigger = zeroValue(mainController.getRawAxis(2));
        rightTrigger = zeroValue(mainController.getRawAxis(3));
        leftAnalogX = zeroValue(mainController.getRawAxis(0));
        rightAnalogX = zeroValue(mainController.getRawAxis(4));
        leftAnalogY = zeroValue(mainController.getRawAxis(1)) * -1;
        rightAnalogY = zeroValue(mainController.getRawAxis(5)) * -1;
    }

    public static void ProcessControllers()
    {
        if(buttonA)
        {
            Robot.hatchMech.hatchIntake();
        }

        if(buttonB)
        {
            Robot.sigmaSight.turnToTarget();
        }

        if(leftTrigger > 0.5)
            Robot.drivetrain.highGear(true);
        else
            Robot.drivetrain.highGear(false);
    }

    private static double zeroValue(double realValue)
    {
        if(realValue < .05 && realValue > -.05)
        {
            return 0;
        }
        else
        {
            return 1 * realValue;
        }
    }

}