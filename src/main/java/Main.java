import model.Detector;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import view.Window;

public class Main {

    private static Detector detector = new Detector();

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        Mat img = Imgcodecs.imread("C:\\Users\\Mikhail\\IdeaProjects\\openCV\\src\\main\\resources\\test2.jpg");
        if (img.empty()) {
            System.out.println("Не удалось найти изображение");
        }

        Imgproc.medianBlur(img, img, 3);
        Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);

        detector.work(img);

        Window window = new Window();
        window.show(img);
    }
}
