package org.firstinspires.ftc.teamcode.CVPipelines;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class OrangePipeline extends OpenCvPipeline {
    private int width;

    public OrangePipeline() {

    }

    public OrangePipeline(int width) {
        this.width = width;
    }

    @Override
    public Mat processFrame(Mat input) {
        Mat mat = new Mat();
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
        if (mat.empty()) {
            return input;
        }
        Scalar lowHSV = new Scalar(36, 70, 80);
        Scalar highHSV = new Scalar(38, 100, 100);

        Mat thresh = new Mat();

        Core.inRange(mat, lowHSV, highHSV, thresh);

        Mat masked = new Mat();
        Core.bitwise_and(mat, mat, masked, thresh);

        Scalar average = Core.mean(masked, thresh);

        Mat scaledmask = new Mat();
        masked.convertTo(scaledmask, );

        Mat edges = new Mat();
        Imgproc.Canny(thresh, edges, 100, 300);

        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(edges, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        MatOfPoint2f[] contoursPoly = new MatOfPoint2f[contours.size()];
        Rect[] boundRect = new Rect[contours.size()];
        for (int i = 0; i < contours.size(); i++) {
            contoursPoly[i] = new MatOfPoint2f();
            Imgproc.approxPolyDP(new MatOfPoint2f(contours.get(i).toArray()), contoursPoly[i], 3, true);
            boundRect[i] = Imgproc.boundingRect(new MatOfPoint(contoursPoly[i].toArray()));


        }

        double middle_x = .5 * width;
        double filler_x = 1 * width;
        boolean middle = false; // true if regular stone found in the middle side
        boolean filler = false; // "" "" on the right side
        for (int i = 0; i != boundRect.length; i++) {
            if (boundRect[i].x < middle_x)
                middle = true;
            if (boundRect[i].x + boundRect[i].width > filler_x)
                filler = true;

            Imgproc.rectangle(mat, boundRect[i], new Scalar(0.5, 76.9, 89.8));
        }
        return mat;
    }
}
