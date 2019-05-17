import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import view.Window;

public class Main {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        Mat img = Imgcodecs.imread("C:\\Users\\Mikhail\\IdeaProjects\\openCV\\src\\main\\resources\\test2.jpg");
        if (img.empty()) {
            System.out.println("Не удалось найти изображение");
        }

        CascadeClassifier face_detector = new CascadeClassifier();
        String path = "C:\\Users\\Mikhail\\IdeaProjects\\openCV\\src\\main\\resources\\haarcascades\\";
        String name = "haarcascade_frontalface_alt.xml";
        if (!face_detector.load(path + name)) {
            System.out.println("Не удалось загрузить классификатор " + name);
            return;
        }

        Imgproc.medianBlur(img, img, 3);
        Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);

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
            for (Rect r2 : eyes.toList()) {
                if (r2.y < (r.x + r.width) / 2) {
                    Mat eye = face.submat(r2);
                    Imgproc.threshold(eye, eye, 100, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
                    Imgproc.rectangle(face, new Point(r2.x, r2.y), new Point(r2.x + r2.width, r2.y + r2.height), new Scalar(1, 1, 1, 2), 2);
                }
            }
            Imgproc.rectangle(img, new Point(r.x, r.y), new Point(r.x + r.width, r.y + r.height), new Scalar(1, 1, 1, 1), 2);
        }

        Window window = new Window();
        window.show(img);
    }
}
