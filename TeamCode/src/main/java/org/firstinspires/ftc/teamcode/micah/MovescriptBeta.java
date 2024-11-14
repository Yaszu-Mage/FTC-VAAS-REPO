package org.firstinspires.ftc.teamcode.micah;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.JavaUtil;

@TeleOp(name="Yaszu's Teleop")
public class MovescriptBeta extends LinearOpMode {
    private final DcMotor leftFront, leftBack, rightFront, rightBack;
    private Servo winston;
    private Servo allison;
    private Servo nick;
    public MovescriptBeta(DcMotor leftFront, DcMotor leftBack, DcMotor rightFront, DcMotor rightBack) {
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
        nick = hardwareMap.get(Servo.class,"nick");
        winston = hardwareMap.get(Servo.class, "winston");
        allison = hardwareMap.get(Servo.class, "allison");
        while (opModeIsActive()){
            double forward = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;
            if (gamepad1.x) {
                forward = forward / 2;
                strafe = strafe / 2;
                turn = turn / 2;
            }
            if (gamepad1.a) {
                nick.setPosition(180);
                if (gamepad1.a) {
                    nick.setPosition(0);
                }
                }
            if (gamepad1.b) {
                winston.setPosition(180);
                allison.setPosition(180);
                if (gamepad1.b) {
                    winston.setPosition(0);
                    allison.setPosition(0);
                }
                }
            }
            double denominator = JavaUtil.maxOfList(JavaUtil.createListWith(1, Math.abs(forward + Math.abs(strafe) + Math.abs(turn))));
            leftFront.setPower((forward + strafe + turn) / denominator);
            leftBack.setPower((forward - (strafe - turn)) / denominator);
            rightFront.setPower((forward - (strafe + turn)) / denominator);
            rightBack.setPower((forward + (strafe - turn)) / denominator);
        }
    }
}
