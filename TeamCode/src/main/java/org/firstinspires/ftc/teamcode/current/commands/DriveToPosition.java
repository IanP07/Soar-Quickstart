package org.firstinspires.ftc.teamcode.current.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.shared.mecanum.MecanumDrive;

public class DriveToPosition extends CommandBase {
    private MecanumDrive m_mecanumDrive;
    private Pose2d m_targetPose;

    public DriveToPosition(MecanumDrive mecanumDrive, Pose2d targetPose) {
        m_mecanumDrive = mecanumDrive;
        m_targetPose = targetPose;

        addRequirements(m_mecanumDrive);
    }

    @Override
    public void initialize() {
        m_mecanumDrive.setTargetPose(m_targetPose);     // Puts targetPose into translationcontrollers
    }

    @Override
    public void execute() {
        m_mecanumDrive.moveFieldRelativeForPID();
    }

    public boolean isFinished() {
        return m_mecanumDrive.atTargetPose();
    }

    public void end(boolean interrupted) {
        m_mecanumDrive.resetPIDS();
        m_mecanumDrive.stop();
    }
}
