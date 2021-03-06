package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import model.Filemanager;

public class FilemanagerDBAO {
    Connection con;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//Date sqldate = new Date(sdf.parse("").getTime());
    
    public FilemanagerDBAO() throws Exception {
        try {
        	DB db = new DB();
	    	con = db.getConnection();
        } catch (Exception ex) {
            System.out.println("Exception in FilemanagerDBAO: " + ex);
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
    
    // Insert values into Filemanager table
    
    public void insertFilemanager(String userName, String gender, String dateOfBirth, String phoneNumber, String email, String address, String NRIC) throws Exception {
        
        try {
        	
            String insertStatement = "INSERT INTO filemanager (UserName, Gender, DateOfBirth, PhoneNumber, Email, Address, NRIC) VALUES(?, ?, ?, ?, ?, ?, ?)";
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
            throw new Exception("insertFilemanager Exception : " + ex.getMessage());
        }

    }
    
    // Insert values into Filemanager table (Update.jsp) 
    
    public void insertFilemanagerCopy(String userName, String gender, String dateOfBirth, String phoneNumber, String email, String address, String NRIC, int departmentID) throws Exception {
        
        try {
        	
            String insertStatement = "INSERT INTO filemanager (UserName, Gender, DateOfBirth, PhoneNumber, Email, Address, NRIC, DepartmentID) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prepStmt = con.prepareStatement(insertStatement);
            prepStmt.setString(1, userName);
            prepStmt.setString(2, gender);
            prepStmt.setString(3, dateOfBirth);
            prepStmt.setString(4, phoneNumber);
            prepStmt.setString(5, email);
            prepStmt.setString(6, address);
            prepStmt.setString(7, NRIC);
            prepStmt.setInt(8, departmentID);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("insertFilemanagerCopy Exception : " + ex.getMessage());
        }

    }
    
    // Retrieve a single filemanager's details
    
    public Filemanager getFilemanagerDetails(String userName) throws Exception {
        Filemanager Fm = new Filemanager();
        
        try {
            String selectStatement = "SELECT * FROM filemanager where userName = ?";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setString(1, userName);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
                Fm.setUserName(userName); 
                Fm.setGender(rs.getString("Gender"));
                Fm.setDOB(rs.getDate("DateOfBirth"));
                Fm.setPhoneNumber(rs.getString("PhoneNumber"));
                Fm.setEmail(rs.getString("Email"));
                Fm.setAddress(rs.getString("Address"));
                Fm.setNRIC(rs.getString("NRIC"));
                Fm.setDepartmentID(rs.getInt("DepartmentID"));
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getFilemanagerDetails Exception : " + ex.getMessage());
        }
        
        return Fm;
    }
    
    // Update details in Filemanager table
    
    public void updateFilemanagerDetails(String userName, String gender, String dateOfBirth, String phoneNumber, String email, String address, String NRIC) throws Exception {
        
        try {
        	
            String updateStatement = "UPDATE filemanager SET Gender=?, DateOfBirth=?, PhoneNumber=?, Email=?, Address=?, NRIC=? WHERE UserName = ?";
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
            throw new Exception("updateFilemanagerDetails Exception : " + ex.getMessage());
        }

    }
    
    // Delete entry from Filemanager table
    
    public void deleteFilemanager(String userName) throws Exception {
        
        try {
        	
            String deleteStatement = "DELETE FROM filemanager WHERE UserName = ?";
            PreparedStatement prepStmt = con.prepareStatement(deleteStatement);
            prepStmt.setString(1, userName);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("deleteFilemanager Exception : " + ex.getMessage());
        }

    }

}// End Of Class
