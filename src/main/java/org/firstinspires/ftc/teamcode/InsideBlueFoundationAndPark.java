package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Autonomous(name = "InsideBlueFoundationAndPark", group = "Testing")
public class InsideBlueFoundationAndPark extends InsideRedFoundationAndPark {

    @Override
    public void init() {
        super.init();
        mirrorValues = true;
    }


}
