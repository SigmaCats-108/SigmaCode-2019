package frc.autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;;

public class Autonomous {
    public static final String kDefaultAuto = "Do Nothing";
    public static final String kCustomAuto = "Drive Straight";

    public Autonomous() {
        Robot.m_chooser.setDefaultOption(kDefaultAuto, kDefaultAuto);
        Robot.m_chooser.addOption(kCustomAuto, kCustomAuto);
        SmartDashboard.putData("Auto choices", Robot.m_chooser);
    }

    public void runAutonomous(String autoMode) {
        switch (autoMode) {
            case kCustomAuto:
                Robot.follower.followPath();
                break;
            case kDefaultAuto:
            default:
                // Put default auto code here
                break;
          }
    }
}