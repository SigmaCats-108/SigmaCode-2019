package frc.team108.inputs;

import edu.wpi.first.wpilibj.XboxController;

public class Controllers
{
    public XboxController Controller = new XboxController(0);

    // Controller Variables
    public  boolean buttonA, buttonB, buttonX, buttonY, leftBumper, rightBumper, leftStick, rightStick;
    public double leftTrigger, rightTrigger, leftAnalogX, leftAnalogY, rightAnalogX, rightAnalogY;

    public void setMainContollerValues()
    {
        buttonA = Controller.getRawButton(1);
        buttonB = Controller.getRawButton(2);
        buttonX = Controller.getRawButton(3);
        buttonY = Controller.getRawButton(4);
        leftBumper = Controller.getRawButton(5);
        rightBumper = Controller.getRawButton(6);
        leftStick = Controller.getRawButton(9);
        rightStick = Controller.getRawButton(10);
        leftTrigger = zeroValue(Controller.getRawAxis(2));
        rightTrigger = zeroValue(Controller.getRawAxis(3));
        leftAnalogX = zeroValue(Controller.getRawAxis(0));
        leftAnalogY = zeroValue(Controller.getRawAxis(1));
        rightAnalogX = zeroValue(Controller.getRawAxis(4));
        rightAnalogY = zeroValue(Controller.getRawAxis(5));
    }

    public static double zeroValue(double realValue)
    {
        if(realValue < .13 && realValue > -.13)
        {
            return 0;
        }
        else
        {
            return 1 * realValue;
        }
    }

}