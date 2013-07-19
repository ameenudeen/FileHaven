package database;


import java.sql.*;
import java.util.*;

import model.Privilege;


public class PrivilegeDBAO {
	
    private Connection con;
    private ArrayList<Privilege> privileges;
    
    public PrivilegeDBAO() throws Exception {
        try {
        	DB db = new DB();
        	con = db.getConnection();
        } catch (Exception ex) {
            System.out.println("Exception in PrivilegeDB: " + ex);
            throw new Exception("Couldn't open connection to database: " +
                    ex.getMessage());
        }
    }
    
    public void remove() {
        try {
            if (con!=null) con.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
   
    public ArrayList<Privilege> getPrivilege(int FileID) throws Exception{
    	privileges=new ArrayList<Privilege>();
    	
        try {
            String selectStatement = "select * " + "from privilege where FileID=?";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setInt(1, FileID);
            ResultSet rs = prepStmt.executeQuery();
            
            while(rs.next()) {
            	Privilege privilege=new Privilege();
            	privilege.setFileID(FileID);
            	privilege.setDepartmentID(rs.getInt(rs.findColumn("DepartmentID")));
            	
                privileges.add(privilege);
            }

            prepStmt.close();
            
            return privileges;
        } catch (SQLException ex) {
            ex.printStackTrace();
            
            return null;
        }
    }
        public boolean createPrivilege(Privilege privilege) throws Exception{
        	try{
        		String stmt="INSERT INTO privilege(FileID,DepartmentID) VALUES (?,?)";
        		 PreparedStatement prepStmt = con.prepareStatement(stmt);
        		 prepStmt.setInt(1, privilege.getFileID());
                 prepStmt.setInt(2, privilege.getDepartmentID());
               
                if( prepStmt.executeUpdate()==1){
                	prepStmt.close();
                	return true;
                }
                prepStmt.close();
                return false;
        	}
        	catch(SQLException ex){
        		ex.printStackTrace();
        		return false;
        	}
        }
        
        public boolean deletePrivilege(int FileID) throws Exception{
        	try{
        		String stmt="DELETE FROM privilege WHERE FileID=?";
        		 PreparedStatement prepStmt = con.prepareStatement(stmt);
                 prepStmt.setInt(1, FileID);
                 System.out.println(stmt);
                if( prepStmt.executeUpdate()==1){
                	return true;
                }
                prepStmt.close();
                return false;
        	}
        	catch(SQLException ex){
        		ex.printStackTrace();
        		return false;
        	}
        }
}
