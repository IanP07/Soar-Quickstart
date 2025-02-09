package org.firstinspires.ftc.teamcode.shared.mecanum;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Translation2d;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.ChassisSpeeds;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.MecanumDriveKinematics;
import com.arcrobotics.ftclib.kinematics.wpilibkinematics.MecanumDriveWheelSpeeds;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.current.util.HolonomicOdometry2025;
import org.firstinspires.ftc.teamcode.shared.util.MathUtil;

public class MecanumDrive extends SubsystemBase {

    public enum Alliance {
        RED, BLUE
    }

    @Config
    public static class BaseMecanumParams {
        // You can change these values on FTCDashboard
        public static double TranslationP = 0.035; // was 0.035
        public static double TranslationI = 0.0175; // was 0.0175
        public static double TranslationD = 0;
        public static double RotationP = 3; // was 3
        public static double RotationI = 0.0175; // was 0.0175
        public static double RotationD = 0;

    }


    protected MotorEx m_frontLeft, m_frontRight, m_backLeft, m_backRight;
    protected MecanumDriveKinematics m_kinematics;
    protected MecanumConfigs m_mecanumConfigs;
    protected Alliance m_alliance;

    public MecanumDrive m_mecanumDrive;

    // Mecanum Constants
    public double deadWheelRadiusCentimeters = 2.4;

    public double ticksPerRevolution = 2000.0;
    public double trackWidthCentimeters = 36.3;
    double perpendicularOffsetCentimeters = 20.32;

    public static double TranslationToleranceCentimeters = 0.5;
    public static double RotationToleranceRad = Math.toRadians(3); // 3 Deg

    private PIDController m_translationXController;
    private PIDController m_translationYController;
    private PIDController m_rotationController;

    private Pose2d m_robotPose;
    private Pose2d m_targetPose = new Pose2d(0, 0, Rotation2d.fromDegrees(0));

    private HolonomicOdometry2025 m_odo;

    private Rotation2d m_referenceRotation = Rotation2d.fromDegrees(0); // Used for heading


    public MecanumDrive(HardwareMap hardwareMap, MecanumConfigs mecanumConfigs, Pose2d initialPose, Alliance alliance) {
        m_mecanumConfigs = mecanumConfigs;
        m_frontLeft = new MotorEx(hardwareMap, m_mecanumConfigs.getFrontLeftName(), Motor.GoBILDA.RPM_312);
        m_frontRight = new MotorEx(hardwareMap, m_mecanumConfigs.getFrontRightName(), Motor.GoBILDA.RPM_312);
        m_backLeft = new MotorEx(hardwareMap, m_mecanumConfigs.getBackLeftName(), Motor.GoBILDA.RPM_312);
        m_backRight = new MotorEx(hardwareMap, m_mecanumConfigs.getBackRightName(), Motor.GoBILDA.RPM_312);

        m_frontLeft.setRunMode(m_mecanumConfigs.getRunMode());
        m_frontRight.setRunMode(m_mecanumConfigs.getRunMode());
        m_backLeft.setRunMode(m_mecanumConfigs.getRunMode());
        m_backRight.setRunMode(m_mecanumConfigs.getRunMode());

        m_kinematics = new MecanumDriveKinematics(m_mecanumConfigs.getFrontLeftPosition(), m_mecanumConfigs.getFrontRightPosition(),
                m_mecanumConfigs.getBackLeftPosition(), m_mecanumConfigs.getBackRightPosition());



        m_alliance = alliance;
        m_translationYController = new PIDController(0,0,0);
        m_translationXController = new PIDController(0,0,0);
        m_rotationController = new PIDController(0,0,0);


        m_frontLeft.setInverted(true);
        m_backLeft.setInverted(true);

        m_robotPose = initialPose;

        m_referenceRotation = initialPose.getRotation();

        if(m_alliance == Alliance.RED) {
            m_referenceRotation = Rotation2d.fromDegrees(90);
        } else {
            m_referenceRotation = Rotation2d.fromDegrees(-90);
        }

        double cm_per_tick = 2 * Math.PI * deadWheelRadiusCentimeters / ticksPerRevolution;
        Motor.Encoder left = m_frontRight.encoder.setDistancePerPulse(cm_per_tick);
        left.setDirection(Motor.Direction.REVERSE);
        Motor.Encoder right = m_frontLeft.encoder.setDistancePerPulse(cm_per_tick);
        right.setDirection(Motor.Direction.REVERSE);
        Motor.Encoder horizontal = m_backLeft.encoder.setDistancePerPulse(cm_per_tick);
        horizontal.setDirection(Motor.Direction.REVERSE);

        m_odo = new HolonomicOdometry2025(
                left::getDistance,
                right::getDistance,
                horizontal::getDistance,
                trackWidthCentimeters,
                perpendicularOffsetCentimeters
        );

        // m_odo is tracking heading / angle offset, so set its initial rotation to 0
        m_odo.updatePose(initialPose);


    }

