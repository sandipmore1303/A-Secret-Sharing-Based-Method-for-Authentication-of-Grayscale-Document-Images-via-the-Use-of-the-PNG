 
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import  ij.io.OpenDialog;

public class PrintImageARGBPixels {
 
    public static void main(String args[]) {
        /**
         * Read a sample image from the filesystem
         */
        
        // image = readImage("F:\\PROJECT_DATA\\ALDAR\\ALDAR_APRIL2014\\Stego_Binary.png");
         ij.io.OpenDialog oi =new ij.io.OpenDialog("Select image","E:\\DATA\\YES_MAN\\JAVA_IMPL\\", "lenna.bmp");
         BufferedImage image = readImage(oi.getDirectory()+oi.getFileName());
         //BufferedImage image = readImage("F:\\PROJECT_DATA\\ALDAR\\ALDAR_APRIL2014\\4x6_bw.png");
 
        /**
         * Call the method that prints out ARGB color Information.
         * ARGB = Alpha, Red, Green and Blue
         */
        printAllARGBDetails(image);
    }
 
public static void printAllARGBDetails(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        System.out.println("Image Dimension: Height-" + height + ", Width-"
                + width);
        System.out.println("Total Pixels: " + (height * width));
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
 
                int pixel = image.getRGB(i, j);
                System.out.println("Pixel Location(" + i + "," + j + ")- ["
                        + getARGBPixelData(pixel) + "]");
            }
        }
    }
 

public static String getARGBPixelData(int pixel) {
        String pixelARGBData = "";     
        int alpha = (pixel >> 24) & 0x000000FF;      
        int red = (pixel >> 16) & 0x000000FF;     
        int green = (pixel >>8 ) & 0x000000FF; 
        int blue = (pixel) & 0x000000FF; 
        pixelARGBData = "Alpha: " + alpha + ", " + "Red: " + red + ", " + "Green: " + green + ", " + "Blue: " + blue; 
        return pixelARGBData;
    }

public static BufferedImage readImage(String fileLocation) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileLocation));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }
}