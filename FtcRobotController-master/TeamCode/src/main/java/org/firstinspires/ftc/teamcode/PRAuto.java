package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="PRAuto")
public class PRAuto extends LinearOpMode {

    private DcMotor leftDrive;
    private DcMotor rightDrive;

    private ElapsedTime runtime = new ElapsedTime();

    static final double Counts_Per_Rev = 1440;
    static final double Drive_Gear_Reduction = 1.0;
    static final double Wheel_Diameter_In = 3;
    static final double Counts_Per_In = (Counts_Per_Rev * Drive_Gear_Reduction) / (Wheel_Diameter_In * 3.1415);
    static final double Drive_Speed = .5;
    static final double Turn_Speed = .3;

    @Override
    public void runOpMode() throws InterruptedException {

        //Initialize
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");

        //Reversed Axel Direction
        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Prove encoder reset
        telemetry.addData("Starting at", "%7d :%7d",
                leftDrive.getCurrentPosition(),
                rightDrive.getCurrentPosition());
        telemetry.update();

        waitForStart();

        //Each leg of the path
        encoderDrive(Drive_Speed, 48, 48, 5.0);
        encoderDrive(Turn_Speed, 12, -12, 4.0);
        encoderDrive(Drive_Speed, -24, -24, 4.0);

        //Proves completed path
        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);

    }

    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        //Ensure opmode is still active
        if (opModeIsActive()) {

            //Determine new target position/send to controller
            newLeftTarget = leftDrive.getCurrentPosition() + (int)(leftInches * Counts_Per_In);
            newRightTarget = rightDrive.getCurrentPosition() + (int)(rightInches * Counts_Per_In);
            leftDrive.setTargetPosition(newLeftTarget);
            rightDrive.setTargetPosition(newRightTarget);

            //Turns on RUN_TO_POSITION
            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //resets timeout, starts motion
            runtime.reset();
            leftDrive.setPower(Math.abs(speed));
            rightDrive.setPower(Math.abs(speed));

            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (leftDrive.isBusy() && rightDrive.isBusy())) {

                telemetry.addData("Running to", "%7d :%7d",
                        leftDrive.getCurrentPosition(), rightDrive.getCurrentPosition());
                telemetry.update();
            }

            //stop all motion
            leftDrive.setPower(0);
            rightDrive.setPower(0);

            //Turn off RUN_TO_POSITION
            leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);
        }
    }
}
