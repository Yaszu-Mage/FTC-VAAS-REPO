package org.firstinspires.ftc.teamcode.achilles;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import static java.lang.Math.round;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "PIDtest")
public class PIDtest extends LinearOpMode {
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor armLiftLeft;
    private DcMotor armLiftRight;
    private DcMotor testslide;
    private DcMotor linearSlide;
    private Servo intakeWrist;
    private CRServo rollerIntakeRight;
    private CRServo rollerIntakeLeft;

    @Override
    public void runOpMode() throws InterruptedException {

    }

    public class PIDTurn {

        // get elapsed time
        private ElapsedTime PIDTimer = new ElapsedTime();

        // i
        private double integral = 0;

        // counter
        private double repetitions = 0;

        // get FIRST pid coefficients to hold pid generated coefficients
        private PIDCoefficients testPID = new PIDCoefficients(0,0,0);

        // constructor
        public PIDTurn() {

        }

        private HardwareMap hwMap = null;
        private DcMotor armLiftRight = null;
        private DcMotor armLiftLeft = null;

        BNO055IMU imu;
        Orientation angles;
        Acceleration gravity;
        BNO055IMU.Parameters imuParameters;

        public void init() {
            armLiftRight = hwMap.dcMotor.get("armliftright");
            armLiftLeft = hwMap.dcMotor.get("armliftleft");
            armLiftRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            armLiftLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            armLiftRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armLiftLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armLiftRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armLiftLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armLiftRight.setPower(0);
            armLiftLeft.setPower(0);
            imu = hwMap.get(BNO055IMU.class, "imu");
            imuParameters = new BNO055IMU.Parameters();
            imuParameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
            imuParameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
            imuParameters.loggingEnabled = false;
            imu.initialize(imuParameters);
        }}}




