package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

@Autonomous(name="PRAuto")
public class PRAuto extends LinearOpMode {

    Robot PRobot = new Robot();

    private ElapsedTime runtime = new ElapsedTime();

    static final double Counts_Per_Rev = 1440;
    static final double Drive_Gear_Reduction = 1.0;
    static final double Wheel_Diameter_In = 3;
    static final double Counts_Per_In = (Counts_Per_Rev * Drive_Gear_Reduction) / (Wheel_Diameter_In * 3.1415);
    static final double Drive_Speed = .5;
    static final double Turn_Speed = .3;

    Integer cpr = 28;
    Integer gearratio = (((1 + (46/17))) * (1+ (46/11)));
    Double diameter = 4.0;
    Double cpi = (cpr * gearratio) / (Math.PI * diameter);
    Double bias = 0.8;
    Double meccyBias = 0.9;

    @Override
    public void runOpMode() throws InterruptedException {

    }
    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) {
        int newFrontLeftTarget;
        int newBackLeftTarget;
        int newFrontRightTarget;
        int newBackRightTarget;

        //Ensure opmode is still active
        if (opModeIsActive()) {

            //Determine new target position/send to controller
            newFrontLeftTarget = PRobot.fL.getCurrentPosition() + (int) (leftInches * Counts_Per_In);
            newBackLeftTarget = PRobot.bL.getCurrentPosition() + (int) (leftInches * Counts_Per_In);
            newFrontRightTarget = PRobot.fR.getCurrentPosition() + (int) (rightInches *Counts_Per_In);
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

            sleep(250);
        }
    }
    public void strafeToPosition(double inches, double speed) {

        int move = (int) (Math.round(inches * cpi * meccyBias * 1.265));

        PRobot.fL.setTargetPosition(PRobot.fL.getCurrentPosition() + move);
        PRobot.bL.setTargetPosition(PRobot.bL.getCurrentPosition() - move);
        PRobot.fR.setTargetPosition(PRobot.fR.getCurrentPosition() - move);
        PRobot.bR.setTargetPosition(PRobot.bR.getCurrentPosition() + move);

        PRobot.fL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        PRobot.bL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        PRobot.fR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        PRobot.bR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        PRobot.fL.setPower(speed);
        PRobot.bL.setPower(speed);
        PRobot.fR.setPower(speed);
        PRobot.bR.setPower(speed);

        while (PRobot.fL.isBusy() && PRobot.bL.isBusy() && PRobot.fR.isBusy() && PRobot.bR.isBusy()) {
        }
        PRobot.fL.setPower(0);
        PRobot.bL.setPower(0);
        PRobot.fR.setPower(0);
        PRobot.bR.setPower(0);
        return;
    }
}

