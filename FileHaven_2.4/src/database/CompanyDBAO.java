package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.Company;

public class CompanyDBAO {
    Connection con;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//Date sqldate = new Date(sdf.parse("").getTime());
    
    public CompanyDBAO() throws Exception {
        try {
        	DB db = new DB();
	    	con = db.getConnection();
        } catch (Exception ex) {
            System.out.println("Exception in CompanyDBAO: " + ex);
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
    
    // Insert values into Company table
    
    public void insertCompany(String companyName, String address, int storageSpace, String companyLogo) throws Exception {
        
        try {
        	
            String insertStatement = "INSERT INTO company (Name, Address, StorageSpace, CompanyLogo, StartTime, EndTime) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement prepStmt = con.prepareStatement(insertStatement);
            prepStmt.setString(1, companyName);
            prepStmt.setString(2, address);
            prepStmt.setInt(3, storageSpace);
            prepStmt.setString(4, companyLogo);
            prepStmt.setString(5, "00:00:00");
            prepStmt.setString(6, "00:00:00");
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("insertCompany Exception : " + ex.getMessage());
        }

    }
    
    // Retrieve a single Company's details
    
    public Company getCompanyDetails(int companyID) throws Exception {
        Company com = new Company();
        
        try {
            String selectStatement = "SELECT * FROM company where ID = ?";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, companyID);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
                com.setCompanyID(rs.getInt("ID")); 
                com.setCompanyName(rs.getString("Name"));
                com.setAddress(rs.getString("Address"));
                com.setStorageSpace(rs.getInt("StorageSpace"));
                com.setCompanyLogo(rs.getString("CompanyLogo"));
                com.setStartTime(rs.getString("startTime"));
                com.setEndTime(rs.getString("endTime"));
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getCompanyDetails Exception : " + ex.getMessage());
        }
        
        return com;
    }
    
    // Retrieve a single Company's ID by name
    
    public int getCompanyID(String companyName) throws Exception {
        int id = 0;
        
        try {
            String selectStatement = "SELECT ID FROM company where Name = ?";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setString(1, companyName);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
                id = rs.getInt("ID");
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getCompanyID Exception : " + ex.getMessage());
        }
        
        return id;
    }
    
    // Retrieve a list of Companys details
    
    public ArrayList<Company> getCompanysList() throws Exception {
        ArrayList<Company> companyList = new ArrayList<Company>();
        
        try {
            String selectStatement = "SELECT ID, Name FROM company";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
            	Company com = new Company();
            	
                com.setCompanyID(rs.getInt("ID")); 
                com.setCompanyName(rs.getString("Name"));
                
                companyList.add(com);
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getCompanysList Exception : " + ex.getMessage());
        }
        
        return companyList;
    }
    
    // Update details in Company table
    
    public void updateCompanyDetails(int companyID, String companyName, String address) throws Exception {
        
        try {
        	
            String updateStatement = "UPDATE company SET Name=?, Address=? WHERE ID = ?";
            PreparedStatement prepStmt = con.prepareStatement(updateStatement);
            prepStmt.setString(1, companyName);
            prepStmt.setString(2, address);
            prepStmt.setInt(3, companyID);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("updateCompanyDetails Exception : " + ex.getMessage());
        }

    }
    
    // Update working time in Company table
    
    public void updateWorkingTime(int companyID, String startTime, String endTime) throws Exception {
        
        try {
        	
            String updateStatement = "UPDATE company SET StartTime=?, EndTime=? WHERE ID = ?";
            PreparedStatement prepStmt = con.prepareStatement(updateStatement);
            prepStmt.setString(1, startTime);
            prepStmt.setString(2, endTime);
            prepStmt.setInt(3, companyID);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("updateWorkingTime Exception : " + ex.getMessage());
        }

    }
    
    // Delete entry from Company table
    
    public void deleteCompany(int companyID) throws Exception {
        
        try {
        	
            String deleteStatement = "DELETE FROM company WHERE ID = ?";
            PreparedStatement prepStmt = con.prepareStatement(deleteStatement);
            prepStmt.setInt(1, companyID);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("deleteCompany Exception : " + ex.getMessage());
        }

    }
    
}
