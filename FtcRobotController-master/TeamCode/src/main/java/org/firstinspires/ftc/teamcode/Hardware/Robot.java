package org.firstinspires.ftc.teamcode.Hardware;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.hardware.motors.RevRoboticsCoreHexMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {
    //Drivetrain
    public DcMotor fL;
    public DcMotor fR;
    public DcMotor bL;
    public DcMotor bR;
    public double dtSpeed = 1;

    //Intake
    public DcMotor lift;
    public CRServo claw1;
    public CRServo claw2;
    //to be continued

    //constuctor
    public Robot() {

    }

    public void init(HardwareMap hMap) {
        //Drivetrain
        fL = hMap.dcMotor.get("fL");
        fR = hMap.dcMotor.get("fR");
        bL = hMap.dcMotor.get("bL");
        bR = hMap.dcMotor.get("bR");

        fL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Intake
        lift = hMap.dcMotor.get("lift");
    }

    //extra functions for quality of life
    public void drive(double power) {
        drive(power, power, power, power);
    }

    public void drive(double frL, double frR, double brL, double brR) {
        fL.setPower(dtSpeed * frL);
        fR.setPower(dtSpeed * frR);
        bL.setPower(dtSpeed * brL);
        bR.setPower(dtSpeed * brR);
    }

    public void stopWheels() {
        fR.setPower(0);
        fL.setPower(0);
        bR.setPower(0);
        bL.setPower(0);
    }
}
