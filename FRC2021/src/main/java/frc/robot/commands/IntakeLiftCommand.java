// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class IntakeLiftCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final IntakeSubsystem intakeSubsystem;
  private boolean lifted = false; 
  double power; 

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public IntakeLiftCommand(IntakeSubsystem subsystem, double power) {
    intakeSubsystem = subsystem;
    this.power = power; 
   
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      this.lifted = false; 
      this.intakeSubsystem.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      System.out.println("Lift Motor: " + this.intakeSubsystem.getStartPosition());
      this.lifted = intakeSubsystem.liftToPosition(power);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
     // intakeSubsystem.lift(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return lifted;
  }
}
