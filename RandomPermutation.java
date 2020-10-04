// Generating random permutations.

import java.io.*;

class RandomPermutation {
     
   public static int [] GenerateRandomPermutation(int n)
    { int a[]=new int[n];
      for (int k =0;k< a.length ;  k++)
      {
         a[k]=k; 
      }
     int[] b = (int[])a.clone();
	for (int k = b.length - 1; k > 0; k--) {
	    int w = (int)Math.floor(Math.random() * (k+1));
	    int temp = b[w];
	    b[w] = b[k];
	    b[k] = temp;
	}
	return(b);
     
    }
    // Print content of array a
    private static void printArray(int[] a) {
	for (int k = 0; k < a.length; k++)
	    System.out.print("  " + a[k]);
	System.out.println();
    }

  
    // print 10 random permutations
    public static void main(String[] args) {
        int aa[]=new int[10];
	 
        RandomPermutation rp=new RandomPermutation();
        aa=rp.GenerateRandomPermutation(10);
	   rp.printArray(aa);
    }
}
