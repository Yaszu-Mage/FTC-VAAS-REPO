package org.firstinspires.ftc.teamcode.achilles;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
@TeleOp(name = "MecanumTest (Blocks to Java)")
public class ignore extends LinearOpMode{







    private DcMotor armLift;
    private DcMotor backLeft;
    private DcMotor frontLeft;
    private Servo intakeWrist;
    private DcMotor frontRight;
    private DcMotor backRight;
    private CRServo intakeRoller;
    private DcMotor test;

    /**
     * This sample contains the bare minimum Blocks for any regular OpMode. The 3 blue
     * Comment Blocks show where to place Initialization code (runs once, after touching the
     * DS INIT button, and before touching the DS Start arrow), Run code (runs once, after
     * touching Start), and Loop code (runs repeatedly while the OpMode is active, namely not
     * Stopped).
     */
    @Override
    public void runOpMode() {
        float forward;
        float strafe;
        float turn;
        double denominator;

        armLift = hardwareMap.get(DcMotor.class, "armLift");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        intakeWrist = hardwareMap.get(Servo.class, "intakeWrist");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        intakeRoller = hardwareMap.get(CRServo.class, "intakeRoller");
        test = hardwareMap.get(DcMotor.class, "test");

        // Put initialization blocks here.
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        intakeWrist.setPosition(0);
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                // Put loop blocks here.
                forward = -gamepad1.left_stick_y;
                strafe = gamepad1.left_stick_x;
                turn = gamepad1.right_stick_x;
                if (gamepad1.x) {
                    forward = forward / 2;
                    strafe = strafe / 2;
                    turn = turn / 2;
                }
                denominator = JavaUtil.maxOfList(JavaUtil.createListWith(1, Math.abs(forward + Math.abs(strafe) + Math.abs(turn))));
                frontLeft.setPower((forward + strafe + turn) / denominator);
                backLeft.setPower((forward - (strafe - turn)) / denominator);
                frontRight.setPower((forward - (strafe + turn)) / denominator);
                backRight.setPower((forward + (strafe - turn)) / denominator);
                // arm test code
                if (gamepad1.left_bumper) {
                    intakeRoller.setPower(1);
                } else if (gamepad1.right_bumper) {
                    intakeRoller.setPower(-1);
                } else {
                    intakeRoller.setPower(0);
                }
                if (gamepad1.a) {
                    intakeWrist.setPosition(0);
                } else {
                    intakeWrist.setPosition(0.35);
                }
                if (gamepad1.b) {
                    armLift.setPower(1);
                    telemetry.addLine("Shmeeb B");
                } else if (gamepad1.y) {
                    armLift.setPower(-1);
                    telemetry.addLine("Shmeeb A");
                } else {
                    armLift.setPower(0);
                }
                telemetry.addData("Servo Position", intakeWrist.getPosition());
                telemetry.addData("Lift Position", armLift.getCurrentPosition());
                telemetry.update();
            }
        }
    }
}

