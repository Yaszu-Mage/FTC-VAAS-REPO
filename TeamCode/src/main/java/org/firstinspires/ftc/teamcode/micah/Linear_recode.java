package org.firstinspires.ftc.teamcode.micah;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
@TeleOp(name = "Yahoo")
public class Linear_recode extends LinearOpMode {
    private DcMotor backRight;
    private DcMotor frontRight;
    private DcMotor armLiftLeft;
    private Servo intakeWrist;
    private DcMotor testslide;
    private DcMotor armLiftRight;
    private DcMotor linearSlide;
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private CRServo rollerIntakeLeft;
    private CRServo rollerIntakeRight;
    public Extend_Preference Car_Lone = Extend_Preference.Hang2;
    // Boolean to determine if you can extend, found this out during pep rally
    public enum Extend_Preference {
        Hang1,
        Hang2,
    }
    //array for max values for each setting

    public int[] hangvalues = {328,358,0,1403,1408,-1357};
    public boolean Sticking_it_X() {
        boolean output = false;
        if (gamepad2.right_stick_x > 0 || gamepad2.right_stick_x < 0){
            output = true;
        }
        if (gamepad2.right_stick_y > 0 || gamepad2.right_stick_y < 0) {
            output = true;
        }
        return output;
    }
    public boolean Sticking_it_Y() {
        boolean output = false;
        if (gamepad2.left_stick_x > 0 || gamepad2.left_stick_x < 0) {
            output = true;
        }
        if (gamepad2.left_stick_y > 0 || gamepad2.left_stick_y < 0) {
            output = true;
        }
        return output;
    }
    public boolean Canextendlinear() {
        boolean output = false;
        telemetry.addLine("Checking extendation linear");
        switch (Car_Lone) {
            case Hang1:
                telemetry.addLine("Hang1");
                if (testslide.getCurrentPosition() > hangvalues[2]) {
                    output = true;
                }else {
                    output = false;
                }
            case Hang2:
                telemetry.addLine("Hang2");
                if (testslide.getCurrentPosition() > hangvalues[5]  & testslide.getCurrentPosition() <= 0) {
                    output = true;
                    if (gamepad2.right_stick_x <= 0.1 & testslide.getCurrentPosition() >= hangvalues[5]) {
                        output = true;
                    }else {
                        output = false;
                    }
                }else {
                    output = false;
                    if (gamepad2.right_stick_x >= 0.1 & testslide.getCurrentPosition() <= 0) {
                        output = true;
                    }else {
                        output = false;
                    }
        }
    }return output;}




    public boolean Canextendright() {
        boolean output = false;
        switch (Car_Lone) {
            case Hang1:
                if (armLiftRight.getCurrentPosition() <= hangvalues[1]) {
                    output = true;
                }else {
                    output = false;
                }
            case Hang2:
                if (armLiftLeft.getCurrentPosition() <= hangvalues[4]) {
                    output = true;
                }else {
                    output = false;
                }
        }
        return output;
    }
    //328,358
    public boolean Canextendleft() {
        boolean output = false;
        switch (Car_Lone) {
            case Hang1:
                if (armLiftLeft.getCurrentPosition() <= hangvalues[0]) {
                    output = true;
                }else {
                    output = false;
                }
            case Hang2:
                if (armLiftLeft.getCurrentPosition() <= hangvalues[3]) {
                    output = true;
                }else {
                    output = false;
                }
        }
        return output;
    }
    @Override
    public void runOpMode() throws InterruptedException {
        float forward;
        float strafe;
        float turn;
        double denominator;

        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        armLiftLeft = hardwareMap.get(DcMotor.class, "armLiftLeft");
        intakeWrist = hardwareMap.get(Servo.class, "intakeWrist");
        testslide = hardwareMap.get(DcMotor.class, "testSlide");
        armLiftRight = hardwareMap.get(DcMotor.class, "armLiftRight");
        linearSlide = hardwareMap.get(DcMotor.class, "linearSlide");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        rollerIntakeLeft = hardwareMap.get(CRServo.class, "rollerIntakeLeft");
        rollerIntakeRight = hardwareMap.get(CRServo.class, "rollerIntakeRight");
        testslide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // Put initialization blocks here.
        backRight.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        armLiftLeft.setDirection(DcMotor.Direction.REVERSE);
        intakeWrist.setPosition(0.45);
        armLiftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armLiftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ((DcMotorEx) armLiftLeft).setVelocityPIDFCoefficients(1.17, 0.117, 0, 11.7);
        ((DcMotorEx) armLiftRight).setVelocityPIDFCoefficients(1.17, 0.117, 0, 11.7);
        testslide.setTargetPosition(0);
        testslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        testslide.setPower(1);
        waitForStart();
        while (opModeIsActive()){
            // Movement Start
            telemetry.update();
            forward = gamepad1.left_stick_y;
            strafe = gamepad1.left_stick_x;
            turn = gamepad1.right_stick_x;
            telemetry.addData("Right Stick X", gamepad2.right_stick_x);
            if (gamepad1.x) {
                forward = forward / 2;
                strafe = strafe / 2;
                turn = turn / 2;
            }
            telemetry.addData("TestSlide",testslide.getCurrentPosition());
            denominator = JavaUtil.maxOfList(JavaUtil.createListWith(1, Math.abs(forward) + strafe + turn));
            frontLeft.setPower((forward - (strafe + turn)) / denominator);
            backLeft.setPower((forward + (strafe - turn)) / denominator);
            frontRight.setPower((forward + strafe + turn) / denominator);
            backRight.setPower((forward - (strafe - turn)) / denominator);
            if (Sticking_it_X()) {
                if (Canextendlinear() && gamepad2.right_stick_x != 0) {
                    testslide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    testslide.setPower(gamepad2.right_stick_x);
                    telemetry.addLine("IM TRYING TO COMMUNICATE");
                    telemetry.addData("I AM AT ",testslide.getCurrentPosition());
                } else {
                    testslide.setPower(0);
                    testslide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }
            }
            if (Sticking_it_X()) {
                if (Canextendleft() && gamepad2.left_stick_x != 0 || Canextendright()  && gamepad2.left_stick_x != 0) {

                } else {

                }
            }
            if (gamepad2.a) {
                Car_Lone = Extend_Preference.Hang1;
            } else if (gamepad2.b) {
                Car_Lone = Extend_Preference.Hang2;
            }
        }
        telemetry.update();
        // Movement end
        // Claw Start
    }
}
