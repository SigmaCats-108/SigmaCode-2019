package frc.team108.inputs;

import frc.team108.subsystems.Drivetrain;

public class Encoders
{
    public int leftEncoderValue, rightEncoderValue;
	public int  rVelocity, lVelocity;
	public double rVelocityFPS, lVelocityFPS;

	public int prevLEncoder, prevREncoder;
	
	public void setEncoderValues()
	{
		leftEncoderValue = Drivetrain.getLeftEncoder();
		rightEncoderValue = Drivetrain.getRightEncoder();
	}
}