package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
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
    public Servo spoolControl;
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
        spoolControl = hMap.servo.get("spoolControl");
    }

    //extra functions for quality of life
    public void drive(double power){
        drive(power,power,power,power);
    }
    private void drive(double frL, double frR, double brL, double brR) {
        fL.setPower(dtSpeed*frL);
        fR.setPower(dtSpeed*frR);
        bL.setPower(dtSpeed*brL);
        bR.setPower(dtSpeed*brR);
    }
    public void stopWheels(){
        fR.setPower(0);
        fL.setPower(0);
        bR.setPower(0);
        bL.setPower(0);
    }
}
