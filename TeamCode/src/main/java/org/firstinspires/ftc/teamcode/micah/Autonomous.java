package org.firstinspires.ftc.teamcode.micah;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Autonomous")
public class Autonomous extends LinearOpMode {
    private final int READ_PERIOD = 1;
    private DcMotor LeftMotor;
    private DcMotor RightMotor;
    private HuskyLens visual;
    private Servo servotest;
    private Servo winston;
    private final DcMotor leftFront, leftBack, rightFront, rightBack;
    public double forward = -0;
    public double strafe = 0;
    public double turn = 0;
    public Autonomous(DcMotor leftFront, DcMotor leftBack, DcMotor rightFront, DcMotor rightBack) {
        this.leftFront = leftFront;
        this.leftBack = leftBack;
        this.rightFront = rightFront;
        this.rightBack = rightBack;
        leftFront = hardwareMap.get(DcMotorEx.class,"LeftDrive");
        rightFront = hardwareMap.get(DcMotorEx.class, "RightDrive");
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    @Override
    public void runOpMode() throws InterruptedException {
        LeftMotor  = hardwareMap.get(DcMotor.class, "LeftDrive");
        RightMotor = hardwareMap.get(DcMotor.class, "RightDrive");
        servotest = hardwareMap.get(Servo.class, "servotest");
        visual = hardwareMap.get(com.qualcomm.hardware.dfrobot.HuskyLens.class,"meowmeow");
        winston = hardwareMap.get(Servo.class,"winston");
        Deadline rateLimit = new Deadline(READ_PERIOD, TimeUnit.SECONDS);
        rateLimit.expire();
        if (!visual.knock()) {
            telemetry.addData(">>", "Problem communicating with " + visual.getDeviceName());
        } else {
            telemetry.addData(">>", "Press start to continue");
        }
        visual.selectAlgorithm(HuskyLens.Algorithm.TAG_RECOGNITION);

        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            if (!rateLimit.hasExpired()) {
                continue;
            }
            rateLimit.reset();

            HuskyLens.Block[] blocks = visual.blocks();
            telemetry.addData("Block count", blocks.length);
            for (int i = 0; i < blocks.length; i++) {
                telemetry.addData("Block", blocks[i].toString());
                if (blocks[i].id == 1) {
                    telemetry.addLine("I'm seeing some "+ blocks[i].id + " in my vision...");               }
            }
                boolean slowed = false;
                if (slowed) {
                    forward = forward / 2;
                    strafe = strafe / 2;
                    turn = turn / 2;
                }
                double denominator = JavaUtil.maxOfList(JavaUtil.createListWith(1, Math.abs(forward + Math.abs(strafe) + Math.abs(turn))));
                leftFront.setPower((forward + strafe + turn) / denominator);
                leftBack.setPower((forward - (strafe - turn)) / denominator);
                rightFront.setPower((forward - (strafe + turn)) / denominator);
                rightBack.setPower((forward + (strafe - turn)) / denominator);
            telemetry.update();
        }
    }
}
