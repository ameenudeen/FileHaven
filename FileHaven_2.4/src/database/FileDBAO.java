package database;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import model.Files;
import model.Privilege;


public class FileDBAO{
	
	private Connection con;
	private ArrayList<Files> fileList;
	
	public FileDBAO() throws Exception {
	    try {
	    	DB db = new DB();
	    	con = db.getConnection();
	    } catch (Exception ex) {
	        System.out.println("Exception in FileDB: " + ex);
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
	
	
	public ArrayList<Files> getFileList(String username) throws Exception{
	    fileList = new ArrayList<Files>();
	    ArrayList<Files> temp=new ArrayList<Files>();
        
	    try {
	        String selectStatement = "select * " + "from file";
	        PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	        ResultSet rs = prepStmt.executeQuery();
	        
	        while (rs.next()) {
	        	Files file = new Files();
	        	file.setFileID(rs.getInt(rs.findColumn("ID")));
	        	file.setFileName(rs.getString(rs.findColumn("Name")));
	        	file.setFileExtension(rs.getString(rs.findColumn("Extension")));
	        	file.setAccountID(rs.getString(rs.findColumn("AccountID")));
	        	file.setFileSize(rs.getDouble(rs.findColumn("Size")));
	        	file.setFileUploadedTime(rs.getString(rs.findColumn("UploadedTime")));
	        	file.setFileDeletedTime(rs.getString(rs.findColumn("DeletedTime")));
	        	file.setEncrypted(rs.getString(rs.findColumn("Encrypted")));
	        	file.setPrivilege(new PrivilegeDBAO().getPrivilege(file.getFileID()));
	        	file.setHash(rs.getString(rs.findColumn("Hash")));
            	
            	
	        	fileList.add(file);
	        }
	        
	        prepStmt.close();
	       
	        AccountDBAO adb=new AccountDBAO();
	    	ArrayList<String> nameList=adb.getAccountNames(adb.getAccountDetails(username).getCompanyID());
	    	for(int i=0;i<fileList.size();i++){
	    		for(String name:nameList)
	    			if(fileList.get(i).getAccountID().equals(name)){
	    				temp.add(fileList.get(i));
	    			}
	    	}
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return temp;
	}
	public ArrayList<Files> getFileList() throws Exception{
	    fileList = new ArrayList<Files>();
	    
	    try {
	        String selectStatement = "select * " + "from file";
	        PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	        ResultSet rs = prepStmt.executeQuery();
	        
	        while (rs.next()) {
	        	Files file = new Files();
	        	file.setFileID(rs.getInt(rs.findColumn("ID")));
	        	file.setFileName(rs.getString(rs.findColumn("Name")));
	        	file.setFileExtension(rs.getString(rs.findColumn("Extension")));
	        	file.setAccountID(rs.getString(rs.findColumn("AccountID")));
	        	file.setFileSize(rs.getDouble(rs.findColumn("Size")));
	        	file.setFileUploadedTime(rs.getString(rs.findColumn("UploadedTime")));
	        	file.setFileDeletedTime(rs.getString(rs.findColumn("DeletedTime")));
	        	file.setEncrypted(rs.getString(rs.findColumn("Encrypted")));
	        	file.setPrivilege(new PrivilegeDBAO().getPrivilege(file.getFileID()));
	        	file.setHash(rs.getString(rs.findColumn("Hash")));
            	
	        	fileList.add(file);
	        }
	        
	        prepStmt.close();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return fileList;
	}

	public Files getFile(int Id) throws Exception{

    try {
        String selectStatement = "select * " + "from file where ID=?";
        PreparedStatement prepStmt = con.prepareStatement(selectStatement);
        prepStmt.setInt(1, Id);
        ResultSet rs = prepStmt.executeQuery();
        
        if(rs.next()) {
        	Files file = new Files();
        	file.setFileID(rs.getInt(rs.findColumn("ID")));
        	file.setFileName(rs.getString(rs.findColumn("Name")));
        	file.setFileExtension(rs.getString(rs.findColumn("Extension")));
        	file.setAccountID(rs.getString(rs.findColumn("AccountID")));
        	file.setFileSize(rs.getDouble(rs.findColumn("Size")));
        	file.setFileUploadedTime(rs.getString(rs.findColumn("UploadedTime")));
        	file.setFileDeletedTime(rs.getString(rs.findColumn("DeletedTime")));
        	file.setPrivilege(new PrivilegeDBAO().getPrivilege(file.getFileID()));
        	file.setEncrypted(rs.getString(rs.findColumn("Encrypted")));
        	file.setHash(rs.getString(rs.findColumn("Hash")));
            prepStmt.close();
            
        	return file;
        }

        prepStmt.close();
        
        return null;
    } catch (SQLException ex) {
        ex.printStackTrace();
        
        return null;
    }
}
    public boolean createFile(Files file) throws Exception{
    	try{
    		 String stmt="INSERT INTO file(ID,Name,Extension,Size,AccountID,UploadedTime,DeletedTime,Encrypted,Hash) VALUES (?,?,?,?,?,?,?,?,?)";
    		 PreparedStatement prepStmt = con.prepareStatement(stmt);
    		 ArrayList<Files> files=getFileList();
    		 if(files.isEmpty()){
    			 file.setFileID(0);
    		 }
    		 else{
    			 Collections.sort(files);
    			 file.setFileID(files.get(files.size()-1).getFileID()+1);
    		 }
    		 prepStmt.setInt(1, file.getFileID());
             prepStmt.setString(2, file.getFileName());
             prepStmt.setString(3, file.getFileExtension());
             prepStmt.setDouble(4, file.getFileSize());
             prepStmt.setString(5, file.getAccountID());
             prepStmt.setString(6, file.getFileUploadedTime());
             prepStmt.setString(7, file.getFileDeletedTime());
             prepStmt.setString(8,file.getEncrypted());
             prepStmt.setString(9,file.getHash());
             
             boolean success=true;
             
             if( prepStmt.executeUpdate()==1){
            	if(file.getPrivilege()!=null){
	            	for(Privilege p:file.getPrivilege()){
	            		p.setFileID(file.getFileID());
	            		if(!new PrivilegeDBAO().createPrivilege(p)){
		            		success=false;
	            		}
	            	}
            	}
            	if(!new FileDataDBAO().createFileData(file.getFileID(), file.getData())){
            		success=false;
            	}
            	if(success){
            		prepStmt.close();
            		return true;
            	}
            	//new PrivilegeDBAO().deletePrivilege(file.getFileID());
            	//new FileDataDBAO().deleteFileData(file.getFileID());
            	deleteFile(file);
            	prepStmt.close();
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
    
    public boolean deleteFile(Files file) throws Exception{
    	try{
    		String stmt="DELETE FROM file WHERE ID=?";
    		 PreparedStatement prepStmt = con.prepareStatement(stmt);
             prepStmt.setInt(1, file.getFileID());
            if( prepStmt.executeUpdate()==1){
            	prepStmt.close();
            	if(new FileDataDBAO().deleteFileData(file.getFileID())){
            	if(new PrivilegeDBAO().deletePrivilege(file.getFileID()))
            		return true;
            	return false;
            	}
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
    public boolean updateFile(Files file,String column,String val) throws Exception{
    	try{
    		String stmt="UPDATE file SET "+column+"=? WHERE ID=?";
    		PreparedStatement prepStmt = con.prepareStatement(stmt);
    		prepStmt.setString(1,val);
    		prepStmt.setInt(2, file.getFileID());
    		
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
}