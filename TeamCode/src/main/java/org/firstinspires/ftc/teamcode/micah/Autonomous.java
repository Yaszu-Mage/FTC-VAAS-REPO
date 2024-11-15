//AUTONOMOUS
package org.firstinspires.ftc.teamcode.micah;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;

import java.util.concurrent.TimeUnit;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name="Autonomous")
public class Autonomous extends LinearOpMode {
    private final int READ_PERIOD = 1;
    private final int CYCLE_TIME = 1;
    private DcMotor LeftMotor;
    private DcMotor RightMotor;
    private HuskyLens visual;
    private Servo servotest;
    private Servo winston;
    public final boolean neutral = false;
    private CRServo allison;
    private CRServo rollerIntakeLeft;
    private CRServo rollerIntakeRight;
    private CRServo nick;
    private final DcMotor leftFront, leftBack, rightFront, rightBack;
    public boolean alliance = false;
    // if red true if not red false
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
        rollerIntakeLeft = hardwareMap.get(CRServo.class, "rollerIntakeLeft");
        rollerIntakeRight = hardwareMap.get(CRServo.class, "rollerIntakeRight");
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
        allison = hardwareMap.get(Servo.class, "allison");

        Deadline rateLimit = new Deadline(READ_PERIOD, TimeUnit.SECONDS);
        Deadline physicscycle = new Deadline(CYCLE_TIME, TimeUnit.SECONDS);
        physicscycle.expire();
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
            if (physicscycle.hasExpired()) {
                //thunk
                visual.selectAlgorithm(HuskyLens.Algorithm.OBJECT_RECOGNITION);
                HuskyLens.Block[] blocks = visual.blocks();
                telemetry.addLine("Physics Cycle expired!");
                telemetry.addLine("Thinking...");
                for (int i = 0; i < blocks.length; i++) {
                    telemetry.addLine(blocks[i].toString());
                    // TODO finish autonomous - Micah
                    // should we use object classification first or should we use color recognitio
                    // either way code is the same
                    // I suggest object classification first so that we can use that as a base for color recognition
                    // Issue, how are we going to navigate it based on distance, it can recognize the object but not distance?
                    // Aidan look for documentation and put it here
                    //documents
                    // documention like this https://raw.githubusercontent.com/DFRobot/Wiki/master/SEN0305/res/HuskyLens%20WIKI%20Document.pdf
                    //https://github.com/google/ftc-object-detection somthing i found
                    // docs here
                    if (blocks[i].id == 1 && alliance) {
                        //red block
                        if (blocks[i].width >= 64 && blocks[i].height >= 64) {
//"&&" is supposedly the "and" statement
                            forward = 1.0;
                            allison.setPosition(180.0);
                            nick.setPosition(0.0);
                           if (blocks[i].x >= 240 || blocks[i].x <= -240 && blocks[i].y >= 200 || blocks[i].y <= -240) {
                                // cornered!!!
                                //blue = false, red = true


                            }
                        }else{

                            //turn to it
                        }
                    } else if (blocks[i].id == 2 && !alliance && !neutral) {
                        //blue block
                        if (blocks[i].width > 64 && blocks[i].height > 64) {
                            forward = -1.0;


                        } 
                    } else if (blocks[i].id == 3 && neutral) {
                        if (blocks[i].width >= 64 && blocks[i].height >= 64) {
//"&&" is supposedly the "and" statement
                            forward = 1.0;
                            if (blocks[i].x >= 240 || blocks[i].x <= -240 && blocks[i].y >= 200 || blocks[i].y <= -200) {
                                if (blocks[i].x <= -240) {
                                    turn = -1;
                                }else{
                                    turn = 1;
                                }
                                if (blocks[i].y <= -200 && blocks[i].x <= -200  ) {
                                    //claw
                                    nick.setPower(-1);
                                    allison.setPower(1);
                                } else if (blocks[i].y <= 200) {
                                    forward = 1;
                                }

                            }
                        }else{
                            turn = 1
                        }
                    }
                }
            } //code to identify color due to husky lens
            physicscycle.reset();
            HuskyLens.Block[] blocks = visual.blocks();
            telemetry.addData("Block count", blocks.length);
            for (int i = 0; i < blocks.length; i++) {
                telemetry.addData("Block", blocks[i].toString());
                if (blocks[i].id == 1) {
                    telemetry.addLine("I'm seeing some "+ blocks[i].id + " in my vision...");               }
            } //reaction to seeing blocks

                boolean slowed = false;
                if (slowed) {
                    forward = forward / 2;
                    strafe = strafe / 2;
                    turn = turn / 2;
                } //micah wheres the teleop
                double denominator = JavaUtil.maxOfList(JavaUtil.createListWith(1, Math.abs(forward + Math.abs(strafe) + Math.abs(turn))));
                leftFront.setPower((forward + strafe + turn) / denominator);
                leftBack.setPower((forward - (strafe - turn)) / denominator);
                rightFront.setPower((forward - (strafe + turn)) / denominator);
                rightBack.setPower((forward + (strafe - turn)) / denominator);
            telemetry.update();
        }
    }
}
