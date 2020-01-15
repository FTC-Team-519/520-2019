package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

@Autonomous(name = "BlueSkyStone", group = "Testing")
public class BlueSkyStoneSample extends BaseAuto{

    // IMPORTANT: If you are using a USB WebCam, you must select CAMERA_CHOICE = BACK; and PHONE_IS_PORTRAIT = false;
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false  ;
    private static final String VUFORIA_KEY =
            "AVjWNQH/////AAABmfTAg894fEL/rQj8b+u8l7Qw34HtrMgOnmf6xTlvK+Afn5EmrjzwTJ7/aTw0eGzNWdd0u+f1Rv8T8gH+kytJmYIPDIKOiLHuHJvMc0lwvEgKfiE33bZAoGW/ZoX2kyIHVWgr9I2yNKtE/SS4Ik4imJIJbe4QwFBMed02dz05R+j6Oi3wW4CutaknKYb5BH68RviV8b98QDV6FUwLa0u+biIkAEciicgHoQuDWCA2hrByaIEEm4XgXCF0H37hyv0Ra7SZsm6YMcTC2mNSIblMD77iL7MFyUoFdoQnykv+KJiNelhdjfswwCQWszNLYpqzwo56nAimSAr8s4C7Cub1GAlYVfq5XnG/7ZWH0oSg1x8T";

    // Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
    // We will define some constants and conversions here
    private static final float mmPerInch        = 25.4f;

    // Constant for Stone Target
    private static final float stoneZ = 2.00f * mmPerInch;
    private OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia = null;

    VuforiaTrackables targetsSkyStone = null;

    List<VuforiaTrackable> allTrackables = null;

    private float phoneXRotate    = 0;
    private float phoneYRotate    = 0;
    private float phoneZRotate    = 0;

    WebcamName webcamName = null;

    public boolean targetVisible = false;
    public int targetNotSeenCount = 0;

    public int skystonePosition = 3;

    @Override
    public void init() {
        super.init();

        //vuforia = new ConceptVuforiaSkyStoneNavigationWebcam();
        /*
         * Retrieve the camera we are to use.
         */
        webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");

        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
         * If no camera monitor is desired, use the parameter-less constructor instead (commented out below).
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;

        /**
         * We also indicate which camera on the RC we wish to use.
         */
        parameters.cameraName = webcamName;

        //  Instantiate the Vuforia engine
        this.vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Load the data sets for the trackable objects. These particular data
        // sets are stored in the 'assets' part of our application.
        this.targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");


        VuforiaTrackable stoneTarget = targetsSkyStone.get(0);
        stoneTarget.setName("Stone Target");

        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targetsSkyStone);


