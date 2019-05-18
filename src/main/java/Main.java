import input.Camera;
import model.Detector;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import view.Window;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {

    private static int swap = 0;
    private static int dopSwap = 0;
    private static Window window = new Window();
    private static Detector detector = new Detector();
    private static Camera camera = new Camera(1, 640, 480);

    private static int xLeftEye = 0;
    private static int xLeftPoint = 0;
    private static int widthLeftPoint = 0;
    private static int xMyPoint = 0;

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
            int xEye1 = points[0];
            int yEye1 = points[1];
            int widthEye1 = points[2];
            int xPoint1 = points[3];
            int yPoint1 = points[4];
            int widthPoint1 = points[5];
            int xEye2 = points[6];
            int yEye2 = points[7];
            int widthEye2 = points[8];
            int xPoint2 = points[9];
            int yPoint2 = points[10];
            int widthPoint2 = points[11];
            int xFace = points[12];
            int yFace = points[13];


            if (xEye1 < xEye2) {
                xLeftEye = xEye1;
                xLeftPoint = xPoint1;
                widthLeftPoint = widthPoint1;
            } else {
                xLeftEye = xEye2;
                xLeftPoint = xPoint2;
                widthLeftPoint = widthPoint2;
            }

            int xMain = xFace + xLeftEye + xLeftPoint + widthLeftPoint / 2;
            Imgproc.line(img, new Point((double) img.width() / 2, 0), new Point((double) img.width() / 2, 600), new Scalar(255, 255, 255, 255), 2);
            Imgproc.line(img, new Point(xMain, 0), new Point(xMain, 600), new Scalar(255, 255, 255, 255), 2);
            Imgproc.line(img, new Point(xFace + xLeftEye + xMyPoint, 0), new Point(xFace + xLeftEye + xMyPoint, 600), new Scalar(1, 1, 1, 255), 2);

            window.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (xLeftEye > 0 && xLeftEye < img.width() / 2) {
                        if (xMain > xFace + xLeftEye + xMyPoint) {
                            xMyPoint += 1;
                        } else if (xMain < xFace + xLeftEye + xMyPoint) {
                            xMyPoint -= 1;
                        }
                    }
                    System.out.println(xMyPoint);
                }
            });

            Core.flip(img, img, 1);

            if (xMain > xFace + xLeftEye + xMyPoint) {
                if (swap < 0) {
                    drawRight(img);
                    swap = 0;
                } else {
                    drawLeft(img);
                    dopSwap++;
                    if (dopSwap > 15) {
                        swap = 1;
                    }
                }
            } else if (xMain < xFace + xLeftEye + xMyPoint) {
                if (swap > 0) {
                    drawLeft(img);
                    swap = 0;
                } else {
                    drawRight(img);
                    dopSwap--;
                    if (dopSwap < -15) {
                        swap = -1;
                    }
                }
            }

            window.show(img);
        }
    }

    private static void drawLeft(Mat img) {
        for (int i = 0; i < img.rows(); i++) {
            for (int j = 0; j < img.cols() / 2; j++) {
                img.put(i, j, img.get(i, j)[0] - 80);
            }
        }
    }

    private static void drawRight(Mat img) {
        for (int i = 0; i < img.rows(); i++) {
            for (int j = img.cols() / 2; j < img.cols(); j++) {
                img.put(i, j, img.get(i, j)[0] - 80);
            }
        }
    }
}
