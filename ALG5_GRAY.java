import javax.swing.*;
import java.awt.*;
import javax.imageio.*;
import ij.IJ;
import ij.*;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import java.awt.Color;
import java.awt.*;
import java.awt.color.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.image.renderable.*;
import java.util.*;
import javax.media.jai.*;
import javax.media.jai.operator.*;
import javax.media.jai.widget.*;
import java.awt.Frame;
import java.awt.image.renderable.ParameterBlock;
import java.io.IOException;
import javax.media.jai.Interpolation;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import com.sun.media.jai.codec.FileSeekableStream;
import javax.media.jai.widget.ScrollingImagePanel;
import ij.*;
import ij.process.ImageConverter;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import ij.process.*;

import ij.gui.*;
import java.awt.Rectangle;
import ij.*;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;
import java.awt.*;
import java.awt.color.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.image.renderable.*;
import java.util.*;
import javax.media.jai.*;
import javax.media.jai.operator.*;
import javax.media.jai.widget.*;
import java.awt.Frame;
import java.awt.image.renderable.ParameterBlock;
import java.io.IOException;
import javax.media.jai.Interpolation;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import com.sun.media.jai.codec.FileSeekableStream;
import ij.*;
import ij.process.ImageConverter;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import ij.process.*;
import ij.gui.Roi.*;
import jxl.CellReferenceHelper;
import jxl.CellView;
import jxl.HeaderFooter;
import jxl.Range;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.Orientation;
import jxl.format.PageOrder;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.format.ScriptStyle;
import jxl.format.UnderlineStyle;
import jxl.write.Blank;
import jxl.write.Boolean;
import jxl.write.DateFormat;
import jxl.write.DateFormats;
import jxl.write.DateTime;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableHyperlink;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class ALG5_GRAY {
    public static ij.io.OpenDialog oi;
    public static double[] G;
    public static double thres;
    public static int P_1D[];
    public static double[] Moments(int [] data ) {
		 
		double total =0;
		double m0=1.0, m1=0.0, m2 =0.0, m3 =0.0, sum =0.0, p0=0.0;
		double cd, c0, c1, z0, z1;	/* auxiliary variables */
		int threshold = -1;

		double [] histo = new  double [data.length];

		for (int i=0; i<data.length; i++)
			total+=data[i];

		for (int i=0; i<data.length; i++)
			histo[i]=(double)(data[i]/total); //normalised histogram

		/* Calculate the first, second, and third order moments */
		for ( int i = 0; i < data.length; i++ ){
			m1 += i * histo[i];
			m2 += i * i * histo[i];
			m3 += i * i * i * histo[i];
		}
		/* 
		First 4 moments of the gray-level image should match the first 4 moments
		of the target binary image. This leads to 4 equalities whose solutions 
		are given in the Appendix of Ref. 1 
		*/
		cd = m0 * m2 - m1 * m1;
		c0 = ( -m2 * m2 + m1 * m3 ) / cd;
		c1 = ( m0 * -m3 + m2 * m1 ) / cd;
		z0 = 0.5 * ( -c1 - Math.sqrt ( c1 * c1 - 4.0 * c0 ) );
		z1 = 0.5 * ( -c1 + Math.sqrt ( c1 * c1 - 4.0 * c0 ) );
		p0 = ( z1 - m1 ) / ( z1 - z0 );  /* Fraction of the object pixels in the target binary image */

		// The threshold is the gray-level closest  
		// to the p0-tile of the normalized histogram 
		sum=0;
		for (int i=0; i<data.length; i++){
			sum+=histo[i];
			if (sum>p0) {
				threshold = i;
				break;
			}
		}
                double G[]=new double[2];
                G[0]=z0;
                G[1]=z1;
		return G;
	}
    private static void display_images() {
    //JFrame frame_oi = new JFrame("Original Color image");
    //Panel panel_oi = new ShowImage(oi.getDirectory()+"Original_Color.jpg");
    //frame_oi.getContentPane().add(panel_oi);
    //frame_oi.setSize(500, 500);
    //frame_oi.setVisible(true);
    
    
    JFrame frame_gi = new JFrame("Original JPEG image");
    Panel panel_gi = new ShowImage(oi.getDirectory()+"Original_Gray.jpg");
    frame_gi.getContentPane().add(panel_gi);
    frame_gi.setSize(500, 500);
    frame_gi.setVisible(true);
    
    
     
    
    
    
    JFrame frame_gs = new JFrame("Y plane");
    Panel panel_gs = new ShowImage(oi.getDirectory()+"Y.jpg");
    frame_gs.getContentPane().add(panel_gs);
    frame_gs.setSize(500, 500);
    frame_gs.setVisible(true);
    
    
    JFrame frame_bs = new JFrame("Cb plane");
    Panel panel_bs = new ShowImage(oi.getDirectory()+"Cb.jpg");
    frame_bs.getContentPane().add(panel_bs);
    frame_bs.setSize(500, 500);
    frame_bs.setVisible(true);
    
    
    JFrame frame_bs1 = new JFrame("Cr plane");
    Panel panel_bs1 = new ShowImage(oi.getDirectory()+"Cr.jpg");
    frame_bs1.getContentPane().add(panel_bs1);
    frame_bs1.setSize(500, 500);
    frame_bs1.setVisible(true);
    
    
    JFrame frame_bs2 = new JFrame("YCbCr");
    Panel panel_bs2 = new ShowImage(oi.getDirectory()+"YCbCr.jpg");
    frame_bs2.getContentPane().add(panel_bs2);
    frame_bs2.setSize(500, 500);
    frame_bs2.setVisible(true);
    
    
    
    
    
    JFrame frame_gs1 = new JFrame("Stego Y plane ");
    Panel panel_gs1 = new ShowImage(oi.getDirectory()+"Ym.jpg");
    frame_gs1.getContentPane().add(panel_gs1);
    frame_gs1.setSize(500, 500);
    frame_gs1.setVisible(true);
    
    
    JFrame frame_bs3 = new JFrame("Stego Cb plane");
    Panel panel_bs3 = new ShowImage(oi.getDirectory()+"Cbm.jpg");
    frame_bs3.getContentPane().add(panel_bs3);
    frame_bs3.setSize(500, 500);
    frame_bs3.setVisible(true);
    
    
    JFrame frame_bs4 = new JFrame("Stego Cr plane");
    Panel panel_bs4 = new ShowImage(oi.getDirectory()+"Crm.jpg");
    frame_bs4.getContentPane().add(panel_bs4);
    frame_bs4.setSize(500, 500);
    frame_bs4.setVisible(true);
    
    
    JFrame frame_bs5 = new JFrame("Stego YCbCr");
    Panel panel_bs5 = new ShowImage(oi.getDirectory()+"YCbCrm.jpg");
    frame_bs5.getContentPane().add(panel_bs5);
    frame_bs5.setSize(500, 500);
    frame_bs5.setVisible(true);
    
    JFrame frame_bs6 = new JFrame("Stego RGB");
    Panel panel_bs6 = new ShowImage(oi.getDirectory()+"Stego_Color.jpg");
    frame_bs6.getContentPane().add(panel_bs6);
    frame_bs6.setSize(500, 500);
    //frame_bs6.setVisible(true);
    
   
      
       }
    public  void MAIN(String args) throws IOException {
        //1.1 read image
        ImagePlus original_image = null ;
        BufferedImage image_oi=null;
        File f_oi;
        oi =new ij.io.OpenDialog("Select image","E:\\DATA\\YES_MAN\\JAVA_IMPL\\", "lenna.bmp");
        try {f_oi=new File(oi.getDirectory()+oi.getFileName());
        image_oi = ImageIO.read(f_oi);
        } catch (IOException e) {}
        try {ImageIO.write(image_oi, "jpg", new File(oi.getDirectory()+"Original_Gray.jpg"));
        }catch (IOException e) {}

      
        
        BufferedImage Y = new BufferedImage(image_oi.getWidth(), image_oi.getHeight(),  BufferedImage.TYPE_INT_ARGB);
        BufferedImage Cb= new BufferedImage(image_oi.getWidth(), image_oi.getHeight(),  BufferedImage.TYPE_INT_ARGB);
        BufferedImage Cr = new BufferedImage(image_oi.getWidth(), image_oi.getHeight(),  BufferedImage.TYPE_INT_ARGB);
        BufferedImage YCbCr = new BufferedImage(image_oi.getWidth(), image_oi.getHeight(),  BufferedImage.TYPE_INT_ARGB);
        
        BufferedImage Ym = new BufferedImage(image_oi.getWidth(), image_oi.getHeight(),  BufferedImage.TYPE_INT_ARGB);
        BufferedImage Cbm= new BufferedImage(image_oi.getWidth(), image_oi.getHeight(),  BufferedImage.TYPE_INT_ARGB);
        BufferedImage Crm = new BufferedImage(image_oi.getWidth(), image_oi.getHeight(),  BufferedImage.TYPE_INT_ARGB);
        BufferedImage YCbCrm = new BufferedImage(image_oi.getWidth(), image_oi.getHeight(),  BufferedImage.TYPE_INT_ARGB);
        
      //1.2. convert to grayscale
        
        BufferedImage gray_i = new BufferedImage(image_oi.getWidth(), image_oi.getHeight(),  BufferedImage.TYPE_INT_ARGB);
        
        
        BufferedImage gray_with_alpha = new BufferedImage(gray_i.getWidth(), gray_i.getHeight(),  BufferedImage.TYPE_INT_ARGB);
        int alpha=0;
        int red=0;
        int green=0;
        int blue=0;
        int rgb=0;
        int k=0;
        
        
        
          //convert to YCbCr
        YCbCr obj=new YCbCr();
        for(int i=0;i<gray_i.getWidth();i++)
            for(int j=0;j<gray_i.getHeight();j++)
            {rgb=  image_oi.getRGB(i, j);
            alpha=255;
            red=(rgb & 0x00FF0000)  >>> 16;           
            green=(rgb & 0x0000FF00)  >>> 8;
            blue=(rgb & 0x000000FF)  >>> 0;
            rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
            gray_with_alpha.setRGB(i, j, rgb);
            gray_i.setRGB(i, j, rgb);
            
            
            obj.rgb2ycbcr(red, green, blue);
            
            
            //set Y plane
            red=(int)obj.getY();
            green=(int)obj.getY();
            blue=(int)obj.getY();
            rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
            //System.out.println("Y="+red);
            Y.setRGB(i,j , rgb);
            //Ym.setRGB(i,j , rgb);
            //set Cb plane 
            red=(int) obj.getCb();
            green=(int) obj.getCb();
            blue=(int) obj.getCb();
            rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
            Cb.setRGB(i,j, rgb );
            //Cbm.setRGB(i,j , rgb);
            //set Cr
            red=(int) obj.getCr();
            green=(int) obj.getCr();
            blue=(int) obj.getCr();
            rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
            Cr.setRGB(i,j , rgb);
            //Crm.setRGB(i,j , rgb);
            
            
            //set YCbCr
            red=(int) obj.getY();
            green=(int) obj.getCb();
            blue=(int) obj.getCr();
            rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;

            YCbCr.setRGB(i, j, rgb);
            //YCbCrm.setRGB(i,j , rgb);
          }
        
        //1.3 save graysacle image
        try { ImageIO.write(gray_with_alpha, "jpg", new File(oi.getDirectory()+"Original_Gray.jpg"));
        }catch (IOException e) {}
        
        //1.4 convert to binary     
        int data[]=new int[image_oi.getWidth()*image_oi.getHeight()];
        int Gray_Image_original[][]=new int[image_oi.getWidth()][image_oi.getHeight()];
        int Alpha_Image_original[][]=new int[image_oi.getWidth()][image_oi.getHeight()];
        int Binary_Image_original[][]=new int[image_oi.getWidth()][image_oi.getHeight()];        
        int Alpha_Image_mod[][]=new int[image_oi.getWidth()][image_oi.getHeight()];
         
          k=0; 
        for (int cx=0;cx<image_oi.getWidth();cx++) {          
            for (int cy=0;cy<image_oi.getHeight();cy++) {
                rgb = Y.getRGB(cx, cy); 
                 red=(rgb & 0x00FF0000)  >>> 16;           
                 green=(rgb & 0x0000FF00)  >>> 8;
                 blue=(rgb & 0x000000FF)  >>> 0;
                              
                data[k++]=(red+green+blue)/3;
                Gray_Image_original[cx][cy]=(red+green+blue)/3;
                Alpha_Image_original[cx][cy]=255;
            }
        }
        //1.4.1 compute g1 and g2
        int histogram[]=new int[256];//histogram og gray image
        for (int i=0; i<data.length; i++)
            histogram[data[i]]=histogram[data[i]]+1;
        
        G = Moments(histogram);
        
       //System.out.println("Stage I—generation of authentication signals");
       //System.out.println("\nStep 1.Input image binarization");
       //System.out.println("\nMoment Preserving thresholding");
       //System.out.println("Value of gray level g1="+G[0]);
       //System.out.println("Value of gray level g2="+G[1]);
       //1.4.2 compute threshold for binarization  
       thres=(G[0]+G[1])/2.0;
      //System.out.println("Value of threshod for binarization="+thres);
       
       BufferedImage Binary_i = new BufferedImage(gray_i.getWidth(), gray_i.getHeight(),  BufferedImage.TYPE_INT_ARGB);
       
       //1.4.3 binarize gray image
       k=0;
       for (int cx=0;cx<image_oi.getWidth();cx++) {          
           for (int cy=0;cy<image_oi.getHeight();cy++) {
                          if(data[k]<thres)
                          {Binary_Image_original[cx][cy]=0;}
                          else
                              Binary_Image_original[cx][cy]=1;  
                          
                          k++;
                          alpha=255; 
                          red=Binary_Image_original[cx][cy]*255; 
                          green=Binary_Image_original[cx][cy]*255;
                          blue=Binary_Image_original[cx][cy]*255;
                          rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
                          Binary_i.setRGB(cx, cy, rgb);
           }
       }
       
       //save binary iamge with alpha
       try {ImageIO.write(Binary_i, "jpg", new File(oi.getDirectory()+"Original_Binary.jpg"));
       }catch (IOException e) {} 
       
       //get Binary and alpha into 2D array
       int  lim_r,  lim_c;
       lim_c=image_oi.getWidth()-(image_oi.getWidth()%3) ;//columns
       lim_r=image_oi.getHeight()-(image_oi.getHeight()%2) ;//rows
       int BI[][]=new int[lim_c][lim_r]; 
       int AI[][]=new int[lim_c][lim_r]; 
       int AI_mod[][]=new int[lim_c][lim_r];  
       
      //System.out.println("\n\nContents of Binarized image");
       for (int cx=0;cx<lim_c;cx++) {
           for (int cy=0;cy<lim_r;cy++)
           {BI[cx][cy]= Binary_Image_original[cx][cy];
           //System.out.println("BI="+BI[cx][cy]+"\tcol="+cx+"\trow="+cy);            
           }
       }
       
        
       
       
       //find key K and inverse
       int K[]= new int[lim_r];
       int K_i[]= new int[lim_r];
       RandomPermutation rp=new RandomPermutation();
       K=rp.GenerateRandomPermutation(lim_r);
      //System.out.println("\nGenerating Key value for authentication:\nKey Length="+K.length);
       for ( k = 0; k < K.length; k++)
          //System.out.print("  " + K[k])
           ;
       
       int index=-1;
       for(int ii=0;ii<lim_r;ii++)
       {k=0;
       index=-1;
       while(true)
       {if(K[k]==ii)   {index=k;break;}      else k++; } 
       K_i[ii]=index;
       }
       
      //System.out.println("\nInverse Key length="+K.length+"\nInverse Key:");
     for(int ii=0;ii<lim_r;ii++)
     {//System.out.print(K_i[ii]+" ");
     }
       //2. for every block of binary and alpha 
        
       boolean p[]=new boolean[6];
       int SDATA_mod[][]=new int[lim_c][lim_r]; 
       int SDATA[][]=new int[lim_c][lim_r]; 
       //System.out.println("\nbefore:");
       int blk_no=0;
        for (int j=0; j<lim_c  && j%3==0 ;j=j+3)
            for(int i=0; i<lim_r  && i%2==0;i=i+2) 
          { 
              //System.out.println("\n\nBlock no="+blk_no++);
               if( BI[j][i]==1)p[0]=true;        else        p[0]=false;               
               if( BI[j][i+1]==1)p[1]=true;      else        p[1]=false;              
               if( BI[j+1][i]==1)p[2]=true;      else        p[2]=false;             
               if( BI[j+1][i+1]==1)p[3]=true;    else        p[3]=false;       
               if( BI[j+2][i]==1)p[4]=true;      else        p[4]=false;    
               if( BI[j+2][i+1]==1)p[5]=true;    else        p[5]=false; 
               
               boolean a1,a2;
               a1=(p[0]  ^  p[1]) ^ p[2];               
               a2=(p[3]  ^  p[4]) ^ p[5];
              //System.out.println("p[0]="+p[0]+"\tp[1]="+p[1]+"\tp[2]="+p[2]+"\tp[3]="+p[3]+"\tp[4]="+p[4]+"\tp[5]="+p[5]);
               //System.out.println("s[0]="+a1+"\ts[1]="+a2);
              //System.out.println("a1="+a1+"\ta2="+a2);
               
               int  m1[]=new int [4];
               if(a1==true)      m1[0]=1;            else        m1[0]=0;
               if(a2==true)      m1[1]=1;            else        m1[1]=0;
               if(p[0]==true)    m1[2]=1;            else        m1[2]=0;
               if(p[1]==true)    m1[3]=1;            else        m1[3]=0;
               
               int m2[]=new int [4];
               if(p[2]==true)    m2[0]=1;            else        m2[0]=0;
               if(p[3]==true)    m2[1]=1;            else        m2[1]=0;
               if(p[4]==true)    m2[2]=1;            else        m2[2]=0;
               if(p[5]==true)    m2[3]=1;            else        m2[3]=0;  
               
               int d=0,c1=0;  d=(m1[0]*2*2*2+m1[1]*2*2+m1[2]*2+m1[3]*1);
               c1=(m2[0]*2*2*2+m2[1]*2*2+m2[2]*2+m2[3]*1);
               int x[]=new int[6];
               int q[]=new int[6];
               int q1[]=new int[6];
               int F[]=new int[6];
               for (int mmm=0;mmm<6;mmm++)
               {x[mmm]=mmm+1;
               F[mmm]=   ((d+c1*x[mmm])%17);
               q[mmm]=  ((d+c1*x[mmm])%17);
               q1[mmm]=  (q[mmm]+238);
              //System.out.println("q["+mmm+"]="+q[mmm] );
               }
              //System.out.println("Step 7. (Mapping of the partial shares)");
              //System.out.println(("d="+d+"\tc1="+c1));
              
              //System.out.println("Step 8. (Embedding two partial shares in the current block)");
              AI[j][i]=  q1[0];               
              AI[j][i+1]=q1[1];               
              AI[j+1][i]=q1[2];              
              AI[j+1][i+1]=q1[3];
              AI[j+2][i]=q1[4];
              AI[j+2][i+1]=q1[5];  
              
              AI_mod[j][i]=  q1[0];               
              AI_mod[j][i+1]=q1[1];               
              AI_mod[j+1][i]=q1[2];              
              AI_mod[j+1][i+1]=q1[3];
              AI_mod[j+2][i]=q1[4];
              AI_mod[j+2][i+1]=q1[5];  
            
              //System.out.println();
              for(int iii=0;iii<6;iii++)
                  //System.out.print(" "+q1[iii]+" ");    
             
                  
              SDATA_mod[j][i]= q[0]; 
              SDATA_mod[j][i+1]=q[1]; 
              SDATA_mod[j+1][i]= q[2];  
              SDATA_mod [j+1][i+1]=q[3];
              SDATA_mod[j+2][i] =q[4];
              SDATA_mod[j+2][i+1] = q[5];  
              
              SDATA[j][i]= q[0]; 
              SDATA[j][i+1]=q[1];
              SDATA[j+1][i]= q[2]; 
              SDATA[j+1][i+1]=q[3];  
              SDATA[j+2][i] =q[4];  
              SDATA[j+2][i+1] = q[5]; 
           } 
      
    //display randomize result
      //System.out.println("\n\nBefore randomizaton:");        
          for ( int j=0; j<lim_c  && j%3==0 ;j=j+3) 
               for(int i=0; i<lim_r  && i%2==0;i=i+2) {
                   //System.out.println();
                   //System.out.println("("+j+","+i+")"+AI_mod[j][i]+" ("+(j+1)+","+i+")"+AI_mod[j+1][i]+" ("+(j+2)+","+(i)+") "+AI_mod[j+2][i]);  
                   //System.out.println("("+(j)+","+(i+1)+")"+AI_mod[j][(i+1)]+" ("+(j+1)+","+(i+1)+")"+AI_mod[(j+1)][i+1]+" ("+(j+2)+","+(i+1)+") "+AI_mod[j+2][(i+1)]);                   
               }  
    //randomize data
    //System.out.println("doing ...");
          
       for (int j=0; j<lim_c;j++){
          //System.out.println("Randomizing alpha palne ...");
            if(j%3 !=0)
           {//get current column 
             //rearrane it as per k
               
             for(int i=0; i<lim_r ;i++) {
                //System.out.println("*****************************");
                 //System.out.println("\n("+j+","+i+")"+AI_mod[j][i]+"\tK["+i+"]="+K[i]);
                 AI_mod[j][i]=AI[j][K[i]]; 
                 //System.out.println("("+j+","+i+")"+AI_mod[j][i]+"\tK["+i+"]="+K[i]);
                 //System.out.println("*****************************");
                SDATA_mod[j][i]=SDATA[j][K[i]];
           }
       }
       }
       
             
          
          //System.out.println("\n\nAfter randomizaton:");        
          for ( int j=0; j<lim_c  && j%3==0 ;j=j+3) 
               for(int i=0; i<lim_r  && i%2==0;i=i+2) {
             //System.out.println();
             //System.out.println("("+j+","+i+")"+AI_mod[j][i]+" ("+(j+1)+","+i+")"+AI_mod[j+1][i]+" ("+(j+2)+","+(i)+") "+AI_mod[j+2][i]);  
             //System.out.println("("+(j)+","+(i+1)+")"+AI_mod[j][(i+1)]+" ("+(j+1)+","+(i+1)+")"+AI_mod[(j+1)][i+1]+" ("+(j+2)+","+(i+1)+") "+AI_mod[j+2][(i+1)]);                   
           }  
            
            
      // BufferedImage stego_color=new BufferedImage(gray_i.getWidth(), gray_i.getHeight(),  BufferedImage.TYPE_INT_ARGB);
       BufferedImage stego_gray=new BufferedImage(gray_i.getWidth(), gray_i.getHeight(),  BufferedImage.TYPE_INT_ARGB);
       BufferedImage stego_binary=new BufferedImage(gray_i.getWidth(), gray_i.getHeight(),  BufferedImage.TYPE_INT_ARGB);
       BufferedImage stego_color=new BufferedImage(gray_i.getWidth(), gray_i.getHeight(),  BufferedImage.TYPE_INT_ARGB);
       //stego_binary=Binary_i;
       for (int cy=0;cy<lim_c;cy++) { 
            for (int cx=0;cx<lim_r;cx++){
               rgb=image_oi.getRGB(cy, cx); 
               alpha=AI_mod[cy][cx];
               red=(rgb & 0x00FF0000)  >>> 16;   
               green=(rgb & 0x0000FF00)  >>> 8;
               blue=(rgb & 0x000000FF)  >>> 0;    
               rgb=(alpha << 24) | (blue << 16) | (blue << 8) | blue;
              // stego_color.setRGB(cy, cx, rgb);  
               ////System.out.println("alpha="+alpha);
               
               rgb=gray_with_alpha.getRGB(cy, cx);
             
               //alpha=AI[cy][cx];
               red=(rgb & 0x00FF0000)  >>> 16;
               green=(rgb & 0x0000FF00)  >>> 8;
               blue=(rgb & 0x000000FF)  >>> 0;
               rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
               stego_gray.setRGB(cy, cx, rgb);   
               
               rgb=Binary_i.getRGB(cy, cx);
                
               red=(rgb & 0x00FF0000)  >>> 16;
               green=(rgb & 0x0000FF00)  >>> 8;
               blue=(rgb & 0x000000FF)  >>> 0;
               rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
               stego_binary.setRGB(cy, cx, rgb); 
               ////System.out.println();
               //modify Y
               //SDATA_mod
               rgb=Y.getRGB(cy, cx);
              
               red=(rgb & 0x00FF0000)  >>> 16;
               green=(rgb & 0x0000FF00)  >>> 8;
               blue=(rgb & 0x000000FF)  >>> 0;
               //red=red+AI_mod[cy][cx]-238;
               //green=green+AI_mod[cy][cx]-238;
               //blue=blue+AI_mod[cy][cx]-238;
               
               rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
               ////System.out.println("alpha="+alpha+"red="+red+"green="+green+"blue="+blue+"RGB="+Integer.toString(rgb,2));
               Ym.setRGB(cy, cx, rgb); 
               
               
               
               
               //modify Cb
               //SDATA_mod
               rgb=Cb.getRGB(cy, cx);
              
               red=(rgb & 0x00FF0000)  >>> 16;
               green=(rgb & 0x0000FF00)  >>> 8;
               blue=(rgb & 0x000000FF)  >>> 0;
               
               red=red+AI_mod[cy][cx]-238;
               green=green+AI_mod[cy][cx]-238;
               blue=blue+AI_mod[cy][cx]-238;
               //red=AI_mod[cy][cx];
               //green=AI_mod[cy][cx];
               //blue=AI_mod[cy][cx];
               
               

               rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
               
               Cbm.setRGB(cy, cx, rgb); 
               
               
               //modify Cr componenet
               rgb=Cr.getRGB(cy, cx);
               
               red=(rgb & 0x00FF0000)  >>> 16;
               green=(rgb & 0x0000FF00)  >>> 8;
               blue=(rgb & 0x000000FF)  >>> 0;
               
               red=red+AI_mod[cy][cx]-238;
               green=green+AI_mod[cy][cx]-238;
               blue=blue+AI_mod[cy][cx]-238;
               //red=AI_mod[cy][cx];
               //green=AI_mod[cy][cx];
               //blue=AI_mod[cy][cx];

               rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
               
               Crm.setRGB(cy, cx, rgb); 
               
               
               
                //modify YCbCr componenet
                
               
               
               rgb=Ym.getRGB(cy, cx);
               red=(rgb & 0x00FF0000)  >>> 16;
               
               rgb=Cbm.getRGB(cy, cx);               
               green=(rgb & 0x0000FF00)  >>> 8;
               
               rgb=Crm.getRGB(cy, cx);
               blue=(rgb & 0x000000FF)  >>> 0;
               
               alpha=AI_mod[cy][cx];
               //red=red+AI_mod[cy][cx]-238;
               //green=green+AI_mod[cy][cx]-238;
               //blue=blue+AI_mod[cy][cx]-238;

               rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
               
               YCbCrm.setRGB(cy, cx, rgb); 
               
                
           }
       }
        
      
       
    
      
      try {    
          ImageIO.write(stego_gray, "jpg", new File(oi.getDirectory()+"Stego_Gray.jpg"));
      }catch (IOException e) {} 
      try {     
          ImageIO.write(stego_binary, "jpg", new File(oi.getDirectory()+"Stego_Binary.jpg"));
      }catch (IOException e) {} 
     try {     
          ImageIO.write(stego_binary, "png", new File(oi.getDirectory()+"Stego_Binary.png"));
      }catch (IOException e) {} 
      
      //save Y palne Cb plane and Cr plane
      try {    
          ImageIO.write(Y, "jpg", new File(oi.getDirectory()+"Y.jpg"));
      }catch (IOException e) {} 
      
       try {    
          ImageIO.write(Cb, "jpg", new File(oi.getDirectory()+"Cb.jpg"));
      }catch (IOException e) {} 
      
        try {    
          ImageIO.write(Cr, "jpg", new File(oi.getDirectory()+"Cr.jpg"));
      }catch (IOException e) {} 
        
        
        try {    
          ImageIO.write(YCbCr, "jpg", new File(oi.getDirectory()+"YCbCr.jpg"));
      }catch (IOException e) {} 
        
        
        
        try {    
          ImageIO.write(Ym, "jpg", new File(oi.getDirectory()+"Ym.jpg"));
      }catch (IOException e) {} 
      
       try {    
          ImageIO.write(Cbm, "jpg", new File(oi.getDirectory()+"Cbm.jpg"));
      }catch (IOException e) {} 
      
        try {    
          ImageIO.write(Crm, "jpg", new File(oi.getDirectory()+"Crm.jpg"));
      }catch (IOException e) {} 
        
        
        try {    
          ImageIO.write(YCbCrm, "jpg", new File(oi.getDirectory()+"YCbCrm.jpg"));
      }catch (IOException e) {} 
        
        
        
        
        //convert YCbCrm to RGB and save
         //convert to YCbCr
        YCbCr obj1=new YCbCr();
        for(int i=0;i<gray_i.getWidth();i++)
            for(int j=0;j<gray_i.getHeight();j++)
            {rgb=YCbCrm.getRGB(i, j);
            alpha=(rgb & 0xFF000000)  >>> 24;
            red=(rgb & 0x00FF0000)  >>> 16;           
            green=(rgb & 0x0000FF00)  >>> 8;
            blue=(rgb & 0x000000FF)  >>> 0;          
            
            
            obj1.ycbcr2rgb(red, green, blue);            
            
            //set Y plane
            red=(int)obj1.getRed();
            green=(int)obj1.getGreen();
            blue=(int)obj1.getBlue();
            rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
            ////System.out.println(rgb);
            stego_color.setRGB(i,j , rgb);
            }
    
         //save modified PNG image color
     try {     
          ImageIO.write(stego_color, "jpg", new File(oi.getDirectory()+"Stego_Color.jpg"));
     }catch (IOException e) {} 
      
        
      
      
    //save values key and g1 g2
      FileWriter fw2 = new FileWriter(oi.getDirectory()+"KEY.txt");
      BufferedWriter bw2 = new BufferedWriter(fw2);
      
      String content = String.valueOf((G[0]));
      bw2.write(content+"\n");
      
      content = String.valueOf((G[1]));
      bw2.write(content+"\n");
      
      for(int i=0;i<K.length;i++){
      content = String.valueOf(K[i]);
      bw2.write(content+"\n");
      }
      
      bw2.close();
      //diplay images
      display_images();
      
      //PSNR claculation
      
         PSNR TMP=new PSNR(); 
        double psnr = PSNR.psnr(YCbCr,YCbCrm);
    	System.out.println("psnr = "+psnr);
      image_oi.flush();
     //System.out.println("End of alg 3"); 
    }

     

}

