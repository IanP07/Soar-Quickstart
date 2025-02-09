package org.firstinspires.ftc.teamcode.current.commands;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;

import org.firstinspires.ftc.teamcode.current.subsytems.Arm2025;
import org.firstinspires.ftc.teamcode.current.subsytems.Lift2025;
import org.firstinspires.ftc.teamcode.shared.mecanum.MecanumDrive;

public class BlueSampleCommandRunner extends SequentialCommandGroup {

    private MecanumDrive m_mecanumDrive;
    private Arm2025 m_armSubsystem;
    private Lift2025 m_liftSubsystem;

    public BlueSampleCommandRunner(MecanumDrive mecanum2025, Arm2025 armSubsystem, Lift2025 liftSubsystem) {
        m_mecanumDrive = mecanum2025;
        m_armSubsystem = armSubsystem;
        m_liftSubsystem = liftSubsystem;

        // Starting Position:
        addCommands(
                // SCORING PRELOADED SAMPLE
                new ArmCommand(m_armSubsystem, ArmCommand.ArmPosition.ARM_CLEAR_BARRIER).withTimeout(1),

                new ArmCommand(m_armSubsystem, m_liftSubsystem, ArmCommand.ArmPosition.RIGHT_BUMPER_PRESSED).withTimeout(1), // ensure the arm stays put
                // Hooks preloaded specimen onto bar
                new ParallelCommandGroup(
                        new ArmCommand(m_armSubsystem, ArmCommand.ArmPosition.ARM_SCORE_SAMPLE_IN_LOW).withTimeout(1500),
                        new DriveToPosition(m_mecanumDrive, new Pose2d(124, 124, Rotation2d.fromDegrees(215))).withTimeout(2700)    // moves to basket
                ),
                new ArmCommand(m_armSubsystem, m_liftSubsystem, ArmCommand.ArmPosition.LEFT_BUMPER_PRESSED).withTimeout(700),

                new IntakeCommand(m_armSubsystem, IntakeCommand.IntakeSetting.INTAKE_DEPSOSIT).withTimeout(700),

                new DriveToPosition(m_mecanumDrive, new Pose2d(110, 115, Rotation2d.fromDegrees(215))).withTimeout(200),
                new ArmCommand(m_armSubsystem, m_liftSubsystem, ArmCommand.ArmPosition.RIGHT_BUMPER_PRESSED).withTimeout(500),  // retracts arm
                new IntakeCommand(m_armSubsystem, IntakeCommand.IntakeSetting.INTAKE_COLLECT).withTimeout(1), // intakes second sample




                // SCORING SECOND SECOND SAMPLE
                new ParallelCommandGroup(
                        new DriveToPosition(m_mecanumDrive, new Pose2d(53, 109, Rotation2d.fromDegrees(140))).withTimeout(2000) // was 2200  // moves to second sample
                ),
                new ArmCommand(m_armSubsystem, ArmCommand.ArmPosition.ARM_COLLECT).withTimeout(900), // was 1200 // collects second sample
                new DriveToPosition(m_mecanumDrive, new Pose2d(67, 97, Rotation2d.fromDegrees(142))).withTimeout(2000), // was 2500  // moves to second sample

                new ParallelCommandGroup(
                    new ArmCommand(m_armSubsystem, ArmCommand.ArmPosition.ARM_SCORE_SAMPLE_IN_LOW).withTimeout(1000), // was 1500
                    new DriveToPosition(m_mecanumDrive, new Pose2d(124, 124, Rotation2d.fromDegrees(215))).withTimeout(2500)    // moves to basket
                ),
                new ArmCommand(m_armSubsystem, m_liftSubsystem, ArmCommand.ArmPosition.LEFT_BUMPER_PRESSED).withTimeout(700),
                new IntakeCommand(m_armSubsystem, IntakeCommand.IntakeSetting.INTAKE_DEPSOSIT).withTimeout(800),

                new DriveToPosition(m_mecanumDrive, new Pose2d(110, 115, Rotation2d.fromDegrees(215))).withTimeout(200), // was 300
                new ArmCommand(m_armSubsystem, m_liftSubsystem, ArmCommand.ArmPosition.RIGHT_BUMPER_PRESSED).withTimeout(700),  // retracts arm




            // SCORING THIRD SAMPLE
            new ParallelCommandGroup(
                new IntakeCommand(m_armSubsystem, IntakeCommand.IntakeSetting.INTAKE_COLLECT).withTimeout(1), // intakes third sample
                new DriveToPosition(m_mecanumDrive, new Pose2d(65, 90, Rotation2d.fromDegrees(155))).withTimeout(2200)   // moves near third sample
            ),
            new ArmCommand(m_armSubsystem, ArmCommand.ArmPosition.ARM_COLLECT).withTimeout(900),  // was 1200 // collects second sample
                new DriveToPosition(m_mecanumDrive, new Pose2d(80, 75, Rotation2d.fromDegrees(155))).withTimeout(2200),   // collects third sample

                new ParallelCommandGroup(
                        new ArmCommand(m_armSubsystem, ArmCommand.ArmPosition.ARM_SCORE_SAMPLE_IN_LOW).withTimeout(1000), // was 1500
                        new DriveToPosition(m_mecanumDrive, new Pose2d(124, 124, Rotation2d.fromDegrees(215))).withTimeout(2500)    // moves to basket
                ),

                new ArmCommand(m_armSubsystem, m_liftSubsystem, ArmCommand.ArmPosition.LEFT_BUMPER_PRESSED).withTimeout(700),
                new IntakeCommand(m_armSubsystem, IntakeCommand.IntakeSetting.INTAKE_DEPSOSIT).withTimeout(700),




//            // SCORING FOURTH SAMPLE
//
//                new DriveToPosition(m_mecanumDrive, new Pose2d(110, 115, Rotation2d.fromDegrees(215))).withTimeout(200),  // backs away from basket
//                new ArmCommand(m_armSubsystem, m_liftSubsystem, ArmCommand.ArmPosition.RIGHT_BUMPER_PRESSED).withTimeout(500),  // retracts arm
//
//
//                new ParallelCommandGroup(
//                    new IntakeCommand(m_armSubsystem, IntakeCommand.IntakeSetting.INTAKE_COLLECT).withTimeout(1), // intakes fourth sample
//                    new DriveToPosition(m_mecanumDrive, new Pose2d(80, 50, Rotation2d.fromDegrees(180))).withTimeout(2200)   // moves near fourth sample
//            ),
//                new ArmCommand(m_armSubsystem, ArmCommand.ArmPosition.ARM_COLLECT).withTimeout(1000), // was 1500
//                new DriveToPosition(m_mecanumDrive, new Pose2d(100, 50, Rotation2d.fromDegrees(170))).withTimeout(2000),   // collects fourth sample
//
//        new ParallelCommandGroup(
//                new ArmCommand(m_armSubsystem, ArmCommand.ArmPosition.ARM_SCORE_SAMPLE_IN_LOW).withTimeout(1500),
//                new DriveToPosition(m_mecanumDrive, new Pose2d(124, 125, Rotation2d.fromDegrees(215))).withTimeout(2800)    // moves to basket
//        ),
//                new ArmCommand(m_armSubsystem, m_liftSubsystem, ArmCommand.ArmPosition.LEFT_BUMPER_PRESSED).withTimeout(700),
//                new IntakeCommand(m_armSubsystem, IntakeCommand.IntakeSetting.INTAKE_DEPSOSIT).withTimeout(700),
//
//                new DriveToPosition(m_mecanumDrive, new Pose2d(110, 115, Rotation2d.fromDegrees(213))).withTimeout(300),
//                new ArmCommand(m_armSubsystem, m_liftSubsystem, ArmCommand.ArmPosition.RIGHT_BUMPER_PRESSED).withTimeout(700)  // retracts arm


                new DriveToPosition(m_mecanumDrive, new Pose2d(110, 115, Rotation2d.fromDegrees(215))).withTimeout(200),  // backs away from basket
                new ArmCommand(m_armSubsystem, m_liftSubsystem, ArmCommand.ArmPosition.RIGHT_BUMPER_PRESSED).withTimeout(400),  // retracts arm

                new DriveToPosition(m_mecanumDrive, new Pose2d(90, 29.7, Rotation2d.fromDegrees(0))).withTimeout(1500),  // moves close to bar
                new DriveToPosition(m_mecanumDrive, new Pose2d(45, 29.7, Rotation2d.fromDegrees(4))).withTimeout(2250)  // rotates to face bar
                
        );
    }

}
