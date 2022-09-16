package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="org.firstinspires.ftc.teamcode.TeleOp.PRTeleOp")
public class PRTeleOp extends LinearOpMode {
    public DcMotor leftDrive;
    public DcMotor rightDrive;
    public DcMotor  leftArm;
    public Servo leftClaw;
    public Servo rightClaw;

    @Override
    public void runOpMode() {
        double left;
        double right;
        double drive;
        double turn;
        double clawopen;
        double clawclose;
        double clawup;
        double clawdown;

        leftDrive  = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        leftArm    = hardwareMap.get(DcMotor.class, "left_arm");

        leftClaw  = hardwareMap.get(Servo.class, "left_hand");
        rightClaw = hardwareMap.get(Servo.class, "right_hand");

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);

        drive = gamepad1.left_stick_y;
        turn  =  gamepad1.right_stick_x;

        left  = drive + turn;
        right = drive - turn;
    }


}
