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
        int iterations = 25;
        int steps = 400;
        try {
            String destination = "/media/sammy/New Volume/Projects/Non-Linear-Dynamics/Fractals/Nova Fractals/";
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
            long startTime = System.nanoTime();
            for (int h = 0; h < steps; h++)
            {
                for (int j = 0; j < width; j++) {
                    for (int k = 0; k < height; k++) {
                        ZMatrix[j][k][0] = j - width / 2;
                        ZMatrix[j][k][1] = k - height / 2;                    
                    }
                }    
                for (int i = 0; i < iterations; i++) 
                {
                    for (int j = 0; j < width; j++) 
                    {
                        for (int k = 0; k < height; k++) 
                        {
                            Complex Zi = new Complex(ZMatrix[j][k][0], ZMatrix[j][k][1]);
                            Complex C = new Complex(((double)(2 * h) / steps) - 1.0, 0.0);
                            Complex Zi1 = (Zi.minus((Zi.function()).divides(Zi.derivative()))).plus(C);
                            ZMatrix[j][k][0] = Zi1.re();
                            ZMatrix[j][k][1] = Zi1.im();
                        }
                    }
                }
                for (int j = 0; j < width; j++) 
                {
                    for (int k = 0; k < height; k++) 
                    {
                        ColorMatrix[0] = 0;
                        ColorMatrix[1] = 0;
                        ColorMatrix[2] = 0;
                        double distance = width;
                        for(int l = 0; l < 3; l++)
                        {
                            if (Math.pow(Math.abs(ZMatrix[j][k][0] - RootMatrix[l][0]), 2) + Math.pow(Math.abs(ZMatrix[j][k][1] - RootMatrix[l][1]), 2)<=Math.pow(distance, 2)) 
                            {
                                ColorMatrix[l] = 255;
                                distance = Math.sqrt(Math.pow(Math.abs(ZMatrix[j][k][0] - RootMatrix[l][0]), 2) + Math.pow(Math.abs(ZMatrix[j][k][1] - RootMatrix[l][1]), 2));
                                for(int m = 0; m<l; m++)
                                {
                                    ColorMatrix[m]=0;
                                }
                            }
                        }
                        Color newColor = new Color(ColorMatrix[0], ColorMatrix[1], ColorMatrix[2]);
                        image.setRGB(j, k, ((int) (newColor.getRGB())));
                    }
                }
                long endTime = System.nanoTime();
                long timeElapsed = endTime - startTime;
                File output = new File(destination + "Renders/Frame" + (h + 1) + ".jpg");
                for (int j = 0; j < 150; j++) 
                {
                    System.out.print("\b");
                }
                System.out
                        .print("Rendered " + (h + 1) + " out of " + (steps) + " images, ETA: "
                        + (int) ((timeElapsed / 1000000000) * (((double) (steps - h - 1) / (double) (h + 1))) * 100) / 6000 + " minutes "
                        + ((int) ((timeElapsed / 1000000000)* (((double) (steps - h - 1) / (double) (h + 1))) * 100) / 100)%60 + " seconds.");
                ImageIO.write(image, "jpg", output);
            }
        } catch (Exception e) 
        {
        }
    }
}
