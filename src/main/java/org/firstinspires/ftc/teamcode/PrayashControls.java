package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "PrayashControls", group = "testing")
public class PrayashControls extends BaseTeleop {

    @Override
    public void loop() {
        telemetry.addData("Status", "Run Time: " + runtime.toString());

        x = shapeInput(driver.left_stick_x) * driveSpeedModifier;
        y = shapeInput(-driver.left_stick_y) * driveSpeedModifier;
        z = shapeInput(driver.right_stick_x) * driveSpeedModifier;

        if (flipOrientation) {
            x = -x;
            y = -y;
        }

        setDrivePowers(x, y, z);

        lift.setPower(shapeInput(gunner.left_stick_y)/2.0);

        if (gunner.left_trigger >= 0.5) {
            leftIntake.setPower(0.90);
            rightIntake.setPower(0.90);
        } else if (gunner.right_trigger >= 0.5) {
            //This is inwards intake
            leftIntake.setPower(-0.99);
            rightIntake.setPower(-0.99);
        } else {
            leftIntake.setPower(0);
            rightIntake.setPower(0);
        }

        if (gunner.left_bumper) {
            interiorGrabber.setPosition(INTERIOR_GRABBER_OPENED);
        } else if (gunner.right_bumper) {
            interiorGrabber.setPosition(INTERIOR_GRABBER_CLOSED);
        }

        if (driver.dpad_up) {
            foundationGrabber.setPosition(FOUNDATION_GRABBER_UP);
        } else if (driver.dpad_down) {
            foundationGrabber.setPosition(FOUNDATION_GRABBER_DOWN);
        }

        if (driver.x) {
            frontExteriorGrabber.setPosition(FRONT_EXTERIOR_GRABBER_UP);
            backExteriorGrabber.setPosition(BACK_EXTERIOR_GRABBER_UP);
        } else if (driver.b) {
            frontExteriorGrabber.setPosition(FRONT_EXTERIOR_GRABBER_DOWN);
            backExteriorGrabber.setPosition(BACK_EXTERIOR_GRABBER_DOWN);
        }

        if (driver.right_bumper) {
            driveSpeedModifier = FAST_MODE_MODIFIER;
        } else if (driver.left_bumper) {
            driveSpeedModifier = SLOW_MODE_MODIFIER;
        }

        if (driver.a && !driver.start) {
            flipOrientation = true;
        } else if (driver.y) {
            flipOrientation = false;
        }

    }


    public static float shapeInput(float input) {
        float shapedValue = 0.0f;
        if (input != 0.0f) {
            if (input < 0.0f) {
                shapedValue = input * -input;
            } else {
                shapedValue = input * input;
            }
        }

        return shapedValue;
    }
}

