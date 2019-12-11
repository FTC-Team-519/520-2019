package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


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

                break;
        }


    }
}
