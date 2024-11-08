package org.firstinspires.ftc.teamcode.achilles;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;

@TeleOp(name="achilles")
public class achilles extends LinearOpMode {
    private final int READ_PERIOD = 1;
    private DcMotor LeftMotor;
    private DcMotor RightMotor;
    private HuskyLens visual;
    private Servo winston;
    @Override
    public void runOpMode() throws InterruptedException {
        LeftMotor  = hardwareMap.get(DcMotor.class, "LeftDrive");
        RightMotor = hardwareMap.get(DcMotor.class, "RightDrive");
        winston = hardwareMap.get(Servo.class, "servotest");
        visual = hardwareMap.get(com.qualcomm.hardware.dfrobot.HuskyLens.class,"meowmeow");
        Deadline rateLimit = new Deadline(READ_PERIOD, TimeUnit.SECONDS);
        telemetry.addLine("Initialized!");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            if (!visual.knock()) {
                telemetry.addData(">>", "Problem communicating with " + visual.getDeviceName());
            } else {
                telemetry.addData(">>", "Press start to continue");
             }
}}}
