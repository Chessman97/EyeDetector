package model;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.util.ArrayList;
import java.util.List;

public class Detector {

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
        System.out.println(path + name);
        if (!eye_detector.load(path + name)) {
            System.out.println("Не удалось загрузить классификатор " + name);
            System.exit(1);
        }
    }

    public List<Mat> work(Mat img) {
        MatOfRect faces = new MatOfRect();
        face_detector.detectMultiScale(img, faces);
        for (Rect r : faces.toList()) {
            Mat face = img.submat(r);
            MatOfRect eyes = new MatOfRect();
            eye_detector.detectMultiScale(face, eyes);
            for (Rect r2 : eyes.toList()) {
                if (r2.y < (r.height) / 2 && r2.width > 60) { // TODO
                    Mat eye = face.submat(r2);
                    for (int i = 0; i < eye.rows(); i++) {
                        for (int j = 0; j < eye.cols(); j++) {
                            if (eye.get(i,j)[0] > 70) { // TODO
                                eye.put(i, j, 255);
                            } else {
                                eye.put(i, j, 0);
                            }
                        }
                    }
//                    Imgproc.rectangle(face, new Point(r2.x, r2.y), new Point(r2.x + r2.width, r2.y + r2.height), new Scalar(1, 1, 1, 1), 2);

                    Imgproc.Canny(eye, eye, 80, 200);
                    ArrayList<MatOfPoint> contours = new ArrayList<>();
                    Imgproc.findContours(eye, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
                    System.out.println(contours.size());
                    for (MatOfPoint contour : contours) {
                        Rect r3 = Imgproc.boundingRect(contour);
                        if (r3.width > 20) { // TODO
                            Imgproc.rectangle(eye, new Point(r3.x, r3.y), new Point(r3.x + r3.width - 1, r3.y + r3.height - 1), new Scalar(255, 0, 0, 255), 1);
                        }
                    }
                }
            }
//            Imgproc.rectangle(img, new Point(r.x, r.y), new Point(r.x + r.width, r.y + r.height), new Scalar(1, 1, 1, 1), 2);
        }
        return null;
    }
}
