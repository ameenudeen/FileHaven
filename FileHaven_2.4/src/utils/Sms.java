package utils;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.TimeZone;

import model.FileReport;

public class Sms {
	
//	 public static String generatePassword() {
//	        String chars = "abcdefghijklmnopqrstuvwxyz"
//	                     + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
//	                     + "0123456789!@%$%&^?|~'\"#+="
//	                     + "\\*/.,:;[]()-_<>";
//
//	        final int PW_LENGTH = 20;
//	        Random rnd = new SecureRandom();
//	        StringBuilder pass = new StringBuilder();
//	        for (int i = 0; i < PW_LENGTH; i++)
//	            pass.append(chars.charAt(rnd.nextInt(chars.length())));
//	        return pass.toString();
//	    }

	    public static void main(String[] args) {
	    	FileReport f1 = new FileReport();
	    	String test=f1.getDownloadedDate().toString();
	    	 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	    	    SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss.SS dd/MM/yyyy");
	    	    fmt.setTimeZone(TimeZone.getTimeZone("GMT"));
	    	    System.out.println(fmt.format(timestamp));
//	        System.out.println(generatePassword());
//	        System.out.println(generatePassword());
//	        System.out.println(generatePassword());
	    }


}
