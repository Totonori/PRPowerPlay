package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

@TeleOp(name="PRTeleOp")
public class PRTeleOp extends OpMode {

    Robot PRobot = new Robot();

    @Override
    public void init() {
        PRobot.init(hardwareMap);
    }

    @Override
    public void loop() {
        if (Math.abs(gamepad1.left_stick_y) > .1) {
            PRobot.drive(gamepad1.left_stick_y*.7,-gamepad1.left_stick_y*.7,gamepad1.left_stick_y*.7,-gamepad1.left_stick_y*.7);
            telemetry.addData("Moving U/D",-gamepad1.left_stick_y);
            telemetry.addLine();
            telemetry.update();

        }
        else if (gamepad1.right_stick_x < 0 || gamepad1.right_stick_x > 0){
            PRobot.drive(-gamepad1.right_stick_x*.7);
            telemetry.addData("Turning L/R", gamepad1.right_stick_x);
            telemetry.addLine();
            telemetry.update();
        }
        else if (gamepad1.left_bumper){
            PRobot.fL.setPower(0.6);
            PRobot.bL.setPower(-0.6);
            PRobot.fR.setPower(0.6);
            PRobot.bR.setPower(-0.6);
            telemetry.addData("Strafe Left",gamepad1.left_bumper);
            telemetry.update();
        }
        else if (gamepad1.right_bumper){
            PRobot.fL.setPower(-0.6);
            PRobot.bL.setPower(0.6);
            PRobot.fR.setPower(-0.6);
            PRobot.bR.setPower(0.6);
            telemetry.addData("Strafe Right",gamepad1.right_bumper);
            telemetry.update();
        }
        else {

            PRobot.stopWheels();
        }

        if (gamepad2.dpad_up){
            PRobot.lift.setPower(1);
            telemetry.addData("Lift Up",gamepad2.dpad_up);
            telemetry.update();
        }
        else if (gamepad2.dpad_down){
            PRobot.lift.setPower(-1);
            telemetry.addData("Lift Down",gamepad2.dpad_down);
            telemetry.update();
        }
        else{
            PRobot.lift.setPower(0);
        }

        if (gamepad2.right_bumper){
            PRobot.spoolControl.setPower(1);
        }
        else {
            PRobot.spoolControl.setPower(0);
        }

        if(gamepad2.left_bumper){
            PRobot.spoolControl.setPower(-1);
        }
        else {
            PRobot.spoolControl.setPower(0);
        }
    }
}


