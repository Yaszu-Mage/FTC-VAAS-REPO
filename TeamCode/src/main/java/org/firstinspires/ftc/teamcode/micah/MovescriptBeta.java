package org.firstinspires.ftc.teamcode.micah;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.JavaUtil;

@TeleOp(name="Yaszu's Teleop")
public class MovescriptBeta extends LinearOpMode {
    private final DcMotor leftFront, leftBack, rightFront, rightBack;
    private Servo winston;
    private CRServo allison;
    public DcMotor derek;
    private CRServo nick;
    public double forward;
    public double strafe;
    public double turn;
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
        derek.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        derek = hardwareMap.get(DcMotor.class, "derek");
        nick = hardwareMap.get(CRServo.class,"nick");
        winston = hardwareMap.get(Servo.class, "winston");
        allison = hardwareMap.get(CRServo.class, "allison");
        while (opModeIsActive()){
            forward = -gamepad1.left_stick_y;
            strafe = gamepad1.left_stick_x;
            turn = gamepad1.right_stick_x;
            if (gamepad1.x) {
                forward = forward / 2;
                strafe = strafe / 2;
                turn = turn / 2;
            }
            if (gamepad1.right_trigger > 0) {
                forward = forward * gamepad1.right_trigger;
                strafe = strafe * gamepad1.right_trigger;
                turn = turn * gamepad1.right_trigger;
            }
            if (gamepad1.dpad_up) {
                //extend
                derek.setPower(0.2);
            } else if (gamepad1.dpad_down) {
                //recede
                derek.setPower(-0.2);
            }
            // TODO test this please!!!!
             if (gamepad1.a) {
                winston.setPosition(0.35);
            }else if (gamepad1.b) {
                winston.setPosition(0);
            }
            if (gamepad1.left_bumper) {
                // intake
                allison.setPower(-1);
                nick.setPower(1);
            } else if (gamepad1.right_bumper) {
                // outtake
                allison.setPower(1);
                nick.setPower(-1);
            } else {
                allison.setPower(0);
                nick.setPower(0);
            }
            }
            double denominator = JavaUtil.maxOfList(JavaUtil.createListWith(1, Math.abs(forward + Math.abs(strafe) + Math.abs(turn))));
            leftFront.setPower((forward + strafe + turn) / denominator);
            leftBack.setPower((forward - (strafe - turn)) / denominator);
            rightFront.setPower((forward - (strafe + turn)) / denominator);
            rightBack.setPower((forward + (strafe - turn)) / denominator);
        }
    }

