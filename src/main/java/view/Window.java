package view;

import org.opencv.core.Mat;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private boolean egg1Visible = true;

    private ImageIcon imageIconWolf1 = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\wolf1.png");
    private ImageIcon imageIconWolf2 = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\wolf2.png");
    private ImageIcon hand1 = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\hand1.png");
    private ImageIcon hand2 = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\hand3.png");
    private ImageIcon hand3 = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\hand2.png");
    private ImageIcon hand4 = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\hand4.png");
    private ImageIcon egg = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\egg.png");
    private ImageIcon egg1 = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\egg1.png");
    private ImageIcon egg2 = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\egg2.png");
    private ImageIcon egg3 = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\egg3.png");
    private int state;
    private boolean game;

    private double xEgg = -60;
    private double yEgg = -20;

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
        state = numberState;
        game = true;
    }

    private void setImage(ImageIcon imageIcon, int n) {
        int x = 1366 - 850;
        int y = 240;
        getGraphics().drawImage(imageIcon.getImage(), x, y,null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        if (game) {
            g2.setStroke(new BasicStroke(10.0f));  // толщина равна 10
            g2.drawLine(0, 100, 300, 200);
            g2.drawLine(0, 350, 300, 520);
            g2.drawLine(1066, 270, 1390, 100);
            g2.drawLine(1066, 520, 1390, 350);
        }
        if (state == 1 || state == 3) {
            setImage(imageIconWolf1, state);
            if (state == 1) {
                setImageHand(hand1, state);
            }
            else {
                setImageHand(hand3, state);
            }
        }
        if (state == 2 || state == 4) {
            setImage(imageIconWolf2, state);
            if (state == 2){
                setImageHand(hand2, state);
            }
            else{
                setImageHand(hand4, state);
            }
        }
        if (xEgg < 300) {
            xEgg += 9;
            yEgg += 3;
        } else {
            yEgg += 9;
            if (state == 1 && yEgg > 290) {
                egg1Visible = false;
                System.out.println("Вы поймали яйцо!");
            }
        }
        if (egg1Visible) {
            getGraphics().drawImage(egg.getImage(), (int) xEgg, (int) yEgg + 30, null);
        }
    }

    private void setImageHand(ImageIcon imageIcon, int n) {
        int x = 0;
        int y = 0;
        if (n == 1) {
            x = 300;
            y = 270;
        }
        if (n == 2) {
            x = 1066 - 260;
            y = 270;
        }
        if (n == 3) {
            x = 300;
            y = 450;
        }
        if (n == 4) {
            x = 1066 - 260;
            y = 450;
        }
        getGraphics().drawImage(imageIcon.getImage(), x, y,null);
    }

}
