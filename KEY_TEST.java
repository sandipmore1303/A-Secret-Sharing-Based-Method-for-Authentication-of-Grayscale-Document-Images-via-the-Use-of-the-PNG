/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sam
 */
public class KEY_TEST {
    
    
     public static void main (String args[])   {
           //find key K and inverse
         int lim_r=5;
       int K[]= new int[lim_r];
       int K_i[]= new int[lim_r];
       RandomPermutation rp=new RandomPermutation();
       K=rp.GenerateRandomPermutation(lim_r);
       System.out.println("\nGenerating Key value for authentication:\nKey Length="+K.length);
       int k = 0;
       for ( k = 0; k < K.length; k++)
           System.out.print("  " + K[k]);
       
        
       for(int ii=0;ii<lim_r;ii++)
       {
       K_i[K[ii]]=ii;
       }
       
       System.out.println("\nInverse Key length="+K.length+"\nInverse Key:");
     for(int ii=0;ii<lim_r;ii++)
     {System.out.print(K_i[ii]+" ");
     }
     }
    
}
