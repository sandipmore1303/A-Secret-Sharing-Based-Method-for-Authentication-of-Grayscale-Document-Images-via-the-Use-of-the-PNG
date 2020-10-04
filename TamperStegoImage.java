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

public class TamperStegoImage {
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
  
     private static void display_images() {
    JFrame frame_oi = new JFrame("Original Color image");
    Panel panel_oi = new ShowImage(oi.getDirectory()+"Original_Color.png");
    frame_oi.getContentPane().add(panel_oi);
    frame_oi.setSize(500, 500);
    frame_oi.setVisible(true);
    
    
    JFrame frame_gi = new JFrame("Original Gray image");
    Panel panel_gi = new ShowImage(oi.getDirectory()+"Original_Gray.png");
    frame_gi.getContentPane().add(panel_gi);
    frame_gi.setSize(500, 500);
    frame_gi.setVisible(true);
    
    
    JFrame frame_bi = new JFrame("Original Binarized image");
    Panel panel_bi = new ShowImage(oi.getDirectory()+"Original_Binary.png");
    frame_bi.getContentPane().add(panel_bi);
    frame_bi.setSize(500, 500);
    frame_bi.setVisible(true);
    
    
    
    
    
    //stego images
    
    JFrame frame_os = new JFrame("Stego Color image with alpha channel");
    Panel panel_os = new ShowImage(oi.getDirectory()+"Stego_Color.png");
    frame_os.getContentPane().add(panel_os);
    frame_os.setSize(500, 500);
    frame_os.setVisible(true);
    
    
    JFrame frame_gs = new JFrame("Stego Gray image with alpha channel");
    Panel panel_gs = new ShowImage(oi.getDirectory()+"Stego_Gray.png");
    frame_gs.getContentPane().add(panel_gs);
    frame_gs.setSize(500, 500);
    frame_gs.setVisible(true);
    
    
    JFrame frame_bs = new JFrame("Stego Binarized image with alpha channel");
    Panel panel_bs = new ShowImage(oi.getDirectory()+"Stego_Binary.png");
    frame_bs.getContentPane().add(panel_bs);
    frame_bs.setSize(500, 500);
    frame_bs.setVisible(true);
    
      
       }
     
 
 public static void main (String args[]) throws IOException {
      
    //1 read stego color image  
   ImagePlus original_image = null ;
   BufferedImage image_oi=null;
   File f_oi;
   oi =new ij.io.OpenDialog("Select stego image",
                  "E:\\DATA\\YES_MAN\\JAVA_IMPL\\",
                  "lenna.bmp");
   try {
	    f_oi=new File(oi.getDirectory()+oi.getFileName());
            image_oi = ImageIO.read(f_oi);
	        } catch (IOException e) {}   
      
     
     //2 convert to grayscale    
    
      
     BufferedImageOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
     
     BufferedImage gray_i = op.filter(image_oi, null);
     
     
     //3. convert to binary
     System.out.println("\nObtaining values g1 and g2 ");
     String sCurrentLine; 
     String sp[]=new String[2];
     BufferedReader br = null;
     br = new BufferedReader(new FileReader(oi.getDirectory()+"//KEY.txt"));  
     sCurrentLine = br.readLine();
     sp[0]=sCurrentLine.substring(0,sCurrentLine.indexOf("."));
     sp[1]=sCurrentLine.substring(sCurrentLine.indexOf(".")+1);
     int parseInt1 = Integer.parseInt(sp[0]);
     int parseInt2 = Integer.parseInt(sp[1].substring(0,5));
     G[0]=(parseInt1+parseInt2/(100000.0));      
     sCurrentLine = br.readLine();  
     sp[0]=sCurrentLine.substring(0,sCurrentLine.indexOf("."));
     sp[1]=sCurrentLine.substring(sCurrentLine.indexOf(".")+1);     
     parseInt1 = Integer.parseInt(sp[0]);
     parseInt2 = Integer.parseInt(sp[1].substring(0,5));
     G[1]=parseInt1+parseInt2/(100000.0);
     System.out.println("Value of gray level g1="+G[0]);
     System.out.println("Value of gray level g2="+G[1]);
     thres=(G[0]+G[1])/2.0;
     System.out.println("Value of threshod for binarization="+thres);      
     System.out.println("\nMoment Preserving thresholding");
     int data[]=new int[image_oi.getWidth()*image_oi.getHeight()];
      
     int Alpha_Image_original[][]=new int[image_oi.getWidth()][image_oi.getHeight()];
     int Binary_Image_original[][]=new int[image_oi.getWidth()][image_oi.getHeight()];
     
     BufferedImage Binary_i = new BufferedImage(gray_i.getWidth(), gray_i.getHeight(),  BufferedImage.TYPE_INT_ARGB);
     int  lim_r,  lim_c;
     lim_c=image_oi.getWidth()-(image_oi.getWidth()%3) ;//columns
     lim_r=image_oi.getHeight()-(image_oi.getHeight()%2) ;//rows
    
     int alpha=0;
     int red=0;
     int green=0;
     int blue=0;
     int rgb=0;
     int k=0;
       
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
          
    
      
     //find key K and inverse key
     
     int K[]= new int[lim_r];
     int K_i[]= new int[lim_r];
     for(int ii=0;ii<lim_r;ii++)
     {
     K[ii]=Integer.parseInt( br.readLine());
     }
     int index=-1;
     for(int ii=0;ii<lim_r;ii++)
     {k=0;
     index=-1;
     while(true)
     {if(K[k]==ii)   {index=k;break;}      else k++; } 
     K_i[ii]=index;
     }
     System.out.println("Key length="+K.length+"\nKey:");
     for(int ii=0;ii<lim_r;ii++)
     {//System.out.print(K[ii]+" ");
     }
     //System.out.println("\nInverse Key length="+K.length+"\nInverse Key:");
     for(int ii=0;ii<lim_r;ii++)
     {//System.out.print(K_i[ii]+" ");
     }
     
     
     //3. obtain alpha component  and binary components into 2d array  
     int BI[][]=new int[lim_r][lim_c];
     int BI_mod[][]=new int[lim_r][lim_c];
     int AI[][]=new int[lim_r][lim_c];
     int AI_mod[][]=new int[lim_r][lim_c];
     
     for (int cx=0;cx<lim_c;cx++) {  
         for (int cy=0;cy<lim_r;cy++) { 
             rgb=image_oi.getRGB(cx, cy); 
             alpha = ((rgb >> 24) & 0x000000ff);// System.out.println(alpha);
             AI_mod[cy][cx]=alpha;
             AI[cy][cx]=alpha;
             BI[cy][cx]=Binary_Image_original[cx][cy];
         }
     }
 //get original data ie de-randomized 
     //randomize data
        
       for (int j=0; j<lim_c;j++){
           if(j%3 !=0)
           {//get current column 
             //rearrane it as per k
             for(int i=0; i<lim_r ;i++) {
                AI[i][j]=AI_mod[K_i[i]][j]; 
                 
             }
           }
       }
       
          //display  result
       //System.out.println("Before de randomizaton:"); 
        for(int i=0; i<lim_r  && i%2==0;i=i+2) 
           for (int j=0; j<lim_c  && j%3==0 ;j=j+3){
               //System.out.println();
               //System.out.println("("+i+","+j+")"+AI_mod[i][j]+" ("+i+","+(j+1)+")"+AI_mod[i][j+1]+" ("+i+","+(j+2)+") "+AI_mod[i][j+2]);  
               //System.out.println("("+(i+1)+","+j+")"+AI_mod[(i+1)][j]+" ("+(i+1)+","+(j+1)+")"+AI_mod[(i+1)][j+1]+" ("+(i+1)+","+(j+2)+") "+AI_mod[(i+1)][j+2]);                   
           }
          
         System.out.println("After de randomizaton:");   
         for(int i=0; i<lim_r  && i%2==0;i=i+2) 
           for (int j=0; j<lim_c  && j%3==0 ;j=j+3){
               //System.out.println();
               //System.out.println("("+i+","+j+")"+AI[i][j]+" ("+i+","+(j+1)+")"+AI[i][j+1]+" ("+i+","+(j+2)+") "+AI[i][j+2]);  
               //System.out.println("("+(i+1)+","+j+")"+AI[(i+1)][j]+" ("+(i+1)+","+(j+1)+")"+AI[(i+1)][j+1]+" ("+(i+1)+","+(j+2)+") "+AI[(i+1)][j+2]);                   
           }
          
         
     //4. from each block of alpha and binary 
     //4.1find p1 to p6 from binary
     //4.2 find q1 to q6 from alpha use key K
     //4.3 from alpha find s=a1a2 
         //4.3.1 substarct 238 from each q1 to q6
         //4.3.2 extract d and c1 using shamir recovery with [1,q1][2,q2]
         //4.3.3 covert d to 4 bit bianry value and c1 into 4 bit binary value and combine them to 8 bit value.
               //take first two bits of it as s=a1a2
     //4.4 from binary plane find two bit authentication signal s'=a1'a2'with a1'=p1 xor p2 xor p3 and a2'=p4 xor p5 xor p6
     //4.5 match s1 and s' if mismatch occurs mark currnt binary block and color image blok and all the six shares p1 to p6 as tampered

     
 boolean p[]=new boolean[6];
 int q1[]=new int[6];
 int q[]=new int[6];
 boolean tampered_blocks[][]=new boolean[lim_r][lim_c];
 boolean tampered_shares[][]=new boolean[lim_r][lim_c];
 
 for(int i=0; i<lim_r  ;i=i+2)   
  for (int j=0; j<lim_c   ;j=j+3) {
      //4.1find p1 to p6 from binary
      if( BI[i][j]==1)        p[0]=true;        else    p[0]=false;  
      if( BI[i+1][j]==1)      p[1]=true;        else    p[1]=false;  
      if( BI[i][j+1]==1)      p[2]=true;        else    p[2]=false;  
      if( BI[i+1][j+1]==1)    p[3]=true;        else    p[3]=false;  
      if( BI[i][j+2]==1)      p[4]=true;        else    p[4]=false;  
      if( BI[i+1][j+2]==1)    p[5]=true;        else    p[5]=false;  
      
      //4.2 find q1' to q6' from alpha use key K
      q1[0]=AI[i][j];
      q1[1]=AI[i+1][j];
      q1[2]=AI[i][j+1];
      q1[3]=AI[i+1][j+1];
      q1[4]=AI[i][j+2];
      q1[5]=AI[i+1][j+2];
      
      //4.3 from alpha find s=a1a2 
         //4.3.1 substarct 238 from each q1 to q6
      for(int ii=0;ii<6;ii++)q[ii]=q1[ii]-238;     
         //4.3.2 extract d and c1 using shamir recovery with [1,q1][2,q2]
           ALGO_2 a2=new ALGO_2(1,q[0],2,q[1]);
           int _d = a2.get_d();
           int _c1 = a2.get_c1();
           int d_c1= ((_d<<4)+_c1);
       //4.3.3 covert d to 4 bit bianry value and c1 into 4 bit binary value and combine them to 8 bit value.
       //take first two bits of it as s=a1a2
           toBinary tb=new toBinary(d_c1);
           System.out.println("ddddd"+d_c1+"d="+_d+"_c1="+_c1+"tb.len="+tb.size);
           int tb1[]=new int[8];
           k=0;
           for(int kk=8-tb.size;kk<8;kk++){
             tb1[kk]=tb.B[k++];  
           }
           boolean s[]=new boolean[2];
           if (tb1[0]==1)s[0]=true;         else     s[0]=false;
           if (tb1[1]==1)s[1]=true;         else     s[1]=false;         
            
           //System.out.println();
           
           //4.4 from binary plane find two bit authentication signal s'=a1'a2'with a1'=p1 xor p2 xor p3 and a2'=p4 xor p5 xor p6
           boolean s_[]=new boolean[2];
           s_[0]=(p[0]  ^  p[1]) ^ p[2];               
           s_[1]=(p[3]  ^  p[4]) ^ p[5];
     //4.5 match s1 and s' if mismatch occurs mark currnt binary block and color image blok and all the six shares p1 to p6 as tampered
           if((s[0] !=s_[0]) || (s[1] != s_[1]))
           {
               //mark current blocks as tampered and p1 to p6 as tampered
               tampered_blocks[i][j]   =true;               
               tampered_shares[i][j]   =true;
               tampered_shares[i+1][j] =true;               
           }
           else
           {   tampered_blocks[i][j]     =false;               
               tampered_shares[i][j]     =false;
               tampered_shares[i+1][j]   =false;          
           }
               

  }
 
              
  //5. self-repairing of the original image content from binary and apha: if block is marked as tampered 
  int rep=0,nrep=0;   
 for(int i=0; i<lim_r  ;i=i+2)   
  for (int j=0; j<lim_c   ;j=j+3) {
      if(tampered_blocks[i][j]==true)//block is marked as tampered
      {
          //5.1 from alpha block Extract the remaining partial shares q3' to q6' ... substract 238 to get q3 to q6
          q1[0]=AI[i][j];
          q1[1]=AI[i+1][j];
          q1[2]=AI[i][j+1];
          q1[3]=AI[i+1][j+1];
          q1[4]=AI[i][j+2];
          q1[5]=AI[i+1][j+2];         
          //5.2 From the six partial shares through of binary block 
          //two computed in Step 3(1) and four in Step 7(2) above , choose two of them, e.g., qk and ql, 
          //which are not marked as tampered, if possible..... if not possible mark as not repairable
          int qk=-1;int ql=-1;
          k=0;
          boolean flag=false;
          for(int jm=j;jm<j+3;jm++)
              for(int im=i;im<2;im++){
                  if(tampered_shares[im][jm]==true ){
                      if(qk==-1){qk=k;k++;} else if(ql==-1){ql=k;flag=true;break;} else{flag=true;break;}                   
                  }  
                  if(flag==true)break;
          }
          if(flag==true){//repairable
              rep++;
              System.out.println("repairable"+"qk="+qk+"ql="+ql);
              //5.3 with two sahres [k,qk] and [l,ql] obtain d and c1 using shamir recovery
              ALGO_2 al2=new ALGO_2(qk,q1[qk],ql,q1[ql]);
              int _d = al2.get_d();
              int _c1 = al2.get_c1();
              //5.4 d and c1 to 4 bit binary ...combine to form 8 bit value S'..
              int d_c1= ((_d<<4)+_c1);
              //5.5 take last 6 bits b1' to b6' from  S' ...
              toBinary tb=new toBinary(d_c1);
              System.out.println("Length s_="+tb.size+"SS="+d_c1);
              int S_[]=new int[8];
              int bi[]=new int[6];
              k=0;
              for(int mn=8-tb.size;mn<tb.size;mn++){
                  System.out.print(tb.B[mn]);
                  //bi[k++]=tb.B[mn];
                  S_[k++]=tb.B[mn];
              }
              for(int mn=0;mn<6;mn++){
                  bi[mn]=S_[mn+2];
              }
              //if bi'=0 set yi'=g1 else set y1'=g2 where yi' is a corresponding gray level block's ith pixel and i=1,2,3,4,5,6
              //mark blok as repaired 
              
          }
          else{ //not repairable
              nrep++;
              System.out.println("Not repairable"+"qk="+qk+"ql="+ql);
              
          }
          
      }
 
      
    
  }
 
   
  //display_images();
       
  System.out.println("repairable blocks="+rep);
 System.out.println("Nonrepairable blocks="+nrep);
       
  image_oi.flush();
System.out.println("Bye 4");

}
public static void MAIN(String args) throws IOException {
      
    //1 read stego color image  
   ImagePlus original_image = null ;
   BufferedImage image_oi=null;
   File f_oi;
   oi =new ij.io.OpenDialog("Select stego image",
                  "E:\\DATA\\YES_MAN\\JAVA_IMPL\\",
                  "lenna.bmp");
   try {
	    f_oi=new File(oi.getDirectory()+oi.getFileName());
            image_oi = ImageIO.read(f_oi);
	        } catch (IOException e) {}   
      
     
     //2 convert to grayscale    
    
      
     BufferedImageOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
     
     BufferedImage gray_i = op.filter(image_oi, null);
     
     
     //3. convert to binary
     System.out.println("\nObtaining values g1 and g2 ");
     String sCurrentLine; 
     String sp[]=new String[2];
     BufferedReader br = null;
     br = new BufferedReader(new FileReader(oi.getDirectory()+"//KEY.txt"));  
     sCurrentLine = br.readLine();
     sp[0]=sCurrentLine.substring(0,sCurrentLine.indexOf("."));
     sp[1]=sCurrentLine.substring(sCurrentLine.indexOf(".")+1);
     int parseInt1 = Integer.parseInt(sp[0]);
     int parseInt2 = Integer.parseInt(sp[1].substring(0,5));
     G[0]=(parseInt1+parseInt2/(100000.0));      
     sCurrentLine = br.readLine();  
     sp[0]=sCurrentLine.substring(0,sCurrentLine.indexOf("."));
     sp[1]=sCurrentLine.substring(sCurrentLine.indexOf(".")+1);     
     parseInt1 = Integer.parseInt(sp[0]);
     parseInt2 = Integer.parseInt(sp[1].substring(0,5));
     G[1]=parseInt1+parseInt2/(100000.0);
     System.out.println("Value of gray level g1="+G[0]);
     System.out.println("Value of gray level g2="+G[1]);
     thres=(G[0]+G[1])/2.0;
     System.out.println("Value of threshod for binarization="+thres);      
     System.out.println("\nMoment Preserving thresholding");
     int data[]=new int[image_oi.getWidth()*image_oi.getHeight()];
      
     int Alpha_Image_original[][]=new int[image_oi.getWidth()][image_oi.getHeight()];
     int Binary_Image_original[][]=new int[image_oi.getWidth()][image_oi.getHeight()];
     
     BufferedImage Binary_i = new BufferedImage(gray_i.getWidth(), gray_i.getHeight(),  BufferedImage.TYPE_INT_ARGB);
     int  lim_r,  lim_c;
     lim_c=image_oi.getWidth()-(image_oi.getWidth()%3) ;//columns
     lim_r=image_oi.getHeight()-(image_oi.getHeight()%2) ;//rows
    
     int alpha=0;
     int red=0;
     int green=0;
     int blue=0;
     int rgb=0;
     int k=0;
       
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
       
     
    
      
     //find key K and inverse key
     
     int K[]= new int[lim_r];
     int K_i[]= new int[lim_r];
     for(int ii=0;ii<lim_r;ii++)
     {
     K[ii]=Integer.parseInt( br.readLine());
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
     int BI[][]=new int[lim_r][lim_c];
     int BI_mod[][]=new int[lim_r][lim_c];
     int AI[][]=new int[lim_r][lim_c];
     int AI_mod[][]=new int[lim_r][lim_c];
     
     for (int cx=0;cx<lim_c;cx++) {  
         for (int cy=0;cy<lim_r;cy++) { 
             rgb=image_oi.getRGB(cx, cy); 
             alpha = ((rgb >> 24) & 0x000000ff);// System.out.println(alpha);
             AI_mod[cy][cx]=alpha;
             AI[cy][cx]=alpha;
             BI[cy][cx]=Binary_Image_original[cx][cy];
         }
     }
 //get original data ie de-randomized 
     //randomize data
        
       for (int j=0; j<lim_c;j++){
           if(j%3 !=0)
           {//get current column 
             //rearrane it as per k
             for(int i=0; i<lim_r ;i++) {
                AI[i][j]=AI_mod[K_i[i]][j]; 
                 
             }
           }
       }
       
          //display  result
       //System.out.println("Before de randomizaton:"); 
        for(int i=0; i<lim_r  && i%2==0;i=i+2) 
           for (int j=0; j<lim_c  && j%3==0 ;j=j+3){
               //System.out.println();
               //System.out.println("("+i+","+j+")"+AI_mod[i][j]+" ("+i+","+(j+1)+")"+AI_mod[i][j+1]+" ("+i+","+(j+2)+") "+AI_mod[i][j+2]);  
               //System.out.println("("+(i+1)+","+j+")"+AI_mod[(i+1)][j]+" ("+(i+1)+","+(j+1)+")"+AI_mod[(i+1)][j+1]+" ("+(i+1)+","+(j+2)+") "+AI_mod[(i+1)][j+2]);                   
           }
          
         //System.out.println("After de randomizaton:");   
         for(int i=0; i<lim_r  && i%2==0;i=i+2) 
           for (int j=0; j<lim_c  && j%3==0 ;j=j+3){
               //System.out.println();
               //System.out.println("("+i+","+j+")"+AI[i][j]+" ("+i+","+(j+1)+")"+AI[i][j+1]+" ("+i+","+(j+2)+") "+AI[i][j+2]);  
               //System.out.println("("+(i+1)+","+j+")"+AI[(i+1)][j]+" ("+(i+1)+","+(j+1)+")"+AI[(i+1)][j+1]+" ("+(i+1)+","+(j+2)+") "+AI[(i+1)][j+2]);                   
           }
          
         
     //4. from each block of alpha and binary 
     //4.1find p1 to p6 from binary
     //4.2 find q1 to q6 from alpha use key K
     //4.3 from alpha find s=a1a2 
         //4.3.1 substarct 238 from each q1 to q6
         //4.3.2 extract d and c1 using shamir recovery with [1,q1][2,q2]
         //4.3.3 covert d to 4 bit bianry value and c1 into 4 bit binary value and combine them to 8 bit value.
               //take first two bits of it as s=a1a2
     //4.4 from binary plane find two bit authentication signal s'=a1'a2'with a1'=p1 xor p2 xor p3 and a2'=p4 xor p5 xor p6
     //4.5 match s1 and s' if mismatch occurs mark currnt binary block and color image blok and all the six shares p1 to p6 as tampered

     
 boolean p[]=new boolean[6];
 int q1[]=new int[6];
 int q[]=new int[6];
 boolean tampered_blocks[][]=new boolean[lim_r][lim_c];
 boolean tampered_shares[][]=new boolean[lim_r][lim_c];
 
 for(int i=0; i<lim_r  ;i=i+2)   
  for (int j=0; j<lim_c   ;j=j+3) {
      //4.1find p1 to p6 from binary
      if( BI[i][j]==1)        p[0]=true;        else    p[0]=false;  
      if( BI[i+1][j]==1)      p[1]=true;        else    p[1]=false;  
      if( BI[i][j+1]==1)      p[2]=true;        else    p[2]=false;  
      if( BI[i+1][j+1]==1)    p[3]=true;        else    p[3]=false;  
      if( BI[i][j+2]==1)      p[4]=true;        else    p[4]=false;  
      if( BI[i+1][j+2]==1)    p[5]=true;        else    p[5]=false;  
      
      //4.2 find q1' to q6' from alpha use key K
      q1[0]=AI[i][j];
      q1[1]=AI[i+1][j];
      q1[2]=AI[i][j+1];
      q1[3]=AI[i+1][j+1];
      q1[4]=AI[i][j+2];
      q1[5]=AI[i+1][j+2];
      
      //4.3 from alpha find s=a1a2 
         //4.3.1 substarct 238 from each q1 to q6
      for(int ii=0;ii<6;ii++)q[ii]=q1[ii]-238;     
         //4.3.2 extract d and c1 using shamir recovery with [1,q1][2,q2]
           ALGO_2 a2=new ALGO_2(1,q[0],2,q[1]);
           int _d = a2.get_d();
           int _c1 = a2.get_c1();
           int d_c1= ((_d<<4)+_c1);
       //4.3.3 covert d to 4 bit bianry value and c1 into 4 bit binary value and combine them to 8 bit value.
       //take first two bits of it as s=a1a2
           toBinary tb=new toBinary(d_c1);
           //System.out.println("ddddd"+d_c1+"d="+_d+"_c1="+_c1+"tb.len="+tb.size);
           int tb1[]=new int[8];
           k=0;
           for(int kk=8-tb.size;kk<8;kk++){
             tb1[kk]=tb.B[k++];  
           }
           boolean s[]=new boolean[2];
           if (tb1[0]==1)s[0]=true;         else     s[0]=false;
           if (tb1[1]==1)s[1]=true;         else     s[1]=false;         
            
           //System.out.println();
           
           //4.4 from binary plane find two bit authentication signal s'=a1'a2'with a1'=p1 xor p2 xor p3 and a2'=p4 xor p5 xor p6
           boolean s_[]=new boolean[2];
           s_[0]=(p[0]  ^  p[1]) ^ p[2];               
           s_[1]=(p[3]  ^  p[4]) ^ p[5];
     //4.5 match s1 and s' if mismatch occurs mark currnt binary block and color image blok and all the six shares p1 to p6 as tampered
           if((s[0] !=s_[0]) || (s[1] != s_[1]))
           {
               //mark current blocks as tampered and p1 to p6 as tampered
               tampered_blocks[i][j]=true;
               
               tampered_shares[i][j]     =true;
               tampered_shares[i+1][j]   =true;               
           }
           else
           {   tampered_blocks[i][j]     =false;
               
               tampered_shares[i][j]     =false;
               tampered_shares[i+1][j]   =false;          
           }
               

  }
 
              
  //5. self-repairing of the original image content from binary and apha: if block is marked as tampered 
     
 for(int i=0; i<lim_r  ;i=i+2)   
  for (int j=0; j<lim_c   ;j=j+3) {
      if(tampered_blocks[i][j]==true)//block is marked as tampered
      {
          //5.1 from alpha block Extract the remaining partial shares q3' to q6' ... substract 238 to get q3 to q6
          q1[0]=AI[i][j];
          q1[1]=AI[i+1][j];
          q1[2]=AI[i][j+1];
          q1[3]=AI[i+1][j+1];
          q1[4]=AI[i][j+2];
          q1[5]=AI[i+1][j+2];         
          //5.2 From the six partial shares through of binary block 
          //two computed in Step 3(1) and four in Step 7(2) above , choose two of them, e.g., qk and ql, 
          //which are not marked as tampered, if possible..... if not possible mark as not repairable
          int qk=-1;int ql=-1;
          k=0;
          boolean flag=false;
          for(int jm=j;jm<j+3;jm++)
              for(int im=i;im<2;im++){
                  if(tampered_shares[im][jm]==true ){
                      if(qk==-1){qk=k;k++;} else if(ql==-1){ql=k;flag=true;break;} else{flag=true;break;}                   
                  }  
                  if(flag==true)break;
          }
          if(flag==true){//repairable
              System.out.println("repairable"+"qk="+qk+"ql="+ql);
              //5.3 with two sahres [k,qk] and [l,ql] obtain d and c1 using shamir recovery
              ALGO_2 al2=new ALGO_2(qk,q1[qk],ql,q1[ql]);
              int _d = al2.get_d();
              int _c1 = al2.get_c1();
              //5.4 d and c1 to 4 bit binary ...combine to form 8 bit value S'..
              int d_c1= ((_d<<4)+_c1);
              //5.5 take last 6 bits b1' to b6' from  S' ...
              toBinary tb=new toBinary(d_c1);
              System.out.println("Length s_="+tb.size+"SS="+d_c1);
              int S_[]=new int[8];
              int bi[]=new int[6];
              k=0;
              for(int mn=8-tb.size;mn<tb.size;mn++){
                  System.out.print(tb.B[mn]);
                  //bi[k++]=tb.B[mn];
                  S_[k++]=tb.B[mn];
              }
              for(int mn=0;mn<6;mn++){
                  bi[mn]=S_[mn+2];
              }
              //if bi'=0 set yi'=g1 else set y1'=g2 where yi' is a corresponding gray level block's ith pixel and i=1,2,3,4,5,6
              //mark blok as repaired 
              
          }
          else{ //not repairable
              
          }
          
      }
 
      
    
  }
 
   
  //display_images();     
  image_oi.flush();
  System.out.println("Bye 4");

}


  


}
