package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.RobotMap;

/**
 * The robot mechanism for the picking up and scoring of balls.
 */
public class BallMech
{

	private static CANSparkMax testMotor1 = new CANSparkMax(RobotMap.BALLMECH_TEST1, MotorType.kBrushless);
    private static CANEncoder testEncoder1 = testMotor1.getEncoder();
    private static double testEncVal1;
	
    private double targetEncVal;
    private double ticksPerRev = 80.0;
	public Boolean doTurn = false;
	public int turnState = 0;

    public void updateBallMech()
    {
        testEncVal1 = testEncoder1.getPosition();

        if(doTurn)
        {
            encoderTurn(1);
        }
    }

	public void encoderTurn(int revolutions)
	{

		System.out.println("Turnstate: " + turnState);

		switch(turnState)
		{
			case 0:

            targetEncVal = testEncVal1 + (revolutions * ticksPerRev);
            turnState++;
            
			break;
			
			case 1:

			if(testEncVal1 <= targetEncVal)
			{
				testMotor1.set(0.5);
			}
			else
			{
				testMotor1.set(0.0);
				turnState--;
				doTurn = false;
			}
        }
        
		/*
		if(hasTurned)
		{
			targetEncVal = leftEnc + ticksPerRev ;
			hasTurned = false;
		}
		else if (leftEnc < targetEncVal)
		{
			sigmaDrive(-0.5, 0.0);
		}
		else
		{
			//hasTurned = true;
			a += 1;
		}
		if(leftEnc >= targetEncVal)
		{
			sigmaDrive(0.0, 0.0);
		}	
		if (a > 1)
		{
			hasTurned = true;
		}	
    */

	}

}