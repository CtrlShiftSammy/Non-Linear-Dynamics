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
        int iterations = 200;
        int steps = 5;
        int r = 6;
        double speed = 0.99;
        //double zoom;
        try {
            String destination = "/media/sammy/New Volume/Projects/Non-Linear-Dynamics/Fractals/Newton Fractals with Zoom/";
            File input = new File(destination + "BaseImage.jpg");
            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();
            double[][][] ZMatrix = new double[width][height][2];
            double[][] RootMatrix = new double[r][2];
            int[] ColorMatrix = new int[3];
            for (int i = 0; i < r; i++) {
                RootMatrix[i][0] = Math.cos(2.0 * pi * i / r);
                RootMatrix[i][1] = Math.sin(2.0 * pi * i / r);
            }
            Complex a = new Complex(1.0, 1.0);
            Complex Target = new Complex((0.24) * width , (-0.28) * height);
            //RootMatrix[0][0] = 1.0;
            //RootMatrix[0][1] = 0.0;
            //RootMatrix[1][0] = -0.5;
            //RootMatrix[1][1] = 0.5 * Math.sqrt(3);
            //RootMatrix[2][0] = -0.5;
            //RootMatrix[2][1] = -0.5 * Math.sqrt(3);
            //Complex Target = new Complex((0.15858) * width , (-0.15858 * Math.tan(pi / 3.0)) * height);
            long startTime = System.nanoTime();
            for (int h = 0; h < steps; h++)
            {
                for (int j = 0; j < width; j++) {
                    for (int k = 0; k < height; k++) {
                        ZMatrix[j][k][0] = ((double)j - (double)width / 2.0 - Target.re()) * Math.pow(speed, h) + Target.re();
                        ZMatrix[j][k][1] = ((double)k - (double)height / 2.0 - Target.im()) * Math.pow(speed, h) + Target.im();
                        //zoom = Math.pow(0.99, -2.0*h);
                        //ZMatrix[j][k][0] = (j - width / 2.0) * Math.sqrt(1.0 - (double) h / (steps+1));
                        //ZMatrix[j][k][1] = (k - height / 2.0) * Math.sqrt(1.0 - (double) h / (steps+1));                    
                        //ZMatrix[j][k][0] = (j - width / 2.0) / Math.sqrt(1.0 +  zoom * h / (steps-1));
                        //ZMatrix[j][k][1] = (k - height / 2.0) / Math.sqrt(1.0 + zoom * h / (steps-1));                    
                    }
                }    
                for (int i = 0; i < iterations; i++) 
                {
                    for (int j = 0; j < width; j++) 
                    {
                        for (int k = 0; k < height; k++) 
                        {
                            Complex Zi = new Complex(ZMatrix[j][k][0], ZMatrix[j][k][1]);
                            Complex Zi1 = Zi.minus((Zi.function()).times(a).divides(Zi.derivative()));
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
                        for(int l = 0; l < r; l++)
                        {
                            if (Math.pow(Math.abs(ZMatrix[j][k][0] - RootMatrix[l][0]), 2) + Math.pow(Math.abs(ZMatrix[j][k][1] - RootMatrix[l][1]), 2)<=Math.pow(distance, 2)) 
                            {
                                //ColorMatrix[l] = 255;
                                distance = Math.sqrt(Math.pow(Math.abs(ZMatrix[j][k][0] - RootMatrix[l][0]), 2) + Math.pow(Math.abs(ZMatrix[j][k][1] - RootMatrix[l][1]), 2));
                                //for(int m = 0; m<l; m++)
                                //{
                                //    ColorMatrix[m]=0;
                                //}
                                if (l == 0) {
                                    ColorMatrix[0] = 255;
                                    ColorMatrix[1] = 0;
                                    ColorMatrix[2] = 0;
                                }
                                else if (l == 1) {
                                    ColorMatrix[0] = 0;
                                    ColorMatrix[1] = 0;
                                    ColorMatrix[2] = 255;
                                }
                                else if (l == 2) {
                                    ColorMatrix[0] = 0;
                                    ColorMatrix[1] = 255;
                                    ColorMatrix[2] = 0;
                                }
                                else if (l == 3) {
                                    ColorMatrix[0] = 0;
                                    ColorMatrix[1] = 255;
                                    ColorMatrix[2] = 255;
                                }
                                else if (l == 4) {
                                    ColorMatrix[0] = 255;
                                    ColorMatrix[1] = 255;
                                    ColorMatrix[2] = 0;
                                }
                                else if (l == 5) {
                                    ColorMatrix[0] = 255;
                                    ColorMatrix[1] = 0;
                                    ColorMatrix[2] = 255;
                                }
                            }
                        }
                        Color newColor = new Color(ColorMatrix[0], ColorMatrix[1], ColorMatrix[2]);
                        image.setRGB(j, k, ((int) (newColor.getRGB())));
                    }
                }
                long endTime = System.nanoTime();
                long timeElapsed = endTime - startTime;
                File output1 = new File(destination + "Renders/Frame" + (h + 1) + ".jpg");
                //File output2 = new File(destination + "Renders/Frame" + (2*steps - h) + ".jpg");
                for (int j = 0; j < 150; j++) 
                {
                    System.out.print("\b");
                }
                System.out
                        .print("Rendered " + (h + 1) + " out of " + (steps) + " images, ETA: "
                        + (int) ((timeElapsed / 1000000000) * (((double) (steps - h - 1) / (double) (h + 1))) * 100) / 6000 + " minutes "
                        + ((int) ((timeElapsed / 1000000000)* (((double) (steps - h - 1) / (double) (h + 1))) * 100) / 100)%60 + " seconds.");
                ImageIO.write(image, "jpg", output1);
                //ImageIO.write(image, "jpg", output2);
            }
        } catch (Exception e) 
        {
        }
    }
}
