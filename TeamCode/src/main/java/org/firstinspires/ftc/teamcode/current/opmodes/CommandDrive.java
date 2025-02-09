package org.firstinspires.ftc.teamcode.current.opmodes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

import org.firstinspires.ftc.teamcode.current.commands.DriverRelativeDrive;
import org.firstinspires.ftc.teamcode.current.subsytems.Arm2025;
import org.firstinspires.ftc.teamcode.current.subsytems.Lift2025;
import org.firstinspires.ftc.teamcode.shared.mecanum.MecanumConfigs;
import org.firstinspires.ftc.teamcode.shared.mecanum.MecanumDrive;
import org.firstinspires.ftc.teamcode.shared.util.CommandGamepad;

public class CommandDrive extends CommandOpMode {
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
        m_operator = new CommandGamepad(gamepad2, 0, 0);
        m_mecanumDrive.setDefaultCommand(new DriverRelativeDrive(m_mecanumDrive, m_driver));

        // Further commands go under this line

    }
}
