package org.firstinspires.ftc.teamcode.current.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.current.subsytems.Arm2025;

public class IntakeCommand extends CommandBase {

    public enum IntakeSetting {
        INTAKE_COLLECT,

        INTAKE_DEPSOSIT,

        INTAKE_OFF
    }

    public Arm2025 m_armSubsystem;
    public IntakeSetting m_intakeSetting;

    public IntakeCommand(Arm2025 armSubsystem, IntakeSetting intakeSetting) {
            m_armSubsystem = armSubsystem;
            m_intakeSetting = intakeSetting;

            addRequirements(m_armSubsystem);
    }

  public void initialize() {
        switch (m_intakeSetting) {
            case INTAKE_COLLECT:
                m_armSubsystem.setIntake(m_armSubsystem.getINTAKE_COLLECT());
                break;

            case INTAKE_OFF:
                m_armSubsystem.setIntake(m_armSubsystem.getINTAKE_OFF());
                break;

            case INTAKE_DEPSOSIT:
                m_armSubsystem.setIntake(m_armSubsystem.getINTAKE_DEPOSIT());
                break;

        }
  }

}
