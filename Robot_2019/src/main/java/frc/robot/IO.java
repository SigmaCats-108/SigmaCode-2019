package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class IO
{
    public static XboxController mainController = new XboxController(0);
    public static XboxController operatorController = new XboxController(1);

    // Main Controller Variables
    public static boolean m_buttonA, m_buttonB, m_buttonX, m_buttonXRaw, m_buttonY, m_leftBumper, m_leftBumperReleased, m_rightBumper, m_leftStick, m_rightStick, o_buttonBReleased;
    public static double m_leftTrigger, m_rightTrigger, m_leftAnalogX, m_rightAnalogX, m_leftAnalogY, m_rightAnalogY;
    public static boolean o_buttonA, o_buttonB, o_buttonX, o_buttonXReleased, o_buttonY, o_leftBumper, o_rightBumper, o_leftStick, o_rightStick;
    public static double o_leftTrigger, o_rightTrigger, o_leftAnalogX, o_rightAnalogX, o_leftAnalogY, o_rightAnalogY;

    public static void UpdateControllers()
    {
        m_buttonA = mainController.getRawButtonPressed(1);
        m_buttonB = mainController.getRawButtonPressed(2);
        m_buttonX = mainController.getRawButton(3);
        m_buttonXRaw = mainController.getRawButton(3);
        m_buttonY = mainController.getRawButtonPressed(4);
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
        o_buttonBReleased = operatorController.getRawButtonReleased(2);
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
        //Main Controller
        
        /**
         * Changes the state of the Hatch Clamp
         */
        if(!o_buttonB && !o_buttonX)
        {
            if(m_buttonA)
            {
                Robot.hatchMech.hatchClamp();
            }

        /**
         * Changes the state of the Hatch Extender
         */
            if(m_buttonY)
            {
                Robot.hatchMech.hatchExtender();
            }
        
        /**
         * Activates the pistons that shoot the hatch from the mechanism
         */
            if(m_buttonB)
            {
                Robot.hatchMech.hatchEjector();
            }

        /**
         * Changes the state of the Robot Lifter
         */
            if(m_buttonX)
            {
                Robot.climbMech.liftRobot(true);
            }
            else
            {
                Robot.climbMech.liftRobot(false);
            }
        }

        // /**
        //  * Activates the hatch scoring / aquiring mechanism
        //  */
        // if(m_buttonB)
        // {
        //     Robot.hatchMech.scoreHatch();
        // }
        // else
        // {
        //     Robot.hatchMech.resetHatchState();
        // }
        
        /* if(!Robot.drivetrain.turnAngle(180) && m_buttonX)
        {
            Robot.drivetrain.turnState = 0;
        } */

        /**
         * Changes the gearstate of the drivetrain
         */
        if(m_leftTrigger > 0.5)
        {
            Robot.drivetrain.highGear(true);
        }
        else
        {
            Robot.drivetrain.highGear(false);
        }

        /**
         * Runs the intake
         */
        if(m_leftBumper)
        {
            Robot.ballMech.intake(0.1);
        }
        else if(m_rightBumper)
        {
            Robot.ballMech.outake(0.1);
        }
        else
        {
            Robot.ballMech.intake(0.00); //Holds the ball in place (Set to zero if this becomes problamatic)
        }

       

        // Operator Controller

        /**
         * Sets the ballMech arm position
         */
        // if(o_buttonA)
        // {
        //     Robot.ballMech.setArm(RobotMap.ArmPosition.LOADING_FLOOR);
        // }
        // else if(o_buttonB)
        // {
        //     Robot.ballMech.setArm(RobotMap.ArmPosition.LOADING_WALL);
        // }
        // else if(o_buttonX)
        // {
        //     Robot.ballMech.setArm(RobotMap.ArmPosition.SCORING);
        // }
        // else if(o_buttonY)
        // {
        //     Robot.ballMech.setArm(RobotMap.ArmPosition.CLIMBING);
        // }
        // else
        // {
        //     Robot.ballMech.stopArm();
        // }

        /**
         * Turns the arm motor based on button input
         */
        if(o_buttonY)
        {
            Robot.ballMech.spinArm(0.80);
        }
        else if(o_buttonA)
        {
            Robot.ballMech.spinArm(-0.80);
        }
        else if (!(o_buttonY || o_buttonX))
        {
            Robot.ballMech.stopArm();
        }

        if(o_buttonB)
        {
            Robot.hatchMech.hatchSequence();

         //   Robot.hatchMech.scoreHatch();
        }
        else
        {
            Robot.hatchMech.hatchScoreState = 0;
            Robot.hatchMech.hatchMechState = 0;
        }

        if (o_buttonX)
        {
            Robot.hatchMech.hatchPickupSequence();
        }
        else
        {
            Robot.hatchMech.hatchMechPickupState = 0;
        }

        if(o_leftBumper)
        {
            Robot.climbMech.setClimbMotors(0.95);
        }
        else if(o_rightBumper)
        {
            Robot.climbMech.setClimbMotors(-0.95);
        }
        else
        {
            Robot.climbMech.setClimbMotors(0.0);
        }
    }

    public static void TestControllers()
    {
        // if(o_buttonA)
        // {
        //     Robot.test.push1();
        // }
        // if(o_buttonB)
        // {
        //     Robot.test.push2();
        // }
        // if(o_buttonX)
        // {
        //     Robot.test.push3();
        // }
        // if(o_buttonY)
        // {
        //     Robot.test.push4();
        // }
        // if(o_leftBumper)
        // {
        //     Robot.test.push5();
        // }
    }

    /**
     * Adds a deadband to our controller axis values
     */
    private static double zeroValue(double realValue)
    {
        if(realValue < .09 && realValue > -.09)
        {
            return 0;
        }
        else
        {
            return 1 * realValue;
        }
    }

}