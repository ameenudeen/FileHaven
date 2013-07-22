package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.Permission;

public class PermissionDBAO {
	Connection con;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//Date sqldate = new Date(sdf.parse("").getTime());
    
    public PermissionDBAO() throws Exception {
        try {
        	DB db = new DB();
	    	con = db.getConnection();
        } catch (Exception ex) {
            System.out.println("Exception in PermissionDBAO: " + ex);
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
    
    // Insert values into Permission table
    
    public void insertPermission(String startTime, String endTime, String extendStart, String extendEnd, String grantBy, String userName, int companyID) throws Exception {
        
        try {
        	
            String insertStatement = "INSERT INTO permission (StartTime, EndTime, ExtendStart, ExtendEnd, GrantBy, UserName, CompanyID) VALUES(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prepStmt = con.prepareStatement(insertStatement);
            
            java.util.Date temp = sdf.parse(startTime);
            Timestamp tss = new Timestamp(temp.getTime());
            temp = sdf.parse(endTime);
            Timestamp tse = new Timestamp(temp.getTime());
            
            prepStmt.setTimestamp(1, tss);
            prepStmt.setTimestamp(2, tse);
            
            prepStmt.setString(3, extendStart);
            prepStmt.setString(4, extendEnd);
            prepStmt.setString(5, grantBy);
            prepStmt.setString(6, userName);
            prepStmt.setInt(7, companyID);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("insertPermission Exception : " + ex.getMessage());
        }

    }
    
    // Retrieve details from Permission table
    
    public Permission getPermissionDetails(int ID) throws Exception {
    	Permission pm = new Permission();
    	
        try {
            String selectStatement = "SELECT * FROM permission where ID = ? ";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, ID);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
            	pm.setID(ID);
            	
            	String startTime = rs.getTimestamp("StartTime").toString();
                java.util.Date tempDate = sdf.parse(startTime);
                startTime = sdf.format(tempDate);
                pm.setStartTime(startTime);
                
                String endTime = rs.getTimestamp("EndTime").toString();
                tempDate = sdf.parse(endTime);
                endTime = sdf.format(tempDate);
                pm.setEndTime(endTime);
            	
                pm.setExtendStart(rs.getString("ExtendStart"));
                pm.setExtendEnd(rs.getString("ExtendEnd"));
                pm.setGrantBy(rs.getString("GrantBy"));
                pm.setUserName(rs.getString("UserName"));
                pm.setcompanyID(rs.getInt("CompanyID"));
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getPermissionDetails Exception : " + ex.getMessage());
        }
        
        return pm;
    }
    
    // Retrieve ID from Permission table by userName and date
    
    public ArrayList<Integer> getUserPermission(String userName, String today) throws Exception {
    	ArrayList<Integer> IDList = new ArrayList<Integer>();
    	
        try {
            String selectStatement = "SELECT ID FROM permission WHERE UserName = ? AND StartTime <= ? AND EndTime >= ?  ORDER BY ID";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setString(1, userName);
            prepStmt.setString(2, today);
            prepStmt.setString(3, today);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
            	int ID = rs.getInt("ID");
                
                IDList.add(ID);
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getUserPermission Exception : " + ex.getMessage());
        }
        
        return IDList;
    }
    
    // Retrieve permissions from Permission table based on companyID
    
    public ArrayList<Permission> getCompanyPermissions(int companyID) throws Exception {
    	ArrayList<Permission> pmList = new ArrayList<Permission>();
    	
        try {
            String selectStatement = "SELECT * FROM permission " +
            						 "WHERE CompanyID = ? " +
            						 "ORDER BY ID";
            
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, companyID);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
            	Permission pm = new Permission();
                
                pm.setID(rs.getInt("ID"));
                
                sdf.applyPattern("yyyy-MM-dd");
                
                String tempTime = rs.getTimestamp("StartTime").toString();
                java.util.Date tempDate = sdf.parse(tempTime);
                tempTime = sdf.format(tempDate);
                pm.setStartTime(tempTime);
                
                tempTime = rs.getTimestamp("EndTime").toString();
                tempDate = sdf.parse(tempTime);
                tempTime = sdf.format(tempDate);
                pm.setEndTime(tempTime);
                
                pm.setExtendStart(rs.getTime("ExtendStart").toString());
                pm.setExtendEnd(rs.getTime("ExtendEnd").toString());
                pm.setGrantBy(rs.getString("GrantBy"));
                pm.setUserName(rs.getString("UserName"));
                pm.setcompanyID(rs.getInt("CompanyID"));
                
                pmList.add(pm);
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getCompanyPermissions Exception : " + ex.getMessage());
        }
        
        return pmList;
    }
    
