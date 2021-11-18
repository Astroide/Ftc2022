package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.opencv.core.Mat;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

@TeleOp(name = "TeleOp", group = "Default")
public class MainOpMode extends OpMode {
    private ElapsedTime time;
    private DcMotor leftMotor = null, rightMotor = null;
    private int cameraMonitorViewId;
    private OpenCvCamera camera;

    @Override
    public void init() {
        leftMotor = hardwareMap.get(DcMotor.class, "left_motor");
        rightMotor = hardwareMap.get(DcMotor.class, "right_motor");
        cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        telemetry.addData("Status", "Initialization completed");
//        telemetry.update();
        camera = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
//        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
//            @Override
//            public void onOpened() {
//                camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
//                camera.setPipeline(new OpenCvPipeline() {
//                    @Override
//                    public Mat processFrame(Mat input) {
//                        return null;
//                    }
//                });
//            }
//
//            @Override
//            public void onError(int errorCode) {
//                telemetry.addData("Error", "OpenCV error : " + errorCode);
//            }
//        });
    }

    @Override
    public void start() {
        time.reset();
    }

    @Override
    public void loop() {
        double leftPower, rightPower;

        double drive = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;
        leftPower = Range.clip(drive + turn, -1.0, 1.0);
        rightPower = Range.clip(drive - turn, -1.0, 1.0);
        leftMotor.setPower(leftPower);
        rightMotor.setPower(rightPower);
        telemetry.addData("Status", "Elapsed Time: " + time.toString());
        telemetry.addData("Motors", "Left %.2f Right %.2f", leftPower, rightPower);
        telemetry.update();

    }
}
