package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

public class prSpinnyArm extends OpMode {
    Robot PRobot = new Robot();
    @Override
    public void init() {PRobot.init(hardwareMap);}

    @Override
    public void loop() {
        if (gamepad1.a){
        PRobot.spinnyArm.setPosition(0);
    }
        else{
            PRobot.spinnyArm.setPosition(1);
        }

}
}
