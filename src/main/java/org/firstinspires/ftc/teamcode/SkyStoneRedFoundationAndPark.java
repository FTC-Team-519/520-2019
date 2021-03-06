package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "SkyStoneRedFoundationAndPark", group = "Testing")
public class SkyStoneRedFoundationAndPark extends BaseAuto {

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {
        backExteriorGrabber.setPosition(BACK_EXTERIOR_GRABBER_UP);
        frontExteriorGrabber.setPosition(FRONT_EXTERIOR_GRABBER_UP);
        foundationGrabberLeft.setPosition(FOUNDATION_GRABBER_LEFT_UP);
        foundationGrabberRight.setPosition(FOUNDATION_GRABBER_RIGHT_UP);
    }

    @Override
    public void loop() {
        switch (stepCounter.getStep()) {

            case 0:
                setDrivePowers(-.5,0,0);
                stepCounter.increment();
                break;
            case 1:
                if (elapsedTime.seconds() >= 0.7) {
                    stopMoving();
                    stepCounter.increment();
                }
                break;
            case 2:
                setDrivePowers(0, -0.3,0);
                stepCounter.increment();
                break;
            case 3:
                if (elapsedTime.seconds() >= 1.70) {
                    stopMoving();
                    stepCounter.increment();
                }
                break;
            case 4:
                foundationGrabberLeft.setPosition(FOUNDATION_GRABBER_LEFT_DOWN);
                foundationGrabberRight.setPosition(FOUNDATION_GRABBER_RIGHT_DOWN);
                stepCounter.increment();
                break;
            case 5:
                if (elapsedTime.seconds() >= 1.0) {
                    stepCounter.increment();
                }
                break;
            case 6:
                setDrivePowers(.1,.25,.25);
                stepCounter.increment();
                break;
            case 7:
                if (elapsedTime.seconds() >= 2.8) {
                    stopMoving();
                    stepCounter.increment();
                }
                break;
            case 8:
                foundationGrabberLeft.setPosition(FOUNDATION_GRABBER_LEFT_UP);
                foundationGrabberRight.setPosition(FOUNDATION_GRABBER_RIGHT_UP);
                stepCounter.increment();
                break;
            case 9:
                if (elapsedTime.seconds() >= 0.49) {
                    stepCounter.increment();
                }
                break;
            case 10:
                setDrivePowers(0, -.5,0);
                stepCounter.increment();
                break;
            case 11:
                if (elapsedTime.seconds() >= 1.0) {
                    stopMoving();
                    stepCounter.increment();
                }
                break;
            case 12:
                setDrivePowers(-.50,-0.15,0);
                stepCounter.increment();
                break;
            case 13:
                if (elapsedTime.seconds() >= 1.00) {
                    stopMoving();
                    stepCounter.increment();
                }
                break;
            case 14:
                setDrivePowers(0,0.5,0);
                stepCounter.increment();
                break;
            case 15:
                if (elapsedTime.seconds() >= 0.5) {
                    stopMoving();
                    stepCounter.increment();
                }
                break;
            case 16:
                interiorGrabber.setPosition(INTERIOR_GRABBER_CLOSED);
                stepCounter.increment();
                break;
            case 17:
                if (elapsedTime.seconds() >= 0.2) {
                    stepCounter.increment();
                }
                break;
            case 18:
                lift.setPower(0.5);
                stepCounter.increment();
                break;
            case 19:
                if (elapsedTime.seconds() >= 0.5) {
                    lift.setPower(0);
                    interiorGrabber.setPosition(INTERIOR_GRABBER_OPENED);
                    stepCounter.increment();
                }
                break;
            case 20:
                setDrivePowers(0,.5,0);
                stepCounter.increment();
                break;
            case 21:
                if (elapsedTime.seconds() >= 0.70) {
                    stopMoving();
                    stepCounter.increment();
                }
                break;
            default:
                telemetry.addData("Status", "In default");
                stopMoving();
                break;
        }


    }
}
