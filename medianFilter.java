import java.awt.*;
import java.awt.image.*;
import java.applet.*;
import java.net.*;
import java.io.*;
import java.lang.Math;
import java.util.*;
 
 // additional stuff for swing
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JApplet;
import javax.imageio.*;
import java.util.Arrays;


public class medianFilter {

		int[] input;
		int[] output;
		float[] template;
		int progress;
		int templateSize;
		int width;
		int height;

		public void medianFilter() {
			progress=0;
		}
		
		public void init(int[] original, int widthIn, int heightIn, int templatesizein) {
			templateSize=templatesizein;
			width=widthIn;
			height=heightIn;
			input = new int[width*height];
			output = new int[width*height];
			template = new float[templateSize*templateSize];
			input=original;
		}
		public int[] process() {
			float sum;
			progress=0;
			int vals[];
			int count;
			int outputsmaller[] = new int[(width-(templateSize-1))*(height-(templateSize-1))];

			for(int x=(templateSize-1)/2; x<width-(templateSize+1)/2;x++) {
				progress++;
				for(int y=(templateSize-1)/2; y<height-(templateSize+1)/2;y++) {
					vals=new int [templateSize*templateSize];
					count=0;
					for(int x1=x-((templateSize-1)/2);x1<x+((templateSize+1)/2);x1++) {
						for(int y1=y-((templateSize-1)/2);y1<y+((templateSize+1)/2);y1++) {
							vals[count]=input[y1*width+x1]&0xff;
							count++;
						}
					}
					java.util.Arrays.sort(vals);
					int middle = vals[((templateSize*templateSize+1)/2)];
					outputsmaller[(y-(templateSize-1)/2)*(width-(templateSize-1))+(x-(templateSize-1)/2)] = 0xff000000 | (middle << 16 | middle << 8 | middle);
				}
			}

			Toolkit tk = Toolkit.getDefaultToolkit();

			Image tempImage = tk.createImage(new MemoryImageSource((width-(templateSize-1)), (height-(templateSize-1)), outputsmaller, 0, (width-(templateSize-1)))).getScaledInstance(256, 256, Image.SCALE_SMOOTH);
			PixelGrabber grabber = new PixelGrabber(tempImage, 0, 0, width, height, output, 0, width);
			try {
				grabber.grabPixels();
			}
			catch(InterruptedException e2) {
				System.out.println("error: " + e2);
			}
			progress=width;

			return output;
		}

		public int getProgress() {
			return progress;
		}


	}
