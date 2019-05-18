import input.Camera;
import model.Detector;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoWriter;
import org.opencv.videoio.Videoio;
import view.Window;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {

    private static int swap = 0;
    private static int dopSwap = 0;
    private static int fps = 13;
    private static Window window = new Window();
    private static Detector detector = new Detector();
    private static Camera camera = new Camera(1, 640, 480);



    private static int xLeftEye = 0;
    private static int xLeftPoint = 0;
    private static int widthLeftPoint = 0;
    private static int heightLeftPoint = 0;
    private static int xMyPoint = 0;
    private static int yLeftEye = 0;
    private static int yLeftPoint = 0;
    private static int yidthLeftPoint = 0;
    private static int yMyPoint = 0;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {

        VideoWriter writer = new VideoWriter("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\videos\\1.avi", VideoWriter.fourcc('M', 'J', 'P', 'G'), fps, new Size(640, 480), true);
        while (true) {
            Mat img = new Mat();
            if(camera.read(img)){
                System.out.println("Video");
                writer.write(img);
            }

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
            int heightPoint1 = points[14];
            int heightPoint2 = points[15];



            if (xEye1 < xEye2) {
                xLeftEye = xEye1;
                yLeftEye = yEye1;
                xLeftPoint = xPoint1;
                yLeftPoint = yPoint1;
                heightLeftPoint = heightPoint1;
                widthLeftPoint = widthPoint1;
            } else {
                xLeftEye = xEye2;
                yLeftEye = yEye2;
                xLeftPoint = xPoint2;
                yLeftPoint = yPoint2;
                heightLeftPoint = heightPoint2;
                widthLeftPoint = widthPoint2;
            }

            int xMain = xFace + xLeftEye + xLeftPoint + widthLeftPoint / 2;
            int yMain = yFace + yLeftEye + yLeftPoint + heightLeftPoint / 2;
            Imgproc.line(img, new Point(0, yMain), new Point(640, yMain), new Scalar(255, 255, 255, 255), 2);
            Imgproc.line(img, new Point((double) img.width() / 2, 0), new Point((double) img.width() / 2, 600), new Scalar(255, 255, 255, 255), 1);
            Imgproc.line(img, new Point(0, (double)img.height() / 2), new Point(640, (double) img.height() / 2), new Scalar(255, 255, 255, 255), 1);
            Imgproc.line(img, new Point(xMain, 0), new Point(xMain, 600), new Scalar(255, 255, 255, 255), 2);
            Imgproc.line(img, new Point(xFace + xLeftEye + xMyPoint, 0), new Point(xFace + xLeftEye + xMyPoint, 600), new Scalar(1, 1, 1, 255), 2);
            Imgproc.line(img, new Point(0, yFace + yLeftEye + yMyPoint), new Point(640, yFace + yLeftEye + yMyPoint), new Scalar(1, 1, 1, 255), 2);

            window.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (xLeftEye > 0 && xLeftEye < img.width() / 2) {
                        if (xMain > xFace + xLeftEye + xMyPoint) {
                            xMyPoint += 1;
                        } else if (xMain < xFace + xLeftEye + xMyPoint) {
                            xMyPoint -= 1;
                        }
                        if (yMain > yFace + yLeftEye + yMyPoint) {
                            yMyPoint += 1;
                        } else if (yMain < yFace + yLeftEye + yMyPoint) {
                            yMyPoint -= 1;
                        }
                    }
                    System.out.println(xMyPoint);
                }
            });

            Core.flip(img, img, 1);

            // лево
            if (xMain - (xFace + xLeftEye + xMyPoint) > 0) {
                if (yMain - (yFace + yLeftEye + yMyPoint) > -4) {
                    drawLeftBottom(img);
                } else {
                    drawLeftTop(img);
                }
            } else {
                if (yMain - (yFace + yLeftEye + yMyPoint) > -4) {
                    drawRightBottom(img);
                } else {
                    drawRightTop(img);
                }
            }
            window.show(img);
        }
    }

    private static void drawLeftTop(Mat img) {
        for (int i = 0; i < img.rows() / 2; i++) {
            for (int j = 0; j < img.cols() / 2; j++) {
                img.put(i, j, img.get(i, j)[0] - 80);
            }
        }
    }

    private static void drawLeftBottom(Mat img) {
        for (int i = img.rows() / 2; i < img.rows(); i++) {
            for (int j = 0; j < img.cols() / 2; j++) {
                img.put(i, j, img.get(i, j)[0] - 80);
            }
        }
    }

    private static void drawRightTop(Mat img) {
        for (int i = 0; i < img.rows() / 2; i++) {
            for (int j = img.cols() / 2; j < img.cols(); j++) {
                img.put(i, j, img.get(i, j)[0] - 80);
            }
        }
    }

    private static void drawRightBottom(Mat img) {
        for (int i = img.rows() / 2; i < img.rows(); i++) {
            for (int j = img.cols() / 2; j < img.cols(); j++) {
                img.put(i, j, img.get(i, j)[0] - 80);
            }
        }
    }
}
