package view;

import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Window extends JFrame {

    private ImageIcon imageIconWolf1 = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\wolf1.png");
    private ImageIcon imageIconWolf2 = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\wolf2.png");
    private ImageIcon hand1 = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\hand1.png");
    private ImageIcon hand2 = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\hand2.png");
    private ImageIcon hand3 = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\hand3.png");
    private ImageIcon hand4 = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\hand4.png");

    public Window() {
        setTitle("EggCatcher");
        setSize(800, 600);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void show(Mat img) {
        ImageIcon imageIcon = new ImageIcon(SwingUtils.MatToBufferedImage(img));
        getGraphics().drawImage(imageIcon.getImage(), 363, 150,null);
    }

    public void setState(int numberState) {
        getGraphics().clearRect(0,0,1920, 1080);
        if (numberState == 1 || numberState == 3) {
            setWolf(imageIconWolf1);
            if (numberState == 1) {
                setImage(hand1, 1);
            } else {
                setImage(hand3, 3);
            }
        }
        if (numberState == 2 || numberState == 4) {
            setWolf(imageIconWolf2);
            if (numberState == 2) {
                setImage(hand2, 2);
            } else {
                setImage(hand4, 4);
            }
        }
    }

    private void setWolf(ImageIcon imageIcon) {
        getGraphics().drawImage(imageIcon.getImage(), 300, 0,null);
    }

    private void setImage(ImageIcon imageIcon, int n) {
        int x = 0;
        int y = 0;
        if (n == 1) {
            x = 0;
            y = 0;
        }
        if (n == 2) {
            x = 1300 - 460;
            y = 0;
        }
        if (n == 3) {
            x = 0;
            y = 700 - 330;
        }
        if (n == 4) {
            x = 1300 - 460;
            y = 700 - 330;
        }
        getGraphics().drawImage(imageIcon.getImage(), x, y,null);
    }
}