        // Set the position of the Stone Target.  Since it's not fixed in position, assume it's at the field origin.
        // Rotated it to to face forward, and raised it to sit on the ground correctly.
        // This can be used for generic target-centric approach algorithms
        stoneTarget.setLocation(OpenGLMatrix
                .translation(0, 0, stoneZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        //
        // Create a transformation matrix describing where the phone is on the robot.
        //
        // NOTE !!!!  It's very important that you turn OFF your phone's Auto-Screen-Rotation option.
        // Lock it into Portrait for these numbers to work.
        //
        // Info:  The coordinate frame for the robot looks the same as the field.
        // The robot's "forward" direction is facing out along X axis, with the LEFT side facing out along the Y axis.
        // Z is UP on the robot.  This equates to a bearing angle of Zero degrees.
        //
        // The phone starts out lying flat, with the screen facing Up and with the physical top of the phone
        // pointing to the LEFT side of the Robot.
        // The two examples below assume that the camera is facing forward out the front of the robot.

        // We need to rotate the camera around it's long axis to bring the correct camera forward.
        if (CAMERA_CHOICE == BACK) {
            phoneYRotate = -90;
        } else {
            phoneYRotate = 90;
        }

        // Rotate the phone vertical about the X axis if it's in portrait mode
        if (PHONE_IS_PORTRAIT) {
            phoneXRotate = 90 ;
        }

        // Next, translate the camera lens to where it is on the robot.
        // In this example, it is centered (left to right), but forward of the middle of the robot, and above ground level.
        /*final float CAMERA_FORWARD_DISPLACEMENT  = 4.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot-center
        final float CAMERA_VERTICAL_DISPLACEMENT = 4.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT     = 0;     // eg: Camera is ON the robot's center line

        OpenGLMatrix robotFromCamera = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

          Let all the trackable listeners know where the phone is.
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }
         */
    }

    @Override
    public void start() {
        super.start();

        targetsSkyStone.activate();
        targetNotSeenCount = 0;
        //stepCounter.set(0);
        stepCounter.set(-10);
    }


    @Override
    public void loop() {
        targetVisible = false;
        for (VuforiaTrackable trackable : allTrackables) {
            if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                telemetry.addData("Visible Target", trackable.getName());
                targetVisible = true;

                // getUpdatedRobotLocation() will return null if no new information is available since
                // the last time that call was made, or if the trackable is not currently visible.
                //OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                //if (robotLocationTransform != null) {
                //    lastLocation = robotLocationTransform;
                //    telemetry.addLine("Updated last location");
                //}
                break;
            }
        }
        switch (stepCounter.getStep()) {
            case -10:
                stepCounter.set(15);
                break;
            case -6:
                setDrivePowers(0,-0.25,0);
                stepCounter.increment();
                break;
            case -5:
                if (elapsedTime.seconds() >= 1.25) {
                    stopMoving();
                    stepCounter.increment();
                }
                break;
            case -4:
                if (elapsedTime.seconds() >= 0.5) {
                    stepCounter.increment();
                }
                break;
            case -3:
                setDrivePowers(0,0,0.45);
                stepCounter.increment();
                break;
            case -2:
                if (elapsedTime.seconds() >= 0.75) {
                    stopMoving();
                    stepCounter.increment();
                }
                break;
            case -1:
                if (elapsedTime.seconds() >= 2.0) {
                    //stepCounter.increment();
                    telemetry.addData("Skyblock visible Status", "" + targetVisible);
                }
                break;
            case 0:
                if (targetVisible) {
                    telemetry.addData("I saw it", "it was there");
                    skystonePosition = 1;
                    stepCounter.set(6);
                } else if (elapsedTime.seconds() >= 2.0) {
                    telemetry.addLine("Never seen for first position");
                    targetNotSeenCount = 0;
                    stepCounter.increment();
                } else {
                    telemetry.addLine("Not seen for first position");
                    ++targetNotSeenCount;
                }
                break;
            case 1:
                setDrivePowers(0, 0.25, 0);
                telemetry.addData("Moving to", "position 2");
                stepCounter.increment();
                break;
            case 2:
                if (elapsedTime.seconds() >= 0.6) {
                    telemetry.addData("Moving to", "position 2");
                    stopMoving();
                    stepCounter.increment();
                }
                break;
            case 3:
                if (targetVisible) {
                    telemetry.addData("I saw it", "2nd position");
                    skystonePosition = 2;
                    stepCounter.set(6);
                } else if (elapsedTime.seconds() >= 2.0) {
                    telemetry.addLine("Never seen for second position");
                    targetNotSeenCount = 0;
                    stepCounter.increment();
                } else {
                    telemetry.addLine("Not seen for second position");
                    ++targetNotSeenCount;
                }
                break;
            case 4:
                setDrivePowers(0, 0.25, 0);
                telemetry.addData("Moving to", "position 3");
                stepCounter.increment();
                break;
            case 5:
                if (elapsedTime.seconds() >= 0.7) {
                    telemetry.addData("Moving to", "position 3");
                    stopMoving();
                    stepCounter.increment();
                }
                break;
            case 6:
                telemetry.addData("SkystonePos", "" + skystonePosition);
                telemetry.addData("target visible", "" + targetVisible);
                stepCounter.increment();
                break;
            case 7:
                if (elapsedTime.seconds() >= 0.50) {
                    stepCounter.increment();
                }
                break;
            case 8:
                strafeRight();
                stepCounter.increment();
                break;
            case 9:
                if (elapsedTime.seconds() >= 0.55) {
                    stopMoving();
                    stepCounter.increment();
                }
                break;
            case 10:
                frontExteriorGrabber.setPosition(FRONT_EXTERIOR_GRABBER_DOWN);
                stepCounter.increment();
                break;
            case 11:
                if (elapsedTime.seconds() >= 1.0) {
                    stepCounter.increment();
                }
                break;
            case 12:
                strafeLeft();
                //strafe left curves diagonally down a lot
                stepCounter.increment();
                break;
            case 13:
                if (elapsedTime.seconds() >= 0.50) {
                    stopMoving();
                    //stepCounter.increment();
                }
                break;
            case 14:
                setDrivePowers(0,0.50,0);
                stepCounter.increment();
                break;
            case 15:
                if (elapsedTime.seconds() >= 1.0) {
                    stopMoving();
                    frontExteriorGrabber.setPosition(FRONT_EXTERIOR_GRABBER_DOWN);
                    stepCounter.increment();
                }
                break;
            case 16:
                if (elapsedTime.seconds() >= 0.500) {
                    stepCounter.increment();
                }
                break;
            case 17:
                setDrivePowers(0,0,-0.25);
                stepCounter.increment();
                break;
            case 18:
                if (elapsedTime.seconds() >= 0.2) {
                    stopMoving();
                    stepCounter.increment();
                }
                break;
            case 19:
                setDrivePowers(0,-0.50,0);
                stepCounter.increment();
                break;
            case 20:
                // Drive across the field
                skystonePosition = 3;
                if (elapsedTime.seconds() >= dragBlockAcrossFieldDuration()) {
                    stopMoving();
                    stepCounter.increment();
                }
                break;
            case 21:
                if (elapsedTime.seconds() >= 0.5000) {
                    stepCounter.increment();
                }
                break;
            case 22:
                setDrivePowers(0.25,0,0);
                stepCounter.increment();
                break;
            case 23:
                if (elapsedTime.seconds() >= 1.0) {
                    stopMoving();
                    stepCounter.increment();
                }
                break;
            case 24:
                if (elapsedTime.seconds() >= 0.50000) {
                    stepCounter.increment();
                }
                break;
            case 25:
                frontExteriorGrabber.setPosition(FRONT_EXTERIOR_GRABBER_UP);
                stepCounter.increment();
                break;
            case 26:
                if (elapsedTime.seconds() >= 0.6) {
                    stepCounter.increment();
                }
                break;
            case 27:
                setDrivePowers(0,0.250,0);
                stepCounter.increment();
                break;
            case 28:
                if (elapsedTime.seconds() >= 2.0) {
                    stopMoving();
                    stepCounter.increment();
                }
                break;
            case 29:
                if (elapsedTime.seconds() >= 0.500000) {
                    stepCounter.increment();
                }
                break;
            case 30:
                setDrivePowers(0.250,0,0);
                stepCounter.increment();
                break;
            case 31:
                if (elapsedTime.seconds() >= 0.8) {
                    stopMoving();
                }
                break;
            default:
                telemetry.addData("Status", "Something Went Wrong");
                break;
        }
        telemetry.update();
        }
    private double dragBlockAcrossFieldDuration() {
        double duration = 0.0;

        switch (skystonePosition) {
            case 1:
                duration = 1.5;
                break;
            case 2:
                duration = 1.8;
                break;
            case 3:
                duration = 2.0;
                break;
            default:
                break;
        }

        return duration;
    }
}
