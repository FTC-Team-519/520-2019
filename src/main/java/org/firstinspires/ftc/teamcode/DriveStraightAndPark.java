package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "DriveStraightAndPark", group = "testing")
public class DriveStraightAndPark  extends BaseAuto{

    @Override
    public void init() {
        super.init();
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
                setDrivePowers(.5,0,0);
                break;
            case 1:
                if (elapsedTime.seconds() >= 0.5) {
                    stopMoving();
                    stepCounter.increment();
                }
                break;
        }

    }

}
