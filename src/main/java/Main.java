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
import java.io.*;
import java.util.Scanner;

public class Main {

    private static boolean tab = false;
    private static double procent = 0;
    private static double procent2 = 0;
    private static boolean start = false;

    private static int xMain;
    private static int yMain;
    private static int xFace;
    private static int yFace;

    private static Window window = new Window();
    private static Detector detector = new Detector();
    private static Camera camera = new Camera(1, 640, 480);

    private static int xLeftEye = 0;
    private static int xLeftPoint = 0;
    private static int widthLeftEye = 0;
    private static int heightLeftEye = 0;
    private static int widthLeftPoint = 0;
    private static int heightLeftPoint = 0;
    private static int xMyPoint = 0;
    private static int yLeftEye = 0;
    private static int yLeftPoint = 0;
    private static int yidthLeftPoint = 0;
    private static int yMyPoint = 0;
    private static boolean keyboardState;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        loadMyPoint();

        Mat img = new Mat();
        int[] points;
        int xEye1;
        int yEye1;
        int widthEye1;
        int xPoint1;
        int yPoint1;
        int widthPoint1;
        int xEye2;
        int yEye2;
        int widthEye2;
        int xPoint2;
        int yPoint2;
        int widthPoint2;
        int heightPoint1;
        int heightPoint2;

        while (true) {
            camera.read(img);

            Imgproc.medianBlur(img, img, 5);
            Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);

            points = detector.work(img);
            xEye1 = points[0];
            yEye1 = points[1];
            widthEye1 = points[2];
            heightLeftEye = points[2];
            xPoint1 = points[3];
            yPoint1 = points[4];
            widthPoint1 = points[5];
            xEye2 = points[6];
            yEye2 = points[7];
            widthEye2 = points[8];
            xPoint2 = points[9];
            yPoint2 = points[10];
            widthPoint2 = points[11];
            xFace = points[12];
            yFace = points[13];
            heightPoint1 = points[14];
            heightPoint2 = points[15];

            if (xEye1 < xEye2) {
                xLeftEye = xEye1;
                yLeftEye = yEye1;
                widthLeftEye = widthEye1;
                xLeftPoint = xPoint1;
                yLeftPoint = yPoint1;
                heightLeftPoint = heightPoint1;
                widthLeftPoint = widthPoint1;
            } else {
                xLeftEye = xEye2;
                yLeftEye = yEye2;
                widthLeftEye = widthEye2;
                xLeftPoint = xPoint2;
                yLeftPoint = yPoint2;
                heightLeftPoint = heightPoint2;
                widthLeftPoint = widthPoint2;
            }

            xMain = xFace + xLeftEye + xLeftPoint + widthLeftPoint / 2;
            yMain = yFace + yLeftEye + yLeftPoint + heightLeftPoint / 2;
            Imgproc.line(img, new Point(0, yMain), new Point(640, yMain), new Scalar(255, 255, 255, 255), 2);
            Imgproc.line(img, new Point((double) img.width() / 2, 0), new Point((double) img.width() / 2, 600), new Scalar(255, 255, 255, 255), 1);
            Imgproc.line(img, new Point(0, (double) img.height() / 2), new Point(640, (double) img.height() / 2), new Scalar(255, 255, 255, 255), 1);
            Imgproc.line(img, new Point(xMain, 0), new Point(xMain, 600), new Scalar(255, 255, 255, 255), 2);
            Imgproc.line(img, new Point(xFace + xLeftEye + procent * widthLeftEye, 0), new Point(xFace + xLeftEye + procent * widthLeftEye, 600), new Scalar(1, 1, 1, 255), 2);
            Imgproc.line(img, new Point(0, yFace + yLeftEye + procent2 * heightLeftEye), new Point(640, yFace + yLeftEye + procent2 * heightLeftEye), new Scalar(1, 1, 1, 255), 2);

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
                    procent = (double) xMyPoint / widthLeftEye;
                    procent2 = (double) yMyPoint / heightLeftEye;

                    tab = false;
                    if (e.getKeyChar() == 'r' && !tab) {
                        keyboardState = false;
                        tab = true;
                    }
                    if (e.getKeyChar() == 's' && !tab) {
                        start = !start;
                        tab = true;
                    }
                    if (e.getKeyChar() == 'e' && !tab) {
                        saveMyPoint();
                        System.exit(0);
                    }
                    if (e.getKeyChar() == '1' && !tab) {
                        window.setState(1);
                        keyboardState = true;
                        tab = true;
                    }
                    if (e.getKeyChar() == '2' && !tab) {
                        window.setState(2);
                        keyboardState = true;
                        tab = true;
                    }
                    if (e.getKeyChar() == '3' && !tab) {
                        window.setState(3);
                        keyboardState = true;
                        tab = true;
                    }
                    if (e.getKeyChar() == '4' && !tab) {
                        window.setState(4);
                        keyboardState = true;
                        tab = true;
                    }
                    if (start) {
                        window.repaint();
                    }
                }
            });

            Core.flip(img, img, 1);
            if (!keyboardState) {
                if (xMain - (xFace + xLeftEye + xMyPoint) > 0) {
                    if (yMain - (yFace + yLeftEye + yMyPoint) > -2) {
                        if (start) {
                            window.setState(3);
                        } else {
                            drawLeftBottom(img);
                        }
                    } else {
                        if (start) {
                            window.setState(1);
                        } else {
                            drawLeftTop(img);
                        }
                    }
                } else {
                    if (yMain - (yFace + yLeftEye + yMyPoint) > -2) {
                        if (start) {
                            window.setState(4);
                        } else {
                            drawRightBottom(img);
                        }
                    } else {
                        if (start) {
                            window.setState(2);
                        } else {
                            drawRightTop(img);
                        }
                    }
                }
            }

            if (start) {
                window.repaint();
            } else {
                window.show(img);
            }
        }
    }

    private static void loadMyPoint() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\xy.txt"));
            String s =bufferedReader.readLine();
            Scanner sc = new Scanner(s);
            xMyPoint = sc.nextInt();
            yMyPoint = sc.nextInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveMyPoint() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\xy.txt"));
            bufferedWriter.write(xMyPoint + " " + yMyPoint + "\n");
            bufferedWriter.close();
        } catch (IOException e1) {
            e1.printStackTrace();
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
