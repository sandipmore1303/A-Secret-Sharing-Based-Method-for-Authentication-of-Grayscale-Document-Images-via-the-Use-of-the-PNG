 

 

import java.awt.*;



public class YCbCr
{ 
    


 
    
   void   rgb2ycbcr(double red,double green,double blue )
    {
        
   /* this.red=red;this.green=green;this.blue=blue;
    
    this.Y = (double) (0.299*this.red + 0.587*this.green + 0.114*this.blue);

    this.Cb = (double) (128 - 0.168736*this.red - 0.331264*this.green + 0.5*this.blue);

    this.Cr = (double) (128 + 0.5*this.red - 0.418688*this.green -0.081312*this.blue);
*/
        this.red=red;this.green=green;this.blue=blue;
    
    this.Y = (double) (0.299*this.red + 0.587*this.green + 0.114*this.blue);

    this.Cb = (double) (128- 0.168736*this.red - 0.331264*this.green + 0.5*this.blue);

    this.Cr = (double) ( 128+ 0.5*this.red - 0.418688*this.green -0.081312*this.blue);
    }

   double getY()
   {return this.Y;
       
   }
    double getCb()
   {return this.Cb;
       
   }
     double getCr()
   {return this.Cr;
       
   } 

void     ycbcr2rgb(double Y,double Cb,double Cr )
    {   
     this.Y=Y;this.Cb=Cb;this.Cr=Cr;
    this.red = (double) (this.Y + 1.402*(this.Cr - 128));

    this.green =  (double) (this.Y - 0.34414* (this.Cb - 128) - 0.71414*(this.Cr - 128));

    this.blue = (double) (this.Y + 1.772*(this.Cb - 128));
 
    }
 
  double getRed()
   {return this.red;
       
   }
    double getGreen()
   {return this.green;
       
   }
     double getBlue()
   {return this.blue;
       
   }
     
    public double Y;     
    public double Cb;
    public double Cr;
    public double red,green,blue;
    
    
    public static void main (String args[]){
        YCbCr t=new YCbCr();
        t.rgb2ycbcr(255, 255, 255);
    
                System.out.println("Y="+t.getY()+"Cb="+t.getCb()+"Cr="+t.getCr());
                t.ycbcr2rgb(t.getY(), t.getCb(), t.getCr());
                System.out.println("Y="+t.getRed()+"Cb="+t.getGreen()+"Cr="+t.getBlue());
                System.out.println(""+Integer.toString(41,2));
                
            }
}




