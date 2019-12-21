package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcontroller.external.samples.ConceptVuforiaSkyStoneNavigation;

@Autonomous(name = "RedSkyStoneSampleTest", group = "testing")
public class RedSkyStoneSampleTest extends TestBoardBase {

    private boolean placeholder;


    @Override
    public void init() {
    //   super.init();
    }

    @Override
    public void start() {
      //  super.start();
    }


    public void loop() {
        switch (stepCounter.getStep()) {
            case 0:
                if (placeholder) {
                //    setDrivePowers(0,0,0);
                } else if (placeholder) {
                 //   setDrivePowers(0,0,0);
                } else {
                //    setDrivePowers(0,0,0);
                }
                break;
            case 1:

                break;
            default:
                telemetry.addData("Status", "Something Went Wrong");
                break;
        }
    }
}
