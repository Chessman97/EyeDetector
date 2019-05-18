package view;

import org.opencv.core.Mat;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    JButton button = new JButton();

    public Window() {
        setTitle("EggCatcher");
        setSize(800, 600);
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(null);
//        add(button);
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
        getGraphics().clearRect(0,0,1920, 1080);
        if (numberState == 1 || numberState == 3) {
            if (numberState == 1) {
                setImage("/home/vladimir/IdeaProjects/EggsCatcher/src/main/resources/img/hand1.png", 1);
            } else {
                setImage("/home/vladimir/IdeaProjects/EggsCatcher/src/main/resources/img/hand3.png", 3);
            }
            setWolf("/home/vladimir/IdeaProjects/EggsCatcher/src/main/resources/img/wolf1.png");
        }
        if (numberState == 2 || numberState == 4) {
            if (numberState == 2) {
                setImage("/home/vladimir/IdeaProjects/EggsCatcher/src/main/resources/img/hand2.png", 2);
            } else {
                setImage("/home/vladimir/IdeaProjects/EggsCatcher/src/main/resources/img/hand4.png", 4);
            }
            setWolf("/home/vladimir/IdeaProjects/EggsCatcher/src/main/resources/img/wolf2.png");
        }
    }

    private void setWolf(String path) {
        getGraphics().drawImage(new ImageIcon(path).getImage(), 700, 0,null);
    }

    private void setImage(String path, int n) {
        int x = 0;
        int y = 0;
        if (n == 1) {
            x = 0;
            y = 0;
        }
        if (n == 2) {
            x = 1920 - 460;
            y = 0;
        }
        if (n == 3) {
            x = 0;
            y = 1080 - 330;
        }
        if (n == 4) {
            x = 1920 - 460;
            y = 1080 - 330;
        }
        getGraphics().drawImage(new ImageIcon(path).getImage(), x,y,null);
    }
}
