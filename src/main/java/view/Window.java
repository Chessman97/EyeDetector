package view;

import org.opencv.core.Mat;

import javax.swing.*;

public class Window extends JFrame {

    public Window() {
        setTitle("Simple GUI");
        setSize(800, 600);
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
}
