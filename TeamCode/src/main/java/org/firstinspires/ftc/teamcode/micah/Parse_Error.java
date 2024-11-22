package org.firstinspires.ftc.teamcode.micah;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static java.lang.Math.round;

public class Parse_Error extends LinearOpMode {
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
    public Linear_recode.Extend_Preference Car_Lone = Linear_recode.Extend_Preference.Dropoff2;
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
    @Override
    public void runOpMode() throws InterruptedException {

    }

    // Boolean to determine if you can extend, found this out during pep rally
    public enum Extend_Preference {
        Dropoff1,
        Dropoff2,
    }
    //array for max values for each setting

    public int[] hangvalues = {328, 358, 0, 1403, 1408, -1357};

}
