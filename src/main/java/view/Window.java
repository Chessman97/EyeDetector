package view;

import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Window extends JFrame {

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
        JLabel label = new JLabel(imageIcon);
        label.setDisabledIcon(imageIcon);
        JScrollPane pane = new JScrollPane(label);
        setContentPane(pane);
        setVisible(true);
    }

    public void setState(int numberState) {
        if (numberState == 1 || numberState == 3) {
            setImage("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\wolf1.png");
            if (numberState == 1) {
                setImage("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\hand1.png");
            } else {
                setImage("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\hand3.png");
            }
        }
        if (numberState == 2 || numberState == 4) {
            setImage("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\wolf2.png");
            if (numberState == 2) {
                setImage("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\hand2.png");
            } else {
                setImage("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\hand4.png");
            }
        }
    }

    private void setImage(String path) {
        try {
            BufferedImage bi = ImageIO.read(new File(path));
            ImageIcon imageIcon = new ImageIcon(bi);
            JLabel label = new JLabel(imageIcon);
            label.setDisabledIcon(imageIcon);
            JScrollPane pane = new JScrollPane(label);
            setContentPane(pane);
            setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
