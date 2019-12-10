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
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Demonstrates empty OpMode
 */
public abstract class BaseOpMode extends OpMode {

    public static final double INTERIOR_GRABBER_OPENED = 0.55;
    public static final double INTERIOR_GRABBER_CLOSED = 0.35;

    public static final double FOUNDATION_GRABBER_UP = 0.8;
    public static final double FOUNDATION_GRABBER_DOWN = 0.0;

    public static final double FRONT_EXTERIOR_GRABBER_UP = 0.4;
    public static final double FRONT_EXTERIOR_GRABBER_DOWN = 0.6;
    public static final double BACK_EXTERIOR_GRABBER_UP = 0.6;
    public static final double BACK_EXTERIOR_GRABBER_DOWN = 0.4;

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
    protected Servo foundationGrabber;

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

    }

    /*
     * This method will be called ONCE when start is pressed
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */
    @Override
    public void start()
    {
        super.start();

        runtime.reset();
    }

}
