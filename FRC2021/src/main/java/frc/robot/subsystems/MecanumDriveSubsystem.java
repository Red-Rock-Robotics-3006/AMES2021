// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class MecanumDriveSubsystem extends SubsystemBase {
    /** Creates a new ExampleSubsystem. */

    private WPI_TalonFX frontLeft = new WPI_TalonFX(4);
    private WPI_TalonFX frontRight = new WPI_TalonFX(2);
    private WPI_TalonFX backLeft = new WPI_TalonFX(3);
    private WPI_TalonFX backRight = new WPI_TalonFX(1);
    private double frontLeftSpeed, frontRightSpeed, backRightSpeed, backLeftSpeed; 
    
    MecanumDrive mecanumDrive;
  public MecanumDriveSubsystem() {

    this.frontLeft.configFactoryDefault();
    this.frontRight.configFactoryDefault();
    this.backLeft.configFactoryDefault();
    this.backRight.configFactoryDefault();

    this.frontRight.setInverted(true);
    this.backRight.setInverted(true);

    // Sets the distance per pulse for the encoders
    this.frontLeft.setNeutralMode(NeutralMode.Brake);
    this.frontRight.setNeutralMode(NeutralMode.Brake);
    this.backRight.setNeutralMode(NeutralMode.Brake);
    this.backLeft.setNeutralMode(NeutralMode.Brake);



    this.mecanumDrive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
  }

  public void mecDrive(double leftY, double rightY, double leftX, double rightX)
  {
    leftY *= -0.4; 
    rightY *= -0.4; 
    leftX *= 0.4; 
    rightX *= 0.4; 

    this.frontLeft.set(ControlMode.PercentOutput, leftY + leftX + rightX);
    this.backRight.set(ControlMode.PercentOutput, 1.1 * rightY + leftX + rightX);
    this.frontRight.set(ControlMode.PercentOutput, rightY - leftX - rightX);
    this.backLeft.set(ControlMode.PercentOutput, 1.1 * leftY - leftX - rightX);

    // System.out.println("Left Y: " + leftY);
    // System.out.println("Right Y: " + rightY);
    // System.out.println("Right X: " + rightX); 
    // System.out.println("Left X: " + leftX);
    // System.out.println("Left Encoder: " + this.getLeftEncoderDistance());
    // System.out.println("Right Encoder: " + this.getRightEncoderDistance());
  }

  public void stop()
  {
      this.mecDrive(0, 0, 0, 0);
  }

  public double getFrontLeftSpeed()
  {
    return this.frontLeft.getSensorCollection().getIntegratedSensorVelocity();
  }


  public double getBackLeftEncoderDistance()
  {
    return Math.abs(this.backLeft.getSensorCollection().getIntegratedSensorPosition());
  }
  public double getLeftEncoderDistance()
  {
    return Math.abs(this.frontLeft.getSensorCollection().getIntegratedSensorPosition()) * DriveConstants.kEncoderDistancePerPulse;
  }

  public double getRightEncoderDistance()
  {
    return Math.abs(this.frontRight.getSensorCollection().getIntegratedSensorPosition()) * DriveConstants.kEncoderDistancePerPulse;
  }

  public void resetEncoders()
  {
    this.frontLeft.getSensorCollection().setIntegratedSensorPosition(0, 0);
    this.frontRight.getSensorCollection().setIntegratedSensorPosition(0, 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
 
  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
