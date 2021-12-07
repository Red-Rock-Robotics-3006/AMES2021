// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class IntakeSubsystem extends SubsystemBase {
  

  private WPI_TalonFX gatherMotor = new WPI_TalonFX(7);
  private WPI_TalonFX liftMotor = new WPI_TalonFX(5);

  public IntakeSubsystem() {

    this.liftMotor.getSensorCollection().setIntegratedSensorPosition(0, 0);
    gatherMotor.configFactoryDefault();
    liftMotor.configFactoryDefault();

    gatherMotor.setNeutralMode(NeutralMode.Brake);
    liftMotor.setNeutralMode(NeutralMode.Brake);
  }

  public void resetEncoders()
  {
    this.liftMotor.getSensorCollection().setIntegratedSensorPosition(0, 0);
  }
  public void lift(double power)
  {
    this.liftMotor.set(ControlMode.PercentOutput, power);
      
  }

  public void gather(double power)
  {
      this.gatherMotor.set(ControlMode.PercentOutput, power);
  }

  public boolean reachedLimit()
  {
    if(this.getStartPosition() >= 10 || this.getStartPosition() <= -10)
    {
      System.out.println("REACHED LIMIT");
      return true; 
    }
    return false; 
  }

  public double getStartPosition()
  {
    return this.liftMotor.getSensorCollection().getIntegratedSensorPosition()/DriveConstants.kEncoderCPR;
  }

  public boolean liftToPosition(double power)
  {
    if(reachedLimit()){
      lift(0);
      System.out.println("REACHED LIMIT");
      return true; 
    } else {
      System.out.println("HERE");
      lift(power);
      return false; 
    }
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
