package org.firstinspires.ftc.teamcode.current.util;

import com.arcrobotics.ftclib.geometry.Pose2d;
import com.arcrobotics.ftclib.geometry.Rotation2d;

public class FieldPoses2025 {
    /*   Contains all initial starting positions and rotations for the 2024-2025
     *   Into the Deep Season
     */

    /*   For our 4 starting alliance positions, there is no dedicated starting spot, it can be anywhere as long as it
     *   is touching the alliance wall. But we should pick a spot and make sure we can position the robot there every time,
     *   maybe use the edge of wall pieces to do this. Note that the robot cannot start touching any tape.
     *   These are example starting poses, they should be changed if needed.
     */


    // starts closer to the blue alliance pieces
    public final Pose2d blueTeamStart = new Pose2d(195, 320, Rotation2d.fromDegrees(-90));

    // starts closer to the neutral yellow alliance pieces
    public final Pose2d blueNeutralStart = new Pose2d(-60, 320, Rotation2d.fromDegrees(-90));


    /*   Adjust blue team starting positions and add red team starting positions
     *   Add locations and rotations of baskets,
     *   (position/rotation where we can reasonably score by moving the arm),
     *   and down the line, a set position to grab specimen.
     */
}
