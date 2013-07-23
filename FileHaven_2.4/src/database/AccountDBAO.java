package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.*;


public class AccountDBAO {
    Connection con;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//Date sqldate = new Date(sdf.parse("").getTime());
    //leon1
	//chun boon
    public AccountDBAO() throws Exception {
        try {
        	DB db = new DB();
	    	con = db.getConnection();
        } catch (Exception ex) {
            System.out.println("Exception in AccountDBAO: " + ex);
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
    
    // Insert values into Account table
    
    public void insertAccount(String userName, String name, String password, char type, String creatorID, String createdTime, int companyID) throws Exception {
        
        try {
        	
            String insertStatement = "INSERT INTO account (UserName, Name, Password, Type, Availability, CreatorID, CreatedTime, CompanyID) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prepStmt = con.prepareStatement(insertStatement);
            prepStmt.setString(1, userName);
            prepStmt.setString(2, name);
            prepStmt.setString(3, password);
            prepStmt.setString(4, Character.toString(type));
            prepStmt.setString(5, "True");
            prepStmt.setString(6, creatorID);
            
            java.util.Date now = sdf.parse(createdTime);
            Timestamp ts = new Timestamp(now.getTime());
            
            prepStmt.setTimestamp(7, ts);
            prepStmt.setInt(8, companyID);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("insertAccount Exception : " + ex.getMessage());
        }

    }
    
    // Get password from Account table
    
    public String getPassword(String userName) throws Exception {
    	String password = "";
    	
        try {
            String selectStatement = "SELECT Password FROM account where UserName = ? AND Availability = 'True'";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setString(1, userName);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
                password = rs.getString("Password");
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getPassword Exception : " + ex.getMessage());
        }
        
        return password;
    }
    
    // Retrieve details from Account table
    
    public Account getAccountDetails(String userName) throws Exception {
    	Account acc = new Account();
    	
        try {
            String selectStatement = "SELECT Name, Type, Availability, CreatorID, CreatedTime, CompanyID,UserPattern FROM account where UserName = ? ";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setString(1, userName);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
            	acc.setUserName(userName);
            	acc.setName(rs.getString("Name"));
            	acc.setType(rs.getString("Type").charAt(0));
            	acc.setAvailability(rs.getString("Availability"));
                acc.setCreatorID(rs.getString("CreatorID"));
                acc.setUserPattern(rs.getString("UserPattern"));
                
                String createdTime = rs.getTimestamp("CreatedTime").toString();
                java.util.Date tempDate = sdf.parse(createdTime);
                createdTime = sdf.format(tempDate);
                acc.setCreatedTime(createdTime);
                
                
                acc.setCompanyID(rs.getInt("CompanyID"));
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getAccountDetails Exception : " + ex.getMessage());
        }
        
        return acc;
    }
    
    // Retrieve usernames from Account table by companyID
    
    public ArrayList<String> getAccountUserNames(int companyID, char type) throws Exception {
    	ArrayList<String> userNameList = new ArrayList<String>();
    	
        try {
            String selectStatement = "SELECT UserName FROM account WHERE CompanyID = ? AND Availability = 'True' AND Type <> 'A' AND Type <> ? ORDER BY Name";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, companyID);
            prepStmt.setString(2, Character.toString(type));
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
            	String userName = rs.getString("UserName");
                
                userNameList.add(userName);
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getAccountUserNames Exception : " + ex.getMessage());
        }
        return userNameList;
    }
    
    // Retrieve Names from Account table by companyID
    
    public ArrayList<String> getAccountNames(int companyID) throws Exception {
    	ArrayList<String> nameList = new ArrayList<String>();
    	
        try {
            String selectStatement = "SELECT UserName FROM account WHERE CompanyID = ? AND Availability = 'True' ORDER BY Name";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, companyID);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
            	String name = rs.getString("UserName");
                
                nameList.add(name);
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getAccountNames Exception : " + ex.getMessage());
        }
        
        return nameList;
    }
    
    // Retrieve Names from Account table by companyID and below current account position
    
