import input.Camera;
import model.Detector;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import view.Window;

public class Main {

    private static Window window = new Window();
    private static Detector detector = new Detector();
    private static Camera camera = new Camera(1, 800, 600);

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        while (true) {
            Mat img = new Mat();
            camera.read(img);

            Imgproc.medianBlur(img, img, 5);
            Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);

            detector.work(img);

            window.show(img);
        }
    }
}
