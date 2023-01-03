package Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Robot;

@TeleOp(name = "PRClaw", group = "Tests")
public class PRClaw extends OpMode {

    Robot PRobot = new Robot();

    @Override
    public void init() {
        PRobot.init(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            PRobot.claw1.setPower(1);
            PRobot.claw2.setPower(-1);

        }
        else {
            PRobot.claw1.setPower(0);
            PRobot.claw2.setPower(0);
        }
    if (gamepad1.b) {
        PRobot.arm1.setPower(1);

    }
    else {
        PRobot.arm1.setPower(0);

        if (gamepad1.dpad_up) {
            PRobot.chachaslide.setPower(1);
        }
        else if (gamepad1.dpad_down) {
            PRobot.chachaslide.setPower(-1);
        }
        else {
            PRobot.chachaslide.setPower(0);
        }
    }
    }
    }