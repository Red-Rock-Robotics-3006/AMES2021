// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.BasicAuto;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.IntakeLiftCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.MecanumDriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.JoystickConstants;;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private static final double _0_5 = 0.5;

  // The robot's subsystems and commands are defined here...
   private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

   private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
   private final ShooterSubsystem shooter = new ShooterSubsystem();
   private final IntakeSubsystem intake = new IntakeSubsystem();
   private final GrabberSubsystem grabber = new GrabberSubsystem(false);
   
  private double shootPower = 0.5;
  private final MecanumDriveSubsystem mecanumDriveSubsystem = new MecanumDriveSubsystem();
  private final BasicAuto basicAutoCommand = new BasicAuto(mecanumDriveSubsystem);

  private final WPI_TalonFX launcherMotor = new WPI_TalonFX(10);
  private final Joystick driveStick = new Joystick(0);
  private final Joystick mechStick = new Joystick(1);
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    mecanumDriveSubsystem.setDefaultCommand(
        new RunCommand(() -> 
            mecanumDriveSubsystem.mecDrive(driveStick.getRawAxis(JoystickConstants.leftYAxis), driveStick.getRawAxis(JoystickConstants.rightYAxis), driveStick.getRawAxis(JoystickConstants.leftXAxis), driveStick.getRawAxis(JoystickConstants.rightXAxis)), mecanumDriveSubsystem));
            
           
    configureButtonBindings();
    
    
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    
    // new JoystickButton(driveStick, JoystickConstants.buttonX)
    // .whenHeld(new ShootCommand(shooter, intake));

    new Button(() -> mechStick.getRawAxis(JoystickConstants.rightTrigger) > 0.3)
    .whenPressed(new InstantCommand(() -> {
      shooter.shoot(this.shootPower);
    }))
    .whenReleased(new InstantCommand(() -> {
      shooter.stop();
    }));

    new Button(() -> -1 * mechStick.getRawAxis(JoystickConstants.leftYAxis) > 0.3)
    .whenPressed(new InstantCommand(() -> {
      this.shootPower *= 1.5;
    }))
    .whenReleased(new InstantCommand(() -> {
      this.shootPower = this.shootPower;
    }));

    new Button(() -> -1 * mechStick.getRawAxis(JoystickConstants.leftYAxis) < -0.3)
    .whenPressed(new InstantCommand(() -> {
      this.shootPower *= 0.75;
    }))
    .whenReleased(new InstantCommand(() -> {
      this.shootPower = this.shootPower;
    }));

    new Button(() -> mechStick.getRawAxis(JoystickConstants.leftTrigger) > 0.3)
    .whenPressed(new InstantCommand(() -> {
      shooter.feed(0.5);
    }))
    .whenReleased(new InstantCommand(() -> {
      shooter.feed(0.0);
    }));

    new JoystickButton(mechStick, JoystickConstants.buttonX)
    .whenPressed(new InstantCommand(() -> 
    {
      this.intake.gather(0.2);
    }))
    .whenReleased(new InstantCommand(() ->
    {
      this.intake.gather(0.0);
    }));

    new JoystickButton(mechStick, JoystickConstants.buttonY)
    .whenPressed(new InstantCommand(() -> 
    {
      double delta = this.intake.getStartPosition();
      this.intake.lift(1.0);
    }))
    .whenReleased(new InstantCommand(() ->
    {
      double delta = this.intake.getStartPosition();
      this.intake.lift(0.0);
    }));

    new JoystickButton(mechStick, JoystickConstants.buttonA)
    .whenPressed(new InstantCommand(() -> 
    {
      double delta = this.intake.getStartPosition();
      this.intake.lift(-1.0);
    }))
    .whenReleased(new InstantCommand(() ->
    {
      double delta = this.intake.getStartPosition();
      this.intake.lift(0.0);
    }));

    new JoystickButton(mechStick, JoystickConstants.buttonLeftBumper)
    .whenPressed(new InstantCommand(() -> {
      grabber.moveToPosition(0.05);
    }))
    .whenReleased(new InstantCommand(() -> {
      grabber.stop();
    }));

    new JoystickButton(mechStick, JoystickConstants.buttonRightBumper)
    .whenPressed(new InstantCommand(() -> {
      grabber.moveToPosition(-0.05);
    }))
    .whenReleased(new InstantCommand(() -> {
      grabber.stop();
    }));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return basicAutoCommand;
  }
}
