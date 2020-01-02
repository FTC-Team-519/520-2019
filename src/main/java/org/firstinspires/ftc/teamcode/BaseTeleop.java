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

import com.qualcomm.robotcore.hardware.Gamepad;
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

    protected boolean lockAndLoad = false;


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

    @Override
    public void start() {
        super.start();

        foundationGrabberLeft.setPosition(FOUNDATION_GRABBER_LEFT_UP);
        foundationGrabberRight.setPosition(FOUNDATION_GRABBER_RIGHT_UP);
        backExteriorGrabber.setPosition(BACK_EXTERIOR_GRABBER_UP);
        frontExteriorGrabber.setPosition(FRONT_EXTERIOR_GRABBER_UP);

    }


    public void loop() {}

}

