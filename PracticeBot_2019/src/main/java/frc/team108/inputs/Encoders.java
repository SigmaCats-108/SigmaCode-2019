package frc.team108.inputs;

import frc.team108.Robot;

public class Encoders
{
    public int leftDrive, rightDrive;
	
	public void updateEncoders()
	{
		leftDrive = Robot.drivetrain.getLeftEncoder();
		rightDrive = Robot.drivetrain.getRightEncoder();
	}
}