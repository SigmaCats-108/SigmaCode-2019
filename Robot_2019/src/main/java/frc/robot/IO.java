package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.subsystems.BallMech.ArmPosition;

public class IO
{
    public static XboxController mainController = new XboxController(0);
    public static XboxController operatorController = new XboxController(1);

    // Main Controller Variables
    public static boolean m_buttonA, m_buttonB, m_buttonX, m_buttonXRaw, m_buttonY, m_leftBumper, m_leftBumperReleased, m_rightBumper, m_leftStick, m_rightStick;
    public static double m_leftTrigger, m_rightTrigger, m_leftAnalogX, m_rightAnalogX, m_leftAnalogY, m_rightAnalogY;
    public static boolean o_buttonA, o_buttonB, o_buttonX, o_buttonXReleased, o_buttonY, o_leftBumper, o_rightBumper, o_leftStick, o_rightStick;
    public static double o_leftTrigger, o_rightTrigger, o_leftAnalogX, o_rightAnalogX, o_leftAnalogY, o_rightAnalogY;

    public static void UpdateControllers()
    {
        m_buttonA = mainController.getRawButtonPressed(1);
        m_buttonB = mainController.getRawButton(2);
        m_buttonX = mainController.getRawButton(3);
        m_buttonXRaw = mainController.getRawButton(3);
        m_buttonY = mainController.getRawButton(4);
        m_leftBumper = mainController.getRawButton(5);
        m_leftBumperReleased = mainController.getRawButtonReleased(5);
        m_rightBumper = mainController.getRawButton(6);
        m_leftStick = mainController.getRawButton(9);
        m_rightStick = mainController.getRawButton(10);
        m_leftTrigger = zeroValue(mainController.getRawAxis(2));
        m_rightTrigger = zeroValue(mainController.getRawAxis(3));
        m_leftAnalogX = zeroValue(mainController.getRawAxis(0));
        m_rightAnalogX = zeroValue(mainController.getRawAxis(4));
        m_leftAnalogY = zeroValue(mainController.getRawAxis(1)) * -1;
        m_rightAnalogY = zeroValue(mainController.getRawAxis(5)) * -1;

        o_buttonA = operatorController.getRawButton(1);
        o_buttonB = operatorController.getRawButton(2);
        o_buttonX = operatorController.getRawButton(3);
        o_buttonY = operatorController.getRawButton(4);
        o_leftBumper = operatorController.getRawButton(5);
        o_rightBumper = operatorController.getRawButton(6);
        o_leftStick = operatorController.getRawButton(9);
        o_rightStick = operatorController.getRawButton(10);
        o_leftTrigger = zeroValue(operatorController.getRawAxis(2));
        o_rightTrigger = zeroValue(operatorController.getRawAxis(3));
        o_leftAnalogX = zeroValue(operatorController.getRawAxis(0));
        o_rightAnalogX = zeroValue(operatorController.getRawAxis(4));
        o_leftAnalogY = zeroValue(operatorController.getRawAxis(1)) * -1;
        o_rightAnalogY = zeroValue(operatorController.getRawAxis(5)) * -1;
    }

    public static void ProcessControllers()
    {
        //main controller
        if(m_buttonA)
        {
            Robot.hatchMech.hatchClamp();
        }

        if(m_buttonB)
        {
            Robot.hatchMech.scoreHatch();
        }
        else
        {
            Robot.hatchMech.resetHatchState();
        }
        
        /* if(!Robot.drivetrain.turnAngle(180) && m_buttonX)
        {
            Robot.drivetrain.turnState = 0;
        } */

        if(m_leftBumper)
            Robot.drivetrain.highGear(false);
        else if(m_leftBumperReleased)
            Robot.drivetrain.highGear(true);


        //operator controller
        if(o_buttonA)
        {
            Robot.ballMech.setArm(ArmPosition.LOADING_WALL);
        }
        else if(o_buttonB)
        {
            Robot.ballMech.setArm(ArmPosition.LOADING_FLOOR);
        }
        else if(o_buttonX)
        {
            Robot.ballMech.setArm(ArmPosition.SCORING);
        }
        else if(o_buttonY)
        {
            Robot.ballMech.setArm(ArmPosition.CLIMBING);
        }
        else
        {
            Robot.ballMech.stopArm();
        }



        if(o_leftBumper)
        {
            Robot.ballMech.setIntake(1);
        }
        else if(o_rightBumper)
        {
            Robot.ballMech.setIntake(-1);
        }
        else
        {
            Robot.ballMech.setIntake(-0.02); //Holds the ball in place (Set to zero if this becomes problamatic)
        }

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