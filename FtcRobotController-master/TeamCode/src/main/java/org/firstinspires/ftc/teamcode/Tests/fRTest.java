package Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware.Robot;
@TeleOp (name = "fRTest", group = "Tests")
public class fRTest extends OpMode {

    Robot PRobot = new Robot();

    @Override
    public void init() {
        PRobot.init(hardwareMap);
    }

    @Override
    public void loop() {
        if (gamepad1.a) {
            PRobot.fR.setPower(1);

        }
    }
}
