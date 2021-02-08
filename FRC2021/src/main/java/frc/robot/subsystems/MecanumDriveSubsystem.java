// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class MecanumDriveSubsystem extends SubsystemBase {
    /** Creates a new ExampleSubsystem. */

    private WPI_VictorSPX frontLeft = new WPI_VictorSPX(3);
    private WPI_VictorSPX frontRight = new WPI_VictorSPX(2);
    private WPI_VictorSPX backLeft = new WPI_VictorSPX(4);
    private WPI_VictorSPX backRight = new WPI_VictorSPX(1);
    
    MecanumDrive mecanumDrive;
  public MecanumDriveSubsystem() {

    // Sets the distance per pulse for the encoders
    
    this.mecanumDrive = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
  }

  public void mecDrive(double xSpeed, double ySpeed, double zTwist)
  {
      this.mecanumDrive.driveCartesian(0.6*ySpeed, 0.6*xSpeed, zTwist);
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
