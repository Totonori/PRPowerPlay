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
            PRobot.drive(gamepad1.left_stick_y * .7, -gamepad1.left_stick_y * .7, gamepad1.left_stick_y * .7, -gamepad1.left_stick_y * .7);
            telemetry.addData("Cha Cha Now Y'all", -gamepad1.left_stick_y);
            telemetry.addLine();
            telemetry.update();

        } else if (gamepad1.right_stick_x < 0 || gamepad1.right_stick_x > 0) {
            PRobot.drive(-gamepad1.right_stick_x * .5);
            telemetry.addData("Chris Cross", gamepad1.right_stick_x);
            telemetry.addLine();
            telemetry.update();
        } else if (gamepad1.dpad_left) {
            PRobot.fL.setPower(-0.6);
            PRobot.bL.setPower(0.6);
            PRobot.fR.setPower(0.6);
            PRobot.bR.setPower(-0.6);
            telemetry.addData("Slide to the Left", gamepad1.left_bumper);
            telemetry.update();
        } else if (gamepad1.dpad_right) {
            PRobot.fL.setPower(0.6);
            PRobot.bL.setPower(-0.6);
            PRobot.fR.setPower(-0.6);
            PRobot.bR.setPower(0.6);
            telemetry.addData("Slide to the Right", gamepad1.right_bumper);
            telemetry.update();
        } else {

            PRobot.stopWheels();
        }

        if (gamepad2.dpad_up) {
            PRobot.chachaslide.setPower(-0.7);
            telemetry.addData("Can you bring it to the top", gamepad2.dpad_up);
            telemetry.update();
        } else if (gamepad2.dpad_down) {
            PRobot.chachaslide.setPower(0.7);
            telemetry.addData("How low can you go", gamepad2.dpad_down);
            telemetry.update();
        } else {
            PRobot.chachaslide.setPower(0);

        }
        if (gamepad2.left_bumper) {
            PRobot.claw1.setPosition(.5);
            PRobot.claw2.setPosition(.6);
            telemetry.addData("Left foot 2 stomp", gamepad2.left_bumper);
            telemetry.update();
        } else {
            PRobot.claw1.setPosition(.9);
            PRobot.claw2.setPosition(.2);
        }
        if (gamepad2.x) {
            PRobot.arm1.setPower(0.625);
            telemetry.addData("Reverse", gamepad2.x);
            telemetry.update();
        } else if (gamepad2.y) {
            PRobot.arm1.setPower(-0.625);
            telemetry.addData("Reverse Reverse", gamepad2.y);
            telemetry.update();
        } else {
            PRobot.arm1.setPower(0);
        }
        if (gamepad2.a) {
            PRobot.coneDropper.setPosition(1);
            telemetry.addData("Charlie Brown", gamepad2.a);
            telemetry.update();
        } else {
            PRobot.coneDropper.setPosition(0.1);
        }
        if (gamepad2.right_trigger > .1) {
            PRobot.spinnyArm.setPosition(0.125);
        } else {
            PRobot.spinnyArm.setPosition(0.475);
        }
    }
}



