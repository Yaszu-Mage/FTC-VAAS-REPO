package org.firstinspires.ftc.teamcode.micah;
    //folder (German)

import com.qualcomm.hardware.dfrobot.HuskyLens;
    //
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//Operation
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;
@TeleOp(name = "Yaszu Info")
public class Yaszu_info extends LinearOpMode {
    // Declare Different Motors, Senses and other Variables

    private final int READ_PERIOD = 1;
    private DcMotor LeftMotor;
    private DcMotor RightMotor;
    private HuskyLens visual;
    private Servo servotest;
    private Servo winston;
    public boolean jiamin = false;
    public boolean aidan = false;
    private final int COOL_DOWN = 1;
    // Run OP mode please check documentation
    @Override
    public void runOpMode() throws InterruptedException {
        //Actually Connecting Left Drive and Right Drive to correct Movement Class
        LeftMotor  = hardwareMap.get(DcMotor.class, "LeftDrive");
        RightMotor = hardwareMap.get(DcMotor.class, "RightDrive");
        servotest = hardwareMap.get(Servo.class, "servotest");
        visual = hardwareMap.get(com.qualcomm.hardware.dfrobot.HuskyLens.class,"meowmeow");
        winston = hardwareMap.get(Servo.class,"winston");
        // Declaring Ratelimit for Husky Lens
        Deadline rateLimit = new Deadline(READ_PERIOD, TimeUnit.SECONDS);
        Deadline Cool = new Deadline(COOL_DOWN, TimeUnit.MILLISECONDS);
        Cool.expire();
        telemetry.addLine("Initialized!");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            if (!Cool.hasExpired()) {
                continue;
            } else {
                jiamin = !jiamin;
            }
            if (!Cool.hasExpired()) {
                continue;
            } else {
                aidan= !aidan;
            }
            if (!visual.knock()) {
                telemetry.addData(">>", "Problem communicating with " + visual.getDeviceName());
            } else {
                telemetry.addData(">>", "Press start to continue");
            }
            // Checking if the Stick is being pressed
            if (gamepad1.dpad_up) {
                LeftMotor.setPower(-1);
                RightMotor.setPower(1);
            }else if (gamepad1.dpad_down){
                LeftMotor.setPower(1);
                RightMotor.setPower(-1)
            }else if (Math.abs(gamepad1.right_stick_x) > 0 || Math.abs(gamepad1.right_stick_y) > 0) {
                if (Math.abs(gamepad1.right_stick_x) > Math.abs(gamepad1.right_stick_y)){
                    telemetry.addLine("Moving Left");
                    if (gamepad1.right_stick_x > 0) {
                        LeftMotor.setPower(gamepad1.right_stick_x);
                        RightMotor.setPower(gamepad1.right_stick_x);
                    }else{
                        LeftMotor.setPower(gamepad1.right_stick_x);
                        RightMotor.setPower(gamepad1.right_stick_x);
                    }
            }else{
                LeftMotor.setPower(0);
                RightMotor.setPower(0);
            }
            if (gamepad1.a) {
                // check a bool and do things accordingly
                if (jiamin) {
                    // toggle on
                    servotest.setPosition(0.0);
                } else {
                    servotest.setPosition(60.0);
                    //toggle off
                }
                jiamin = !jiamin;
                Cool.reset();
                //flip bool
            }
            if (gamepad1.a) {
                if (aidan) {
                    winston.setPosition(0.0);
                } else {
                    winston.setPosition(60.0);
                }
                aidan = !aidan;
                Cool.reset();
            }
            visual.selectAlgorithm(HuskyLens.Algorithm.FACE_RECOGNITION);
            telemetry.update();
            waitForStart();

        }
    }
}