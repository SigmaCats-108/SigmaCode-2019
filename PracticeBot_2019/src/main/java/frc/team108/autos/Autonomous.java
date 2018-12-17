package frc.team108.autos;

import frc.team108.Robot;

public class Autonomous
{

    public void runAutonomous()
    {
        Robot.follower.followPath();
        //Robot.drivetrain.sigmaDrive(0.3, 0.3);
    }
}