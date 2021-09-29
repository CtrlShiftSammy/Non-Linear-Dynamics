import ComplexPackage.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.lang.*;

public class Program 
{
    public static int red(int k, int iterations) 
    {
        //return (((int)((double)k * 12.0 / (double)iterations) == 0) || ((int)((double)k * 12.0 / (double)iterations) == 1) || ((int)((double)k * 12.0 / (double)iterations) == 5)) ? 255 : 0;
        switch ((int)((double)k * 12.0 / (double)iterations)) 
        {
            case 0:
                return 255;
            case 1:
                return (int)((2.0 - ((double)k * 12.0 / (double)iterations)) * 255);
            case 2:
                return 0;
            case 3:
                return 0;
            case 4:
                return (int)((((double)k * 12.0 / (double)iterations) - 4.0) * 255);
            case 5:
                return 255;
            case 6:
                return 255;
            case 7:
                return (int)((2.0 - ((double)k * 12.0 / (double)iterations) + 6.0) * 255);
            case 8:
                return 0;
            case 9:
                return 0;
            case 10:
                return (int)((((double)k * 12.0 / (double)iterations) - 4.0 - 6.0) * 255);
            case 11:
                return 255;
            default:
                return 255;
        }
    }
    public static int green(int k, int iterations) 
    {
        //return (((int)((double)k * 12.0 / (double)iterations) == 3) || ((int)((double)k * 12.0 / (double)iterations) == 4) || ((int)((double)k * 12.0 / (double)iterations) == 5)) ? 255 : 0;
        switch ((int)((double)k * 12.0 / (double)iterations)) 
        {
            case 0:
                return 0;
            case 1:
                return 0;
            case 2:
                return (int)((((double)k * 12.0 / (double)iterations) - 2.0) * 255);
            case 3:
                return 255;
            case 4:
                return 255;
            case 5:
                return (int)((6.0 - ((double)k * 12.0 / (double)iterations)) * 255);
            case 6:
                return 0;
            case 7:
                return 0;
            case 8:
                return (int)((((double)k * 12.0 / (double)iterations) - 2.0 - 6.0) * 255);
            case 9:
                return 255;
            case 10:
                return 255;
            case 11:
                return (int)((6.0 - ((double)k * 12.0 / (double)iterations) + 6.0) * 255);
            default:
                return 255;
        }
    }
    public static int blue(int k, int iterations) 
    {
        //return (((int)((double)k * 12.0 / (double)iterations) == 1) || ((int)((double)k * 12.0 / (double)iterations) == 2) || ((int)((double)k * 12.0 / (double)iterations) == 3)) ? 255 : 0;
        switch ((int)((double)k * 12.0 / (double)iterations)) 
        {
            case 0:
                return (int)((((double)k * 12.0 / (double)iterations)) * 255);
            case 1:
                return 255;
            case 2:
                return 255;
            case 3:
                return (int)((4.0 - ((double)k * 12.0 / (double)iterations)) * 255);
            case 4:
                return 0;
            case 5:
                return 0;
            case 6:
                return (int)((((double)k * 12.0 / (double)iterations) - 6.0) * 255);
            case 7:
                return 255;
            case 8:
                return 255;
            case 9:
                return (int)((4.0 - ((double)k * 12.0 / (double)iterations) + 6.0) * 255);
            case 10:
                return 0;
            case 11:
                return 0;
            default:
                return 255;
        }
    }
    public static void main(String[] args) 
    {
        double pi = 4 * Math.atan(1.0);
        BufferedImage image;
        int width;
        int height;
        int iterations = 500;
        int steps = 1;
        double speed = 0.8;
        Complex Target = new Complex(1.5, 0.0);
        try {
            String destination = "/media/sammy/New Volume/Projects/Non-Linear-Dynamics/Fractals/Collatz Conjecture Fractals/";
            File input = new File(destination + "BaseImage.jpg");
            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();
            double[][][] ZMatrix = new double[width][height][2];
            int[] ColorMatrix = new int[3];
            long startTime = System.nanoTime();
            for (int h = 0; h < steps; h++) 
            {
                for (int i = 0; i < width; i++) 
                {
                    for (int j = 0; j < height; j++) 
                    {
                        ZMatrix[i][j][0] = (((double)i - (double)height / 2.0) * (10.0 / 1080.0) - Target.re()) * Math.pow(speed, h) + Target.re();
                        ZMatrix[i][j][1] = (((double)j - (double)width / 2.0) * (10.0 / 1080.0) + Target.im()) * Math.pow(speed, h)- Target.im();
                        ColorMatrix[0] = 0;
                        ColorMatrix[1] = 0;
                        ColorMatrix[2] = 0;
                        boolean settled = false;
                        for (int k = 0; k < iterations && !settled; k++) 
                        {
                            Complex Zk = new Complex(ZMatrix[i][j][0], ZMatrix[i][j][1]);
                            Complex Zk1 = Zk.collatz();
                            ZMatrix[i][j][0] = Zk1.re();
                            ZMatrix[i][j][1] = Zk1.im();
                            if (Zk1.abs() == 1.0) 
                            {
                                settled = true;
                                ColorMatrix[0] = red(k, iterations);
                                ColorMatrix[1] = green(k, iterations);
                                ColorMatrix[2] = blue(k, iterations);
                            }
                        }
                        Color newColor = new Color(ColorMatrix[0], ColorMatrix[1], ColorMatrix[2]);
                        image.setRGB(i, j, ((int) (newColor.getRGB())));
                    }
                }
                long endTime = System.nanoTime();
                long timeElapsed = endTime - startTime;
                File output = new File(destination + "Renders/Frame" + (h + 1) + ".jpg");
                for (int j = 0; j < 150; j++) 
                {
                    System.out.print("\b");
                }
                System.out.print("Rendered " + (h + 1) + " out of " + (steps) + " images, ETA: " + (int) ((timeElapsed / 1000000000) * (((double) (steps - h - 1) / (double) (h + 1))) * 100) / 6000 + " minutes "+ ((int) ((timeElapsed / 1000000000)* (((double) (steps - h - 1) / (double) (h + 1))) * 100) / 100)%60 + " seconds.");
                ImageIO.write(image, "jpg", output);
            }
        } catch (Exception e) 
            {
                System.out.println(e);
            }
    }
}
