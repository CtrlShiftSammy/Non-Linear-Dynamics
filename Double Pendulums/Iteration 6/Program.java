import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.concurrent.TimeUnit;
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
        /*
         * double alpha = (m2 * g * Math.sin(t2) * Math.cos(t1 - t2)) - (m2 *
         * Math.sin(t1 - t2)) * (l1 * w1 * w1 * Math.cos(t1 - t2) + l2 * w2 * w2) - (m1
         * + m2) * g * Math.sin(t1);
         */
        double alpha = -g * (2 * m1 + m2) * Math.sin(t1) - m2 * g * Math.sin(t1 - 2 * t2)
                - 2 * Math.sin(t1 - t2) * m2 * (w2 * w2 * l2 + w1 * w1 * l1 * Math.cos(t1 - t2));
        alpha /= (l1 * (2 * m1 + m2 - m2 * Math.cos(2 * t1 - 2 * t2)));
        alpha -= 0.01 * l1 * w1;
        return alpha;
    }

    public static double a2(double t1, double t2, double w1, double w2) {
        double m1 = 1.0;
        double m2 = 1.0;
        double g = 9.81;
        double l1 = 1.0;
        double l2 = 1.0;
        /*
         * double alpha = (m1 + m2) (l1 * w1 * w1 * Math.sin(t1 - t2) - g * Math.sin(t2)
         * + g * Math.sin(t1) * Math.cos(t1 - t2)) + m2 * l2 * w2 * w2 * Math.sin(t1 -
         * t2) * Math.cos(t1 - t2);
         */
        double alpha = 2 * Math.sin(t1 - t2)
                * (w1 * w1 * l1 * (m1 + m2) + g * (m1 + m2) * Math.cos(t1) + w2 * w2 * l2 * m2 * Math.cos(t1 - t1));
        alpha /= (l2 * (2 * m1 + m2 - m2 * Math.cos(2 * t1 - 2 * t2)));
        alpha -= 0.01 * (l1 + l2 * Math.cos(t1 - t2)) * w2;
        return alpha;
    }

    public static void main(String[] args) {
        double pi = 4 * Math.atan(1.0);
        BufferedImage image;
        int width;
        int height;
        int iterations;
        int time = 25;
        double dt = 0.0025; // (1/400)
        iterations = (int) (time / dt);
        try {
            String destination = "D:/IIT Roorkee/Miscellaneous/Double Pendulums/Iteration 6/";
            File input = new File(destination + "BaseImage.jpg");
            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();
            double[][][] OmegaMatrix = new double[width][height][4];
            int[][][] ColourMatrix = new int[width][height][3];
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < height; k++) {
                    OmegaMatrix[j][k][0] = (double) ((j * 2.0 * pi / (width)) - pi);
                    OmegaMatrix[j][k][1] = (double) ((k * 2.0 * pi / (height)) - pi);
                    OmegaMatrix[j][k][2] = 0.0;
                    OmegaMatrix[j][k][3] = 0.0;
                    Color c = new Color(image.getRGB(j, k));
                    ColourMatrix[j][k][0] = (int) (c.getRed());
                    ColourMatrix[j][k][1] = (int) (c.getGreen());
                    ColourMatrix[j][k][2] = (int) (c.getBlue());
                }
            }
            long startTime = System.nanoTime();
            for (int i = 0; i < iterations; i++) {
                for (int j = 0; j < width; j++) {
                    for (int k = 0; k < height; k++) {
                        Color newColor = new Color(
                                ColourMatrix[(int) ((OmegaMatrix[j][k][0] + pi) * width
                                        / (2 * pi))][(int) ((OmegaMatrix[j][k][1] + pi) * (double) (height)
                                                / (2 * pi))][0],
                                ColourMatrix[(int) ((OmegaMatrix[j][k][0] + pi) * width
                                        / (2 * pi))][(int) ((OmegaMatrix[j][k][1] + pi) * (double) (height)
                                                / (2 * pi))][1],
                                ColourMatrix[(int) ((OmegaMatrix[j][k][0] + pi) * width
                                        / (2 * pi))][(int) ((OmegaMatrix[j][k][1] + pi) * (double) (height)
                                                / (2 * pi))][2]);
                        image.setRGB(j, k, ((int) (newColor.getRGB())));
                        OmegaMatrix[j][k][2] += a1(OmegaMatrix[j][k][0], OmegaMatrix[j][k][1], OmegaMatrix[j][k][2],
                                OmegaMatrix[j][k][3]) * dt;
                        OmegaMatrix[j][k][3] += a2(OmegaMatrix[j][k][0], OmegaMatrix[j][k][1], OmegaMatrix[j][k][2],
                                OmegaMatrix[j][k][3]) * dt;
                        OmegaMatrix[j][k][0] += OmegaMatrix[j][k][2] * dt;
                        OmegaMatrix[j][k][1] += OmegaMatrix[j][k][3] * dt;
                        if (OmegaMatrix[j][k][0] > pi) {
                            OmegaMatrix[j][k][0] += -2 * pi;
                        }
                        if (OmegaMatrix[j][k][0] < -1 * pi) {
                            OmegaMatrix[j][k][0] += 2 * pi;
                        }
                        if (OmegaMatrix[j][k][1] > pi) {
                            OmegaMatrix[j][k][1] += -2 * pi;
                        }
                        if (OmegaMatrix[j][k][1] < -1 * pi) {
                            OmegaMatrix[j][k][1] += 2 * pi;
                        }
                    }
                }
                long endTime = System.nanoTime();
                long timeElapsed = endTime - startTime;
                File output = new File(destination + "Renders/Frame" + (i + 1) + ".jpg");
                for (int j = 0; j < 100; j++) {
                    System.out.print("\b");
                }
                System.out
                        .print("Rendered " + (i + 1) + " out of " + (iterations) + " images, ETA: "
                                + (int) ((timeElapsed / 1000000000)
                                        * (((double) (iterations - i - 1) / (double) (i + 1))) * 100) / 100
                                + " seconds.");
                ImageIO.write(image, "jpg", output);
            }
        } catch (Exception e) {
        }
    }
}
