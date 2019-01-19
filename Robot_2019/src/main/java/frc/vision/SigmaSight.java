package frc.vision;

//import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class SigmaSight
{

    boolean validTarget;
    double xVal, yVal, area, skew;
    double steering_adjust, distance_adjust, left_command, right_command;
    double turnKp = -0.03, distanceKp = 0.049;  // Proportional control constants
    double targetArea = 1.7;
    double min_aim_command = 0.0;

    NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tv = limelightTable.getEntry("tv");
    NetworkTableEntry tx = limelightTable.getEntry("tx");
    NetworkTableEntry ty = limelightTable.getEntry("ty");
    NetworkTableEntry ta = limelightTable.getEntry("ta");
    NetworkTableEntry ts = limelightTable.getEntry("ta");

    //read values periodically
    public void updateValues()
    {
        validTarget = tv.getBoolean(false);
        xVal = tx.getDouble(0.0);
        yVal = ty.getDouble(0.0);
        area = ta.getDouble(0.0);
        skew = ts.getDouble(0.0);
    }

    /**
     * Will turn towards a detected target, slowing down as the error decreases
     */
    public void turnToTarget()
    {
        steering_adjust = turnKp * xVal;

        left_command = -steering_adjust;
        right_command = steering_adjust;

        Robot.drivetrain.sigmaDrive(left_command, right_command);
    }
    
    public boolean aimandrange()
    {

        if (xVal > 1.0)
        {
            steering_adjust = turnKp * -xVal - min_aim_command;
        }
        else if (xVal < 1.0)
        {
            steering_adjust = turnKp * -xVal + min_aim_command;
        }

        //distance_adjust = distanceKp * yVal;
        distance_adjust = ((area / targetArea) - 1) * -1;

        left_command = steering_adjust + distance_adjust;
        right_command = -steering_adjust + distance_adjust;

        Robot.drivetrain.sigmaDrive(left_command, right_command);

        if( area > targetArea - 0.1 && area < targetArea + 0.1 && xVal > -1.0 && xVal < 1.0)
            return true;
        else
            return false;
    }

    /**
     * Prints the detected object's position values to the shuffleboard
     */
    public void testValues()
    {
    /*  // This shuffleboard code causes the RoboRio to constantly crash upon deployment
        Shuffleboard.getTab("Limelight Values").add("tv", validTarget);
        Shuffleboard.getTab("Limelight Values").add("tx", xVal);
        Shuffleboard.getTab("Limelight Values").add("ty", yVal);
        Shuffleboard.getTab("Limelight Values").add("ta", area);
        Shuffleboard.getTab("Limelight Values").add("ts", skew);
    */
    /*  System.out.println("tX = " + xVal);
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
