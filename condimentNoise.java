import java.awt.*;
import java.awt.image.*;
import java.applet.*;
import java.net.*;
import java.io.*;
import java.lang.Math;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JApplet;
import javax.imageio.*;
import javax.swing.event.*;

public class condimentNoise {

		int[] input;
		int[] output;
		int progress;
		float amount;
		int width;
		int height;
		Random randgen;

		public void condimentNoise() {
			progress=0;
		}
		
		public void init(int[] original, int widthIn, int heightIn, float amountIn) {
			randgen = new Random();
			amount=amountIn;
			width=widthIn;
			height=heightIn;
			input = new int[width*height];
			output = new int[width*height];
			input=original;
		}

		public int[] process() {
			double rand;
			int result = 0;

			for(int x=0; x<width;x++) {
				progress++;
				for(int y=0; y<height;y++) {

	  
					rand = randgen.nextFloat();   
					   
					if (rand <= amount) {
				
						//Generate another random number
						rand = randgen.nextFloat();
				
						// choose salt or pepper
						if(rand < 0.5) {
							result = 0;
						} 
						else {
							result = 255;
						}
					} 
					else {
						result = input[y*width+x] & 0xff;
					}	

					//Convert back to grayscale pixel
					output[y*width+x] = 0xff000000 | (result + (result << 16) + (result << 8));
				}
			}
			return output;

		}

		public int getProgress() {
			return progress;
		}


	}
