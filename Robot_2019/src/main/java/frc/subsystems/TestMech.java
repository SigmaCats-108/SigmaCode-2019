package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * A class for independantly testing various robot components
 */
public class TestMech
{
	
	private static CANSparkMax testMotor = new CANSparkMax(33, MotorType.kBrushed);
    private static CANEncoder testEncoder = testMotor.getEncoder();
    
    private double ticksPerRev = 80.0;
    private double targetEncVal;
	public Boolean doTurn = false;
    public int turnState = 0;

    public void runTests()
    {
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

            targetEncVal = testEncoder.getPosition() + (revolutions * ticksPerRev);
            turnState++;
            
			break;
			
			case 1:

			if(testEncoder.getPosition() <= targetEncVal)
			{
				testMotor.set(0.5);
			}
			else
			{
				testMotor.set(0.0);
				turnState--;
				doTurn = false;
			}
        }

	}

}