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
public class BaseTeleop extends BaseOpMode {

    public static final float FAST_MODE_MODIFIER = 1.0f;
    public static final float SLOW_MODE_MODIFIER = 0.5f;

    protected float driveSpeedModifier;

    protected boolean flipOrientation;

    protected Gamepad driver;
    protected Gamepad gunner;

    protected float x;
    protected float y;
    protected float z;

    @Override
    public void init() {
        super.init();

        driver = gamepad1;
        gunner = gamepad2;

        driveSpeedModifier = SLOW_MODE_MODIFIER;

        flipOrientation = false;
    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */
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
