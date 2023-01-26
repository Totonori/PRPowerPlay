package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.CVPipelines.SleeveDetection;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous (name = "Signal Sleeve Red (NO ENCODERS)")
public class PRVisionParkRedXE extends LinearOpMode {

    Robot PRobot = new Robot();

    private SleeveDetection sleeveDetection;
    private OpenCvWebcam webcam;


    private String webcamName = "Webcam 1";

    @Override
    public void runOpMode() throws InterruptedException {
        PRobot.init(hardwareMap);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        sleeveDetection = new SleeveDetection();
        webcam.setPipeline(sleeveDetection);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
                                         @Override
                                         public void onOpened() {
                                             webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
                                         }

                                         @Override
                                         public void onError(int errorCode) {
                                         }
                                     }
        );


        while (!isStarted()) {
            telemetry.addData("ROTATION: ", sleeveDetection.getPosition());
            telemetry.update();
        }
        telemetry.setMsTransmissionInterval(20);

        telemetry.addLine("Waiting for start");
        telemetry.update();

        waitForStart();


        while (opModeIsActive()) {

            sleep(2000);
            telemetry.addData("Position: ", sleeveDetection.getPosition());
            telemetry.update();

            switch (sleeveDetection.getPosition()){
                case LEFT:
                    //Strafe Right
                    PRobot.fR.setPower(-0.6);
                    PRobot.bR.setPower(0.6);
                    PRobot.fL.setPower(0.6);
                    PRobot.bL.setPower(-0.6);
                    sleep(1100);

                    //Move Forward
                    PRobot.fR.setPower(-0.5);
                    PRobot.bR.setPower(-0.5);
                    PRobot.fL.setPower(0.5);
                    PRobot.bL.setPower(0.5);
                    sleep(800);

                    break;

                case CENTER:
                    //Strafe Right
                    PRobot.fR.setPower(-0.6);
                    PRobot.bR.setPower(0.6);
                    PRobot.fL.setPower(0.6);
                    PRobot.bL.setPower(-0.6);
                    sleep(1000);

                    //Move Forward
                    PRobot.fR.setPower(-0.5);
                    PRobot.bR.setPower(-0.5);
                    PRobot.fL.setPower(0.5);
                    PRobot.bL.setPower(0.5);
                    sleep(1600);

                    //Strafe Left
                    PRobot.fR.setPower(0.6);
                    PRobot.bR.setPower(-0.6);
                    PRobot.fL.setPower(-0.6);
                    PRobot.bL.setPower(0.6);
                    sleep(1100);

                    break;

                case RIGHT:
                    //Strafe Left
                    PRobot.fR.setPower(0.6);
                    PRobot.bR.setPower(-0.6);
                    PRobot.fL.setPower(-0.6);
                    PRobot.bL.setPower(0.6);
                    sleep(1100);

                    //Move Forward
                    PRobot.fR.setPower(-0.5);
                    PRobot.bR.setPower(-0.5);
                    PRobot.fL.setPower(0.5);
                    PRobot.bL.setPower(0.5);
                    sleep(800);

                    break;
            }
            webcam.stopStreaming();
            webcam.closeCameraDevice();
            break;
        }
    }
}
