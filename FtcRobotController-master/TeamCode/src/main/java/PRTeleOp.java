import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="PRTeleOp")
public class PRTeleOp extends LinearOpMode {
    public DcMotor leftDrive;
    public DcMotor rightDrive;
    public DcMotor  lift;
    public Servo leftClaw;
    public Servo rightClaw;

    @Override
    public void runOpMode() {
        double left;
        double right;
        double drive;
        double turn;
        boolean liftup;
        boolean liftdown;

        leftDrive  = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        lift    = hardwareMap.get(DcMotor.class, "lift");

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);

        drive = gamepad1.left_stick_y;
        turn  =  gamepad1.right_stick_x;

        left  = drive + turn;
        right = drive - turn;

        liftup = gamepad2.dpad_up;
        liftdown = gamepad2.dpad_down;
    }


}
