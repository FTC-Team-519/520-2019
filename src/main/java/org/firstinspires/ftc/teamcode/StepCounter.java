package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class StepCounter {

    private ElapsedTime elapsedTime;
    private Telemetry telemetry;
    private int step = 0;
    public StepCounter(int start, ElapsedTime elapsedTime, Telemetry telemetry) {
        this.elapsedTime = elapsedTime;
        step = start;
    }

    public void increment() {
        step += 1;
        elapsedTime.reset();
        telemetry.addData("Step", Integer.toString(step));
    }

    public void increment(int delta) {
        step += delta;
        elapsedTime.reset();
    }

    public void set(int to) {
        step = to;
        elapsedTime.reset();
    }

    public int getStep() {
        return step;
    }


}