    public Pose2d getPose() {
        return m_robotPose;
    }

    public Rotation2d getHeading() {
        return m_robotPose.getRotation().minus(m_referenceRotation);
    }

    public void resetHeading() {
        m_referenceRotation = m_robotPose.getRotation();
    }

    public boolean atTargetPose() {
        return (m_translationXController.atSetPoint() && m_translationYController.atSetPoint() && m_rotationController.atSetPoint());
    }
    public void setTargetPose(Pose2d targetPose) {
        m_targetPose = targetPose;
        m_translationXController.setSetPoint(m_targetPose.getX());
        m_translationYController.setSetPoint(m_targetPose.getY());

        double targetRotation = 0;
        if(m_targetPose.getHeading() < 0) {
            targetRotation = m_targetPose.getHeading() + 2 * Math.PI;
        } else {
            targetRotation = m_targetPose.getHeading();
        }

        m_rotationController.setSetPoint(targetRotation);
    }

    public Pose2d getTargetPose() {
        return m_targetPose;
    }

    public void resetPIDS() {
        m_translationXController.reset();
        m_translationYController.reset();
        m_rotationController.reset();
    }

    public void tunePIDS() {
        m_translationXController.setPID(BaseMecanumParams.TranslationP, BaseMecanumParams.TranslationI, BaseMecanumParams.TranslationD);
        m_translationYController.setPID(BaseMecanumParams.TranslationP, BaseMecanumParams.TranslationI, BaseMecanumParams.TranslationD);
        m_rotationController.setPID(BaseMecanumParams.RotationP, BaseMecanumParams.RotationI, BaseMecanumParams.RotationD);

        m_translationXController.setTolerance(TranslationToleranceCentimeters);
        m_translationYController.setTolerance(TranslationToleranceCentimeters);
        m_rotationController.setTolerance(RotationToleranceRad);
    }

    protected void move(ChassisSpeeds speeds) {
        MecanumDriveWheelSpeeds wheelSpeeds = m_kinematics.toWheelSpeeds(speeds);
        m_frontLeft.setVelocity(wheelSpeeds.frontLeftMetersPerSecond / m_mecanumConfigs.getMetersPerTick());
        m_frontRight.setVelocity(wheelSpeeds.frontRightMetersPerSecond / m_mecanumConfigs.getMetersPerTick());
        m_backLeft.setVelocity(wheelSpeeds.rearLeftMetersPerSecond / m_mecanumConfigs.getMetersPerTick());
        m_backRight.setVelocity(wheelSpeeds.rearRightMetersPerSecond / m_mecanumConfigs.getMetersPerTick());
    }



