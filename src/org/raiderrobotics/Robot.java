package org.raiderrobotics;
// yoyo test download
import org.raiderrobotics.sensors.QuickTurnExecutor;

import edu.wpi.first.wpilibj.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	final static int ARCADE = 1;
	final static int TANK = 2;

	//create object references
	Joystick leftStick, rightStick;
	public RobotDrive driveTrain1;
	public Talon talon1, talon2;
	//the JoystickButton class does not exist in our Java FRC plugins!
	// JoystickButton stickLBtn1, stickLBtn2; 

	//global variables
	private int driveState = ARCADE;

	QuickTurnExecutor gyro;
	
	/*This function is run when the robot is first started up and should be used for any initialization code. */

	//create global objects here
	public void robotInit() {
		talon1 = new Talon(1);
		talon2 = new Talon(2);

		/*** do the following lines do anything? 
        talon1.enableDeadbandElimination(true);
        talon2.enableDeadbandElimination(true);
		 ***/

		//reversing 1,2 and 3,4 will switch front and back in arcade mode.
		driveTrain1 = new RobotDrive(talon1, talon2);

		//this works to fix arcade joystick 
		driveTrain1.setInvertedMotor(RobotDrive.MotorType.kFrontLeft,true);
		driveTrain1.setInvertedMotor(RobotDrive.MotorType.kRearLeft,true);
		driveTrain1.setInvertedMotor(RobotDrive.MotorType.kFrontRight,true);
		driveTrain1.setInvertedMotor(RobotDrive.MotorType.kRearRight,true);

		leftStick = new Joystick(0);
		rightStick = new Joystick(1);
		//stickLBtn1 = new JoystickButton(stickL, 1);
		//stickLBtn2 = new JoystickButton(stickL, 2);
		
		gyro = new QuickTurnExecutor(this, rightStick, new Gyro( new AnalogInput(0)));
	}

	/* This function is called periodically during autonomous */
	public void autonomousPeriodic() {

	}

	/* This function is called periodically during operator control */
	// called at 50Hz (every 20ms). This method must not take more than 20ms to complete!
	public void teleopPeriodic() {
		// feed the watchdog
		//Watchdog.getInstance().feed(); //THIS NO LONGER SEEMS TO EXIST!!!

		normalDrive();

		//check for button press to switch mode. Use two buttons to prevent bounce.
		boolean button2 = leftStick.getRawButton(1);
		boolean button3 = leftStick.getRawButton(2);
		if (button2) driveState = ARCADE;
		if (button3) driveState = TANK;
	}

	// drive the robot normally
	private void normalDrive() {
		if (driveState == ARCADE) {
			driveTrain1.arcadeDrive(rightStick, true); //use squared inputs
		} else {
			driveTrain1.tankDrive(leftStick, rightStick);
		}
		
		gyro.check();
	}

	/* This function is called periodically during test mode */
	public void testPeriodic() {

	}

}
