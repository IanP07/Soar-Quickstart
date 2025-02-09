package org.firstinspires.ftc.teamcode.current.subsytems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.shared.util.CommandGamepad;

public class Arm2025 extends SubsystemBase {

    @Config
    public static class Arm2025PARAMS {

        public static double ARM_TICKS_PER_DEGREE =
                28 // number of encoder ticks per rotation of the bare motor
                        * 250047.0 / 4913.0 // This is the exact gear ratio of the 50.9:1 Yellow Jacket gearbox
                        * 100.0 / 20.0 // This is the external gear reduction, a 20T pinion gear that drives a 100T hub-mount gear
                        * 1 / 360.0; // we want ticks per degree, not per rotation
                        // = 19.7924


        public static double ARM_COLLAPSED_INTO_ROBOT = 0;
        public static double ARM_COLLECT = 231.907 * ARM_TICKS_PER_DEGREE;
        public static double ARM_CLEAR_BARRIER = 216.2446191467432 * ARM_TICKS_PER_DEGREE;    // was 230
        public static double ARM_SCORE_SPECIMEN = 149 * ARM_TICKS_PER_DEGREE;       // was 148
        public static double ARM_SCORE_SAMPLE_IN_LOW = 121.258664942099 * ARM_TICKS_PER_DEGREE; // was 126.35
        public static double ARM_ATTACH_HANGING_HOOK = 120 * ARM_TICKS_PER_DEGREE;
        public static double ARM_WINCH_ROBOT = 15 * ARM_TICKS_PER_DEGREE;

        /* Variables to store the positions that the wrist should be set to when folding in, or folding out. */
        public static double WRIST_FOLDED_IN = 0.34; //Previous: 0.82
        public static double WRIST_FOLDED_OUT = 0.01; // Previous: 0.5
        // There's always a difference of exactly 0.32 units.  Folded in will always be 0.32 higher than folded out.

        public static double LIFT_COLLAPSED = 0;
        public static double LIFT_TICKS_PER_MM = (111132.0 / 289.0) / 120.0;
        public static double LIFT_HIGH_BASKET = 480 * LIFT_TICKS_PER_MM;


    }
    private final Servo wrist;
    private final DcMotor armMotor;
    private final DcMotor liftMotor;
    private final CRServo intake;



    /* Variables to store the speed the intake servo should be set at to intake, and deposit game elements. */
    final double INTAKE_COLLECT = -1.0;
    final double INTAKE_OFF = 0.0;
    final double INTAKE_DEPOSIT = 0.6; // was 0.5



    /* A number in degrees that the triggers can adjust the arm position by */
    final double FUDGE_FACTOR = 15 * Arm2025PARAMS.ARM_TICKS_PER_DEGREE;

    public double armPosition = Arm2025PARAMS.ARM_COLLAPSED_INTO_ROBOT;


    public Arm2025(final HardwareMap hardwareMap) {

        wrist = hardwareMap.get(Servo.class, "wrist");
        armMotor = hardwareMap.get(DcMotor.class, "arm");
        intake = hardwareMap.get(CRServo.class, "intake");
        liftMotor = hardwareMap.get(DcMotor.class, "lift");

    }

    public double getARM_TICKS_PER_DEGREE() {
        return Arm2025PARAMS.ARM_TICKS_PER_DEGREE;
    }

    public double getARM_COLLAPSED_INTO_ROBOT() {
        return Arm2025PARAMS.ARM_COLLAPSED_INTO_ROBOT;
    }

    public double getARM_COLLECT() {
        return Arm2025PARAMS.ARM_COLLECT;
    }

    public double getARM_CLEAR_BARRIER() {
        return Arm2025PARAMS.ARM_CLEAR_BARRIER;
    }

    public double getARM_SCORE_SPECIMEN() {
        return Arm2025PARAMS.ARM_SCORE_SPECIMEN;
    }

    public double getARM_SCORE_SAMPLE_IN_LOW() {
        return Arm2025PARAMS.ARM_SCORE_SAMPLE_IN_LOW;
    }

    public double getARM_ATTACH_HANGING_HOOK() {
        return Arm2025PARAMS.ARM_ATTACH_HANGING_HOOK;
    }

    public double getARM_WINCH_ROBOT() {
        return Arm2025PARAMS.ARM_WINCH_ROBOT;
    }

    public double getWRIST_FOLDED_IN() {
        return Arm2025PARAMS.WRIST_FOLDED_IN;
    }

    public double getWRIST_FOLDED_OUT() {
        return Arm2025PARAMS.WRIST_FOLDED_OUT;
    }

    public double getINTAKE_COLLECT() { return INTAKE_COLLECT; }
    public double getINTAKE_OFF() { return INTAKE_OFF; }
    public double getINTAKE_DEPOSIT() { return INTAKE_DEPOSIT; }

    public double getLIFT_COLLAPSED() { return Arm2025PARAMS.LIFT_COLLAPSED; }
    public double getLIFT_HIGH_BASKET() { return Arm2025PARAMS.LIFT_HIGH_BASKET; }

    public void setArmPosition(double armPosition) {
        armMotor.setTargetPosition((int) (armPosition));

        ((DcMotorEx) armMotor).setVelocity(2100);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public void setLeftArmPower(CommandGamepad operator) {
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        armMotor.setPower(-operator.leftTrigger());
    }

    public void setRightArmPower(CommandGamepad operator) {
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        armMotor.setPower(operator.rightTrigger());
    }
    public void setWristPosition(double wristPosition) {
        wrist.setPosition(wristPosition);
    }

    public void setIntake(double intakeSpeed) {
        // Same as motor, speed is between -1 and 1
        intake.setPower(intakeSpeed);
    }

    public void setLiftPosition(double liftPosition) {
//        liftMotor.setTargetPosition((int) (liftPosition));
//
//        ((DcMotorEx) liftMotor).setVelocity(2100);
//        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        liftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        liftMotor.setTargetPosition(0);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        if (liftPosition > getLIFT_HIGH_BASKET()){
            liftPosition = getLIFT_HIGH_BASKET();
        }
        //same as above, we see if the lift is trying to go below 0, and if it is, we set it to 0.
        if (liftPosition < 0){
            liftPosition = 0;
        }


    }


    public double liftPosition() {
        return liftMotor.getCurrentPosition();
    }

    public void moveLiftDown() {
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setPower(-0.6);

    }

    public void moveLiftUp() {
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setPower(0.6);
    }


    public void stopLiftLeft() {
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setPower(0.1);
    }

    public void stopLiftRight() {
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setPower(0);
    }

    @Override
    public void periodic() {
        double motorPosition = liftMotor.getCurrentPosition();
        double armPosition = armMotor.getCurrentPosition();
        TelemetryPacket packet = new TelemetryPacket();
        packet.put("lift position: ", motorPosition);
        packet.put("arm position: ", armPosition);

        FtcDashboard.getInstance().sendTelemetryPacket(packet);
    }

}

