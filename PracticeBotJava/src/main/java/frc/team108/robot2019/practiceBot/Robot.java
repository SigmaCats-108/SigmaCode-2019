package frc.team108.robot2019.practiceBot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.SerialPort.Port;

/* import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX; */
import com.kauailabs.navx.frc.*;

/**
 * @author SigmaCats-108
 * 
 */
public class Robot extends TimedRobot {

	// Smart dashboard autonomous chooser strings
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	
	//Controller & Button Declarations
	public XboxController controller;
	public boolean X;
	public boolean A;
	public boolean Y;
	public boolean B;
	public boolean RB;
	public boolean LB;
	public boolean LS;
	public double LT;
	public double RT;
	public double LY;
	public double RY;
	
	//Speed Controller Declarations
	//Ports 1,2,3
/* 	public WPI_TalonSRX  leftDrive = new WPI_TalonSRX(1);
	public WPI_VictorSPX  left2 = new WPI_VictorSPX(2);
	public WPI_VictorSPX  left3 = new WPI_VictorSPX(3);
	//Ports 4,5,6
	public WPI_TalonSRX  rightDrive = new WPI_TalonSRX(4);
	public WPI_VictorSPX  right2 = new WPI_VictorSPX(5);
	public WPI_VictorSPX  right3 = new WPI_VictorSPX(6); */
    AHRS ahrs;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	
	@Override
	public void robotInit() {
		
		//Autonomous chooser 
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		
		//XBOX Controller Initializations
		controller = new XboxController(0);
		
		//Set the victors to 'follow' the talons
/* 		left2.follow(leftDrive);
		left3.follow(leftDrive);
		right2.follow(rightDrive);
		right3.follow(rightDrive);
		
		rightDrive.setInverted(true);
		right2.setInverted(true);
		right3.setInverted(true); */
		
		try
		{
            ahrs = new AHRS(Port.kOnboard);
		}
	    catch (RuntimeException ex ) 
		{
	        DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
	    }
	    
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		// m_autoSelected = SmartDashboard.getString("Auto Selector",
		// 		kDefaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
			case kCustomAuto:
				// Put custom auto code here
				break;
			case kDefaultAuto:
			default:
				// Put default auto code here
				break;
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		//Button & Axis initializations
		LY = controller.getY(Hand.kLeft);
		RY = controller.getY(Hand.kRight);
		
/* 		leftDrive.set(LY);
		rightDrive.set(RY);
		right3.set(RY);
		System.out.print(LY);
		System.out.print(" --- ");
		System.out.print(RY);
		System.out.println(""); */
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		
	}
}
