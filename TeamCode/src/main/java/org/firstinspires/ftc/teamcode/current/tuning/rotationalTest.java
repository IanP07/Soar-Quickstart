package org.firstinspires.ftc.teamcode.current.tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class rotationalTest extends LinearOpMode{
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;

    @Override
    public void runOpMode() throws InterruptedException {
        // Make sure your motors are named "fL", "fR", "bL", and "bR" in your driverstation config file
        frontLeft = hardwareMap.get(DcMotor.class, "fL");
        frontRight = hardwareMap.get(DcMotor.class, "fR");
        backLeft = hardwareMap.get(DcMotor.class, "bL");
        backRight = hardwareMap.get(DcMotor.class, "bR");


        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();


        // NOTE: This opmode will start rotating and NOT stop until you stop the program on the Driverstation
        while(opModeIsActive()) {
            frontLeft.setPower(1);
            backLeft.setPower(1);
            backRight.setPower(-1);
            frontRight.setPower(-1);
        }
    }
}
