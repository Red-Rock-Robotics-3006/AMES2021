// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class GrabberSubsystem extends SubsystemBase {

  private WPI_TalonFX liftMotor = new WPI_TalonFX(5); //Figure out device ID

  private boolean isOpen = false;
  private boolean isEased; //Functionality to be added later with PID

  private final double OPEN_POS = 0.4;
  private final double CLOSED_POS = 0.1;

  public GrabberSubsystem(boolean isEased) {
    this.isEased = isEased;

    this.liftMotor.getSensorCollection().setIntegratedSensorPosition(0, 0);
    this.liftMotor.configFactoryDefault();

    this.liftMotor.setNeutralMode(NeutralMode.Brake);
  }

  public double getPosition()
  {
    return this.liftMotor.getSensorCollection().getIntegratedSensorPosition()/DriveConstants.kEncoderCPR;
  }
  public void resetEncoders()
  {
    this.liftMotor.getSensorCollection().setIntegratedSensorPosition(0, 0);
  }
  private void move(double power)
  {
    this.liftMotor.set(ControlMode.PercentOutput, power);
      
  }
  private boolean reachedLimit()
  {
    if(this.getPosition() >= this.OPEN_POS || this.getPosition() <= this.CLOSED_POS)
    {
      System.out.println("REACHED LIMIT");
      return true; 
    }
    return false; 
  }

  public boolean moveToPosition(double power)
  {
    if(reachedLimit()){
      this.stop();
      System.out.println("REACHED LIMIT");
      return true; 
    } else {
      System.out.println("HERE");
      move(power);
      return false; 
    }
  }
  public void stop() {
    move(0);
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
