package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "RealTeleop", group = "Testing")
public class RealTeleop extends BaseTeleop {

    private ElapsedTime elapsedTime;


    @Override
    public void init() {
        elapsedTime = new ElapsedTime();
        elapsedTime.reset();
        elapsedTime.startTime();

        super.init();
        telemetry.addLine(String.valueOf(elapsedTime.milliseconds()));
    }

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

        if (gunner.left_bumper) {
            leftIntake.setPower(0.75);
            rightIntake.setPower(0.75);
        } else if (gunner.right_bumper) {
            //This is inwards intake
            leftIntake.setPower(-0.90);
            rightIntake.setPower(-0.90);
        } else {
            leftIntake.setPower(0);
            rightIntake.setPower(0);
        }

        if (gunner.a) {
            interiorGrabber.setPosition(INTERIOR_GRABBER_OPENED);
        } else if (gunner.b) {
            interiorGrabber.setPosition(INTERIOR_GRABBER_CLOSED);
        }

        if (driver.dpad_up) {
            foundationGrabberLeft.setPosition(FOUNDATION_GRABBER_LEFT_UP);
            foundationGrabberRight.setPosition((FOUNDATION_GRABBER_RIGHT_UP));
        } else if (driver.dpad_down) {
            foundationGrabberLeft.setPosition(FOUNDATION_GRABBER_LEFT_DOWN);
            foundationGrabberRight.setPosition(FOUNDATION_GRABBER_RIGHT_DOWN);
        }

        if (driver.x) {
            backExteriorGrabber.setPosition(BACK_EXTERIOR_GRABBER_UP);
        } else if (driver.b) {
            backExteriorGrabber.setPosition(BACK_EXTERIOR_GRABBER_DOWN);
        }

        if (driver.dpad_left) {
            frontExteriorGrabber.setPosition(FRONT_EXTERIOR_GRABBER_UP);
        } else if (driver.dpad_right) {
            frontExteriorGrabber.setPosition(FRONT_EXTERIOR_GRABBER_DOWN);
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

        if (gunner.dpad_up) {
            //tapeShooter.setPosition(TAPE_SHOOTER_OPEN);
        } else {
            //tapeShooter.setPosition(TAPE_SHOOTER_CLOSED);
        }

        if (gunner.x) {
            lockAndLoad = true;
        }

        if (gunner.y) {
            lockAndLoad = false;
        }

        if (lockAndLoad) {
            int currentLiftPosition = lift.getCurrentPosition();
            if (currentLiftPosition < LOCK_AND_LOAD_HIGH) {
                lift.setPower(1.0);
            } else if (currentLiftPosition > LOCK_AND_LOAD_LOW) {
                lift.setPower(-0.4);
            } else {
                lift.setPower(0.0);
                lockAndLoad = false;
            }
        }

        /*
        if (gunner.x) {

            lift.setTargetPosition(-400);
            lift.getTargetPosition();
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.25);
            if (lift.getCurrentPosition() == lift.getTargetPosition()) {
                lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                lift.setPower(0.0);
            }
        }

        if (gunner.y) {
            lift.setTargetPosition(0);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.5);
            if (lift.getTargetPosition() == lift.getCurrentPosition()) {

                lift.setPower(0);
            }
        }
        */
        //lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        telemetry.addLine(String.valueOf(lift.getCurrentPosition()));
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


