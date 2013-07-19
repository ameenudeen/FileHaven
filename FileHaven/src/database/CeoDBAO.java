package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import model.Ceo;

public class CeoDBAO {
    Connection con;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//Date sqldate = new Date(sdf.parse("").getTime());
    
    public CeoDBAO() throws Exception {
        try {
        	DB db = new DB();
	    	con = db.getConnection();
        } catch (Exception ex) {
            System.out.println("Exception in CeoDBAO: " + ex);
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

    // Insert values into Ceo table
    
    public void insertCeo(String userName, String gender, String dateOfBirth, String phoneNumber, String email, String address, String NRIC) throws Exception {
        
        try {
        	
            String insertStatement = "INSERT INTO ceo (UserName, Gender, DateOfBirth, PhoneNumber, Email, Address, NRIC) VALUES(?, ?, ?, ?, ?, ?, ?)";
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
            throw new Exception("insertCeo Exception : " + ex.getMessage());
        }

    }
    
    // Retrieve a single Ceo's details
    
    public Ceo getCeoDetails(String userName) throws Exception {
        Ceo ceo = new Ceo();
        
        try {
            String selectStatement = "SELECT * FROM ceo where UserName = ?";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setString(1, userName);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
                ceo.setUserName(rs.getString("UserName")); 
                ceo.setGender(rs.getString("Gender"));
                ceo.setDOB(rs.getDate("DateOfBirth"));
                ceo.setPhoneNumber(rs.getString("PhoneNumber"));
                ceo.setEmail(rs.getString("Email"));
                ceo.setAddress(rs.getString("Address"));
                ceo.setNRIC(rs.getString("NRIC"));
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getCeoDetails Exception : " + ex.getMessage());
        }
        
        return ceo;
    }
    
    // Update details in Ceo table
    
    public void updateCeoDetails(String userName, String gender, String dateOfBirth, String phoneNumber, String email, String address, String NRIC) throws Exception {
        
        try {
        	
            String updateStatement = "UPDATE ceo SET Gender=?, DateOfBirth=?, PhoneNumber=?, Email=?, Address=?, NRIC=? WHERE userName = ?";
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
            throw new Exception("updateCeoDetails Exception : " + ex.getMessage());
        }

    }
    
    // Delete entry from Ceo table
    
    public void deleteCeo(String userName) throws Exception {
        
        try {
        	
            String deleteStatement = "DELETE FROM ceo WHERE UserName = ?";
            PreparedStatement prepStmt = con.prepareStatement(deleteStatement);
            prepStmt.setString(1, userName);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("deleteCeo Exception : " + ex.getMessage());
        }

    }
    
}// End Of Class
