package org.firstinspires.ftc.teamcode.micah;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.robotcore.external.JavaUtil;

import static java.lang.Math.round;

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
    // Boolean function runs every time bool is checked
    // output = true it moves , while output = false it doesnt.
    // checks array for stop values, rightstop value is always 0
    // check enums
    // check array "hangvalues"
    public boolean Canextendlinear() {
        boolean output = false;
        double linear_pos = testslide.getCurrentPosition();
        //ACHILLIES IM A FUCKING IDIOT CHECK LINE 59 IT WAS LINEAR SLIDE NOT TEST SLIDE...
        double pos_limit = 0;
        int neg_limit;
        telemetry.addLine("IM COMPARING");
        // Declares Output before function runs
        switch (Car_Lone) {
            case Hang1:
                neg_limit = hangvalues[2];
                telemetry.addLine("COMPARING HANG 1");
                if (linear_pos < neg_limit) {
                // check what direction joy stick is going
                    if (gamepad2.right_stick_x > 0 && linear_pos < pos_limit) {
                        output = true;
                        telemetry.addLine("ALL CONDITIONS MET - 1");
                    } else {
                        output = false;
                        telemetry.addLine("NOT MOVING");
                    }

                    // if lin pos is less than equal to limit it runs func to check if it moves or not
            }
                // Executed if Case is Hang 1

            case Hang2:
                // Executed if Case is Hang 2
                telemetry.addLine("HANG 2");
                neg_limit = hangvalues[5];

                /*
                Move = Recieves input at 1 or -1 within limits
                Move if Input = 1 and poslimit > linearpos
                Move if Input = -1 and neglimit < linear pos
                 */
                    // if lin pos is less than equal to limit it runs func to check if it moves or not
                if (round(gamepad2.right_stick_x) <= 0.1 &&  pos_limit > linear_pos) {
                    output = true;
                    testslide.setTargetPosition(0);
                } else if (round(gamepad2.right_stick_x) >= -0.1 && neg_limit < linear_pos) {
                    output = true;
                    testslide.setTargetPosition(-1457);

                }else if (round(gamepad2.right_stick_x) == 0 || neg_limit > linear_pos || pos_limit < linear_pos){
                    output = false;
                    testslide.setTargetPosition(testslide.getCurrentPosition());
                }

                telemetry.addData("Output: ", output);
                telemetry.addData("Linear Pos: ", linear_pos);
                telemetry.addData("Negative Limit", neg_limit);
                telemetry.addData("Positive Limit", pos_limit);
                telemetry.addData("LIMIT: ", testslide.getTargetPosition());
                return output;

        }
        telemetry.addData("Output: ", output);
        telemetry.update();
        return output;

    }




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
        testslide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ((DcMotorEx) armLiftLeft).setVelocityPIDFCoefficients(1.17, 0.117, 0, 11.7);
        ((DcMotorEx) armLiftRight).setVelocityPIDFCoefficients(1.17, 0.117, 0, 11.7);
        testslide.setTargetPosition(0);
        testslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        testslide.setPower(1);
        waitForStart();
        while (opModeIsActive()){
            if (gamepad2.a) {
                Car_Lone = Extend_Preference.Hang1;
            } else if (gamepad2.b) {
                Car_Lone = Extend_Preference.Hang2;
            }
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
                    testslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    testslide.setPower(gamepad2.right_stick_x);
                    telemetry.addLine("IM TRYING TO COMMUNICATE");
                    telemetry.addData("I AM AT ",testslide.getCurrentPosition());
                } else {
                    testslide.setPower(0);
                }
            }else {
                testslide.setPower(0);
            }
            if (Sticking_it_X()) {
                if (Canextendleft() && gamepad2.left_stick_x != 0 || Canextendright()  && gamepad2.left_stick_x != 0) {

                } else {

                }
            }
        }
        telemetry.update();
        // Movement end
        // Claw Start
    }
}
