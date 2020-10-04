/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sam
 */
public class toBinary {
    int size=0;
    int B[];
      toBinary(int d)
    {//calcualtae size of poosible binary value
        int sz=0;
        int X=d;
        do{X=(int)(Math.ceil(X/2));
            sz++;
        }while(X !=0);
                
        this.size=sz;
        B=new int[this.size];
        X=d;
        
        do{B[--sz]=X%2;
            X=(int)(Math.ceil(X/2));         
        }while(X !=0);        
    }
    int []  getBinaryValue()
      {
        return this.B;  
      }
      public static void main (String args[])   {
            toBinary tb=new toBinary(255);
            int tb1[]=new int[8];
            
            for(int i=0;i<tb.size;i++){
             //tb1[i]=tb.B[i++]; 
             System.out.println("t[i]="+tb.B[i]+"\ti="+i);
          }
            
            int k=0;
           for(int kk=8-tb.size;kk<8;kk++){
             tb1[kk]=tb.B[k++]; 
             System.out.println("tb1[kk]="+tb1[kk]+"\tkk="+kk);
          }
             
            
          
       
        
}
}
