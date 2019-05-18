package model;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.util.ArrayList;

public class Detector {

    private static int count = 0;
    private int point_size = 10;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private CascadeClassifier face_detector = new CascadeClassifier();
    private CascadeClassifier eye_detector = new CascadeClassifier();

    public Detector() {
        String path = "C:\\Users\\Mikhail\\IdeaProjects\\openCV\\src\\main\\resources\\haarcascades\\";
        String name = "haarcascade_frontalface_alt.xml";
        if (!face_detector.load(path + name)) {
            System.out.println("Не удалось загрузить классификатор " + name);
            System.exit(1);
        }

        name = "haarcascade_eye.xml";
        if (!eye_detector.load(path + name)) {
            System.out.println("Не удалось загрузить классификатор " + name);
            System.exit(1);
        }
    }

    public int[] work(Mat img) {
        int xEye1 = 0;
        int yEye1 = 0;
        int xPoint1 = 0;
        int yPoint1 = 0;
        int xEye2 = 0;
        int yEye2 = 0;
        int xPoint2 = 0;
        int yPoint2 = 0;

        MatOfRect faces = new MatOfRect();
        face_detector.detectMultiScale(img, faces);
        for (Rect r : faces.toList()) {
            Mat face = img.submat(r);
            MatOfRect eyes = new MatOfRect();
            eye_detector.detectMultiScale(face, eyes);
            for (Rect r2 : eyes.toList()) {
                if (r2.y < (r.height) / 2 && r2.width > 60) {
                    Mat eye = face.submat(r2);
                    Imgproc.medianBlur(eye, eye, 5);
                    int k = 0;
                    for (int i = 0; i < eye.rows(); i++) {
                        for (int j = 0; j < eye.cols(); j++) {
                            k += eye.get(i, j)[0];
                        }
                    }
                    k /= eye.cols() * eye.rows();
                    k *= 0.8;
                    for (int i = 0; i < eye.rows(); i++) {
                        for (int j = 0; j < eye.cols(); j++) {
                            if (eye.get(i,j)[0] > k) {
                                eye.put(i, j, 255);
                            } else {
                                eye.put(i, j, 0);
                            }
                        }
                    }
//                    Imgproc.rectangle(face, new Point(r2.x, r2.y), new Point(r2.x + r2.width, r2.y + r2.height), new Scalar(1, 1, 1, 1), 2);
//                    Imgproc.line(face, new Point(r2.x, r2.y), new Point(r2.x + r2.width, r2.y + r2.height), new Scalar(1, 1, 1, 1), 2);
//                    Imgproc.line(face, new Point(r2.x + r2.width, r2.y), new Point(r2.x, r2.y + r2.height), new Scalar(1, 1, 1, 1), 2);

                    Imgproc.Canny(eye, eye, 80, 200);
                    ArrayList<MatOfPoint> contours = new ArrayList<>();
                    Imgproc.findContours(eye, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
//                    System.out.println(contours.size() + " " + point_size);

                    int contours_size = 0;
                    for (MatOfPoint contour : contours) {
                        Rect r3 = Imgproc.boundingRect(contour);
                        if (r3.width > point_size) {
                            if (xEye1 == 0) {
                                xEye1 = r2.width / 2;
                                yEye1 = r2.height / 2;
                                xPoint1 = r3.x + r3.width / 2;
                                yPoint1 = r3.y + r3.height / 2;
                            } else if (xEye2 == 0) {
                                xEye2 = r2.width / 2;
                                yEye2 = r2.height / 2;
                                xPoint2 = r3.x + r3.width / 2;
                                yPoint2 = r3.y + r3.height / 2;
                            }
                            Imgproc.line(eye, new Point(xEye1, 0), new Point(xEye1, 600), new Scalar(255, 255, 255, 255), 2);
                            Imgproc.line(eye, new Point(xPoint1, 0), new Point(xPoint1, 600), new Scalar(255, 255, 255, 255), 1);
                            Imgproc.line(eye, new Point(0, yEye1), new Point(600, yEye1), new Scalar(255, 255, 255, 255), 2);
                            Imgproc.line(eye, new Point(0, yPoint1), new Point(600, yPoint1), new Scalar(255, 255, 255, 255), 1);
                            contours_size++;
//                            Imgproc.rectangle(eye, new Point(r3.x, r3.y), new Point(r3.x + r3.width - 1, r3.y + r3.height - 1), new Scalar(255, 255, 255, 255), 1);
//                            Imgproc.line(eye, new Point(r3.x, r3.y), new Point(r3.x + r3.width - 1, r3.y + r3.height - 1), new Scalar(255, 255, 255, 255), 1);
//                            Imgproc.line(eye, new Point(r3.x + r3.width - 1, r3.y), new Point(r3.x, r3.y + r3.height - 1), new Scalar(255, 255, 255, 255), 1);
                        }
                    }

                    if (contours_size > 1) {
                        point_size += 1;
                    } else if (contours_size < 1) {
                        point_size -= 1;
                    }
                }
            }
//            Imgproc.rectangle(img, new Point(r.x, r.y), new Point(r.x + r.width, r.y + r.height), new Scalar(1, 1, 1, 1), 2);
//            Imgproc.line(img, new Point(r.x, r.y), new Point(r.x + r.width, r.y + r.height), new Scalar(1, 1, 1, 1), 2);
//            Imgproc.line(img, new Point(r.x + r.width, r.y), new Point(r.x, r.y + r.height), new Scalar(1, 1, 1, 1), 2);
        }
        return new int[]{xEye1, yEye1, xPoint1, yPoint1, xEye2, yEye2, xPoint2, yPoint2};
    }
}
