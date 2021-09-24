import ComplexPackage.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.lang.*;

public class Program {
    public static void main(String[] args) {
        double pi = 4 * Math.atan(1.0);
        BufferedImage image;
        int width;
        int height;
        int iterations;
        int time = 1;
        double dt = 0.0025; // (1/400)
        iterations = (int) (time / dt);
        try {
            String destination = "/media/sammy/New Volume/Projects/Non-Linear-Dynamics/Fractals/Newton Fractals/";
            File input = new File(destination + "BaseImage.jpg");
            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();
            double[][][] ZMatrix = new double[width][height][2];
            double[][] RootMatrix = new double[3][2];
            int[] ColorMatrix = new int[3];
            RootMatrix[0][0] = 1.0;
            RootMatrix[0][1] = 0.0;
            RootMatrix[1][0] = -0.5;
            RootMatrix[1][1] = 0.5 * Math.sqrt(3);
            RootMatrix[2][0] = -0.5;
            RootMatrix[2][1] = -0.5 * Math.sqrt(3);
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < height; k++) {
                    ZMatrix[j][k][0] = j - width / 2;
                    ZMatrix[j][k][1] = k - height / 2;                    
                }
            }
            long startTime = System.nanoTime();
            for (int i = 0; i < iterations; i++) {
                for (int j = 0; j < width; j++) {
                    for (int k = 0; k < height; k++) {
                        Complex Zi = new Complex(ZMatrix[j][k][0], ZMatrix[j][k][1]);
                        Complex Zi1 = Zi.minus((Zi.function()).divides(Zi.derivative()));
                        ZMatrix[j][k][0] = Zi1.re();
                        ZMatrix[j][k][1] = Zi1.im();
                        ColorMatrix[0] = 0;
                        ColorMatrix[1] = 0;
                        ColorMatrix[2] = 0;
                        for(int l = 0; l < 3; l++)
                        {
                            if ((Math.abs(ZMatrix[j][k][0] - RootMatrix[l][0]) < 0.001) && (Math.abs(ZMatrix[j][k][1] - RootMatrix[l][1]) < 0.001)) 
                            {
                                ColorMatrix[l] = 255;

                            }
                        }
                        Color newColor = new Color(ColorMatrix[0], ColorMatrix[1], ColorMatrix[2]);
                        image.setRGB(j, k, ((int) (newColor.getRGB())));
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
                        + (int) ((timeElapsed / 1000000000) * (((double) (iterations - i - 1) / (double) (i + 1))) * 100) / 6000 + " minutes "
                        + ((int) ((timeElapsed / 1000000000)* (((double) (iterations - i - 1) / (double) (i + 1))) * 100) / 100)%60 + " seconds.");
                ImageIO.write(image, "jpg", output);
            }
        } catch (Exception e) {
        }
    }
}
