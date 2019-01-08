package frc.inputs;

import frc.robot.Robot;

public class Encoders
{
    public int leftDrive, rightDrive;
	
	public void updateEncoders()
	{
		leftDrive = Robot.drivetrain.getLeftEncoder();
		rightDrive = Robot.drivetrain.getRightEncoder();
	}

	public void testEncoders()
	{
		System.out.println(leftDrive);
		System.out.println(rightDrive);
	}
}