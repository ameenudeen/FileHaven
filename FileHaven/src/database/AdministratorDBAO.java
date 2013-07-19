package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import model.*;

public class AdministratorDBAO {
    Connection con;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//Date sqldate = new Date(sdf.parse("").getTime());
    
    public AdministratorDBAO() throws Exception {
        try {
        	DB db = new DB();
	    	con = db.getConnection();
        } catch (Exception ex) {
            System.out.println("Exception in AdministratorDBAO: " + ex);
            throw new Exception("Couldn't open connection to database: " +
                    ex.getMessage());
        }
    }
    
    public void remove() {
        try {
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // Insert values into Administrator table
    
    public void insertAdministrator(String userName, String gender, String dateOfBirth, String phoneNumber, String email, String address, String NRIC) throws Exception {
        
        try {
        	
            String insertStatement = "INSERT INTO administrator (UserName, Gender, DateOfBirth, PhoneNumber, Email, Address, NRIC) VALUES(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prepStmt = con.prepareStatement(insertStatement);
            prepStmt.setString(1, userName);
            prepStmt.setString(2, gender);
            prepStmt.setString(3, dateOfBirth);
            prepStmt.setString(4, phoneNumber);
            prepStmt.setString(5, email);
            prepStmt.setString(6, address);
            prepStmt.setString(7, NRIC);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("insertAdministrator Exception : " + ex.getMessage());
        }

    }
    
    // Retrieve a single Administrator's details
    
    public Administrator getAdministratorDetails(String userName) throws Exception {
        Administrator admin = new Administrator();
        
        try {
            String selectStatement = "SELECT * FROM administrator where UserName = ?";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setString(1, userName);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
                admin.setUserName(rs.getString("UserName")); 
                admin.setGender(rs.getString("Gender"));
                admin.setDOB(rs.getDate("DateOfBirth"));
                admin.setPhoneNumber(rs.getString("PhoneNumber"));
                admin.setEmail(rs.getString("Email"));
                admin.setAddress(rs.getString("Address"));
                admin.setNRIC(rs.getString("NRIC"));
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getAdministratorDetails Exception : " + ex.getMessage());
        }
        
        return admin;
    }
    
    // Update details in Administrator table
    
    public void updateAdministratorDetails(String userName, String gender, String dateOfBirth, String phoneNumber, String email, String address, String NRIC) throws Exception {
        
        try {
        	
            String updateStatement = "UPDATE administrator SET Gender=?, DateOfBirth=?, PhoneNumber=?, Email=?, Address=?, NRIC=? WHERE userName = ?";
            PreparedStatement prepStmt = con.prepareStatement(updateStatement);
            prepStmt.setString(1, gender);
            prepStmt.setString(2, dateOfBirth);
            prepStmt.setString(3, phoneNumber);
            prepStmt.setString(4, email);
            prepStmt.setString(5, address);
            prepStmt.setString(6, NRIC);
            prepStmt.setString(7, userName);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("updateAdministratorDetails Exception : " + ex.getMessage());
        }

    }
    
    // Delete entry from Administrator table
    
    public void deleteAdministrator(String userName) throws Exception {
        
        try {
        	
            String deleteStatement = "DELETE FROM administrator WHERE UserName = ?";
            PreparedStatement prepStmt = con.prepareStatement(deleteStatement);
            prepStmt.setString(1, userName);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("deleteAdministrator Exception : " + ex.getMessage());
        }

    }

}
