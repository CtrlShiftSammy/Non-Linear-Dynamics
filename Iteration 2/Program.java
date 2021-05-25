import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.lang.*;

public class Program {
    public static double a1(double t1, double t2, double w1, double w2) {
        double m1 = 1.0;
        double m2 = 1.0;
        double g = 9.81;
        double l1 = 1.0;
        double l2 = 1.0;
        double alpha = (m2 * g * Math.sin(t2) * Math.cos(t1 - t2))
                - (m2 * Math.sin(t1 - t2)) * (l1 * w1 * w1 * Math.cos(t1 - t2) + l2 * w2 * w2)
                - (m1 + m2) * g * Math.sin(t1);
        alpha /= (l1 * (m1 + m2 * Math.sin(t1 - t2) * Math.sin(t1 - t2)));
        return alpha;
    }

    public static double a2(double t1, double t2, double w1, double w2) {
        double m1 = 1.0;
        double m2 = 1.0;
        double g = 9.81;
        double l1 = 1.0;
        double l2 = 1.0;
        double alpha = (m1 + m2)
                * (l1 * w1 * w1 * Math.sin(t1 - t2) - g * Math.sin(t2) + g * Math.sin(t1) * Math.cos(t1 - t2))
                + m2 * l2 * w2 * w2 * Math.sin(t1 - t2) * Math.cos(t1 - t2);
        alpha /= (l2 * (m1 + m2 * Math.sin(t1 - t2) * Math.sin(t1 - t2)));
        return alpha;
    }

    public static void main(String[] args) {
        double pi = Math.atan(1.0);
        BufferedImage image;
        int width;
        int height;
        int iterations;
        int time = 3;
        double dt = 0.01;
        iterations = (int) (time / dt);
        int j1, k1;
        try {
            String destination = "D:/IIT Roorkee/Miscellaneous/Double Pendulums/Iteration 2/";
            File input = new File(destination + "BaseImage.jpg");
            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();
            // int[][][][] ColourMatrix = new int[iterations][width][height][3];
            double[][][][] OmegaMatrix = new double[iterations][width][height][2];

            for (int i = 0; i < iterations; i++) {
                for (int j = 0; j < width; j++) {
                    for (int k = 0; k < height; k++) {
                        OmegaMatrix[i][j][k][0] = 0.0;
                        OmegaMatrix[i][j][k][1] = 0.0;
                    }
                }
            }

            for (int i = 0; i < iterations; i++) {
                if (i != 0) {
                    input = new File(destination + "Renders/Frame" + i + ".jpg");
                    image = ImageIO.read(input);
                }
                for (int j = 0; j < width; j++) {
                    for (int k = 0; k < height; k++) {
                        Color c = new Color(image.getRGB(j, k));
                        int red = (int) (c.getRed());
                        int green = (int) (c.getGreen());
                        int blue = (int) (c.getBlue());
                        double theta1 = (2 * pi * j / width) - pi;
                        double theta2 = (2 * pi * k / height) - pi;
                        j1 = j;
                        k1 = k;
                        double theta1_new = theta1 + OmegaMatrix[i][j][k][0] * dt;
                        double theta2_new = theta2 + OmegaMatrix[i][j][k][1] * dt;
                        if (theta1_new > pi) {
                            theta1_new += -2 * pi;
                        }
                        if (theta1_new < -1 * pi) {
                            theta1_new += 2 * pi;
                        }
                        if (theta2_new > pi) {
                            theta2_new += -2 * pi;
                        }
                        if (theta2_new < -1 * pi) {
                            theta2_new += 2 * pi;
                        }

                        j1 = (int) ((pi + theta1_new) * width / (2 * pi));
                        k1 = (int) ((pi + theta2_new) * height / (2 * pi));
                        double alpha1 = a1(theta1, theta2, OmegaMatrix[i][j][k][0], OmegaMatrix[i][j][k][1]);
                        double alpha2 = a2(theta1, theta2, OmegaMatrix[i][j][k][0], OmegaMatrix[i][j][k][1]);
                        OmegaMatrix[i + 1][j1][k1][0] = OmegaMatrix[i][j][k][0] + alpha1 * dt;
                        OmegaMatrix[i + 1][j1][k1][1] = OmegaMatrix[i][j][k][1] + alpha2 * dt;
                        /**
                         * ColourMatrix[i][j][k][0] = red; ColourMatrix[i][j][k][1] = green;
                         * ColourMatrix[i][j][k][2] = blue;
                         */
                        Color newColor = new Color(red, green, blue);
                        image.setRGB(j1, k1, newColor.getRGB());

                    }
                }
                File output = new File(destination + "Renders/Frame" + (i + 1) + ".jpg");
                System.out.println((i + 1) + " images done");
                ImageIO.write(image, "jpg", output);
            }
        } catch (Exception e) {
        }
    }
}
