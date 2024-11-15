package org.firstinspires.ftc.teamcode.micah;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.JavaUtil;

public class shmeeb extends LinearOpMode {
    private DcMotor backLeft;
    private DcMotor frontLeft;
    private Servo intakeWrist;
    private DcMotor frontRight;
    private DcMotor backRight;
    private CRServo rollerIntakeLeft;
    private CRServo rollerIntakeRight;
    @Override
    public void runOpMode() throws InterruptedException {
        float forward;
        float strafe;
        float turn;
        double denominator;

        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        intakeWrist = hardwareMap.get(Servo.class, "intakeWrist");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        rollerIntakeLeft = hardwareMap.get(CRServo.class, "rollerIntakeLeft");
        rollerIntakeRight = hardwareMap.get(CRServo.class, "rollerIntakeRight");

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
                    // intake
                    rollerIntakeLeft.setPower(-1);
                    rollerIntakeRight.setPower(1);
                } else if (gamepad1.right_bumper) {
                    // outtake
                    rollerIntakeLeft.setPower(1);
                    rollerIntakeRight.setPower(-1);
                } else {
                    rollerIntakeLeft.setPower(0);
                    rollerIntakeRight.setPower(0);
                }
                if (gamepad1.a) {
                    intakeWrist.setPosition(0.35);
                } else if (gamepad1.b) {
                    intakeWrist.setPosition(0);
                }
    }
}}}
