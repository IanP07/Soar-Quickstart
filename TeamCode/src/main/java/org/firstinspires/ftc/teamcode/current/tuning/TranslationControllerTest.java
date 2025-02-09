package org.firstinspires.ftc.teamcode.current.tuning;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.current.commands.DriveToPosition;
import org.firstinspires.ftc.teamcode.shared.mecanum.MecanumConfigs;
import org.firstinspires.ftc.teamcode.shared.mecanum.MecanumDrive;
import org.firstinspires.ftc.teamcode.shared.util.CommandGamepad;

@TeleOp
public class TranslationControllerTest extends CommandOpMode {

    private MecanumDrive m_mecanumDrive;
    private CommandGamepad m_driver;
    @Override
    public void initialize() {
        MecanumConfigs configs = new MecanumConfigs().runMode(MotorEx.RunMode.RawPower);
        m_mecanumDrive = new MecanumDrive(hardwareMap, configs, new Pose2d(0, 0, Rotation2d.fromDegrees(0)), MecanumDrive.Alliance.RED);
        m_driver = new CommandGamepad(gamepad1, 0.2, 0.2);

        // Dy default, drives 100cm forward when A button is pressed, and back when B button is pressed. A higher value will usually lead to more accurate results.
        m_driver.buttonA().whenPressed(new DriveToPosition(m_mecanumDrive, new Pose2d(100, 0, Rotation2d.fromDegrees(0))));
        m_driver.buttonB().whenPressed(new DriveToPosition(m_mecanumDrive, new Pose2d(0, 0, Rotation2d.fromDegrees(0))));


        TelemetryPacket translationPacket = new TelemetryPacket();
        translationPacket.put("Current Position: ", m_mecanumDrive.getPose());
        translationPacket.put("Target Position: ", m_mecanumDrive.getTargetPose());

        FtcDashboard.getInstance().sendTelemetryPacket(translationPacket);
    }

}
