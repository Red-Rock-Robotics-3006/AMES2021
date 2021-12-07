// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class ShootCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  
  private final ShooterSubsystem shooter;
  private final IntakeSubsystem intake; 

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ShootCommand(ShooterSubsystem shooter, IntakeSubsystem intake) {
    this.shooter = shooter;
    this.intake = intake;

    // Use addRequirements() here to declare subsystem dependencies.
   // addRequirements(this.shooter, this.intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
      shooter.shoot(1.0);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      if(this.shooter.isReady())
      {
          this.shooter.feed(1.0);
      } else {
          this.shooter.feed(0.0);
      }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
      this.shooter.stop();
      this.shooter.feed(0.0);
  }


}
