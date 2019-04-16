package frc.autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;;

public class Autonomous {
    public static final String kDefaultAuto = "Default";
    public static final String kCustomAuto = "My Auto";

    public Autonomous() {
        Robot.m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
        Robot.m_chooser.addOption("My Auto", kCustomAuto);
        SmartDashboard.putData("Auto choices", Robot.m_chooser);
    }

    public void runAutonomous(String autoMode) {
        switch (autoMode) {
            case kCustomAuto:
              // Put custom auto code here
              break;
            case kDefaultAuto:
            default:
              // Put default auto code here
              break;
          }
    }
}