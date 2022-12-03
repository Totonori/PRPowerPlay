package org.firstinspires.ftc.teamcode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.CVPipelines.SleeveDetection;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "Signal Sleeve Blue")
public class PRVisionParkBlue extends LinearOpMode {

    Robot PRobot = new Robot();

    private ElapsedTime runtime = new ElapsedTime();

    static final double Counts_Per_Rev = 312;
    static final double Drive_Gear_Reduction = 1.0;
    static final double Wheel_Diameter_In = 3;
    static final double Counts_Per_In = (Counts_Per_Rev * Drive_Gear_Reduction) / (Wheel_Diameter_In * 3.1415);
    static final double Drive_Speed = .5;
    static final double Turn_Speed = .3;

    //values for arm
    static final double COUNTS_PER_ARM_MOTOR_REV = 312;  // eg: TETRIX Motor Encoder //2150.8
    static final double ARM_GEAR_REDUCTION = 0.3;        // This is < 1.0 if geared UP
    static final double SPROCKET_DIAMETER_INCHES = 3.0;     // For figuring circumference

    static final double ARM_PER_INCH = (COUNTS_PER_ARM_MOTOR_REV * ARM_GEAR_REDUCTION) / (SPROCKET_DIAMETER_INCHES * 3.1415);
    static final double LVL_1_INCHES = 5.0;
    static final double LVL_2_INCHES = 8.0;
    static final double LVL_3_INCHES = 19.0;

    Integer cpr = 28;
    Integer gearratio = (((1 + (46 / 17))) * (1 + (46 / 11)));
    Double diameter = 4.0;
    Double cpi = (cpr * gearratio) / (Math.PI * diameter);
    Double bias = 0.8;
    Double meccyBias = 0.9;

    SleeveDetection position = new SleeveDetection();
    SleeveDetection sleeveDetection;
    OpenCvCamera webcam;

    int wait = 500;



    // Name of the Webcam to be set in the config
    String webcamName = "Webcam 1";

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
            telemetry.addData("Position", position.getPosition());
            telemetry.update();


            switch (sleeveDetection.getPosition()) {
                case LEFT:
                    encoderDrive(Drive_Speed,30,30,2);
                    sleep(1000);

                    strafeToPosition(-30,Drive_Speed,3);
                    sleep(1000);

                    break;

                case CENTER:
                    encoderDrive(Drive_Speed,30,30,2);
                    sleep(1000);

                    break;

                case RIGHT:
                    encoderDrive(Drive_Speed, 30, 30, 2);
                    sleep(1000);

                    strafeToPosition(30,Drive_Speed,3);
                    sleep(1000);

                    break;
            }

