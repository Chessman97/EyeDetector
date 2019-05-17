import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class Main {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        Mat img = Imgcodecs.imread("C:\\Users\\Mikhail\\IdeaProjects\\openCV\\src\\main\\resources\\test.jpg");

        CascadeClassifier face_detector = new CascadeClassifier();
        String path = "C:\\Users\\Mikhail\\IdeaProjects\\openCV\\src\\main\\resources\\haarcascades\\";
        String name = "haarcascade_frontalface_alt.xml";
        if (!face_detector.load(path + name)) {
            System.out.println("Не удалось загрузить классификатор " + name);
            return;
        }
        CascadeClassifier eye_detector = new CascadeClassifier();
        name = "haarcascade_eye.xml";
        System.out.println(path + name);
        if (!eye_detector.load(path + name)) {
            System.out.println("Не удалось загрузить классификатор " + name);
            return;
        }

        MatOfRect faces = new MatOfRect();
        face_detector.detectMultiScale(img, faces);
        for (Rect r : faces.toList()) {
            Mat face = img.submat(r);
            MatOfRect eyes = new MatOfRect();
            eye_detector.detectMultiScale(face, eyes);

            int count = 0;
            for (Rect r2 : eyes.toList()) {
                if (count > 1) {
                    break;
                }
                count++;
                Imgproc.rectangle(face, new Point(r2.x, r2.y), new Point(r2.x + r2.width, r2.y + r2.height), new Scalar(1, 1, 1, 1), 1);
            }
            Imgproc.rectangle(img, new Point(r.x, r.y), new Point(r.x + r.width, r.y + r.height), new Scalar(1, 1, 1, 1), 2);
        }

        if (img.empty()) {
            System.out.println("Не удалось найти изображение");
        }
        JFrame frame = new JFrame("Simple GUI");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon imageIcon = new ImageIcon(MatToBufferedImage(img));
        JLabel label = new JLabel(imageIcon);
        JScrollPane pane = new JScrollPane(label);
        frame.setContentPane(pane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static BufferedImage MatToBufferedImage(Mat m) {
        if (m == null || m.empty()) {
            return null;
        }
        if (m.depth() == CvType.CV_8U) {
            System.out.println("Ничего не надо делать");
        } else if (m.depth() == CvType.CV_16U) {
            Mat m_16 = new Mat();
            m.convertTo(m_16, CvType.CV_16U, 255.0 / 65535);
            m = m_16;
        } else if (m.depth() == CvType.CV_32F) {
            Mat m_32 = new Mat();
            m.convertTo(m_32, CvType.CV_8U, 255);
            m = m_32;
        } else {
            return null;
        }
        int type;
        if (m.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (m.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        } else if (m.channels() == 4) {
            type = BufferedImage.TYPE_4BYTE_ABGR;
        } else {
            return null;
        }
        byte[] buf = new byte[m.channels() * m.cols() * m.rows()];
        m.get(0, 0, buf);
        byte tmp;
        if (m.channels() == 4) {
            for (int i = 0; i < buf.length; i += 4) {
                tmp = buf[i + 3];
                buf[i + 3] = buf[i + 2];
                buf[i + 2] = buf[i + 1];
                buf[i + 1] = buf[i];
                buf[i] = tmp;
            }
        }
        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(buf, 0, data, 0, buf.length);
        return image;
    }
}