    /**
     * This function is used to move during autonomous. Most of the math is done with m_translation and m_rotation controllers,
     * through which a target posed is passed in earlier in the setTargetPose function. The calculate function then determines
     * the appropriate X, Y, and rotational velocities (forced to be in between max and negative max robot speed),
     * to move to the desired target position. This corrects for any error like overshoot and undershoot.
     * When the robot is in close enough proximity to the target position, which is determined by the tolerance values in the tunePIDS() function,
     * the robot stops. the move function converts these X, Y, and rotational speeds to individual wheel speeds, and is the last piece of code required
     * to make the robot move. It constantly generates new velocities based on the updating error from the target position.
     */
    public void moveFieldRelativeForPID() {
        // Uses the "error" to calculate appropriate speed, between min and max speed
        double vX = MathUtil.clamp(m_translationXController.calculate(m_robotPose.getX()),
                -m_mecanumConfigs.getMaxRobotSpeedMps(),
                m_mecanumConfigs.getMaxRobotSpeedMps());

        double vY = MathUtil.clamp(m_translationYController.calculate(m_robotPose.getY()),
                -m_mecanumConfigs.getMaxRobotSpeedMps(),
                m_mecanumConfigs.getMaxRobotSpeedMps());

        // Do some angle wrapping to ensure the shortest path is taken to get to the rotation target
        double normalizedRotationRad = m_robotPose.getHeading();
        if(normalizedRotationRad < 0) {
            normalizedRotationRad = m_robotPose.getHeading() + 2 * Math.PI; // Normalize to [0, 2PI], -PI becomes PI
        }

        double vOmega = MathUtil.clamp(m_rotationController.calculate(normalizedRotationRad),       // finds appropriate rotation velocity, again between min and max values
                -m_mecanumConfigs.getMaxRobotRotationRps(),
                m_mecanumConfigs.getMaxRobotRotationRps());

        TelemetryPacket motorVelocities = new TelemetryPacket();
        motorVelocities.put("vX: ", vX);
        motorVelocities.put("vY: ", vY);

        FtcDashboard motorVelocityPacket = FtcDashboard.getInstance();
        motorVelocityPacket.sendTelemetryPacket(motorVelocities);

        // Transform the x and y coordinates to account for differences between global field coordinates and driver field coordinates
        Translation2d transformedVelocities = fieldRelativeToAllianceRelative(new Translation2d(vX, vY));
        ChassisSpeeds speeds = ChassisSpeeds.fromFieldRelativeSpeeds(transformedVelocities.getX(),
                transformedVelocities.getY(), vOmega, getHeading());
        move(speeds);

    }




    /**
     * @param xPercentVelocity The forward velocity. Ranges from -1 to 1.
     * @param yPercentVelocity The leftward (from the driverstation) velocity. Ranges from -1 to 1.
     * @param omegaPercentVelocity The rotational velocity. Positive indicates cc rotation. Ranges from -1 to 1.
     */
    public void moveRobotRelative(double xPercentVelocity, double yPercentVelocity, double omegaPercentVelocity) {
        double vXMps = xPercentVelocity * m_mecanumConfigs.getMaxRobotSpeedMps();
        double vYMps = yPercentVelocity * m_mecanumConfigs.getMaxRobotSpeedMps();
        double omegaRps = omegaPercentVelocity * m_mecanumConfigs.getMaxRobotRotationRps();
        ChassisSpeeds speeds = new ChassisSpeeds(vXMps, vYMps, omegaRps);
        move(speeds);
    }


    public Translation2d fieldRelativeToAllianceRelative(Translation2d translation) {
        Translation2d transformedTranslation;
        if(m_alliance == Alliance.RED) {
            transformedTranslation = new Translation2d(translation.getY(), -translation.getX());
        } else {
            transformedTranslation = new Translation2d(-translation.getY(), translation.getX());
        }
        return transformedTranslation;
    }



    /**
     * @param xPercentVelocity The forward velocity. Ranges from -1 to 1.
     * @param yPercentVelocity The leftward (from the driverstation) velocity. Ranges from -1 to 1.
     * @param omegaPercentVelocity The rotational velocity. Positive indicates cc rotation. Ranges from -1 to 1.
     *
     *  Pass in left joystick y, negative joystick x, and negative right joystick x
     */
    public void moveAllianceRelative(double xPercentVelocity, double yPercentVelocity, double omegaPercentVelocity) {
        double vXMps = xPercentVelocity * m_mecanumConfigs.getMaxRobotSpeedMps();
        double vYMps = yPercentVelocity * m_mecanumConfigs.getMaxRobotSpeedMps();
        double omegaRps = omegaPercentVelocity * m_mecanumConfigs.getMaxRobotRotationRps();
        ChassisSpeeds speeds;

        speeds = ChassisSpeeds.fromFieldRelativeSpeeds(vXMps, vYMps, omegaRps, getHeading());

        TelemetryPacket heading = new TelemetryPacket();
        heading.put("heading", getHeading());

        FtcDashboard dashboard = FtcDashboard.getInstance();

        dashboard.sendTelemetryPacket(heading);

        move(speeds);
    }

    public void stop() {
        m_frontLeft.stopMotor();
        m_frontRight.stopMotor();
        m_backLeft.stopMotor();
        m_backRight.stopMotor();
    }


    // Run at 50hz, independent of other move functions, to generate Pose data from odo pods. m_robotPose is used in MoveFieldRelativeDrive.
    public void periodic() {
        tunePIDS();
        m_odo.updatePose();
        m_robotPose = m_odo.getPose();

    }


}
