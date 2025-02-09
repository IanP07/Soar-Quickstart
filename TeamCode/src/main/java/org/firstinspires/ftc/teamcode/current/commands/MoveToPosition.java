package org.firstinspires.ftc.teamcode.current.commands;


import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.shared.mecanum.MecanumDrive;


/**   This is very similar to the DriveToPosition command, however it lets you manually set the velocity yourself instead
 *    of it manually changing. Can be useful in certain niche circumstances
 **/
public class MoveToPosition extends CommandBase {
    private MecanumDrive m_mecanumDrive;
    private Pose2d m_targetPose;

    public MoveToPosition(MecanumDrive mecanumDrive, Pose2d targetPose) {
        m_mecanumDrive = mecanumDrive;
        m_targetPose = targetPose;

        addRequirements(m_mecanumDrive);
    }

    @Override
    public void initialize() {
        m_mecanumDrive.setTargetPose(m_targetPose);
    }

    @Override
    public void execute() {
        m_mecanumDrive.moveAllianceRelative(0.5,0.5,0.5);
    }

    public boolean isFinished() {
        return m_mecanumDrive.atTargetPose();
    }

    public void end(boolean interrupted) {
        m_mecanumDrive.stop();
    }
}
