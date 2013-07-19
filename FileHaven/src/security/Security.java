package security;

import java.io.InputStream;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.IOUtils;

public class Security {

	public static byte[] generateAESKey(String pass){

		byte[][] keys=new byte[2][16];
		byte[] key=new byte[16];

		int counter=0;
		int offset=0;
		
		byte pass_byte[]=pass.getBytes();
		
		//get the input string and fill up the leaving space with 
		//byte regarding to length or input
		for(int i=0;i<2;i++)
		{	
			for(int j=0;j<16;j++){
				if(counter<pass_byte.length)
					keys[i][j]=pass_byte[counter++];
				else{
					keys[i][j]=(byte)((pass_byte.length+counter)*(counter-pass_byte.length));
					counter++;
				}
			}
		}
			
		//do some random inversion
			if(pass_byte.length<2||pass_byte.length>15){
				offset=3;
			}
			else{
				offset=pass_byte.length/2;
			}
			for(int j=0;j<keys[0].length;j++){
				if(j%offset==0){
					keys[0][j]=(byte) ~keys[0][j];
				}
			}
			

			if(pass_byte.length<3||pass_byte.length>15){
				offset=7;
			}
			else{
				offset=pass_byte.length/3;
			}
			for(int j=0;j<keys[1].length;j++){
				if(j%offset==0){
					keys[1][j]=(byte) ~keys[1][j];
				}
			}
			for(int j=0;j<16;j++){
				key[j]=(byte)(keys[0][j] ^ keys[1][j]);
			}
			

			
			return key;
	}
	
	public static byte[] encryptStream(InputStream is,byte[] key,String transformation) throws Exception{
		byte[] bytes=IOUtils.toByteArray(is);
		Cipher c = Cipher.getInstance(transformation);
		SecretKeySpec k=new SecretKeySpec(key, transformation);
		
		c.init(Cipher.ENCRYPT_MODE, k);
		byte[] data = c.doFinal(bytes);
		return data;
	}
	
	public static byte[] decryptStream(InputStream is,byte[] key,String transformation) throws Exception{
		byte[] bytes=IOUtils.toByteArray(is);
		Cipher c = Cipher.getInstance(transformation);
		SecretKeySpec k =new SecretKeySpec(key, transformation);
		
		c.init(Cipher.DECRYPT_MODE, k);
		byte[] data = c.doFinal(bytes);
		return data;
	}
	
	public static byte[] encryptByte(byte[] input,byte[] key,String transformation) throws Exception{
		Cipher c = Cipher.getInstance(transformation);
		SecretKeySpec k=new SecretKeySpec(key, transformation);
		
		c.init(Cipher.ENCRYPT_MODE, k);
		byte[] data = c.doFinal(input);
		return data;
	}
	
	public static byte[] decryptByte(byte[] input,byte[] key,String transformation) throws Exception{
		Cipher c = Cipher.getInstance(transformation);
		SecretKeySpec k =new SecretKeySpec(key, transformation);
		
		c.init(Cipher.DECRYPT_MODE, k);
		byte[] data = c.doFinal(input);
		return data;
	}
	
	public static String hashStream(InputStream is,String transformation) throws Exception{
		MessageDigest md = MessageDigest.getInstance(transformation);
        byte byteData[] = md.digest(IOUtils.toByteArray(is));
        StringBuffer hexString = new StringBuffer();
    	for (byte b:byteData) {
    		String hex=Integer.toHexString(0xFF & b).toUpperCase();
    		//0xFF &  make the byte to become unsigned
    		//1111 1111
    		//int>byte
   	     	if(hex.length()==1)//byte <16 
   	     		hexString.append('0');
   	     	hexString.append(hex);
    	}
		return hexString.toString();
	}

}
