package frc.autos;

import java.io.IOException;

import edu.wpi.first.wpilibj.Notifier;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;
import jaci.pathfinder.followers.EncoderFollower;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Follower
{
    private EncoderFollower m_left_follower;
    private EncoderFollower m_right_follower;
    private Notifier m_follower_notifier;
    private static final String k_path_name = "example";
    private Trajectory left_trajectory;
    private Trajectory right_trajectory;

    Follower()
    {
        try
        {
            left_trajectory = PathfinderFRC.getTrajectory(k_path_name + ".left");
            right_trajectory = PathfinderFRC.getTrajectory(k_path_name + ".right");
        }
        catch(IOException e) {}


        m_left_follower = new EncoderFollower(left_trajectory);
        m_right_follower = new EncoderFollower(right_trajectory);

        m_left_follower.configureEncoder(0, RobotMap.ENC_TICKS_PER_REV, RobotMap.ROBOT_WHEEL_DIAMETER);
        m_left_follower.configurePIDVA(1.0, 0.0, 0.0, 1 / RobotMap.ROBOT_MAX_VELOCITY, 0);

        m_right_follower.configureEncoder(0, RobotMap.ENC_TICKS_PER_REV, RobotMap.ROBOT_WHEEL_DIAMETER);
        m_right_follower.configurePIDVA(1.0, 0.0, 0.0, 1 / RobotMap.ROBOT_MAX_VELOCITY, 0);

        m_follower_notifier = new Notifier(this::followPath);
        m_follower_notifier.startPeriodic(left_trajectory.get(0).dt);
    }

    public void followPath()
    {
        if (m_left_follower.isFinished() || m_right_follower.isFinished()) 
        {
            m_follower_notifier.stop();
        } 
        else 
        {
            double left_speed = m_left_follower.calculate((int)Robot.drivetrain.leftEncoder.getPosition());
            double right_speed = m_right_follower.calculate((int)Robot.drivetrain.rightEncoder.getPosition());
            double heading = Robot.navX.angle;
            double desired_heading = Pathfinder.r2d(m_left_follower.getHeading());
            double heading_difference = Pathfinder.boundHalfDegrees(desired_heading - heading);
            double turn =  0.8 * (-1.0/80.0) * heading_difference;
            Robot.drivetrain.sigmaDrive(left_speed + turn, right_speed - turn);
        }
    }
}