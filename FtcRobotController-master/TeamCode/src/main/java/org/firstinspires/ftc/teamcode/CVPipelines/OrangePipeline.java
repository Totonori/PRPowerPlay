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
    enum SleevePosition {
        Middle,
        None
    }

    private int width;
    SleevePosition location;

    public OrangePipeline(int width) {
        this.width = width;
    }

    @Override
    public Mat processFrame(Mat input) {
        Mat mat = new Mat();
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);

        if (mat.empty()) {
            location = SleevePosition.None;
            return input;
        }
        Scalar lowHSV = new Scalar(23, 100, 86);
        Scalar highHSV = new Scalar(38, 100, 100);
        Mat thresh = new Mat();

        Core.inRange(mat, lowHSV, highHSV, thresh);

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

        double middle_x = 0.25 * width;
        double none_x = 0.75 * width;
        boolean middle = false; // true if regular stone found on the left side
        boolean none = false; // "" "" on the right side
        for (int i = 0; i != boundRect.length; i++) {
            if (boundRect[i].x < middle_x)
                middle = true;
            if (boundRect[i].x + boundRect[i].width > none_x)
                none = true;

            Imgproc.rectangle(mat, boundRect[i], new Scalar(0.5, 76.9, 89.8));
        }

        if (!middle) location = SleevePosition.Middle;
        else location = SleevePosition.None;

        return mat;
    }
    public SleevePosition getLocation(){
        return this.location;
    }
}
