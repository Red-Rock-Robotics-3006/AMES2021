// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants.DriveConstants;

public class GrabberSubsystem extends SubsystemBase {

  private WPI_TalonFX liftMotor = new WPI_TalonFX(5);

  private PIDController pid = new PIDController(0d, 0d, 0d);

  private int state = 0; //0 is closed; 1 is open; 2 is in between
  private boolean isEased;

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
  /**
   * @param power to move with (positive is raising and negative is lowering)
   */
  private void move(double power)
  {
    this.liftMotor.set(ControlMode.PercentOutput, power);
  }
  private double getPower()
  {
    return this.liftMotor.get();
  }
  private void limitCheck()
  {
    if(this.getPosition() <= this.CLOSED_POS) this.state = 0;
    else if(this.getPosition() >= this.OPEN_POS) this.state = 1;
    else this.state = 2;
  }
  /**
   * @return true for safe and false for unsafe
   */
  private boolean safetyCheck()
  {
    if((this.state == 0 && getPower() < 0) || (this.state == 1 && getPower() > 0)) 
    {
      this.stop();
      return false;
    }
    return true;
  }

  /**
   * @param power to move with (positive is raising and negative is lowering)
   */
  public void moveToPosition(double power)
  {
    if(safetyCheck()) 
    {
      if(!isEased) this.move(power);
      else 
      { //Currently does not ease; Just for debug
        System.out.println("Move: " + power);
        System.out.println(
          "PID: " + 
          MathUtil.clamp(
            pid.calculate( //Check if fractional position can be used like this or if raw position must be used
              this.getPosition(),
              power < 0 ? this.CLOSED_POS : this.OPEN_POS
            ),
            -power,
            power
          )
        );
        this.move(power);
      }
    }
  }
  public void stop() {
    this.move(0);
  }
  /**
   * @return 0 for closed, 1 for open, and 2 for in between
   */
  public int getState() 
  {
    return this.state;
  }
  /**
   * @return 0 for closed, 1 for open, and 2 for in between
   */
  public int getTarget()
  {
    if(getPower() < 0) return 0;
    if(getPower() > 0) return 1;
    else return 2;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if(getPower() != 0) //For efficiency since method is called in periodic
    {
      limitCheck();
      safetyCheck();
    }
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    periodic();
  }
}
