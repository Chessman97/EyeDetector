package view;

import org.opencv.core.Mat;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Window extends JFrame {

    private boolean egg1Visible = true;
    private int eggsCount = 0;
    private int eggsBrakeCount = 0;
    private int eggNumber = 1;
    private boolean win = false;
    private boolean lose = false;

    private ImageIcon imageIconWolf1 = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\wolf1.png");
    private ImageIcon imageIconWolf2 = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\wolf2.png");
    private ImageIcon hand1 = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\hand1.png");
    private ImageIcon hand2 = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\hand3.png");
    private ImageIcon hand3 = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\hand2.png");
    private ImageIcon hand4 = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\hand4.png");
    private ImageIcon egg = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\egg.png");
    private ImageIcon winIcon = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\win.jpg");
    private ImageIcon loseIcon = new ImageIcon("C:\\Users\\Mikhail\\IdeaProjects\\EggsCatcherPath2\\src\\main\\resources\\img\\lose.jpg");
    private int state;
    private boolean game;

    private double xEgg = -90;
    private double yEgg = -30;

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

    private void setImage(ImageIcon imageIcon) {
        int x = 1366 - 800;
        int y = 240;
        getGraphics().drawImage(imageIcon.getImage(), x, y,null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        if (game && !win && !lose) {
            g2.setFont(new Font("Purisa", Font.BOLD, 18));
            g2.drawString("Яйца: " + eggsCount + "/5\n Здровье: " + (3 - eggsBrakeCount) + "/3", 580, 70);
            g2.setStroke(new BasicStroke(10.0f));  // толщина равна 10
            g2.drawLine(0, 100, 300, 200);
            g2.drawLine(0, 350, 300, 450);
            g2.drawLine(1066, 200, 1390, 100);
            g2.drawLine(1066, 450, 1390, 350);

            if (state == 1 || state == 3) {
                setImage(imageIconWolf1);
                if (state == 1) {
                    setImageHand(hand1);
                }
                else {
                    setImageHand(hand3);
                }
            }
            if (state == 2 || state == 4) {
                setImage(imageIconWolf2);
                if (state == 2){
                    setImageHand(hand2);
                }
                else{
                    setImageHand(hand4);
                }
            }
            if (eggNumber == 1 || eggNumber == 3) {
                if (xEgg < 300) {
                    xEgg += 9;
                    yEgg += 3;
                } else {
                    yEgg += 9;
                }
            } else {
                if (xEgg > 1066 - 65) {
                    xEgg -= 9;
                    yEgg += 3;
                } else {
                    if (xEgg > 1066 - 65 - 50) {
                        xEgg -= 1;
                    }
                    yEgg += 9;
                }
            }

            if (yEgg > 720) {
                eggsBrakeCount++;
                newEgg();
            }

            if (state == eggNumber) {
                if (eggNumber == 1 || eggNumber == 2) {
                    if (320 > yEgg && yEgg > 270) {
                        eggsCount++;
                        newEgg();
                    }
                } else {
                    if (450 > yEgg && yEgg > 420) {
                        eggsCount++;
                        newEgg();
                    }
                }
            }

            if (egg1Visible) {
                getGraphics().drawImage(egg.getImage(), (int) xEgg, (int) yEgg + 30, null);
            }
        }

        if (win) {
            getGraphics().drawImage(winIcon.getImage(), 440, 180, null);
            g2.setFont(new Font("Purisa", Font.BOLD, 36));
            g2.drawString("Вы победили!", 440, 520);
        }

        if (lose) {
            getGraphics().drawImage(loseIcon.getImage(), 440, 180, null);
            g2.setFont(new Font("Purisa", Font.BOLD, 36));
            g2.drawString("Вы проиграли!", 440, 590);
        }
    }

    private void newEgg() {
        if (eggsCount == 5) {
            win = true;
            game = false;
        }
        if (eggsBrakeCount == 3) {
            lose = true;
            game = false;
        }
        eggNumber = Math.abs(new Random().nextInt()) % 4 + 1;
        if (eggNumber == 1) {
            xEgg = -69;
            yEgg = -23;
        }
        if (eggNumber == 2) {
            xEgg = 1366 + 69 - 65;
            yEgg = -23;
        }
        if (eggNumber == 3) {
            xEgg = -69;
            yEgg = -23 + 250;
        }
        if (eggNumber == 4) {
            xEgg = 1366 + 69 - 65;
            yEgg = -23 + 250;
        }
    }

    private void setImageHand(ImageIcon imageIcon) {
        int x = 0;
        int y = 0;
        int n = state;
        if (n == 1) {
            x = 340;
            y = 270;
        }
        if (n == 2) {
            x = 1066 - 220;
            y = 270;
        }
        if (n == 3) {
            x = 340;
            y = 450;
        }
        if (n == 4) {
            x = 1066 - 220;
            y = 450;
        }
        getGraphics().drawImage(imageIcon.getImage(), x, y,null);
    }

}
