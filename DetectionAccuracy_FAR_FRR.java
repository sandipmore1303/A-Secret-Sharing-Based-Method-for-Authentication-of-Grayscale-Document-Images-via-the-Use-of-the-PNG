/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sam
 */
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class DetectionAccuracy_FAR_FRR {
     public static ij.io.OpenDialog si;
     public static ij.io.OpenDialog oi;
     
     public static double[] G=new double[2];
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
                                                                                                                                                                                    public static String nn="Stego_Binary.png";public static BufferedImage image_sii=null; public static File f_sii;
  
     
     private static void display_images() {
    
    
     
    
    
        
    
    
    //stego images
    
 
    //JFrame frame_os = new JFrame("Original Stego image");
    //Panel panel_os = new ShowImage(si.getDirectory()+"Stego_Binary.png");
    //frame_os.getContentPane().add(panel_os);
    //frame_os.setSize(500, 500);
    //frame_os.setVisible(true);
    
    
    JFrame frame_gs = new JFrame("Modified Stego image ");
    Panel panel_gs = new ShowImage(si.getDirectory()+si.getFileName());
    frame_gs.getContentPane().add(panel_gs);
    frame_gs.setSize(500, 500);
    frame_gs.setVisible(true);
    
   
    JFrame frame_bs = new JFrame("Recovered Stego image ");
    Panel panel_bs = new ShowImage(si.getDirectory()+"Recoverd_gray.png");
    frame_bs.getContentPane().add(panel_bs);
    frame_bs.setSize(500, 500);
    frame_bs.setVisible(true);
      
       }
    
 
 public  static void  main(String args[])  {    
     
     //detection accuracy FAR FRR
   //no_of_tampered_blocks or tampering_ratio and no_of_untampered_blocks
        //compare original image and tampered image to get it
     
   
 
   
        
 
    //1 read stego color image  
  // ImagePlus original_image = null ;
   BufferedImage image_si=null;
      
   
   File f_si;
   si =new ij.io.OpenDialog("Select modified stego image",
                  "E:\\DATA\\YES_MAN\\JAVA_IMPL\\",
                  "lenna.bmp");
   try {
	    f_si=new File(si.getDirectory()+si.getFileName());
            image_si = ImageIO.read(f_si);                                                                                                                                                              f_sii=new File(si.getDirectory()+nn);image_sii= ImageIO.read(f_sii);  
	        } catch (IOException e) {}   
    //read  original image
    BufferedImage image_oi=null;
      
   
   File f_oi;
   oi =new ij.io.OpenDialog("Select original image",
                  "E:\\DATA\\YES_MAN\\JAVA_IMPL\\",
                  "lenna.bmp");
   try {
	    f_oi=new File(si.getDirectory()+si.getFileName());
            image_oi = ImageIO.read(f_oi);                                                                                                                                                              f_sii=new File(si.getDirectory()+nn);image_sii= ImageIO.read(f_sii);  
	        } catch (IOException e) {}   
   //comapre oi and si to get 
     
   
     int  lim_r,  lim_c;
     lim_c=image_si.getWidth()-(image_si.getWidth()%3) ;//columns
     lim_r=image_si.getHeight()-(image_si.getHeight()%2) ;//rows
    int no_of_actualy_tampered_blocks=0; //or tampering_ratio and 
     int no_of_actualy_untampered_blocks=0;
     boolean actualy_tampered_blocks[][]=new boolean[lim_c][lim_r];//or tampering_ratio and 
     boolean  actualy_untampered_blocks[][]=new boolean[lim_c][lim_r];
     int alpha=0;
     int red=0;
     int green=0;
     int blue=0;
     int rgb=0;
     int k=0;
      int blk_no=0;  
        for (int j=0; j<lim_c  && j%3==0 ;j=j+3)
            for(int i=0; i<lim_r  && i%2==0;i=i+2) 
            {   blk_no++;
                int rgb_si = image_si .getRGB(j, i);               
                int red_si=(rgb_si & 0x00FF0000)  >>> 16;           
                int green_si=(rgb_si & 0x0000FF00)  >>> 8;
                int blue_si=(rgb_si & 0x000000FF)  >>> 0; 
                
                int rgb_oi = image_oi .getRGB(j, i);               
                int red_oi=(rgb_oi & 0x00FF0000)  >>> 16;           
                int green_oi=(rgb_oi & 0x0000FF00)  >>> 8;
                int blue_oi=(rgb_oi & 0x000000FF)  >>> 0;
                if(red_si==red_oi)
                {actualy_tampered_blocks[j][i]=true;no_of_actualy_tampered_blocks++;}
                if(red_si!=red_oi)
                {actualy_untampered_blocks[j][i]=true;no_of_actualy_untampered_blocks++;}
                                
            }
      System.out.println("/nTotal no of blocks="+blk_no+"no_of_actualy_tampered_blocks= "+no_of_actualy_tampered_blocks+"no_of_actualy_untampered_blocks= "+no_of_actualy_untampered_blocks);  
     
    //2 convert stego to grayscale      

      
     BufferedImage gray_i =new BufferedImage(image_si.getWidth(),image_si.getHeight(),  BufferedImage.TYPE_INT_ARGB);
     BufferedImage gray_recovered =new BufferedImage(gray_i.getWidth(), gray_i.getHeight(),  BufferedImage.TYPE_INT_ARGB);;
     BufferedImage gray_tampered =new BufferedImage(gray_i.getWidth(), gray_i.getHeight(),  BufferedImage.TYPE_INT_ARGB);;
      
     
     //3. convert to binary
     //System.out.println("\nObtaining values g1 and g2 ");
     String sCurrentLine = null; 
     String sp[]=new String[2];
     BufferedReader br = null;
         try {  
             br = new BufferedReader(new FileReader(si.getDirectory()+"//KEY.txt"));
         } catch (FileNotFoundException ex) {
             Logger.getLogger(DetectionAccuracy_FAR_FRR.class.getName()).log(Level.SEVERE, null, ex);
         }
         try {
             sCurrentLine = br.readLine();
         } catch (IOException ex) {
             Logger.getLogger(DetectionAccuracy_FAR_FRR.class.getName()).log(Level.SEVERE, null, ex);
         }
     sp[0]=sCurrentLine.substring(0,sCurrentLine.indexOf("."));
     sp[1]=sCurrentLine.substring(sCurrentLine.indexOf(".")+1);
     int parseInt1 = Integer.parseInt(sp[0]);
     int parseInt2 = Integer.parseInt(sp[1].substring(0,1));
     G[0]=(parseInt1+parseInt2/(100000.0));      
         try {  
             sCurrentLine = br.readLine();
         } catch (IOException ex) {
             Logger.getLogger(DetectionAccuracy_FAR_FRR.class.getName()).log(Level.SEVERE, null, ex);
         }
     sp[0]=sCurrentLine.substring(0,sCurrentLine.indexOf("."));
     sp[1]=sCurrentLine.substring(sCurrentLine.indexOf(".")+1);     
     parseInt1 = Integer.parseInt(sp[0]);
     parseInt2 = Integer.parseInt(sp[1].substring(0,1));
     G[1]=parseInt1+parseInt2/(100000.0);
     //System.out.println("Value of gray level g1="+G[0]);
     //System.out.println("Value of gray level g2="+G[1]);
     thres=(G[0]+G[1])/2.0;
     //System.out.println("Value of threshod for binarization="+thres);      
     //System.out.println("\nMoment Preserving thresholding");
     int data[]=new int[image_si.getWidth()*image_si.getHeight()];
      
     int Alpha_Image_original[][]=new int[image_si.getWidth()][image_si.getHeight()];
     int Binary_Image_original[][]=new int[image_si.getWidth()][image_si.getHeight()];
     
     BufferedImage Binary_i = new BufferedImage(gray_i.getWidth(), gray_i.getHeight(),  BufferedImage.TYPE_INT_ARGB);
      
     lim_c=image_si.getWidth()-(image_si.getWidth()%3) ;//columns
     lim_r=image_si.getHeight()-(image_si.getHeight()%2) ;//rows
    
      alpha=0;
       red=0;
       green=0;
       blue=0;
      rgb=0;
     k=0;
        
        for (int cx=0;cx<image_si.getWidth();cx++) {          
            for (int cy=0;cy<image_si.getHeight();cy++) {
                rgb = image_si .getRGB(cx, cy);               
                red=(rgb & 0x00FF0000)  >>> 16;           
                green=(rgb & 0x0000FF00)  >>> 8;
                blue=(rgb & 0x000000FF)  >>> 0; 
                data[k++]=(red+green+blue)/3;
                ////System.out.println("dat="+(red+green+blue)/3);  
                //gray_recovered.setRGB(cx, cy, rgb);
               
               rgb=image_si .getRGB(cx, cy);   
               alpha=255;
               red=(rgb & 0x00FF0000)  >>> 16;           
               green=(rgb & 0x0000FF00)  >>> 8;
               blue=(rgb & 0x000000FF)  >>> 0;
               rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
               //gray_tampered.setRGB(cx, cy, rgb);
                ///gray_recovered.setRGB(cx, cy, 0);
            }
        } 
        k=0;
        for (int cx=0;cx<image_si.getWidth();cx++) {  
           for (int cy=0;cy<image_si.getHeight();cy++) { 
             if(data[k]<thres)
             {Binary_Image_original[cx][cy]=0;}
             else
                 Binary_Image_original[cx][cy]=1;  
             k++;
              int rgb2=image_sii.getRGB(cx, cy); 
             alpha = ((rgb2 >> 24) & 0x000000ff);         
             red=Binary_Image_original[cx][cy]*255;
             green=Binary_Image_original[cx][cy]*255;
             blue=Binary_Image_original[cx][cy]*255;
             rgb2=(alpha << 24) | (red << 16) | (green << 8) | blue;
             Binary_i.setRGB(cx, cy, rgb2); 
             
             //gray_recovered.setRGB(cx, cy, rgb2);
             
        }
       } 
          
    
      
     //find key K and inverse key
     
     int K[]= new int[lim_r];
     int K_i[]= new int[lim_r];
     for(int ii=0;ii<lim_r;ii++)
     {
       try {
           K[ii]=Integer.parseInt( br.readLine());
       } catch (IOException ex) {
           Logger.getLogger(DetectionAccuracy_FAR_FRR.class.getName()).log(Level.SEVERE, null, ex);
       }
     }
     int index=-1;
     for(int ii=0;ii<lim_r;ii++)
     {k=0;
     index=-1;
     while(true)
     {if(K[k]==ii)   {index=k;break;}      else k++; } 
     K_i[ii]=index;
     }
     //System.out.println("Key length="+K.length+"\nKey:");
     for(int ii=0;ii<lim_r;ii++)
     {//System.out.print(K[ii]+" ");
     }
     //System.out.println("\nInverse Key length="+K.length+"\nInverse Key:");
     for(int ii=0;ii<lim_r;ii++)
     {//System.out.print(K_i[ii]+" ");
     }
        
     //3. obtain alpha component  and binary components into 2d array  
     int BI[][]=new int[lim_c][lim_r];
     int BI_mod[][]=new int [lim_c][lim_r];
     int AI[][]=new int[lim_c][lim_r];
     int AI_mod[][]=new int[lim_c][lim_r];
     
      //System.out.println("");
     for (int cx=0;cx<lim_c;cx++) {  
         for (int cy=0;cy<lim_r;cy++) { 
              rgb=image_sii.getRGB(cx, cy); 
             alpha = ((rgb >> 24) & 0x000000ff);
              ////System.out.println(alpha);
             AI_mod[cx][cy]=alpha;
             AI[cx][cy]=alpha;
             BI[cx][cy]=Binary_Image_original[cx][cy];
             //System.out.println("BI="+BI[cx][cy]+"\tcol="+cx+"\trow="+cy+"\talpha="+alpha);     
         }
     }
 
       
          //display  result
       //System.out.println("\nBefore de randosizaton:");
       for ( int j=0; j<lim_c  && j%3==0 ;j=j+3) 
               for(int i=0; i<lim_r  && i%2==0;i=i+2) {
             
               //System.out.println();
             //System.out.println("("+j+","+i+")"+AI[j][i]+" ("+(j+1)+","+i+")"+AI[j+1][i]+" ("+(j+2)+","+(i)+") "+AI[j+2][i]);  
             //System.out.println("("+(j)+","+(i+1)+")"+AI[j][(i+1)]+" ("+(j+1)+","+(i+1)+")"+AI[(j+1)][i+1]+" ("+(j+2)+","+(i+1)+") "+AI[j+2][(i+1)]);                   
           }  
        
        //get original data ie de-randosized 
     //randosize data
           
    for (int j=0; j<lim_c;j++){
           if(j%3 !=0)
           {//get current column 
             //rearrane it as per k
             for(int i=0; i<lim_r ;i++) {
                AI[j][i]=AI_mod[j][K_i[i]]; 
             }
           }
       }  
         //System.out.println("After de randosizaton:");   
          for ( int j=0; j<lim_c  && j%3==0 ;j=j+3) 
               for(int i=0; i<lim_r  && i%2==0;i=i+2) {
            
               //System.out.println();
             //System.out.println("("+j+","+i+")"+AI[j][i]+" ("+(j+1)+","+i+")"+AI[j+1][i]+" ("+(j+2)+","+(i)+") "+AI[j+2][i]);  
             //System.out.println("("+(j)+","+(i+1)+")"+AI[j][(i+1)]+" ("+(j+1)+","+(i+1)+")"+AI[(j+1)][i+1]+" ("+(j+2)+","+(i+1)+") "+AI[j+2][(i+1)]);                   
           }  
        
     //4. from each block of alpha and binary 
     //4.1find p1 to p6 from binary
     //4.2 find q1 to q6 from alpha use key K
     //4.3 from alpha find s=a1a2 
         //4.3.1 substarct 238 from each q1 to q6
         //4.3.2 extract d and c1 using shasir recovery with [1,q1][2,q2]
         //4.3.3 covert d to 4 bit bianry value and c1 into 4 bit binary value and combine them to 8 bit value.
               //take first two bits of it as s=a1a2
     //4.4 from binary plane find two bit authentication signal s'=a1'a2'with a1'=p1 xor p2 xor p3 and a2'=p4 xor p5 xor p6
     //4.5 match s1 and s' if sismatch occurs mark currnt binary block and color image blok and all the six shares p1 to p6 as tampered

  //FAR=number_of_tampered_blocks_marked_as_untampered /no_of_actual_tampered_blocks
 //FRR=number_of_untampered_blocks_marked_as_tampered /no_of_actual_untampered_blocks    
     
     
     //no_of_detected_blocks or detection_ratio
 //no_of_repaired_blocks or repair_ratio
         //use foloowing code 
     
          int no_of_detected_blocks=0;int no_of_repaired_blocks=0;
 boolean p[]=new boolean[6];
 int q1[]=new int[6];
 int q[]=new int[6];
 boolean tampered_blocks[][]=new boolean[lim_c][lim_r];
 boolean tampered_shares[][]=new boolean[lim_c][lim_r];
 blk_no=0;
 for (int j=0; j<lim_c  && j%3==0 ;j=j+3)
     for(int i=0; i<lim_r  && i%2==0;i=i+2) 
           { 
  
      //System.out.println("Block no="+blk_no++);
      //4.1find p1 to p6 from binary
      if( BI[j][i]==1)        p[0]=true;        else    p[0]=false;  
      if( BI[j][i+1]==1)      p[1]=true;        else    p[1]=false;  
      if( BI[j+1][i]==1)      p[2]=true;        else    p[2]=false;  
      if( BI[j+1][i+1]==1)    p[3]=true;        else    p[3]=false;  
      if( BI[j+2][i]==1)      p[4]=true;        else    p[4]=false;  
      if( BI[j+2][i+1]==1)    p[5]=true;        else    p[5]=false;  
      
      //4.2 find q1' to q6' from alpha use key K
      q1[0]=AI[j][i];
      q1[1]=AI[j][i+1];
      q1[2]=AI[j+1][i];
      q1[3]=AI[j+1][i+1];
      q1[4]=AI[j+2][i];
      q1[5]=AI[j+2][i+1];
      
      //4.3 from alpha find s=a1a2 
         //4.3.1 substarct 238 from each q1 to q6
      for(int ii=0;ii<6;ii++)
      {q[ii]=q1[ii]-238;  
       //System.out.println("q["+ii+"]="+q[ii] );
      }  
         //4.3.2 extract d and c1 using shasir recovery with [1,q1][2,q2]
           ALGO_2 a2=new ALGO_2(1,q[0],2,q[1]);
           int _d = a2.get_d();
           int _c1 = a2.get_c1();
           int d_c1= ((_d<<4)+_c1);
       //4.3.3 covert d to 4 bit bianry value and c1 into 4 bit binary value and combine them to 8 bit value.
       //take first two bits of it as s=a1a2
           toBinary tb=new toBinary(d_c1);
           //System.out.println("p[0]="+p[0]+"p[1]="+p[1]+"p[2]="+p[2]+"p[3]="+p[3]+"p[4]="+p[4]+"p[5]="+p[5]);
           //System.out.println("d_c1="+d_c1+"\t_d="+_d+"\t_c1="+_c1+"\ttb.len="+tb.size);
           int tb1[]=new int[8];
           k=0;
           for(int kk=8-tb.size;kk<8;kk++){
             tb1[kk]=tb.B[k++];  
          }
           
           boolean s[]=new boolean[2];
           if (tb1[0]==1)s[0]=true;         else     s[0]=false;
           if (tb1[1]==1)s[1]=true;         else     s[1]=false;         
            
           ////System.out.println();
           
           //4.4 from binary plane find two bit authentication signal s'=a1'a2'with a1'=p1 xor p2 xor p3 and a2'=p4 xor p5 xor p6
           boolean s_[]=new boolean[2];
           s_[0]=(p[0]  ^  p[1]) ^ p[2];               
           s_[1]=(p[3]  ^  p[4]) ^ p[5];
     //4.5 match s1 and s' if sismatch occurs mark currnt binary block and color image blok and all the six shares p1 to p6 as tampered
           //System.out.println("s[0]="+s[0]+"\ts[1]="+s[1]+"\ts_[0]="+s_[0]+"\ts_[1]="+s_[1]);
           if((s[0] !=s_[0]) || (s[1] != s_[1]))
           {
               //mark current blocks as tampered and p1 to p6 as tampered
               //System.out.println("tampered");
               tampered_blocks[j][i]   =true;               
               tampered_shares[j][i]   =true;
               //tampered_shares[i+1][j] =true;  
               rgb=0;
              alpha=255;
              red=(rgb & 0x00FF0000)  >>> 16;           
              green=(rgb & 0x0000FF00)  >>> 8;
              blue=(rgb & 0x000000FF)  >>> 0;
              rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
                 // q1[0]=AI[i][j];
               gray_tampered.setRGB(j, i, rgb);
                  //q1[1]=AI[i+1][j];
                gray_tampered.setRGB(j, i+1, rgb);
                 // q1[2]=AI[i][j+1];      
                 gray_tampered.setRGB(j+1, i, rgb);
                  //q1[3]=AI[i+1][j+1];
                  gray_tampered.setRGB(j+1, i+1, rgb);
                  //q1[4]=AI[i][j+2];
                   gray_tampered.setRGB(j+2, i, rgb);
                  //q1[5]=AI[i+1][j+2];
                    gray_tampered.setRGB(j+2, i+1, rgb);
             
           }
           else
           {   //System.out.println("not tampered");
               tampered_blocks[j] [i]    =false;               
               tampered_shares[j][i]     =false;
               //tampered_shares[i+1][j]   =false;          
           }      
  } 
              
  //5. self-repairing of the original image content from binary and apha: if block is marked as tampered 
 int rep=0,nrep=0,ntampblk=0,noofuntamp=0;    
   blk_no=0;
 for (int j=0; j<lim_c && j%3==0 ;j=j+3)
 { 
   for(int i=0; i<lim_r && i%2==0;i=i+2)
   { 
       //System.out.println("Block no="+blk_no++);
      if(tampered_blocks[j][i]==true)//block is marked as tampered
      {ntampblk++;
       rep++;
      
      //4.2 find q1' to q6' from alpha use key K
      q1[0]=AI[j][i];
      q1[1]=AI[j][i+1];
      q1[2]=AI[j+1][i];
      q1[3]=AI[j+1][i+1];
      q1[4]=AI[j+2][i];
      q1[5]=AI[j+2][i+1];
      
      //4.3 from alpha find s=a1a2 
         //4.3.1 substarct 238 from each q1 to q6
      for(int ii=0;ii<6;ii++)
      {q[ii]=q1[ii]-238;  
       //System.out.println("q["+ii+"]="+q[ii] );
      }  
         //4.3.2 extract d and c1 using shasir recovery with [1,q1][2,q2]
           ALGO_2 a2=new ALGO_2(1,q[0],2,q[1]);
           int _d = a2.get_d();
           int _c1 = a2.get_c1();
           int d_c1= ((_d<<4)+_c1);
       //4.3.3 covert d to 4 bit bianry value and c1 into 4 bit binary value and combine them to 8 bit value.
       //take first two bits of it as s=a1a2
           toBinary tb=new toBinary(d_c1);
           ////System.out.println("p[0]="+tb.B[0]+"p[1]="+tb.B[1]+"p[2]="+tb.B[2]+"p[3]="+tb.B[3]+"p[4]="+tb.B[4]+"p[5]="+tb.B[5]);
           //System.out.println("d_c1="+d_c1+"\t_d="+_d+"\t_c1="+_c1+"\ttb.len="+tb.size);
                      
              int tb1[]=new int[8];
           k=0;
           for(int kk=8-tb.size;kk<8;kk++){
             tb1[kk]=tb.B[k++]; 
             ////System.out.println(tb1[kk]);
          }
               
              int bi[]=new int[6];
               
               int ll=0;
              for(int mn=2;mn<=7;mn++){
                   bi[ll]=tb1[mn];
                   //System.out.println(bi[ll]);
                   ll++;
              }
              //if bi'=0 set yi'=g1 else set y1'=g2 where yi' is a corresponding gray level block's ith pixel and i=1,2,3,4,5,6
              //mark blok as repaired 
              //gray_recovered
               /*    
              */
              if(bi[0]==0)
              {
                  //rgb=gray_recovered.getRGB(j, i);
                  //alpha=(rgb& 0xFF000000)  >>> 24;  
                  alpha=AI[j][i];
                  red=0;        
                  green=0;
                  blue=0;
                  rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
                   
                  //System.out.println("\nRECOVERED RGB"+rgb);
                  gray_recovered.setRGB(j, i, rgb);
                  
              }
              else
              {    
                  alpha=AI[j][i];
                  red=255;        
                  green=255;
                  blue=255;
                  rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
                   
                   
                  //System.out.println("\nRECOVERED RGB"+rgb);
                 gray_recovered.setRGB(j, i, rgb);
                  
                  
              }  
             if(bi[1]==0)
              {
                  //rgb=gray_recovered.getRGB(j, i);
                  //alpha=(rgb & 0xFF000000)  >>> 24;  
                  alpha=AI[j][i+1];
                  red=0;        
                  green=0;
                  blue=0;
                  rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
                  gray_recovered.setRGB(j,i+1, rgb);
              }
              else
              {   //rgb=gray_recovered.getRGB(j, i);
                  //alpha=(rgb & 0xFF000000)  >>> 24;  
                  alpha=AI[j][i+1];
                  red=255;        
                  green=255;
                  blue=255;
                  rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
                  gray_recovered.setRGB( j,i+1, rgb);
                  
              }                  
                
              if(bi[2]==0)
              {
                  
                   alpha=AI[j+1][i];
                   red=0;        
                  green=0;
                  blue=0;
                  rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
                  gray_recovered.setRGB(j+1,i, rgb);
              }
              else
              {     
                  alpha=AI[j+1][i];
                  red=255;        
                  green=255;
                  blue=255;
                  rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
                 gray_recovered.setRGB(j+1,i, rgb);
                  
              }   
               
              if(bi[3]==0)
              {   
                  alpha=AI[j+1][i+1];
                  red=0;        
                  green=0;
                  blue=0;
                  rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
                  gray_recovered.setRGB(j+1,i+1, rgb);
              }
              else
              {    
                  alpha=AI[j+1][i+1];
                 red=255;        
                  green=255;
                  blue=255;
                  rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
                  gray_recovered.setRGB(j+1,i+1, rgb);                  
              }  
               //q1[4]=AI[i][j+2];
              if(bi[4]==0)
              {
                   
                   alpha=AI[j+2][i];
                 red=0;        
                  green=0;
                  blue=0;
                  rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
                  gray_recovered.setRGB(j+2,i, rgb);
              }
              else
              {    
                  alpha=AI[j+2][i];
                  red=255;        
                  green=255;
                  blue=255;
                  rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
                  gray_recovered.setRGB(j+2,i, rgb);
                  
              }  
              //q1[5]=AI[i+1][j+2]
              if(bi[5]==0)
              {
                   
                  alpha=AI[j+2][i+1];
                  red=0;        
                  green=0;
                  blue=0;
                  rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
                  gray_recovered.setRGB(j+2,i+1, rgb);
              }
              else
              {    
                  alpha=AI[j+2][i+1];
                 red=255;        
                  green=255;
                  blue=255;
                  rgb=(alpha << 24) | (red << 16) | (green << 8) | blue;
                  gray_recovered.setRGB( j+2,i+1,rgb);                  
              }  
          }
           
         }
   
  }
 
 
   
  //display_images();
       
  //save modified PNG image color
      try {     
          ImageIO.write(gray_recovered, "png", new File(si.getDirectory()+"Recoverd_gray.png"));
      }catch (IOException e) {} 
      
        try {     
          ImageIO.write(gray_tampered, "png", new File(si.getDirectory()+"Tampered_gray.png"));
      }catch (IOException e) {} 
       

        
   
        
        
  //System.out.println("Total blocks="+blk_no);
  //System.out.println("Tampered bloks="+ntampblk+"UnTampered bloks="+(blk_no-ntampblk)+"recoverable blocks="+rep+"Nonrecoverable blocks="+(ntampblk-rep) );
  //System.out.println("recovered blocks="+rep);
//System.out.println("END OF algo 4");
display_images();
image_si.flush();

}
 


}