    public ArrayList<String> getAccountNames(int companyID, char type) throws Exception {
    	ArrayList<String> nameList = new ArrayList<String>();
    	
        try {
            String selectStatement = "SELECT Name FROM account WHERE CompanyID = ? AND Availability = 'True' AND Type <> 'A' AND Type <> ? ORDER BY Name";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, companyID);
            prepStmt.setString(2, Character.toString(type));
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
            	String name = rs.getString("Name");
                
                nameList.add(name);
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getAccountNames Exception : " + ex.getMessage());
        }
        return nameList;
    }
    
    // Retrieve usernames of employees from Account & Employee tables based on companyID and departmentID
    
    public ArrayList<String> getEmployeeUserNames(int companyID, int departmentID) throws Exception {
    	ArrayList<String> userNameList = new ArrayList<String>();
    	
        try {
            String selectStatement = "SELECT a.UserName FROM account a " +
            						 "INNER JOIN employee e ON a.UserName = e.UserName " +
            						 "WHERE a.CompanyID = ? AND a.Availability = 'True' AND a.Type = 'E' AND e.DepartmentID = ? " +
            						 "ORDER BY a.Name";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, companyID);
            prepStmt.setInt(2, departmentID);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
            	String userName = rs.getString("UserName");
                
                userNameList.add(userName);
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getEmployeeUserNames Exception : " + ex.getMessage());
        }
        return userNameList;
    }
    
    // Retrieve Names of employees from Account & Employee tables based on companyID and departmentID
    
    public ArrayList<String> getEmployeeNames(int companyID, int departmentID) throws Exception {
    	ArrayList<String> nameList = new ArrayList<String>();
    	
        try {
            String selectStatement = "SELECT a.Name FROM account a " +
            						 "INNER JOIN employee e ON a.UserName = e.UserName " +
            						 "WHERE a.CompanyID = ? AND a.Availability = 'True' AND a.Type = 'E' AND e.DepartmentID = ? " +
            						 "ORDER BY a.Name";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, companyID);
            prepStmt.setInt(2, departmentID);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
            	String name = rs.getString("Name");
                
                nameList.add(name);
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getEmployeeUserNames Exception : " + ex.getMessage());
        }
        return nameList;
    }
    
    // Retrieve username from Account table to check if it exists
    
    public boolean checkUserNameAvailability(String userName) throws Exception {
    	boolean result = true;
    	
        try {
            String selectStatement = "SELECT UserName FROM account where UserName = ?";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setString(1, userName);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
            	result = false;
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getAccountUserNames Exception : " + ex.getMessage());
        }
        return result;
    }
    
    // Retrieve details of employees from Account & Employee tables to display in view account page based on companyID
    
    public ArrayList<Account> getCompanyEmployees(int companyID, String wStmt) throws Exception {
    	ArrayList<Account> accList = new ArrayList<Account>();
    	
        try {
            String selectStatement = "SELECT * FROM account a " +
            						 "INNER JOIN employee e ON a.UserName = e.UserName " +
            						 "WHERE CompanyID = ? AND a.Availability = 'True'" + wStmt + " " +
            						 "ORDER BY Name";
            
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, companyID);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
            	Employee emp = new Employee();
            	
            	emp.setUserName(rs.getString("UserName"));
            	emp.setName(rs.getString("Name"));
            	emp.setType(rs.getString("Type").charAt(0));
                emp.setCompanyID(companyID);
                
                emp.setGender(rs.getString("Gender").toString());
                emp.setDOB(rs.getDate("DateOfBirth"));
                emp.setPhoneNumber(rs.getString("PhoneNumber"));
                emp.setEmail(rs.getString("Email"));
                emp.setAddress(rs.getString("Address"));
                emp.setNRIC(rs.getString("NRIC"));
                
                if((Integer) rs.getInt("DepartmentID") != null)
                {
                	emp.setDepartmentID(rs.getInt("DepartmentID"));
                }
                
                accList.add(emp);
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getCompanyEmployees Exception : " + ex.getMessage());
        }
        
        return accList;
    }
    
    // Retrieve details of filemanagers from Account & Filemanager tables to display in view account page based on companyID
    
    public ArrayList<Account> getCompanyFilemanagers(int companyID, String wStmt) throws Exception {
    	ArrayList<Account> accList = new ArrayList<Account>();
    	
        try {
            String selectStatement = "SELECT * FROM account a " +
            						 "INNER JOIN filemanager f ON a.UserName = f.UserName " +
            						 "WHERE CompanyID = ? AND a.Availability = 'True'" + wStmt + " " +
            						 "ORDER BY Name";
            
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, companyID);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
            	Filemanager fm = new Filemanager();
            	
            	fm.setUserName(rs.getString("UserName"));
            	fm.setName(rs.getString("Name"));
            	fm.setType(rs.getString("Type").charAt(0));
                fm.setCompanyID(companyID);
                
                fm.setGender(rs.getString("Gender").toString());
                fm.setDOB(rs.getDate("DateOfBirth"));
                fm.setPhoneNumber(rs.getString("PhoneNumber"));
                fm.setEmail(rs.getString("Email"));
                fm.setAddress(rs.getString("Address"));
                fm.setNRIC(rs.getString("NRIC"));
                
                if((Integer) rs.getInt("DepartmentID") != null)
                {
                	fm.setDepartmentID(rs.getInt("DepartmentID"));
                }
                
                accList.add(fm);
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getCompanyFilemanagers Exception : " + ex.getMessage());
        }
        
        return accList;
    }
    
    // Retrieve details of managers from Account & Manager tables to display in view account page based on companyID
    
    public ArrayList<Account> getCompanyManagers(int companyID, String wStmt) throws Exception {
    	ArrayList<Account> accList = new ArrayList<Account>();
    	
        try {
            String selectStatement = "SELECT * FROM account a " +
            						 "INNER JOIN manager m ON a.UserName = m.UserName " +
            						 "WHERE CompanyID = ? AND a.Availability = 'True'" + wStmt + " " +
            						 "ORDER BY Name";
            
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, companyID);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
            	Manager mg = new Manager();
            	
            	mg.setUserName(rs.getString("UserName"));
            	mg.setName(rs.getString("Name"));
            	mg.setType(rs.getString("Type").charAt(0));
                mg.setCompanyID(companyID);
                
                mg.setGender(rs.getString("Gender").toString());
                mg.setDOB(rs.getDate("DateOfBirth"));
                mg.setPhoneNumber(rs.getString("PhoneNumber"));
                mg.setEmail(rs.getString("Email"));
                mg.setAddress(rs.getString("Address"));
                mg.setNRIC(rs.getString("NRIC"));
                
                if((Integer) rs.getInt("DepartmentID") != null)
                {
                	mg.setDepartmentID(rs.getInt("DepartmentID"));
                }
                
                accList.add(mg);
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getCompanyManagers Exception : " + ex.getMessage());
        }
        
        return accList;
    }
    
    // Update account password in Account table
    
    public void updateAccountPassword(String userName, String password) throws Exception {
        
        try {
        	
            String updateStatement = "UPDATE account SET Password=? WHERE UserName = ?";
            PreparedStatement prepStmt = con.prepareStatement(updateStatement);
            prepStmt.setString(1, password);
            prepStmt.setString(2, userName);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("updateAccountPassword Exception : " + ex.getMessage());
        }

    }
    
    // Update account type in Account table
    
    public void updateAccountType(String userName, char type) throws Exception {
        
        try {
        	
            String updateStatement = "UPDATE account SET Type=? WHERE UserName = ?";
            PreparedStatement prepStmt = con.prepareStatement(updateStatement);
            prepStmt.setString(1, Character.toString(type));
            prepStmt.setString(2, userName);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("updateAccountType Exception : " + ex.getMessage());
        }

    }
    
    // Update availability column in Account table to 'False'
    
    public void updateAccountAvailability(String userName) throws Exception {
        
        try {
        	
            String updateStatement = "UPDATE account SET Availability = 'False' WHERE UserName = ?";
            PreparedStatement prepStmt = con.prepareStatement(updateStatement);
            prepStmt.setString(1, userName);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("updateAccountAvailability Exception : " + ex.getMessage());
        }

    }
    
    // Update timeIn column in Account table
    
    public void updateLoginTime(String userName, String loginTime) throws Exception {
        
        try {
        	
            String updateStatement = "UPDATE account SET TimeIn = ? WHERE UserName = ?";
            PreparedStatement prepStmt = con.prepareStatement(updateStatement);
            
            java.util.Date now = sdf.parse(loginTime);
            Timestamp ts = new Timestamp(now.getTime());
            
            prepStmt.setTimestamp(1, ts);
            prepStmt.setString(2, userName);
            
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("updateLoginTime Exception : " + ex.getMessage());
        }

    }
    
    // Update timeOut column in Account table
    
    public void updateLogoutTime(String userName, String logoutTime) throws Exception {
        
        try {
        	
            String updateStatement = "UPDATE account SET TimeOut = ? WHERE UserName = ?";
            PreparedStatement prepStmt = con.prepareStatement(updateStatement);
            
            java.util.Date now = sdf.parse(logoutTime);
            Timestamp ts = new Timestamp(now.getTime());
            
            prepStmt.setTimestamp(1, ts);
            prepStmt.setString(2, userName);
            
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("updateLogoutTime Exception : " + ex.getMessage());
        }

    }
    
    // Update userPattern column in Account table
    
    public void updateUserPattern(String userName, String userPattern) throws Exception {
        
        try {
        	
            String updateStatement = "UPDATE account SET UserPattern = ? WHERE UserName = ?";
            PreparedStatement prepStmt = con.prepareStatement(updateStatement);            
            prepStmt.setString(1, userPattern);
            prepStmt.setString(2, userName);
            
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("updateUserPattern Exception : " + ex.getMessage());
        }

    }
    
    // Delete entry from Account table
    
    public void deleteAccount(String userName) throws Exception {
        
        try {
        	
            String deleteStatement = "DELETE FROM account WHERE UserName = ?";
            PreparedStatement prepStmt = con.prepareStatement(deleteStatement);
            prepStmt.setString(1, userName);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("deleteAccount Exception : " + ex.getMessage());
        }

    }

} // End Of Class
