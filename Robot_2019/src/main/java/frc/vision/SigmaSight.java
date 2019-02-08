package frc.vision;

//import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class SigmaSight
{
    boolean validTarget;
    double xVal, yVal, area, skew;
    double steering_adjust, distance_adjust, left_command, right_command;
    double turnKp = RobotMap.HATCH_VISION_TURN_PGAIN;
    double distanceKp = RobotMap.HATCH_VISION_DISTANCE_PGAIN;
    double desiredArea = RobotMap.HATCH_VISION_DESIRED_TARGET_AREA;
    double minAimCommand = RobotMap.HATCH_VISION_MIN_AIM_COMMAND;
    
    private enum Direction {LEFT, RIGHT, OTHER};
    Direction targetDirection = Direction.LEFT;

    NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tv = limelightTable.getEntry("tv");
    NetworkTableEntry tx = limelightTable.getEntry("tx");
    NetworkTableEntry ty = limelightTable.getEntry("ty");
    NetworkTableEntry ta = limelightTable.getEntry("ta");
    NetworkTableEntry ts = limelightTable.getEntry("ts");

    /**
     * Updates the vision target's position periodically
     */
    public void updateValues()
    {
        //validTarget = tv.getBoolean(true);
        validTarget = isValidTarget();
        xVal = tx.getDouble(0.0);
        yVal = ty.getDouble(0.0);
        area = ta.getDouble(0.0);
        skew = ts.getDouble(0.0);
        updateLastKnownDirection();
        //System.out.println("ValidTarget: " + validTarget);
    }

    /**
     * Checks for a valid vision target
     * @return True if a target is found, and False if not
     */
    public boolean isValidTarget()
    {
        return !(xVal == 0.0 && yVal == 0.0);
    }

    /**
     * Records the current direction of the detected target for use in the event that
     * the target is lost and must be found again
     */
    public void updateLastKnownDirection()
    {
        if(xVal > 1)
        {
            targetDirection = Direction.RIGHT;
        }
        else if(xVal < -1)
        {
            targetDirection = Direction.LEFT;
        }
        
        //System.out.println(targetDirection);
    }

    /**
     * Turns towards the last known target's direction if no valid target is found
     */
    public void seekTarget()
    {
        if (targetDirection == Direction.RIGHT)
        {
            Robot.drivetrain.sigmaDrive(0.5, -0.5);
        } 
        else if (targetDirection == Direction.LEFT)
        {
            Robot.drivetrain.sigmaDrive(-0.5, 0.5);
        }
    }
    
    /**
     * Will turn towards a detected target, slowing down as the turn error decreases
     */
    public void turnToTarget()
    {
        steering_adjust = turnKp * xVal;
        Robot.drivetrain.sigmaDrive(-steering_adjust, steering_adjust);
    }
    
    /**
     * Turns towards the target and gets within range simultaniously
     * @return Once the robot is within the target zone, returns true
     */
    public boolean aimAndRange()
    {
        if (xVal > 1.0)
        {
            steering_adjust = turnKp * -xVal - minAimCommand;
        }
        else if (xVal < 1.0)
        {
            steering_adjust = turnKp * -xVal + minAimCommand;
        }

        distance_adjust = distanceKp * (desiredArea - area);
        //distance_adjust = ((area / desiredArea) - 1) * -1;

        left_command = steering_adjust + distance_adjust;
        right_command = -steering_adjust + distance_adjust;

        Robot.drivetrain.sigmaDrive(left_command, right_command);

        if( area > desiredArea - 0.1 && area < desiredArea + 0.1 && xVal > -1.0 && xVal < 1.0)
            return true;
        else
            return false;
    }

    /**
     * Prints out the detected object's position values to the dashboard
     */
    public void testValues()
    {
        /*  This shuffleboard code causes the code crash upon deployment
            Do not use until further testing
        Shuffleboard.getTab("Limelight Values").add("tv", validTarget);
        Shuffleboard.getTab("Limelight Values").add("tx", xVal);
        Shuffleboard.getTab("Limelight Values").add("ty", yVal);
        Shuffleboard.getTab("Limelight Values").add("ta", area);
        Shuffleboard.getTab("Limelight Values").add("ts", skew);
        */
        /*
        System.out.println("tX = " + xVal);
        System.out.println("tX = " + xVal);
        System.out.println("tX = " + xVal);
        System.out.println("tX = " + xVal);
        System.out.println("tX = " + xVal);
        */

        SmartDashboard.putBoolean("tv", validTarget);
        SmartDashboard.putNumber("tx", xVal);
        SmartDashboard.putNumber("ty", yVal);
        SmartDashboard.putNumber("ta", area);
        SmartDashboard.putNumber("ts", skew);
    }

}
