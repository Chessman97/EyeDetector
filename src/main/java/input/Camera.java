package input;

import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class Camera extends VideoCapture {

    public Camera(int index, int width, int height) {
        super(index);
        if (!isOpened()) {
            System.out.println("Camera is not open.");
            System.exit(1);
        }
        set(Videoio.CAP_PROP_FRAME_WIDTH, width);
        set(Videoio.CAP_PROP_FRAME_HEIGHT, height);
    }

}
