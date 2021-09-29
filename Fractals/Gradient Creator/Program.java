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
        String destination = "/media/sammy/New Volume/Projects/Non-Linear-Dynamics/Fractals/Gradient Creator/";
        File input = new File(destination + "BaseImage.jpg");
        int[] ColorMatrix = new int[3];
        try {
            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();
            for (int i = 0; i < width; i++) 
            {
                for (int j = 0; j < height; j++) 
                {
                    if (j < 0.5 * height) 
                    {
                        ColorMatrix[0] = (int)(255.0 * i / width);
                        ColorMatrix[1] = (int)(255.0 * i / width);
                        ColorMatrix[2] = (int)(255.0 * i / width);
                        ColorMatrix[(int)(6.0 * j / height)+ ((int)(6.0 * j / height) == 2 ? -2: 1)] = 255;//(int)(255.0 * i / width);
                        ColorMatrix[(int)(6.0 * j / height)+ ((int)(6.0 * j / height) == 0 ? 2: -1)] = 0;
                        ColorMatrix[(int)(6.0 * j / height)] = 255 - (int)(255.0 * i / width);
        }
                    else
                    {
                        ColorMatrix[0] = 255 - (int)(255.0 * i / width);
                        ColorMatrix[1] = 255 - (int)(255.0 * i / width);
                        ColorMatrix[2] = 255 - (int)(255.0 * i / width);
                        ColorMatrix[(int)(6.0 * j / height) - 3 + ((int)(6.0 * j / height) - 3 == 2 ? -2: 1)] = 0;//255 - (int)(255.0 * i / width);
                        ColorMatrix[(int)(6.0 * j / height) - 3 + ((int)(6.0 * j / height) - 3 == 0 ? 2: -1)] = 255;
                        ColorMatrix[(int)(6.0 * j / height) - 3] = (int)(255.0 * i / width);
                    }
                    Color newColor = new Color(ColorMatrix[0], ColorMatrix[1], ColorMatrix[2]);
                    image.setRGB(i, j, ((int) (newColor.getRGB())));

                }
            }
            File output = new File(destination + "/Image.jpg");
            ImageIO.write(image, "jpg", output);
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }

    }
}