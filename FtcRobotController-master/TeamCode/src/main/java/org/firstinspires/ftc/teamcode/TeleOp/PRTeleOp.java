package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

@TeleOp(name="PRTeleOp", group = "TeleOp")
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
            telemetry.addData("Cha Cha Now Y'all",-gamepad1.left_stick_y);
            telemetry.addLine();
            telemetry.update();

        }
        else if (gamepad1.right_stick_x < 0 || gamepad1.right_stick_x > 0){
            PRobot.drive(-gamepad1.right_stick_x*.7);
            telemetry.addData("Chris Cross", gamepad1.right_stick_x);
            telemetry.addLine();
            telemetry.update();
        }
        else if (gamepad1.dpad_left){
            PRobot.fL.setPower(0.4);
            PRobot.bL.setPower(-0.4);
            PRobot.fR.setPower(0.4);
            PRobot.bR.setPower(-0.4);
            telemetry.addData("Slide to the Left",gamepad1.left_bumper);
            telemetry.update();
        }
        else if (gamepad1.dpad_right){
            PRobot.fL.setPower(-0.4);
            PRobot.bL.setPower(0.4);
            PRobot.fR.setPower(-0.4);
            PRobot.bR.setPower(0.4);
            telemetry.addData("Slide to the Right",gamepad1.right_bumper);
            telemetry.update();
        }
        else {

            PRobot.stopWheels();
        }

        if (gamepad2.dpad_up){
            PRobot.chachaslide.setPower(0.7);
            telemetry.addData("Can you bring it to the top",gamepad2.dpad_up);
            telemetry.update();
        }
        else if (gamepad2.dpad_down){
            PRobot.chachaslide.setPower(-0.7);
            telemetry.addData("How low can you go",gamepad2.dpad_down);
            telemetry.update();
        }
        else{
            PRobot.chachaslide.setPower(0);

        }
        if (gamepad2.left_bumper){
            PRobot.claw1.setPower(1);
            PRobot.claw2.setPower(-1);
            telemetry.addData("Left foot 2 stomp",gamepad2.left_bumper);
            telemetry.update();
        }
        else if (gamepad2.right_bumper){
            PRobot.claw1.setPower(-1);
            PRobot.claw2.setPower(1);
            telemetry.addData("Right foot 2 stomp",gamepad2.right_bumper);
            telemetry.update();
        }
        else {
            PRobot.claw2.setPower(0);
            PRobot.claw1.setPower(0);
        }
        if (gamepad2.left_trigger > .1){
            PRobot.arm.setPower(1);
            telemetry.addData("Reverse",gamepad2.left_trigger);
            telemetry.update();
        }
        else if (gamepad2.right_trigger > .1){
            PRobot.arm.setPower(-1);
            telemetry.addData("Reverse Reverse",gamepad2.right_trigger);
            telemetry.update();
        }
        else {
            PRobot.arm.setPower(0);
        }
        if (gamepad2.a){
            PRobot.thatonething.setPower(1);
            telemetry.addData("Charlie Brown",gamepad2.a);
            telemetry.update();
        }
        else if (gamepad2.b){
            PRobot.thatonething.setPower(-1);
            telemetry.addData("Hop it out now",gamepad2.b);
            telemetry.update();
        }
        else {
            PRobot.thatonething.setPower(0);
        }
        }
    }



