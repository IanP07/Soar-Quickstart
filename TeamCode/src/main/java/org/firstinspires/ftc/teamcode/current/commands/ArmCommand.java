package org.firstinspires.ftc.teamcode.current.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.current.subsytems.Arm2025;
import org.firstinspires.ftc.teamcode.current.subsytems.Lift2025;
import org.firstinspires.ftc.teamcode.shared.util.CommandGamepad;


public class ArmCommand extends CommandBase {
    public enum ArmPosition {
        ARM_COLLAPSED_INTO_ROBOT,
        ARM_COLLECT,
        ARM_CLEAR_BARRIER,
        ARM_SCORE_SPECIMEN,
        ARM_SCORE_SAMPLE_IN_LOW,
        ARM_ATTACH_HANGING_HOOK,
        ARM_WINCH_ROBOT,

        LEFT_TRIGGER_PRESSED,

        RIGHT_BUMPER_PRESSED,

        LEFT_BUMPER_PRESSED,

        RIGHT_TRIGGER_PRESSED

    }

    private Arm2025 m_armSubsystem;
    private Lift2025 m_liftSubsystem;
    private ArmPosition m_armPosition;

    private CommandGamepad m_operator;

    public ArmCommand(Arm2025 armSubsystem, Lift2025 liftSubsystem, ArmPosition armPosition) {
        m_armPosition = armPosition;
        m_armSubsystem = armSubsystem;
        m_liftSubsystem = liftSubsystem;
        addRequirements(m_armSubsystem);
        // TODO See if the line above is actually needed

    }

    public ArmCommand(Arm2025 armSubsystem, ArmPosition armPosition) {
        m_armPosition = armPosition;
        m_armSubsystem = armSubsystem;
        addRequirements(m_armSubsystem);
        // TODO See if the line above is actually needed

    }

    public ArmCommand(Arm2025 armSubsystem, Lift2025 liftSubsystem, ArmPosition armPosition, CommandGamepad operator) {
        m_armPosition = armPosition;
        m_liftSubsystem = liftSubsystem;
        m_armSubsystem = armSubsystem;
        m_liftSubsystem = liftSubsystem;
        m_operator = operator;

        addRequirements(m_armSubsystem);
    }

    public void initialize() {
        switch (m_armPosition) {
            case ARM_COLLAPSED_INTO_ROBOT:
//                m_armSubsystem.setLiftPosition(m_armSubsystem.getLIFT_COLLAPSED());
                m_armSubsystem.setArmPosition(m_armSubsystem.getARM_COLLAPSED_INTO_ROBOT());
                m_armSubsystem.setWristPosition(m_armSubsystem.getWRIST_FOLDED_IN());
                break;

            case ARM_COLLECT:
//                m_armSubsystem.setLiftPosition(m_armSubsystem.getLIFT_COLLAPSED());
                m_armSubsystem.setArmPosition(m_armSubsystem.getARM_COLLECT());
                m_armSubsystem.setWristPosition(m_armSubsystem.getWRIST_FOLDED_OUT());
                break;

            case ARM_CLEAR_BARRIER:
//                m_armSubsystem.setLiftPosition(m_armSubsystem.getLIFT_COLLAPSED());
                m_armSubsystem.setArmPosition(m_armSubsystem.getARM_CLEAR_BARRIER());
                m_armSubsystem.setWristPosition(m_armSubsystem.getWRIST_FOLDED_OUT());
                break;

            case ARM_SCORE_SPECIMEN:
//                m_armSubsystem.setLiftPosition(m_armSubsystem.getLIFT_COLLAPSED());
                m_armSubsystem.setArmPosition(m_armSubsystem.getARM_SCORE_SPECIMEN());
                m_armSubsystem.setWristPosition(m_armSubsystem.getWRIST_FOLDED_IN());
                break;

            case ARM_SCORE_SAMPLE_IN_LOW:
                // TODO when working rename to ARM_SCORE_SAMPLE_IN_HIGH
                m_armSubsystem.setArmPosition(m_armSubsystem.getARM_SCORE_SAMPLE_IN_LOW());
                m_armSubsystem.setWristPosition(m_armSubsystem.getWRIST_FOLDED_OUT());
                break;

            case ARM_ATTACH_HANGING_HOOK:
                m_armSubsystem.setArmPosition(m_armSubsystem.getARM_ATTACH_HANGING_HOOK());
                break;

            case LEFT_BUMPER_PRESSED:
                m_liftSubsystem.moveLift(Lift2025.Lift2025Params.LIFT_MAX);
                break;

            case RIGHT_BUMPER_PRESSED:
                m_liftSubsystem.moveLift(Lift2025.Lift2025Params.LIFT_COLLAPSED);
                break;

        }

    }

}
