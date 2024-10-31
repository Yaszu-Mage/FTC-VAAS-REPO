package org.firstinspires.ftc.teamcode.micah;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import java.lang.Math;
import java.util.concurrent.TimeUnit;
@TeleOp(name = "Yaszu's Movescript")
public class Milanes_TankBot extends LinearOpMode {


    private final int READ_PERIOD = 1;
    private HuskyLens meowmeow;
    private DcMotor LeftDrive;
    private Servo servotest;
    private DcMotor RightDrive;
    public boolean Sexism = false;
    public boolean Switchtimer = false;
    public double Movementmod = 0.5;
    public boolean dumde = false;

    /**
     *
     * This OpMode offers Tank Drive style TeleOp control for a direct drive robot.
     *
     * In this Tank Drive mode, the left and right joysticks (up
     * and down) drive the left and right motors, respectively.
     */
    @Override
    public void runOpMode() {
        LeftDrive = hardwareMap.get(DcMotor.class, "LeftDrive");
        servotest = hardwareMap.get(Servo.class, "servotest");
        RightDrive = hardwareMap.get(DcMotor.class, "RightDrive");
        meowmeow = hardwareMap.get(HuskyLens.class, "meowmeow");
        Deadline rateLimit = new Deadline(READ_PERIOD, TimeUnit.SECONDS);
        rateLimit.expire();

        if (!meowmeow.knock()) {
            telemetry.addData(">>", "Problem communicating with " + meowmeow.getDeviceName());
        } else {
            telemetry.addData(">>", "Press start to continue");
        }
        meowmeow.selectAlgorithm(HuskyLens.Algorithm.FACE_RECOGNITION);
        telemetry.update();
        waitForStart();

        // Reverse one of the drive motors.
        // You will have to determine which motor to reverse for your robot.
        // In this example, the right motor was reversed so that positive
        // applied power makes it move the robot in the forward direction.

        LeftDrive.setDirection(DcMotor.Direction.REVERSE);
        servotest.setPosition(0.8);
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                if (!rateLimit.hasExpired()) {
                    continue;
                }
                rateLimit.reset();

                HuskyLens.Block[] blocks = meowmeow.blocks();
                telemetry.addData("Block count", blocks.length);
                for (int i = 0; i< blocks.length; i++) {
                    telemetry.addData("Block", blocks[i].toString());
                    telemetry.addData("Id", blocks[i].id);
                    if (blocks[i].id == 1) {
                        telemetry.addLine("I can see you");
                        dumde = true;
                    }
                }
                telemetry.addData(">>", meowmeow.knock() ? "Touch start to continue" : "Problem communicating with HuskyLens");
                if (dumde) {
                    for (int i = 0; i < 100000; i++) {
                        LeftDrive.setPower(1);
                        RightDrive.setPower(1);
                    }
                    LeftDrive.setPower(1);
                    RightDrive.setPower(1);
                }
                if (gamepad1.a & Sexism){
                    Sexism = false;
                    servotest.setPosition(10 + servotest.getPosition());
                    for (int x = 1; x < 1000; x++) {
                        telemetry.addLine("lol");
                    }
                    Sexism = true;

                }


                // Put loop blocks here.
                // The Y axis of a joystick ranges from -1 in its topmost position to +1 in its bottommost position.
                // We negate this value so that the topmost position corresponds to maximum forward power.
                if ( Math.abs(Math.round(gamepad1.right_stick_x)) < Math.abs(Math.round(gamepad1.right_stick_y))) {
                    if (gamepad1.right_stick_y > 0) {
                        LeftDrive.setPower(gamepad1.right_stick_y);
                        RightDrive.setPower(gamepad1.right_stick_y);
                    }else{
                        LeftDrive.setPower(gamepad1.right_stick_y);
                        RightDrive.setPower(gamepad1.right_stick_y);
                    }

                    LeftDrive.setPower(gamepad1.right_stick_y);
                    RightDrive.setPower(gamepad1.right_stick_y);
                    telemetry.addData("Going forward", gamepad1.right_stick_y);
                }else if (Math.abs(Math.round(gamepad1.right_stick_x)) > Math.abs(Math.round(gamepad1.right_stick_y))) {
                    telemetry.addData("Going turn", gamepad1.right_stick_x);
                    if (Math.round(gamepad1.right_stick_x) == -1) {
                        LeftDrive.setPower(gamepad1.right_stick_x);
                        RightDrive.setPower(-gamepad1.right_stick_x);
                        telemetry.addLine("Im turning right");
                    }else {
                        RightDrive.setPower(gamepad1.right_stick_x);
                        LeftDrive.setPower(-gamepad1.right_stick_x);
                        telemetry.addLine("Im turning right");
                    }
                } else if (Math.abs(Math.round(gamepad1.right_stick_x)) == 0 && Math.abs(Math.round(gamepad1.right_stick_y)) == 0){
                    telemetry.addLine("Im stopped");
                    LeftDrive.setPower(0);
                    RightDrive.setPower(0);
                }
                telemetry.addData("Sticking it X", gamepad1.right_stick_x);
                if (gamepad1.a & !Switchtimer) {
                    // lock up and do nothing like an alpha male
                    Switchtimer = true;
                    if (Sexism) {
                        servotest.setPosition(0);
                        Sexism = false;
                    }else{
                        servotest.setPosition(0.8);
                        Sexism = true;
                    }
                    Switchtimer = false;
                }
                telemetry.addData("Left Pow", LeftDrive.getPower());
                telemetry.addData("Right Pow", RightDrive.getPower());
                telemetry.update();
            }}}}
