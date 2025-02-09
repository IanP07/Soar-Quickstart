package org.firstinspires.ftc.teamcode.current.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.current.subsytems.Arm2025;


public class WristCommand extends CommandBase {

    public enum WristPosition {
        FOLDED_IN,
        FOLDED_OUT
    }

    private Arm2025 m_armSubsystem;
    private WristPosition m_wristPosition;

    public WristCommand(Arm2025 armSubsystem, WristPosition wristPosition) {
        m_wristPosition = wristPosition;
        m_armSubsystem = armSubsystem;

        addRequirements(m_armSubsystem);
    }

    @Override
    public void initialize() {
        switch (m_wristPosition) {
            case FOLDED_IN:
                m_armSubsystem.setWristPosition(m_armSubsystem.getWRIST_FOLDED_IN());
                break;
            case FOLDED_OUT:
                m_armSubsystem.setWristPosition(m_armSubsystem.getWRIST_FOLDED_OUT());
                break;
        }
    }

}
