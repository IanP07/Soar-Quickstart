package org.firstinspires.ftc.teamcode.current.testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class ServoTest extends LinearOpMode {
    public Servo wrist;
    public CRServo intake;

    @Override
    public void runOpMode(){

        wrist = hardwareMap.get(Servo.class, "wrist");
        intake = hardwareMap.get(CRServo.class, "intake"); 
        waitForStart();

        while(opModeIsActive()) {

            if(gamepad1.a) {
                wrist.setPosition(0.5);
            }
            else if(gamepad1.b) {
                wrist.setPosition(0.75);
            }


            else if(gamepad1.y) {
                intake.setPower(0.5);
            }
            else if(gamepad1.x){
                intake.setPower(-0.5);
            }
            else {
                intake.setPower(0);
            }

        }
    }
}
