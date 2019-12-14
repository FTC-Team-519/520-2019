package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "BlueFoundationAndPark", group = "Testing")
public class BlueFoundationAndPark extends RedFoundationAndPark {

    @Override
    public void init() {
        super.init();
        mirrorValues = true;
    }


}
