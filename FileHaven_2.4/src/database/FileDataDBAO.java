package database;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class FileDataDBAO {
	private Connection con;
	
	public FileDataDBAO() throws Exception {
	    try {
	    	DB db = new DB();
	    	con = db.getConnection();
	    } catch (Exception ex) {
	        System.out.println("Exception in FileDataDB: " + ex);
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
	
	public InputStream getFileData(int id) throws Exception{
		InputStream is=null;
		try {
	        String selectStatement = "select * " + "from filedata where  ID=?";
	        System.out.println(selectStatement);
	        PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	        prepStmt.setInt(1,id);
	        ResultSet rs = prepStmt.executeQuery();
	        
	        while (rs.next()) {
			Blob blob=rs.getBlob(rs.findColumn("Data"));
	    	is=blob.getBinaryStream();
	        }
	        prepStmt.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return is;
	}
	
    public boolean createFileData(int id,InputStream is) throws Exception{
    	try{
    		 String stmt="INSERT INTO filedata(ID,Data) VALUES (?,?)";
    		 PreparedStatement prepStmt = con.prepareStatement(stmt);
 
    		 prepStmt.setInt(1, id);
             prepStmt.setBinaryStream(2, is, is.available());
             
             
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
    
    public boolean deleteFileData(int id) throws Exception{
    	try{
    		String stmt="DELETE FROM filedata WHERE ID=?";
    		System.out.println(stmt);
    		 PreparedStatement prepStmt = con.prepareStatement(stmt);
             prepStmt.setInt(1, id);
            if( prepStmt.executeUpdate()==1){
            	prepStmt.close();
            	if(new PrivilegeDBAO().deletePrivilege(id))
            		return true;
            	return false;
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
