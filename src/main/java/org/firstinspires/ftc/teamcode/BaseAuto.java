package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

public class BaseAuto extends BaseOpMode {

    private ElapsedTime elapsedTime;

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


    @Override
    public void init() {
        super.init();

        elapsedTime = new ElapsedTime();
        stepCounter = new StepCounter(0, elapsedTime);
    }

    @Override
    public void loop() {

    }
}
