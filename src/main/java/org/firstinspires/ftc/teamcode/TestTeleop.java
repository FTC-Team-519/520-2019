/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Demonstrates empty OpMode
 */
@TeleOp(name = "TestTeleop2019", group = "Testing")
public class TestTeleop extends OpMode {

    private static final double INTERIOR_GRABBER_OPENED = 0.55;
    private static final double INTERIOR_GRABBER_CLOSED = 0.35;

    private static final double FOUNDATION_GRABBER_UP = 0.15;
    private static final double FOUNDATION_GRABBER_DOWN = 0.80;

    private static final double FRONT_EXTERIOR_GRABBER_UP = 0.4;
    private static final double FRONT_EXTERIOR_GRABBER_DOWN = 0.8;
    private static final double BACK_EXTERIOR_GRABBER_UP = 0.6;
    private static final double BACK_EXTERIOR_GRABBER_DOWN = 0.2;

    private static final float FAST_MODE_MODIFIER = 1.0f;
    private static final float SLOW_MODE_MODIFIER = 0.5f;

    private float driveSpeedModifier;

    private boolean flipOrientation;

    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor lift;
    private DcMotor rightIntake;
    private DcMotor leftIntake;

    private Servo interiorGrabber;
    private Servo frontExteriorGrabber;
    private Servo backExteriorGrabber;
    private Servo foundationGrabber;

    private Gamepad driver;
    private Gamepad gunner;

    private float x;
    private float y;
    private float z;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        frontLeft = hardwareMap.dcMotor.get("front_left");
        backLeft = hardwareMap.dcMotor.get("back_left");
        frontRight = hardwareMap.dcMotor.get("front_right");
        backRight = hardwareMap.dcMotor.get("back_right");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        interiorGrabber = hardwareMap.servo.get("interior_grabber");

        frontExteriorGrabber = hardwareMap.servo.get("front_exterior_grabber");
        backExteriorGrabber = hardwareMap.servo.get("back_exterior_grabber");
        foundationGrabber = hardwareMap.servo.get("foundation_grabber");

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

        driver = gamepad1;
        gunner = gamepad2;

        driveSpeedModifier = SLOW_MODE_MODIFIER;

        flipOrientation = false;
    }

    /*
     * Code to run when the op mode is first enabled goes here
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init_loop() {
    }

    /*
     * This method will be called ONCE when start is pressed
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */
    @Override
    public void loop() {
        telemetry.addData("Status", "Run Time: " + runtime.toString());

//        x = driver.left_stick_x;
//        y = -driver.left_stick_y;
//        z = driver.right_stick_x;
        x = shapeInput(driver.left_stick_x) * driveSpeedModifier;
        y = shapeInput(-driver.left_stick_y) * driveSpeedModifier;
        z = shapeInput(driver.right_stick_x) * driveSpeedModifier;

        if (flipOrientation) {
            x = -x;
            y = -y;
        }

        frontRight.setPower(y - x - z);
        frontLeft.setPower(y + x + z);
        backRight.setPower(y + x - z);
        backLeft.setPower(y - x + z);

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


        /*
        if(driver.x) {
            backExteriorGrabber.setPosition(BACK_EXTERIOR_GRABBER_UP);
        } else if(driver.y) {
            backExteriorGrabber.setPosition(BACK_EXTERIOR_GRABBER_DOWN);

        }
        */
    }

    private static float shapeInput(float input) {
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
