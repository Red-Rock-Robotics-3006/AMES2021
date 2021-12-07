// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.MecanumDriveSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class BasicAuto extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final MecanumDriveSubsystem driveTrain;
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  private boolean doneRunning = false; 
  public BasicAuto(MecanumDriveSubsystem driveTrain) {
    
    this.driveTrain = driveTrain;
  
  }

  @Override
  public void initialize() {
    this.driveTrain.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
     
    double start = driveTrain.getRightEncoderDistance(); 
    while(Math.abs(driveTrain.getRightEncoderDistance() - start) < 3)
    {
      driveTrain.mecDrive(0.5, 0.5, 0, 0);
      System.out.println("Here");
    } 

    start = driveTrain.getRightEncoderDistance(); 

    while(Math.abs(driveTrain.getRightEncoderDistance() - start) < 3)
    {
      driveTrain.mecDrive(0, 0, -0.5, 0);
      System.out.println("Here AGain");
    } 

    start = driveTrain.getRightEncoderDistance(); 

    while(Math.abs(driveTrain.getRightEncoderDistance() - start) < 3)
    {
      driveTrain.mecDrive(0, 0, 0.5, 0);
      System.out.println("Here AGain Again");
    } 


    this.doneRunning = true; 

    // while(this.driveTrain.getRightEncoderDistance() > 3)
    // {
    //   driveTrain.resetEncoders();
    //   System.out.println("Encoder Distance: " + driveTrain.getRightEncoderDistance());
    // }

    // while(driveTrain.getRightEncoderDistance() < 3)
    // {
    //   System.out.println("Here AGain!");
    //   driveTrain.mecDrive(0, 0, 0.0, -0.5);
    // }

    
  }

  @Override
  public boolean isFinished() {
    return doneRunning;
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.driveTrain.stop();
  }

}
