package org.firstinspires.ftc.teamcode.micah;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.Range;
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
    public Extend_Preference Car_Lone = Extend_Preference.Dropoff2;
    public Whichextend prop = Whichextend.Both;


    // Boolean to determine if you can extend, found this out during pep rally
    public enum Extend_Preference {
        Dropoff1,
        Dropoff2,
    }
    public enum Whichextend {
        Leftonly,
        Rightonly,
        Both,
    }
    //array for max values for each setting

    public int[] hangvalues = {328, 358, 0, 1403, 1408, -1357}; // this represents an integer series
    // hangvalues [0] = 328 or the first number, then hangvalue [1] would be 358, or second number.

    public boolean Sticking_it_X() {
        boolean output = false;
        if (gamepad2.right_stick_x > 0 || gamepad2.right_stick_x < 0) {
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
        double pos_limit = 0;
        int neg_limit;
        telemetry.addLine("IM COMPARING");
        // Declares Output before function runs
        switch (Car_Lone) {
            // Executed if Case is Hang 1

            case Dropoff2:
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


            case Dropoff1:
                // Executed if Case is Hang 1
                telemetry.addLine("HANG 1");
                neg_limit = 0;
                pos_limit = 0;
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
                    testslide.setTargetPosition(0);

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
// meow :3


    public boolean Canextendright() {
        // moves entire arm including viper slide
        boolean output = false;
        int pos_limit = 358;
        int neg_limit = 0;
        double linear_pos = armLiftRight.getCurrentPosition();
        switch (Car_Lone) {
            case Dropoff1:
                telemetry.addLine("DROPOFF 1");
                neg_limit = 0;
                pos_limit = 358;
                /*
                Move = Recieves input at 1 or -1 within limits
                Move if Input = 1 and poslimit > linearpos
                Move if Input = -1 and neglimit < linear pos
                 */
                // if lin pos is less than equal to limit it runs func to check if it moves or not
                if (round(gamepad2.right_stick_x) <= 0.1 && pos_limit > linear_pos) {
                    output = true;
                    armLiftRight.setTargetPosition(358);
                    telemetry.addLine("setting positive target position");
                } else if (round(gamepad2.right_stick_x) >= -0.1 && neg_limit < linear_pos) {
                    output = true;
                    armLiftRight.setTargetPosition(0);
                    telemetry.addLine("going back to zero");
                } else if (round(gamepad2.right_stick_x) == 0 || neg_limit > linear_pos || pos_limit < linear_pos) {
                    output = false;
                    armLiftRight.setTargetPosition(armLiftRight.getCurrentPosition());
                    telemetry.addLine("not moving");
                }

                telemetry.addData("Output: ", output);
                telemetry.addData("Linear Pos: ", linear_pos);
                telemetry.addData("Negative Limit", neg_limit);
                telemetry.addData("Positive Limit", pos_limit);
                telemetry.addData("LIMIT: ", armLiftRight.getTargetPosition());
                return output;
            case Dropoff2:
                telemetry.addLine("DROPOFF 2");
                neg_limit = 0;
                pos_limit = 1408;
                /*
                Move = Receives input at 1 or -1 within limits
                Move if Input = 1 and poslimit > linearpos
                Move if Input = -1 and neglimit < linear pos
                 */
                // if lin pos is less than equal to limit it runs func to check if it moves or not
                if (round(gamepad2.left_stick_x) >= 0.1 && pos_limit > linear_pos) {
                    output = true;
                    armLiftRight.setTargetPosition(1408);
                } else if (round(gamepad2.left_stick_x) <= -0.1 && neg_limit < linear_pos) {
                    telemetry.addLine("Right-Neg-Function");
                    output = true;
                    armLiftRight.setTargetPosition(0);

                } else if (round(gamepad2.left_stick_x) == 0 || neg_limit > linear_pos || pos_limit < linear_pos) {
                    output = false;
                    armLiftRight.setTargetPosition(armLiftRight.getCurrentPosition());
                }

                return output;
        }
        return output;
    }

    //328,358
    public boolean Canextendleft() {
        boolean output = false;
        int pos_limit = 328;
        int neg_limit = 0;
        int tolerance = 2;
        int target = 358;
        boolean onTarget = false;
        double linear_pos = armLiftLeft.getCurrentPosition();
        switch (Car_Lone) {
            case Dropoff1:
                telemetry.addLine("DROPOFF 1");
                neg_limit = 0;
                pos_limit = 358;
                /*
                Move = Receives input at 1 or -1 within limits
                Move if Input = 1 and poslimit > linearpos
                Move if Input = -1 and neglimit < linear pos
                 */
                // if lin pos is less than equal to limit it runs func to check if it moves or not
                if (round(gamepad2.left_stick_x) >= 0.1 && pos_limit > linear_pos) {
                    output = true;
                    // implementing PID code to see if it works. at time of writing driver hub is dead and cant test
                    //armLiftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                   /* while (!onTarget) {
                        int error = target - armLiftLeft.getCurrentPosition();
                        // find what our Kp value will be through testing
                        armLiftLeft.setPower(Range.clip(error * 2, -1.0, 1.0));
                        onTarget = Math.abs(error) <= tolerance;
                    }*/
                    armLiftLeft.setPower(1);
                    armLiftLeft.setTargetPosition(328);
                    telemetry.addLine("moving to left position");
                } else if (round(gamepad2.left_stick_x) <= -0.1 && neg_limit < linear_pos) {
                    output = true;
                    armLiftLeft.setTargetPosition(0);
                    telemetry.addLine("moving to zero left position");
                } else if (round(gamepad2.left_stick_x) == 0 || neg_limit > linear_pos || pos_limit < linear_pos) {
                    output = false;
                    armLiftLeft.setTargetPosition(armLiftLeft.getCurrentPosition());
                    telemetry.addLine("freezing");
                }

                return output;
            case Dropoff2:
                telemetry.addLine("DROPOFF 2");
                neg_limit = 0;
                pos_limit = 1408;
                /*
                Move = Receives input at 1 or -1 within limits
                Move if Input = 1 and poslimit > linearpos
                Move if Input = -1 and neglimit < linear pos
                 */
                // if lin pos is less than equal to limit it runs func to check if it moves or not
                if (round(gamepad2.left_stick_x) >= 0.1 && pos_limit > linear_pos) {
                    output = true;
                    armLiftLeft.setTargetPosition(1408);
                } else if (round(gamepad2.left_stick_x) <= -0.1 && neg_limit < linear_pos) {
                    telemetry.addLine("Left-Neg-Function");
                    output = true;
                    armLiftLeft.setTargetPosition(0);

                } else if (round(gamepad2.left_stick_x) == 0 || neg_limit > linear_pos || pos_limit < linear_pos) {
                    output = false;
                    armLiftLeft.setTargetPosition(armLiftLeft.getCurrentPosition());
                }

                return output;
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
        armLiftRight.setTargetPosition(0);
        armLiftLeft.setTargetPosition(0);
        armLiftLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armLiftRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        testslide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        testslide.setPower(1);
        armLiftRight.setPower(1);
        armLiftLeft.setPower(1);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad2.a) {
                Car_Lone = Extend_Preference.Dropoff1;
            } else if (gamepad2.b) {
                Car_Lone = Extend_Preference.Dropoff2;
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
            } else if (gamepad2.a) {
                Car_Lone = Extend_Preference.Dropoff1;
            } else if (gamepad2.b) {
                Car_Lone = Extend_Preference.Dropoff2;
            }
            telemetry.addData("TestSlide", testslide.getCurrentPosition());
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
                    telemetry.addData("I AM AT ", testslide.getCurrentPosition());
                } else {
                    testslide.setPower(0);
                }
            } else {
                testslide.setPower(0);
            }
            if (Sticking_it_Y()) {

               if (Canextendleft() && gamepad2.left_stick_x != 0) {
                   prop = Whichextend.Leftonly;
               }
               if (Canextendright() && gamepad2.left_stick_x != 0 && prop == Whichextend.Leftonly){
                   prop = Whichextend.Both;
               } else if (Canextendright() && gamepad2.left_stick_x != 0) {
                   prop = Whichextend.Rightonly;
               }
               switch (prop){
                   case Leftonly:
                       armLiftLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                       armLiftLeft.setPower(-gamepad2.left_stick_x);
                       telemetry.addLine("Left Only");
                       telemetry.addData("Left Position", armLiftLeft.getCurrentPosition());
                   case Rightonly:
                       armLiftRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                       armLiftRight.setPower(gamepad2.left_stick_x);
                       telemetry.addLine("Right Only");
                       telemetry.addData("Right Position", armLiftRight.getCurrentPosition());
                   case Both:
                       armLiftLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                       armLiftRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                       armLiftLeft.setPower(-gamepad2.left_stick_x);
                       armLiftRight.setPower(gamepad2.right_stick_x);
                       telemetry.addLine("Both");
                       telemetry.addData("Left Position", armLiftLeft.getCurrentPosition());
                       telemetry.addData("Right Position", armLiftRight.getCurrentPosition());
               }
            } else {
                armLiftLeft.setPower(0);
                armLiftRight.setPower(0);
            }
        }
        telemetry.update();
        // Movement end
        // Claw Start
    }

}
