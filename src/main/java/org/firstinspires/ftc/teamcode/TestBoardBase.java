package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class TestBoardBase extends OpMode {
    protected ElapsedTime elapsedTime;

    protected StepCounter stepCounter;

    protected DcMotor right;

    public void init() {
        elapsedTime = new ElapsedTime();
        stepCounter = new StepCounter(0, elapsedTime, telemetry);

        right = hardwareMap.dcMotor.get("right_motor");
    }

    public void start() {}

    public void loop() {}

}