    // Retrieve permission from Permission table based on companyID (user & date filter)
    
    public ArrayList<Permission> getCompanyPermissions(int companyID, String dStmt) throws Exception {
    	ArrayList<Permission> pmList = new ArrayList<Permission>();
    	
        try {
            String selectStatement = "SELECT * FROM permission " +
            						 "WHERE CompanyID = ? " + dStmt + " " +
            						 "ORDER BY ID";
            
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, companyID);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
            	Permission pm = new Permission();
            	
                pm.setID(rs.getInt("ID"));
                
                sdf.applyPattern("yyyy-MM-dd");
                
                String tempTime = rs.getTimestamp("StartTime").toString();
                java.util.Date tempDate = sdf.parse(tempTime);
                tempTime = sdf.format(tempDate);
                pm.setStartTime(tempTime);
                
                tempTime = rs.getTimestamp("EndTime").toString();
                tempDate = sdf.parse(tempTime);
                tempTime = sdf.format(tempDate);
                pm.setEndTime(tempTime);
                
                pm.setExtendStart(rs.getTime("ExtendStart").toString());
                pm.setExtendEnd(rs.getTime("ExtendEnd").toString());
                pm.setGrantBy(rs.getString("GrantBy"));
                pm.setUserName(rs.getString("UserName"));
                pm.setcompanyID(rs.getInt("CompanyID"));
                
                pmList.add(pm);
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getCompanyPermissions Exception : " + ex.getMessage());
        }
        
        return pmList;
    }
    
    // Retrieve startTime & endTime from Account table to check if it clashes with the entered startTime & endTime
    
    public boolean checkDateClash(String userName, String startTime, String endTime) throws Exception {
    	boolean result = false;
    	
        try {
            String selectStatement = "SELECT StartTime, EndTime FROM permission where UserName = ?";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setString(1, userName);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
            	
            	String tempTime = rs.getTimestamp("StartTime").toString();
                java.util.Date startDate = sdf.parse(tempTime);
                
                tempTime = rs.getTimestamp("EndTime").toString();
                java.util.Date endDate = sdf.parse(tempTime);
                
                java.util.Date comStartDate = sdf.parse(startTime);
                java.util.Date comEndDate = sdf.parse(endTime);
                
                if(comStartDate.equals(startDate) || comEndDate.equals(endDate) || comStartDate.after(startDate) && comStartDate.before(endDate)
                || comEndDate.after(startDate) && comEndDate.before(endDate) || comStartDate.before(startDate) && comEndDate.after(endDate))
                {
                	result = true;
                }
                
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("checkDateClash Exception : " + ex.getMessage());
        }
        return result;
    }
    
    // Update details in Permission table
    
    public void updatePermissionDetails(int ID, String startTime, String endTime, String extendStart, String extendEnd) throws Exception {
        
        try {
        	
            String updateStatement = "UPDATE permission SET StartTime=?, EndTime=?, ExtendStart=?, ExtendEnd=? WHERE ID = ?";
            PreparedStatement prepStmt = con.prepareStatement(updateStatement);
            
            java.util.Date temp = sdf.parse(startTime);
            Timestamp tss = new Timestamp(temp.getTime());
            temp = sdf.parse(endTime);
            Timestamp tse = new Timestamp(temp.getTime());
            
            prepStmt.setTimestamp(1, tss);
            prepStmt.setTimestamp(2, tse);
            
            prepStmt.setString(3, extendStart);
            prepStmt.setString(4, extendEnd);
            prepStmt.setInt(5, ID);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("updatePermissionDetails Exception : " + ex.getMessage());
        }

    }
    
    // Delete entry from Permission table
    
    public void deletePermission(int ID) throws Exception {
        
        try {
        	
            String deleteStatement = "DELETE FROM permission WHERE ID = ?";
            PreparedStatement prepStmt = con.prepareStatement(deleteStatement);
            prepStmt.setInt(1, ID);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("deletePermission Exception : " + ex.getMessage());
        }

    }
    
}
