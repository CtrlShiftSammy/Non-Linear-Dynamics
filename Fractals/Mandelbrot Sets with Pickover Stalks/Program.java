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
        int iterations = 100;
        int steps = 50;
        double speed = 0.98;
        Complex Target = new Complex(-0.7037523586615094, 0.26743809989017503);
        try {
            String destination = "/run/media/sammy/sammy-data/Projects/Non-Linear-Dynamics/Fractals/Mandelbrot Sets with Pickover Stalks/";
            File input = new File(destination + "BaseImage.jpg");
            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();
            double[][][] ZMatrix = new double[width][height][2];
            int[] ColorMatrix = new int[3];
            long startTime = System.nanoTime();
            double trap = 1;
            for (int h = 0; h < steps; h++) 
            {
                for (int i = 0; i < width; i++) 
                {
                    for (int j = 0; j < height; j++) 
                    {
                        ZMatrix[i][j][0] = 0.0;
                        ZMatrix[i][j][1] = 0.0;
                        ColorMatrix[0] = 0;
                        ColorMatrix[1] = 0;
                        ColorMatrix[2] = 0;
                        boolean escaped = false;
                        Complex c = new Complex((((double)i - (double)height / 2.0) * (3.0 / 1080.0) - 0.5 - Target.re()) * Math.pow(speed, h) + Target.re(), (((double)j - (double)width / 2.0) * (3.0 / 1080.0) + Target.im()) * Math.pow(speed, h)- Target.im());
                        for (int k = 0; k < iterations && !escaped; k++) 
                        {
                            Complex Zk = new Complex(ZMatrix[i][j][0], ZMatrix[i][j][1]);
                            Complex Zk1 = (Zk.times(Zk)).plus(c);
                            ZMatrix[i][j][0] = Zk1.re();
                            ZMatrix[i][j][1] = Zk1.im();
                            trap = Math.min(trap, Math.min(Math.abs(Zk1.re()), Math.abs(Zk1.im())));
                            if (Zk1.abs() >= 2.0) 
                            {
                                escaped = true;
                                double p = 0.25;
                                if(trap != 0) System.out.println(trap);
                                //ColorMatrix[0] = red(k, (iterations));
                                ColorMatrix[0] = (int)(255 * Math.pow(trap, p));
                                //ColorMatrix[1] = green(k, iterations);
                                ColorMatrix[1] = (int)(255 * Math.pow(trap, p));
                                //ColorMatrix[2] = blue(k, iterations);    
                                ColorMatrix[2] = (int)(255 * Math.pow(trap, p));    
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
