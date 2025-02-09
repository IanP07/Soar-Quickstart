package org.firstinspires.ftc.teamcode.current.opmodes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.current.commands.ArmCommand;
import org.firstinspires.ftc.teamcode.current.commands.DriverRelativeDrive;
import org.firstinspires.ftc.teamcode.current.commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.current.subsytems.Arm2025;
import org.firstinspires.ftc.teamcode.current.subsytems.Lift2025;
import org.firstinspires.ftc.teamcode.shared.mecanum.MecanumConfigs;
import org.firstinspires.ftc.teamcode.shared.mecanum.MecanumDrive;
import org.firstinspires.ftc.teamcode.shared.util.CommandGamepad;

@TeleOp()
public class CommandDriveAndArm2025 extends CommandOpMode {

    private MecanumDrive m_mecanumDrive;
    private CommandGamepad m_driver;
    private CommandGamepad m_operator;
    private Arm2025 armSubsystem;
    private Lift2025 liftSubsystem;
    @Override
    public void initialize() {

        MecanumConfigs configs = new MecanumConfigs().runMode(MotorEx.RunMode.RawPower);

        m_mecanumDrive = new MecanumDrive(hardwareMap, configs, new Pose2d(42.38, 161, Rotation2d.fromDegrees(270)), MecanumDrive.Alliance.BLUE);
        armSubsystem = new Arm2025(hardwareMap);
        liftSubsystem = new Lift2025(hardwareMap);

        m_driver = new CommandGamepad(gamepad1, 0.2, 0.2);
        m_operator = new CommandGamepad(gamepad2, 0,0);
        m_mecanumDrive.setDefaultCommand(new DriverRelativeDrive(m_mecanumDrive, m_driver));

        // Driver Commands
        m_driver.buttonA().whenPressed(new InstantCommand(() -> m_mecanumDrive.resetHeading()));


        // Operator Commands
        m_operator.buttonA().whenPressed(new ArmCommand(armSubsystem, liftSubsystem, ArmCommand.ArmPosition.ARM_COLLECT));
        m_operator.buttonB().whenPressed(new ArmCommand(armSubsystem, liftSubsystem, ArmCommand.ArmPosition.ARM_CLEAR_BARRIER));
        m_operator.buttonX().whenPressed(new ArmCommand(armSubsystem, liftSubsystem, ArmCommand.ArmPosition.ARM_SCORE_SAMPLE_IN_LOW));

        m_driver.leftBumper().whenPressed((new IntakeCommand(armSubsystem, IntakeCommand.IntakeSetting.INTAKE_COLLECT)));
        m_driver.rightBumper().whenPressed(new IntakeCommand(armSubsystem, IntakeCommand.IntakeSetting.INTAKE_DEPSOSIT));

        m_operator.dpadUp().whenPressed(new ArmCommand(armSubsystem, liftSubsystem, ArmCommand.ArmPosition.ARM_SCORE_SPECIMEN));
        m_operator.dpadDown().whenPressed(new ArmCommand(armSubsystem, liftSubsystem, ArmCommand.ArmPosition.ARM_COLLAPSED_INTO_ROBOT));
        m_operator.buttonY().whenPressed(new IntakeCommand(armSubsystem, IntakeCommand.IntakeSetting.INTAKE_OFF));

        m_operator.leftBumper().whenPressed(new ArmCommand(armSubsystem, liftSubsystem, ArmCommand.ArmPosition.LEFT_BUMPER_PRESSED, m_operator));
        m_operator.rightBumper().whenPressed(new ArmCommand(armSubsystem, liftSubsystem, ArmCommand.ArmPosition.RIGHT_BUMPER_PRESSED, m_operator));

//        m_operator.getrightTriggerActive().whileActiveContinuous(new ArmCommand(armSubsystem, liftSubsystem, ArmCommand.ArmPosition.LEFT_TRIGGER_PRESSED, m_operator));
//        m_operator.getrightTriggerActive().whileActiveContinuous(new ArmCommand(armSubsystem, liftSubsystem, ArmCommand.ArmPosition.RIGHT_TRIGGER_PRESSED, m_operator));


    }
}
