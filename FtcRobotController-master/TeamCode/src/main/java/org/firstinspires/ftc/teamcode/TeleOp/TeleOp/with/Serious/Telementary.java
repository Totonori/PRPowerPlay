package org.firstinspires.ftc.teamcode.TeleOp.TeleOp.with.Serious;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

public class Telementary extends OpMode {

    Robot PRobot = new Robot();

    @Override
    public void init() {
        PRobot.init(hardwareMap);
    }

    @Override
    public void loop() {

        if (Math.abs(gamepad1.left_stick_y) > .1) {
            PRobot.drive(gamepad1.left_stick_y*.7,-gamepad1.left_stick_y*.7,gamepad1.left_stick_y*.7,-gamepad1.left_stick_y*.7);
            telemetry.addData("Forward",-gamepad1.left_stick_y);
            telemetry.addLine();
            telemetry.update();

        }
        else if (gamepad1.right_stick_x < 0 || gamepad1.right_stick_x > 0){
            PRobot.drive(-gamepad1.right_stick_x*.5);
            telemetry.addData("Backward", gamepad1.right_stick_x);
            telemetry.addLine();
            telemetry.update();
        }
        else if (gamepad1.dpad_left){
            PRobot.fL.setPower(-0.6);
            PRobot.bL.setPower(0.6);
            PRobot.fR.setPower(0.6);
            PRobot.bR.setPower(-0.6);
            telemetry.addData("Strafe Left",gamepad1.left_bumper);
            telemetry.update();
        }
        else if (gamepad1.dpad_right){
            PRobot.fL.setPower(0.6);
            PRobot.bL.setPower(-0.6);
            PRobot.fR.setPower(-0.6);
            PRobot.bR.setPower(0.6);
            telemetry.addData("Strafe Right",gamepad1.right_bumper);
            telemetry.update();
        }
        else {

            PRobot.stopWheels();
        }

        if (gamepad2.dpad_up){
            PRobot.chachaslide.setPower(-0.7);
            telemetry.addData("slide up",gamepad2.dpad_up);
            telemetry.update();
        }
        else if (gamepad2.dpad_down){
            PRobot.chachaslide.setPower(0.7);
            telemetry.addData("slide down",gamepad2.dpad_down);
            telemetry.update();
        }
        else{
            PRobot.chachaslide.setPower(0);

        }
        if (gamepad2.left_bumper){
            PRobot.claw1.setPosition(.5);
            PRobot.claw2.setPosition(.6);
            telemetry.addData("claw open",gamepad2.left_bumper);
            telemetry.update();
        }
        else {
            PRobot.claw1.setPosition(.9);
            PRobot.claw2.setPosition(.1);
        }
        if (gamepad2.x){
            PRobot.arm1.setPower(0.625);
            telemetry.addData("arm up",gamepad2.x);
            telemetry.update();
        }
        else if (gamepad2.y){
            PRobot.arm1.setPower(-0.625);
            telemetry.addData("arm down",gamepad2.y);
            telemetry.update();
        }
        else {
            PRobot.arm1.setPower(0);
        }
        if (gamepad2.a){
            PRobot.coneDropper.setPosition(1);
            telemetry.addData("dump the bucket",gamepad2.a);
            telemetry.update();
        }
        else {
            PRobot.coneDropper.setPosition(0.1);
        }

        }
    }