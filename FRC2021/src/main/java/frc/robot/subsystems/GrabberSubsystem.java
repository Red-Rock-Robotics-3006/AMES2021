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

  private WPI_TalonFX liftMotor = new WPI_TalonFX(5);

  private PIDController pid = new PIDController(0d, 0d, 0d);

  private int openState = 0; //0 is closed; 1 is open; 2 is inbetween
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
    if(this.getPosition() >= this.OPEN_POS) 
    {
      this.openState = 1;
      return true;
    }
    if (this.getPosition() <= this.CLOSED_POS)
    {
      this.openState = 0;
      return true; 
    }
    this.openState = 2;
    return false; 
  }

  public boolean moveToPosition(double power)
  {
    if(reachedLimit()){
      this.stop();
      return true;
    } else {
      if (!isEased) this.move(power);
      else { //Currently does not ease; Just for debug
        System.out.println("Move: " + power);
        System.out.println("PID: " + MathUtil.clamp(
          pid.calculate( //Check if fractional position can be used like this or if raw position must be used
              this.getPosition(),
              power < 0 ? this.CLOSED_POS : this.OPEN_POS
            )
          ),
          -power,
          power
        );
        this.move(power);
      }
      return false; 
    }
  }
  public void stop() {
    this.move(0);
  }
  public int getOpenState() 
  {
    return this.openState;
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
