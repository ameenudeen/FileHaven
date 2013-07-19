package security;

import java.security.MessageDigest;

public class Hash {
	
	public static String hashString(String password, String salt, String pepper) throws Exception{
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update((password + salt + pepper).getBytes());
 
        byte byteData[] = md.digest();
        
        StringBuffer hexString = new StringBuffer();
        
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
        
        return hexString.toString();
	}
	
}
