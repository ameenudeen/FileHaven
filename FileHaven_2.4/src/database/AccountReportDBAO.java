package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.AccountReport;

public class AccountReportDBAO {
	Connection con;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//Date sqldate = new Date(sdf.parse("").getTime());
    
    public AccountReportDBAO() throws Exception {
        try {
        	DB db = new DB();
	    	con = db.getConnection();
        } catch (Exception ex) {
            System.out.println("Exception in AccountReportDBAO: " + ex);
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
    
    // Insert values into AccountReport table
    
    public void insertAccountReport(String details, String logTime, String userName, int companyID) throws Exception {
        
        try {
        	
            String insertStatement = "INSERT INTO accountreport (Details, LogTime, UserName, CompanyID) VALUES(?, ?, ?, ?)";
            PreparedStatement prepStmt = con.prepareStatement(insertStatement);
            prepStmt.setString(1, details);
            
            java.util.Date temp = sdf.parse(logTime);
            Timestamp ts = new Timestamp(temp.getTime());
            
            prepStmt.setTimestamp(2, ts);
            prepStmt.setString(3, userName);
            prepStmt.setInt(4, companyID);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("insertAccountReport Exception : " + ex.getMessage());
        }

    }
    
    // Retrieve details from AccountReport table
    
    public AccountReport getAccountReportDetails(int ID) throws Exception {
    	AccountReport ar = new AccountReport();
    	
        try {
            String selectStatement = "SELECT * FROM accountreport where ID = ? ";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, ID);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
            	ar.setID(ID);
            	ar.setDetails(rs.getString("Details"));
            	
            	String logTime = rs.getTimestamp("LogTime").toString();
                java.util.Date tempDate = sdf.parse(logTime);
                logTime = sdf.format(tempDate);
                ar.setLogTime(logTime);
            	
                ar.setUserName(rs.getString("UserName"));
                ar.setCompanyID(rs.getInt("CompanyID"));
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getAccountReportDetails Exception : " + ex.getMessage());
        }
        
        return ar;
    }
    
    // Retrieve ID from AccountReport table by companyID
    
    public ArrayList<Integer> getUserReportsComID(int companyID) throws Exception {
    	ArrayList<Integer> IDList = new ArrayList<Integer>();
    	
        try {
            String selectStatement = "SELECT ID FROM accountreport WHERE CompanyID = ? ORDER BY ID";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, companyID);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
            	int ID = rs.getInt("ID");
                
                IDList.add(ID);
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getUserReportsComID Exception : " + ex.getMessage());
        }
        
        return IDList;
    }
    
    // Retrieve ID from AccountReport table by userName
    
    public ArrayList<Integer> getUserReports(String userName) throws Exception {
    	ArrayList<Integer> IDList = new ArrayList<Integer>();
    	
        try {
            String selectStatement = "SELECT ID FROM accountreport WHERE UserName = ? ORDER BY ID";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setString(1, userName);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
            	int ID = rs.getInt("ID");
                
                IDList.add(ID);
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getUserReports Exception : " + ex.getMessage());
        }
        
        return IDList;
    }
    
    // Retrieve details of Account Report from AccountReport table based on companyID
    
    public ArrayList<AccountReport> getCompanyAccountReports(int companyID) throws Exception {
    	ArrayList<AccountReport> arList = new ArrayList<AccountReport>();
    	
        try {
            String selectStatement = "SELECT * FROM accountreport " +
            						 "WHERE CompanyID = ? " +
            						 "ORDER BY ID";
            
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, companyID);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
            	AccountReport ar = new AccountReport();
                
                ar.setID(rs.getInt("ID"));
                ar.setDetails(rs.getString("Details"));
                
                String logTime = rs.getTimestamp("LogTime").toString();
                java.util.Date tempDate = sdf.parse(logTime);
                logTime = sdf.format(tempDate);
                ar.setLogTime(logTime);
                
                ar.setUserName(rs.getString("UserName"));
                ar.setCompanyID(rs.getInt("CompanyID"));
                
                arList.add(ar);
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getCompanyAccountReports Exception : " + ex.getMessage());
        }
        
        return arList;
    }
    
    // Retrieve details of Account Report from AccountReport table based on companyID (user & date filter)
    
    public ArrayList<AccountReport> getCompanyAccountReports(int companyID, String uStmt, String dStmt) throws Exception {
    	ArrayList<AccountReport> arList = new ArrayList<AccountReport>();
    	
        try {
            String selectStatement = "SELECT * FROM accountreport " +
            						 "WHERE CompanyID = ? " + uStmt + " " + dStmt + " " +
            						 "ORDER BY ID";
            
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, companyID);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
            	AccountReport ar = new AccountReport();
                
                ar.setID(rs.getInt("ID"));
                ar.setDetails(rs.getString("Details"));
                
                String logTime = rs.getTimestamp("LogTime").toString();
                java.util.Date tempDate = sdf.parse(logTime);
                logTime = sdf.format(tempDate);
                ar.setLogTime(logTime);
                
                ar.setUserName(rs.getString("UserName"));
                ar.setCompanyID(rs.getInt("CompanyID"));
                
                arList.add(ar);
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getCompanyAccountReports Exception : " + ex.getMessage());
        }
        
        return arList;
    }
    
    // Update details in AccountReport table
    
    public void updateAccountReportDetails(int ID, String details) throws Exception {
        
        try {
        	
            String updateStatement = "UPDATE accountreport SET Details=? WHERE ID = ?";
            PreparedStatement prepStmt = con.prepareStatement(updateStatement);
            prepStmt.setString(1, details);
            prepStmt.setInt(2, ID);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("updateAccountReportDetails Exception : " + ex.getMessage());
        }

    }
    
    // Delete entry from AccountReport table
    
    public void deleteAccountReport(int ID) throws Exception {
        
        try {
        	
            String deleteStatement = "DELETE FROM accountreport WHERE ID = ?";
            PreparedStatement prepStmt = con.prepareStatement(deleteStatement);
            prepStmt.setInt(1, ID);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("deleteAccountReport Exception : " + ex.getMessage());
        }

    }

}
