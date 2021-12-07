// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */

  private WPI_TalonFX feedMotor = new WPI_TalonFX(6);
  private WPI_TalonFX shootMotor = new WPI_TalonFX(8);

  public ShooterSubsystem() {
      feedMotor.configFactoryDefault();
      shootMotor.configFactoryDefault();

      feedMotor.setNeutralMode(NeutralMode.Brake);
      shootMotor.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void feed(double power)
  {
      feedMotor.set(ControlMode.PercentOutput, power);
  }

  public void stop()
  {
    shootMotor.set(ControlMode.PercentOutput, 0);
  }
  public void shoot(double power)
  {
      shootMotor.set(ControlMode.PercentOutput, power);
      System.out.println("Shooter RPS: " + this.getRPS());
  }

  private double getRPS()
  {
      return shootMotor.getSensorCollection().getIntegratedSensorVelocity();
      
  }

  public boolean isReady()
  {
      if(getRPS() > 1000)
        return true;
      else
        return false; 
  }
  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
