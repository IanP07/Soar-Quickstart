package org.firstinspires.ftc.teamcode.current.testing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp
public class LiftMotorTest extends OpMode {
    int targetPosition = 1229;
    double Kp = 0.02;
    DcMotor liftMotor;

    @Override
    public void init() {
        liftMotor = hardwareMap.get(DcMotor.class, "lift");
        liftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // resets encoder
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        liftMotor.setTargetPosition(targetPosition);
        double power = Kp * (liftMotor.getTargetPosition() - liftMotor.getCurrentPosition());
        liftMotor.setPower(-power);


        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Current Motor Position: ", liftMotor.getCurrentPosition());
        packet.put("Power: ", power);
        FtcDashboard.getInstance().sendTelemetryPacket(packet);

    }
}


