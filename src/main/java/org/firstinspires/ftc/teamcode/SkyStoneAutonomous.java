package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name = "SkyStoneAutonomous", group = "Testing")
public class SkyStoneAutonomous extends BaseAuto {



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
        foundationGrabber.setPosition(FOUNDATION_GRABBER_UP);
    }

    @Override
    public void loop() {
        switch (stepCounter.getStep()) {

            case 0:
                setDrivePowers(-.5,0,0);
                stepCounter.increment();
                break;
            case 1:
                if (elapsedTime.seconds() >= 2.0) {
                    stopMoving();
                    stepCounter.increment();
                }
                break;
            case 2:
                setDrivePowers(0, -0.5,0);
                stepCounter.increment();
                break;
            case 3:
                if (elapsedTime.seconds() >= 4.0) {
                    stopMoving();
                    stepCounter.increment();
                }
                break;
            case 4:
                foundationGrabber.setPosition(FOUNDATION_GRABBER_DOWN);
                stepCounter.increment();
                break;
            case 5:
                if (foundationGrabber.getPosition() == FOUNDATION_GRABBER_DOWN) {
                    stepCounter.increment();
                }
                break;
            default:
                telemetry.addData("Status", "In default");
                break;
        }


    }
}
