package org.firstinspires.ftc.teamcode.current.commands;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;

import org.firstinspires.ftc.teamcode.current.subsytems.Arm2025;
import org.firstinspires.ftc.teamcode.shared.mecanum.MecanumDrive;

public class BlueSpecimenCommandRunner extends SequentialCommandGroup {

    public Arm2025 m_armSubsystem;
    public MecanumDrive m_mecanumDrive;

    public BlueSpecimenCommandRunner(MecanumDrive mecanum2025, Arm2025 armSubsystem) {

        m_mecanumDrive = mecanum2025;
        m_armSubsystem = armSubsystem;



        addCommands(
                new ArmCommand(m_armSubsystem, ArmCommand.ArmPosition.ARM_COLLECT).withTimeout(1),
                new IntakeCommand(m_armSubsystem, IntakeCommand.IntakeSetting.INTAKE_COLLECT).withTimeout(1),

                // Hooks preloaded specimen onto bar
                new ParallelCommandGroup(
                        new ArmCommand(m_armSubsystem, ArmCommand.ArmPosition.ARM_SCORE_SPECIMEN).withTimeout(1000),
                        new DriveToPosition(m_mecanumDrive, new Pose2d(3, 102, Rotation2d.fromDegrees(90))).withTimeout(1450)   // was 1750
                ),

                // Deposits preloaded specimen onto bar
                new DriveToPosition(m_mecanumDrive, new Pose2d(3, 135, Rotation2d.fromDegrees(90))).withTimeout(600),

                new ParallelCommandGroup(
                        new IntakeCommand(m_armSubsystem, IntakeCommand.IntakeSetting.INTAKE_DEPSOSIT).withTimeout(600),    // was 900
                        new DriveToPosition(m_mecanumDrive, new Pose2d(3, 135, Rotation2d.fromDegrees(90))).withTimeout(600)
                ),

                new ParallelCommandGroup(
                        new DriveToPosition(m_mecanumDrive, new Pose2d(3, 135, Rotation2d.fromDegrees(90))).withTimeout(400)
                ),

                new IntakeCommand(m_armSubsystem, IntakeCommand.IntakeSetting.INTAKE_OFF).withTimeout(1),

                // Drives next to 3 pre-placed specimen
                new ParallelCommandGroup(
                        new DriveToPosition(m_mecanumDrive, new Pose2d(-98 , 87, Rotation2d.fromDegrees(270))).withTimeout(1450),
                        new ArmCommand(m_armSubsystem, ArmCommand.ArmPosition.ARM_COLLAPSED_INTO_ROBOT).withTimeout(1000)
                ),


                // Pushes second specimen into HP zone
                new DriveToPosition(m_mecanumDrive, new Pose2d(-98, 20, Rotation2d.fromDegrees(270))).withTimeout(500),
                new DriveToPosition(m_mecanumDrive, new Pose2d(-125, 20, Rotation2d.fromDegrees(270))).withTimeout(800),
                new DriveToPosition(m_mecanumDrive, new Pose2d(-125, 157, Rotation2d.fromDegrees(270))).withTimeout(1000), // was 1250
//                new DriveToPosition(m_mecanumDrive, new Pose2d(-133, 100, Rotation2d.fromDegrees(270))).withTimeout(500),

                // Pushes Third Specimen into HP Zone
                new DriveToPosition(m_mecanumDrive, new Pose2d(500, 100, Rotation2d.fromDegrees(270))).withTimeout(170) ,
                new DriveToPosition(m_mecanumDrive, new Pose2d(-140, 20, Rotation2d.fromDegrees(270))).withTimeout(1000),
                new DriveToPosition(m_mecanumDrive, new Pose2d(-152, 20, Rotation2d.fromDegrees(270))).withTimeout(900),
                new DriveToPosition(m_mecanumDrive, new Pose2d(-152, 158, Rotation2d.fromDegrees(270))).withTimeout(1000),
//                new DriveToPosition(m_mecanumDrive, new Pose2d(-155, 140, Rotation2d.fromDegrees(270))).withTimeout(3000),

                // Pushes Fourth Specimen into HP Zone
//                new DriveToPosition(m_mecanumDrive, new Pose2d(500, 140, Rotation2d.fromDegrees(270))).withTimeout(150),
//                new DriveToPosition(m_mecanumDrive, new Pose2d(-152, 20, Rotation2d.fromDegrees(270))).withTimeout(2000),
//                new DriveToPosition(m_mecanumDrive, new Pose2d(-158, 20, Rotation2d.fromDegrees(270))).withTimeout(2000),
//                new DriveToPosition(m_mecanumDrive, new Pose2d(-158, 140, Rotation2d.fromDegrees(270))).withTimeout(2000),
//                new DriveToPosition(m_mecanumDrive, new Pose2d(-168, 140, Rotation2d.fromDegrees(270))).withTimeout(3000),

                // Moves to collect Second specimen
                new IntakeCommand(m_armSubsystem, IntakeCommand.IntakeSetting.INTAKE_COLLECT).withTimeout(1),

                new ParallelCommandGroup(
                        new ArmCommand(m_armSubsystem, ArmCommand.ArmPosition.ARM_COLLECT).withTimeout(1900),

                        // Grabbing Time, Don't Touch
                        new DriveToPosition(m_mecanumDrive, new Pose2d(-133, 85, Rotation2d.fromDegrees(270))).withTimeout(2000)
                ),

                // Actually collects second specimen
                new DriveToPosition(m_mecanumDrive, new Pose2d(-133, 128, Rotation2d.fromDegrees(270))).withTimeout(1800),

                // Moves and rotates next to bar
                //new ParallelCommandGroup(
                        new ArmCommand(m_armSubsystem, ArmCommand.ArmPosition.ARM_SCORE_SPECIMEN).withTimeout(1500),
                        new DriveToPosition(m_mecanumDrive, new Pose2d(0, 150, Rotation2d.fromDegrees(90))).withTimeout(2300), // was 2000
                //),

                // Hanging second specimen
                new IntakeCommand(m_armSubsystem, IntakeCommand.IntakeSetting.INTAKE_COLLECT).withTimeout(1),
                new DriveToPosition(m_mecanumDrive, new Pose2d(0, 100, Rotation2d.fromDegrees(90))).withTimeout(1500),     // was 1100
                new DriveToPosition(m_mecanumDrive, new Pose2d(0, 160, Rotation2d.fromDegrees(90))).withTimeout(1050),      // was 800
                new IntakeCommand(m_armSubsystem, IntakeCommand.IntakeSetting.INTAKE_DEPSOSIT).withTimeout(1),


                // Backs up after hanging second specimen
                // Moves to collect third specimen
                new DriveToPosition(m_mecanumDrive, new Pose2d(0, 161, Rotation2d.fromDegrees(90))).withTimeout(200),      // needed to backup from the bar

                new ParallelCommandGroup(
                        new DriveToPosition(m_mecanumDrive, new Pose2d(-133, 88, Rotation2d.fromDegrees(270))).withTimeout(2550)  // timeout can be reduced
                ),
        // Grabbing Time, Don't Touch
//                        new DriveToPosition(m_mecanumDrive, new Pose2d(-133, 93, Rotation2d.fromDegrees(270))).withTimeout(1000)


                new IntakeCommand(m_armSubsystem, IntakeCommand.IntakeSetting.INTAKE_COLLECT).withTimeout(1),
                new ArmCommand(m_armSubsystem, ArmCommand.ArmPosition.ARM_COLLECT).withTimeout(800),

                // Actually collects third specimen
                new ParallelCommandGroup(
                        new DriveToPosition(m_mecanumDrive, new Pose2d(-133, 128, Rotation2d.fromDegrees(270))).withTimeout(2100)
                ),



                // Moves and rotates next to bar
                new ParallelCommandGroup(
                        new ArmCommand(m_armSubsystem, ArmCommand.ArmPosition.ARM_SCORE_SPECIMEN).withTimeout(1800),
                        new DriveToPosition(m_mecanumDrive, new Pose2d(-20, 155, Rotation2d.fromDegrees(90))).withTimeout(2000)//1750
                ),
//                new DriveToPosition(m_mecanumDrive, new Pose2d(-20, 140, Rotation2d.fromDegrees(90))).withTimeout(300),

                // Hooks third specimen
                new IntakeCommand(m_armSubsystem, IntakeCommand.IntakeSetting.INTAKE_COLLECT).withTimeout(1),
                new DriveToPosition(m_mecanumDrive, new Pose2d(-20, 100, Rotation2d.fromDegrees(90))).withTimeout(1500),
                new DriveToPosition(m_mecanumDrive, new Pose2d(-20, 150, Rotation2d.fromDegrees(90))).withTimeout(1000),
                new IntakeCommand(m_armSubsystem, IntakeCommand.IntakeSetting.INTAKE_DEPSOSIT).withTimeout(1),


                // Puts down arm to get ready for teleop mode
                new ParallelCommandGroup(
                        new DriveToPosition(m_mecanumDrive, new Pose2d(-42.38, 161, Rotation2d.fromDegrees(90))).withTimeout(3000),
                        new ArmCommand(m_armSubsystem, ArmCommand.ArmPosition.ARM_COLLAPSED_INTO_ROBOT).withTimeout(1200)
                )

        );
    }


}
