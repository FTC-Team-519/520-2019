package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

public class BaseAuto extends BaseOpMode {

    protected boolean mirrorValues = false;

    protected ElapsedTime elapsedTime;

    protected StepCounter stepCounter;

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

    /*public void strafeRight(double power) {
        frontLeft.setPower(-power);//.9
        frontRight.setPower(power);
        backRight.setPower(power);
        backLeft.setPower(-power);
    }
*/
    public void stopMoving() {
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    @Override
    public void setDrivePowers(double x, double y, double z) {
        if (mirrorValues) {
            x = -x;
            z = -z;
            // Y stays the same
        }
        super.setDrivePowers(x, y, z);
    }

    @Override
    public void init() {
        super.init();

        foundationGrabberLeft.setPosition(FOUNDATION_GRABBER_LEFT_UP);
        foundationGrabberRight.setPosition(FOUNDATION_GRABBER_RIGHT_UP);
        backExteriorGrabber.setPosition(BACK_EXTERIOR_GRABBER_UP);
        frontExteriorGrabber.setPosition(FRONT_EXTERIOR_GRABBER_UP);


        elapsedTime = new ElapsedTime();
        stepCounter = new StepCounter(0, elapsedTime, telemetry);
    }

    @Override
    public void loop() {

    }
}
