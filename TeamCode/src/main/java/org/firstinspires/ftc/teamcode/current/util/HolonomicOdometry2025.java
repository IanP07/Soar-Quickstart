package org.firstinspires.ftc.teamcode.current.util;


import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.arcrobotics.ftclib.geometry.Twist2d;
import com.arcrobotics.ftclib.kinematics.Odometry;

import java.util.function.DoubleSupplier;

public class HolonomicOdometry2025 extends Odometry {

    private double prevLeftEncoder, prevRightEncoder, prevHorizontalEncoder;
    private Rotation2d previousAngle;
    private double centerWheelOffset;

    // the suppliers
    DoubleSupplier m_left, m_right, m_horizontal;

    public HolonomicOdometry2025(DoubleSupplier leftEncoder, DoubleSupplier rightEncoder,
                             DoubleSupplier horizontalEncoder, double trackWidth, double centerWheelOffset) {
        this(trackWidth, centerWheelOffset);
        m_left = leftEncoder;
        m_right = rightEncoder;
        m_horizontal = horizontalEncoder;
    }

    public HolonomicOdometry2025(Pose2d initialPose, double trackwidth, double centerWheelOffset) {
        super(initialPose, trackwidth);
        previousAngle = initialPose.getRotation();
        this.centerWheelOffset = centerWheelOffset;
    }

    public HolonomicOdometry2025(double trackwidth, double centerWheelOffset) {
        this(new Pose2d(), trackwidth, centerWheelOffset);
    }

    /**
     * This handles all the calculations for you.
     */
    @Override
    public void updatePose() {
        update(m_left.getAsDouble(), m_right.getAsDouble(), m_horizontal.getAsDouble());
    }

    @Override
    public void updatePose(Pose2d pose) {
        previousAngle = pose.getRotation();
        robotPose = pose;

        prevLeftEncoder = 0;
        prevRightEncoder = 0;
        prevHorizontalEncoder = 0;
    }

    /*
    A note about coordinates and encoders
    1) The horizontal encoders should record positive displacements when the robot moves forward in its frame
    2) The perpendicular encoder should record positive displacements when the robot moves to the left in its frame
    3) The angle increases counterclockwise (see below)
     */
    public void update(double leftEncoderPos, double rightEncoderPos, double horizontalEncoderPos) {
        double deltaLeftEncoder = leftEncoderPos - prevLeftEncoder;
        double deltaRightEncoder = rightEncoderPos - prevRightEncoder;
        double deltaHorizontalEncoder = horizontalEncoderPos - prevHorizontalEncoder;

        Rotation2d angle = previousAngle.plus(
                new Rotation2d(
                        (deltaRightEncoder - deltaLeftEncoder) / trackWidth
                )
        );

        prevLeftEncoder = leftEncoderPos;
        prevRightEncoder = rightEncoderPos;
        prevHorizontalEncoder = horizontalEncoderPos;

        double dw = (angle.minus(previousAngle).getRadians());

        double dx = (deltaLeftEncoder + deltaRightEncoder) / 2;
        double dy = deltaHorizontalEncoder + (centerWheelOffset * dw); // The horizontal value and dw cancel each other out when we are just rotating
        Twist2d twist2d = new Twist2d(dx, dy, dw);

        Pose2d newPose = robotPose.exp(twist2d);

        previousAngle = angle;

        robotPose = new Pose2d(newPose.getTranslation(), angle);
    }
}
