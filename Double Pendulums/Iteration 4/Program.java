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
        int time = 5;
        double dt = 1;
        iterations = (int) (time / dt);
        try {
            String destination = "D:/IIT Roorkee/Miscellaneous/Double Pendulums/Iteration 4/";
            File input = new File(destination + "BaseImage.jpg");
            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();
            // int[][][][] ColourMatrix = new int[iterations][width][height][3];
            double[][][] OmegaMatrix = new double[width][height][4];
            int[][][] ColourMatrix = new int[width][height][3];
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < height; k++) {
                    OmegaMatrix[j][k][0] = (double) j;
                    OmegaMatrix[j][k][1] = (double) k;
                    OmegaMatrix[j][k][2] = 0.0;
                    OmegaMatrix[j][k][3] = 0.0;
                    Color c = new Color(image.getRGB(j, k));
                    ColourMatrix[j][k][0] = (int) (c.getRed());
                    ColourMatrix[j][k][1] = (int) (c.getGreen());
                    ColourMatrix[j][k][2] = (int) (c.getBlue());
                }
            }
            for (int i = 0; i < iterations; i++) {
                for (int j = 0; j < width; j++) {
                    for (int k = 0; k < height; k++) {
                        Color newColor = new Color(ColourMatrix[j][k][0], ColourMatrix[j][k][1], ColourMatrix[j][k][2]);
                        image.setRGB(((int) (OmegaMatrix[j][k][0])), ((int) (OmegaMatrix[j][k][1])),
                                ((int) (newColor.getRGB())));
                        double theta1 = (2 * pi * OmegaMatrix[j][k][0] / width) - pi;
                        double theta2 = (2 * pi * OmegaMatrix[j][k][1] / height) - pi;
                        theta1 += OmegaMatrix[j][k][2] * dt;
                        theta2 += OmegaMatrix[j][k][3] * dt;
                        if (theta1 > pi) {
                            theta1 += -2 * pi;
                        }
                        if (theta1 < -1 * pi) {
                            theta1 += 2 * pi;
                        }
                        if (theta2 > pi) {
                            theta2 += -2 * pi;
                        }
                        if (theta2 < -1 * pi) {
                            theta2 += 2 * pi;
                        }
                        OmegaMatrix[j][k][0] = width * (theta1 + pi) / (2 * pi);
                        OmegaMatrix[j][k][1] = height * (theta2 + pi) / (2 * pi);
                        OmegaMatrix[j][k][2] += a1(theta1, theta2, OmegaMatrix[j][k][2], OmegaMatrix[j][k][3]) * dt;
                        OmegaMatrix[j][k][3] += a2(theta1, theta2, OmegaMatrix[j][k][2], OmegaMatrix[j][k][3]) * dt;
                    }
                }
                File output = new File(destination + "Renders/Frame" + (i + 1) + ".jpg");
                System.out.println((i + 1) + " images done");
                for (int j = 0; j < width; j++) {
                    for (int k = 0; k < height; k++) {
                        Color c = new Color(image.getRGB(j, k));
                        ColourMatrix[j][k][0] = (int) (c.getRed());
                        ColourMatrix[j][k][1] = (int) (c.getGreen());
                        ColourMatrix[j][k][2] = (int) (c.getBlue());
                    }
                }
                ImageIO.write(image, "jpg", output);
            }
        } catch (Exception e) {
        }
    }
}
