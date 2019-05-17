import input.Camera;
import model.Detector;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import view.Window;

public class Main {

    private static Window window = new Window();
    private static Detector detector = new Detector();
    private static Camera camera = new Camera(1, 640, 480);

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        while (true) {
            Mat img = new Mat();
            camera.read(img);

            Imgproc.medianBlur(img, img, 5);
            Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);

            int[] points = detector.work(img);

            int xEye = points[0];
            int yEye = points[1];
            int xPoint = points[2];
            int yPoint = points[3];

//            System.out.println(xPoint + "  " + xEye);
            if (xPoint > xEye) {
                if (yEye > yPoint) {
                    for (int i = 0; i < img.rows() / 2; i++) {
                        for (int j = 0; j < img.cols() / 2; j++) {
                            img.put(i, j, 0);
                        }
                    }
                } else {
                    for (int i = img.rows() / 2; i < img.rows(); i++) {
                        for (int j = 0; j < img.cols() / 2; j++) {
                            img.put(i, j, 0);
                        }
                    }
                }
            } else {
                if (yEye > yPoint) {
                    for (int i = 0; i < img.rows() / 2; i++) {
                        for (int j = img.cols() / 2; j < img.cols(); j++) {
                            img.put(i, j, 0);
                        }
                    }
                } else {
                    for (int i = img.rows() / 2; i < img.rows(); i++) {
                        for (int j = img.cols() / 2; j < img.cols(); j++) {
                            img.put(i, j, 0);
                        }
                    }
                }
            }

            window.show(img);
        }
    }
}
