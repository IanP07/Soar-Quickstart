package org.firstinspires.ftc.teamcode.current.autos;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.current.commands.BlueSampleCommandRunner;
import org.firstinspires.ftc.teamcode.current.subsytems.Arm2025;
import org.firstinspires.ftc.teamcode.current.subsytems.Lift2025;
import org.firstinspires.ftc.teamcode.shared.mecanum.MecanumConfigs;
import org.firstinspires.ftc.teamcode.shared.mecanum.MecanumDrive;

@Autonomous
public class BlueSampleAuto extends CommandOpMode {

    private MecanumDrive m_mecanumDrive;
    private Arm2025 m_armSubsystem;
    private Lift2025 m_liftSubsystem;

    @Override
    public void initialize() {
        MecanumConfigs mecanumConfigs = new MecanumConfigs().runMode(Motor.RunMode.RawPower);
        // Pose is set to Blue alliance, closer to yellow samples. Control click https://screamingeagles2025.netlify.app/ to see exactly where it's positioned
        m_mecanumDrive = new MecanumDrive(hardwareMap, mecanumConfigs, new Pose2d(76.6, 159.3, Rotation2d.fromDegrees(270)), MecanumDrive.Alliance.BLUE); // y was 159.3
        m_armSubsystem = new Arm2025(hardwareMap);
        m_liftSubsystem = new Lift2025(hardwareMap);


        CommandScheduler.getInstance().schedule(  new BlueSampleCommandRunner(m_mecanumDrive, m_armSubsystem, m_liftSubsystem));
    }
}
