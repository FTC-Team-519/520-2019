package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class VuforiaBaseAuto extends LinearOpMode {

    protected boolean mirrorValues = false;

    protected ElapsedTime elapsedTime;

    protected StepCounter stepCounter;


    public static final double INTERIOR_GRABBER_OPENED = 0.55;
    public static final double INTERIOR_GRABBER_CLOSED = 0.35;

    public static final double FOUNDATION_GRABBER_RIGHT_DOWN = 0.88;
    public static final double FOUNDATION_GRABBER_RIGHT_UP = 0.06;
    public static final double FOUNDATION_GRABBER_LEFT_UP = 0.88;
    public static final double FOUNDATION_GRABBER_LEFT_DOWN = 0.06;

    public static final double FRONT_EXTERIOR_GRABBER_UP = 0.4;
    public static final double FRONT_EXTERIOR_GRABBER_DOWN = 0.6;
    public static final double BACK_EXTERIOR_GRABBER_UP = 0.4;
    public static final double BACK_EXTERIOR_GRABBER_DOWN = 0.8;

    protected ElapsedTime runtime = new ElapsedTime();

    protected DcMotor frontLeft;
    protected DcMotor backLeft;
    protected DcMotor frontRight;
    protected DcMotor backRight;
    protected DcMotor lift;
    protected DcMotor rightIntake;
    protected DcMotor leftIntake;

    protected Servo interiorGrabber;
    protected Servo frontExteriorGrabber;
    protected Servo backExteriorGrabber;
    protected Servo foundationGrabberRight;
    protected Servo foundationGrabberLeft;


    public void forward(double amount) {
        frontLeft.setPower(amount);
        frontRight.setPower(amount);
        backRight.setPower(amount);
        backLeft.setPower(amount);
    }

    public void reverse(double amount) {
        forward(-amount);
    }

    public void turnLeft(double power, boolean turnOnSpot) {
        frontRight.setPower(power);
        backRight.setPower(power);
        if (turnOnSpot) {
            backLeft.setPower(-power);
            frontLeft.setPower(-power);
        }
    }

    public void turnRight(double power, boolean turnOnSpot) {
        frontLeft.setPower(power);
        backLeft.setPower(power);
        if (turnOnSpot) {
            backRight.setPower(-power);
            frontRight.setPower(-power);
        }
    }

    public void strafeLeft(double power) {
        frontLeft.setPower(-1*power); //-1
        frontRight.setPower(.6*power); // .85
        backRight.setPower(-.71*power); //-.975
        backLeft.setPower(0.75*power); // .725
    }

    public void strafeRight(double power) {
        frontLeft.setPower(.9*power);//.9
        frontRight.setPower(-.7*power);
        backRight.setPower(.6*power);
        backLeft.setPower(-1*power);
    }

    public void stopMoving() {
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }


    public void setDrivePowers(double x, double y, double z) {
        frontRight.setPower(y - x - z);
        frontLeft.setPower(y + x + z);
        backRight.setPower(y + x - z);
        backLeft.setPower(y - x + z);
        if (mirrorValues) {
            x = -x;
            z = -z;
            // Y stays the same
        }
        setDrivePowers(x, y, z);
    }

    @Override
    public void runOpMode() throws InterruptedException{

        telemetry.addData("Status", "Initialized");

        frontLeft = hardwareMap.dcMotor.get("front_left");
        backLeft = hardwareMap.dcMotor.get("back_left");
        frontRight = hardwareMap.dcMotor.get("front_right");
        backRight = hardwareMap.dcMotor.get("back_right");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        interiorGrabber = hardwareMap.servo.get("interior_grabber");

        frontExteriorGrabber = hardwareMap.servo.get("front_exterior_grabber");
        frontExteriorGrabber.setPosition(FRONT_EXTERIOR_GRABBER_UP);
        backExteriorGrabber = hardwareMap.servo.get("back_exterior_grabber");
        backExteriorGrabber.setPosition(BACK_EXTERIOR_GRABBER_UP);
        foundationGrabberRight = hardwareMap.servo.get("foundation_grabber_right");
        foundationGrabberLeft = hardwareMap.servo.get("foundation_grabber_left");

        lift = hardwareMap.dcMotor.get("lift");
        rightIntake = hardwareMap.dcMotor.get("right_intake");
        leftIntake = hardwareMap.dcMotor.get("left_intake");

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        elapsedTime = new ElapsedTime();
        stepCounter = new StepCounter(0, elapsedTime, telemetry);
    }
}
