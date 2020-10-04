import java.awt.Color;
import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import javax.imageio.*;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import javax.media.jai.operator.MedianFilterDescriptor;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
/*
 
 * Logic: Captures the colour of 8 pixels around the target pixel.Including the target pixel there will be 9 pixels.
 *        Isolate the R,G,B values of each pixels and put them in an array.Sort the arrays.Get the Middle value of the array
 *        Which will be the Median of the color values in those 9 pixels.Set the color to the Target pixel and move on!
 */

class MedianFilter_1{
     static ij.io.OpenDialog oi=null;
    private static void display_images() {
    //JFrame frame_oi = new JFrame("Original Color image");
    //Panel panel_oi = new ShowImage(oi.getDirectory()+"Original_Color.png");
    //frame_oi.getContentPane().add(panel_oi);
    //frame_oi.setSize(500, 500);
    //frame_oi.setVisible(true);
    
  
    JFrame frame_gi = new JFrame("Original image");
    Panel panel_gi = new ShowImage(oi.getDirectory()+oi.getFileName());
    frame_gi.getContentPane().add(panel_gi);
    frame_gi.setSize(500, 500);
    frame_gi.setVisible(true);
    
    JFrame frame_gi1 = new JFrame("Median Filtered image");
    Panel panel_gi1 = new ShowImage(oi.getDirectory()+"Median_Filtered_Input_Image.png");
    frame_gi1.getContentPane().add(panel_gi1);
    frame_gi1.setSize(500, 500);
    frame_gi1.setVisible(true);
    }
     public  void MAIN(String a)throws Throwable
             {
         oi =new ij.io.OpenDialog("Select Source image",
                  "E:\\DATA\\YES_MAN\\JAVA_IMPL\\",
                  "lenna.bmp");
        File f=new File(oi.getDirectory()+oi.getFileName());                               //Input Photo File
         
       
        File output_png=new File(oi.getDirectory()+"Median_Filtered_Input_Image.png"); 
        File output_jpg=new File(oi.getDirectory()+"Median_Filtered_Input_Image.jpg"); 
        
        BufferedImage img=ImageIO.read(f);
        BufferedImage img1=new BufferedImage(img.getWidth(),img.getHeight(), img.getType());
         
        
        

ParameterBlock pb = new ParameterBlock();
pb.addSource(img);
pb.add(MedianFilterDescriptor.MEDIAN_MASK_SQUARE);
pb.add(3);
RenderedOp result = JAI.create("MedianFilter", pb);
img1=result.getAsBufferedImage();



        ImageIO.write(img1,"jpg",output_png);
        ImageIO.write(img1,"png",output_jpg);
        display_images();
        
        PSNR TMP=new PSNR(); 
        double psnr = PSNR.psnr(img,img1);
    	System.out.println("psnr = "+psnr);
    }

    public static void main(String[] a)throws Throwable{
        oi =new ij.io.OpenDialog("Select Source image",
                  "E:\\DATA\\YES_MAN\\JAVA_IMPL\\",
                  "lenna.bmp");
        File f=new File(oi.getDirectory()+oi.getFileName());                               //Input Photo File
         
       
        File output_png=new File(oi.getDirectory()+"Median_Filtered_Input_Image.png"); 
        File output_jpg=new File(oi.getDirectory()+"Median_Filtered_Input_Image.jpg"); 
        
        BufferedImage img=ImageIO.read(f);
        BufferedImage img1=new BufferedImage(img.getWidth(),img.getHeight(), img.getType());
         
        
        

ParameterBlock pb = new ParameterBlock();
pb.addSource(img);
pb.add(MedianFilterDescriptor.MEDIAN_MASK_SQUARE);
pb.add(3);
RenderedOp result = JAI.create("MedianFilter", pb);
img1=result.getAsBufferedImage();



        ImageIO.write(img1,"jpg",output_png);
        ImageIO.write(img1,"png",output_jpg);
        display_images();
        
        PSNR TMP=new PSNR(); 
        double psnr = PSNR.psnr(img,img1);
    	System.out.println("psnr = "+psnr);
    }
    
}