            webcam.stopStreaming();
            webcam.closeCameraDevice();
            break;

        }

    }

    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) {
        int newFrontLeftTarget;
        int newBackLeftTarget;
        int newFrontRightTarget;
        int newBackRightTarget;

        //Ensure opmode is still active
        if (opModeIsActive()) {

            //Determine new target position/send to controller
            newFrontLeftTarget = PRobot.fL.getCurrentPosition() + (int) (-leftInches * Counts_Per_In);
            newBackLeftTarget = PRobot.bL.getCurrentPosition() + (int) (-leftInches * Counts_Per_In);
            newFrontRightTarget = PRobot.fR.getCurrentPosition() + (int) (rightInches * Counts_Per_In);
            newBackRightTarget = PRobot.bR.getCurrentPosition() + (int) (rightInches * Counts_Per_In);

            PRobot.fL.setTargetPosition(newFrontLeftTarget);
            PRobot.bL.setTargetPosition(newBackLeftTarget);
            PRobot.fR.setTargetPosition(newFrontRightTarget);
            PRobot.bR.setPower(newBackRightTarget);

            //Turns on RUN_TO_POSITION
            PRobot.fL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            PRobot.bL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            PRobot.fR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            PRobot.bR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //resets timeout, starts motion
            runtime.reset();

            PRobot.fL.setPower(Math.abs(speed));
            PRobot.bL.setPower(Math.abs(speed));
            PRobot.fR.setPower(Math.abs(speed));
            PRobot.bR.setPower(Math.abs(speed));

            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (PRobot.fL.isBusy() && PRobot.bL.isBusy() && PRobot.fR.isBusy() && PRobot.bR.isBusy())) {

                telemetry.addData("Running to", "%7d :%7d",
                        PRobot.fL.getCurrentPosition(), PRobot.bL.getCurrentPosition(), PRobot.fR.getCurrentPosition(), PRobot.bR.getCurrentPosition());
                telemetry.update();
            }

            //stop all motion
            PRobot.fL.setPower(0);
            PRobot.bL.setPower(0);
            PRobot.fR.setPower(0);
            PRobot.bR.setPower(0);

            //Turn off RUN_TO_POSITION
            PRobot.fL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            PRobot.bL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            PRobot.fR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            PRobot.bR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            // sleep(250);
        }
    }

    public void strafeToPosition(double inches, double speed, double timeoutS) {

        int move = (int) (Math.round(inches * cpi * meccyBias * 1.265));

        PRobot.fL.setTargetPosition(PRobot.fL.getCurrentPosition() + move);
        PRobot.bL.setTargetPosition(PRobot.bL.getCurrentPosition() - move);
        PRobot.fR.setTargetPosition(PRobot.fR.getCurrentPosition() + move);
        PRobot.bR.setTargetPosition(PRobot.bR.getCurrentPosition() - move);

        PRobot.fL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        PRobot.bL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        PRobot.fR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        PRobot.bR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        PRobot.fL.setPower(speed);
        PRobot.bL.setPower(speed);
        PRobot.fR.setPower(speed);
        PRobot.bR.setPower(speed);

        while (opModeIsActive() &&
                (runtime.seconds() < timeoutS) &&
                (PRobot.fL.isBusy() && PRobot.bL.isBusy() && PRobot.fR.isBusy() && PRobot.bR.isBusy())) {

            telemetry.addData("Running to", "%7d :%7d",
                    PRobot.fL.getCurrentPosition(), PRobot.bL.getCurrentPosition(), PRobot.fR.getCurrentPosition(), PRobot.bR.getCurrentPosition());
            telemetry.update();
        }

        while (PRobot.fL.isBusy() && PRobot.bL.isBusy() && PRobot.fR.isBusy() && PRobot.bR.isBusy()) {
        }
        PRobot.fL.setPower(0);
        PRobot.bL.setPower(0);
        PRobot.fR.setPower(0);
        PRobot.bR.setPower(0);
        return;
    }

    public void liftEncoderDrive(double speed,
                                 double inches,
                                 double timeoutS) {
        int newarmTarget;
        // int newBackLeftTarget;
        //  int newFrontRightTarget;
        //  int newBackRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            PRobot.lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            // frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            // backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            // backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            // Determine new target position, and pass to motor controller
            newarmTarget = PRobot.lift.getCurrentPosition() + (int) (inches * ARM_PER_INCH);
            // newBackLeftTarget = backLeft.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            // newFrontRightTarget = frontRight.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            // newBackRightTarget = backRight.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            PRobot.lift.setTargetPosition(newarmTarget);
            //  backLeft.setTargetPosition(newBackLeftTarget);
            //  frontRight.setTargetPosition(newFrontRightTarget);
            //  backRight.setTargetPosition(newBackRightTarget);
            // Turn On RUN_TO_POSITION
            PRobot.lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // reset the timeout time and start motion.
            runtime.reset();
            PRobot.lift.setPower(Math.abs(speed));
            // backLeft.setPower(Math.abs(speed));
            //  frontRight.setPower(Math.abs(speed));
            //  backRight.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (PRobot.lift.isBusy())) {// frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d ", newarmTarget);//newBackLeftTarget, newFrontRightTarget, newBackRightTarget);
                telemetry.addData("Path2", "Running at %7d  ",
                        PRobot.lift.getCurrentPosition());
                // backLeft.getCurrentPosition(),
                // frontRight.getCurrentPosition(),
                //  backRight.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            PRobot.lift.setPower(0);
            //backLeft.setPower(0);
            // frontRight.setPower(0);
            // backRight.setPower(0);

            // Turn off RUN_TO_POSITION
            PRobot.lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            // backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            // frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            // backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);   // optional pause after each move
        }
    }
}