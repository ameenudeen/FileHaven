package database;

import java.sql.*;

public class DB {
    Connection con;
    
    // Database configuration
    
    public static String dbdriver = "com.mysql.jdbc.Driver";


//    public static String url = "jdbc:mysql://adeeldb.ch30tsalfl52.ap-southeast-1.rds.amazonaws.com:3306/filehaven";
//    public static String username = "adeeldb";
//    public static String password = "Iamtheking";
    
    public static String url = "jdbc:mysql://localhost/FileHaven";
    public static String username = "root";
    public static String password = "password";
    
    
    public DB() throws Exception {
        try {
            Class.forName(dbdriver);
            con = DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            System.out.println("Exception in File Haven: " + ex);
            throw new Exception("Couldn't open connection to database: " +
                    ex.getMessage());
        }
    }
    public Connection getConnection() {
    	return con;
    }
    
    public static void main(String args[]){
    	try{
    		new DB().getConnection();
    	}
    	catch(Exception ex){ex.printStackTrace();}
    }
}
