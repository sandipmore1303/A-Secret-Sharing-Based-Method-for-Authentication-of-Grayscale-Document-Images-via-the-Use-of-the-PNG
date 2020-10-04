 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Random;


public class shamir{
	 
	public static boolean isRepeat(BigInteger x, shamirKey[] k){
		if(k.length == 0)
			return false;
		
		for(int i = 0; i < k.length; i++){
			if(k[i] == null)
				break;
			if(k[i].getX() == x)
				return true;		
		}
		
		return false;
	}
 
	public static BigInteger calculatePolynomial(BigInteger s[], BigInteger x, BigInteger p){
		BigInteger result = BigInteger.ZERO;
		for(int i = 0; i < s.length; i++)
			result = result.add(s[i].multiply(x.pow(i)));
		
		result = result.mod(p);
		return result;
	}	
	
 
	public static BigInteger[] generateParameters(int t, int numBits, byte[] SecretBytes)throws shamirException{
		BigInteger secret = new BigInteger(SecretBytes);
		
		if(secret.bitLength() >= numBits)
			throw new shamirException("numBits is too small");
						
		BigInteger s[] = new BigInteger[t];
		s[0] = secret;
		System.out.println("s(0) = " + secret + " (secret)" );
		
		for(int i = 1; i < t; i++){
			s[i] = new BigInteger(numBits, new Random());
			//System.out.println("s("+i+") = " +s[i]);
		}
		
		return s;
	}
	
	 
	public static byte[] calculateLagrange(shamirKey[] sk){
		BigInteger p = sk[0].getP();		
		BigInteger d;
		BigInteger D;		
		BigInteger c;
		BigInteger S = BigInteger.ZERO;		
		for(int i = 0; i < sk.length; i++){
			d = BigInteger.ONE;
			D = BigInteger.ONE;		
			for(int j = 0; j < sk.length; j++){
				if(j==i)
					continue;					
				d = d.multiply(sk[j].getX());
				D = D.multiply(sk[j].getX().subtract(sk[i].getX()));
			}
			c = d.multiply(D.modInverse(p)).mod(p);
			S = S.add(c.multiply(sk[i].getF())).mod(p);
		}
		return S.toByteArray();
	}
        
 
	public static shamirKey[] generateKeys(int n, int t, int numBits, BigInteger[] s)throws shamirException{
		shamirKey[] keys = new shamirKey[n];
		if(s[0].bitLength() >= numBits)
			throw new shamirException("numBits is too small");			
		if(t > n)
			throw new shamirException("number of need shares greater than number of shares");

		//BigInteger prime = BigInteger.probablePrime(numBits, new Random());
                BigInteger prime = BigInteger.valueOf(17);
		BigInteger fx, x;
		for(int i = 1; i <= n; i++){
			//do{
			//	x = new BigInteger(numBits, new Random());
			//}while(isRepeat(x, keys));	
                        x=BigInteger.valueOf(i);
			fx = calculatePolynomial(s, x, prime);
			keys[i-1] = new shamirKey();
			keys[i-1].setP(prime);
			keys[i-1].setX(x);
			keys[i-1].setF(fx);
			//System.out.println(i+"-> f("+x+") = " +keys[i-1].getF());
		}	
		return keys;
	}
        public static void main(String[] args) throws shamirException {
            
          // System.out.println("Enter "+jTextField1_K_value.getText()+" shares\n");
         BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
         String userName[]=new String[2];
         String split[][] =new String[2][2];
         for(int i=0;i<2;i++)
         {System.out.println("Enter "+(i+1)+"th share separated by ;"+"\n");
         try {
             userName[i] = br.readLine();
             String[] sp1= userName[i].split(";");
             split[i][0]=sp1[0];
             split[i][1]=sp1[1];
             System.out.println("x= "+split[0] +"\t"+"F(x)="+split[1]);
         } catch (IOException ioe) {
             System.out.println("IO error trying to read data!");//System.exit(1);
         } 
         }
                   
     shamirKey[] sk=new shamirKey[2] ;
            //sk[0].setP();
     byte[] val=new byte[1];
     val[0]=17;
     BigInteger bi= new BigInteger(val);
    sk[0]=new shamirKey();
    sk[1]=new shamirKey();
               sk[0].setP(bi ); 
               sk[0].setX( new BigInteger(split[0][0]));
               sk[0].setF( new BigInteger(split[0][1]));
               sk[1].setP(BigInteger.valueOf(17)); 
               sk[1].setX( new BigInteger(split[1][0]));
               sk[1].setF( new BigInteger(split[1][1]));
                
         
        shamir rs=new shamir();
           byte[] calculateLagrange = rs.calculateLagrange(sk);
            System.out.println(calculateLagrange.length);
            System.out.println(calculateLagrange[0]);
        
         
            
        }
}