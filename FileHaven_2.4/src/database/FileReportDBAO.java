 package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import model.Account;
import model.Department;
import model.FileReport;
import model.Files;

public class FileReportDBAO {
	
	Connection con;
	private boolean conFree = true;

	// Database configuration
//	public static String url = "jdbc:mysql://filehaven.ch30tsalfl52.ap-southeast-1.rds.amazonaws.com:3306/filehaven";
//	public static String dbdriver = "com.mysql.jdbc.Driver";
//	public static String username = "filehaven";
//	public static String password = "filehaven";

	public FileReportDBAO() throws Exception {
		  try {
	        	DB db = new DB();
		    	con = db.getConnection();
	        } catch (Exception ex) {
	            System.out.println("Exception in FileReportDBAO: " + ex);
	            throw new Exception("Couldn't open connection to database: " +
	                    ex.getMessage());
	        }
	}
	
	public ArrayList<FileReport> getReports(int companyId)
	{
		ArrayList<FileReport> m1 = new ArrayList<FileReport>();

		try {
			String selectStatement = "SELECT DISTINCT FileName,FileID FROM filereport WHERE CompanyID=?";
			getConnection();

			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setInt(1, companyId);

			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				FileReport f1 = new FileReport();
				
				f1.setFileName(rs.getString("FileName"));
				f1.setFileID(rs.getInt("FileID"));
				
				m1.add(f1);

			}
						
			
			prepStmt.close();
			releaseConnection();

		} catch (SQLException ex) {
			releaseConnection();
			ex.printStackTrace();

		}
		return m1;
	}
	
	public Boolean insertFileReport(FileReport file){
		boolean status=false;
		try{
		
			final Date currentTime = new Date();

			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			final SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm:ss");


			// Give it to me in GMT time.
			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
			sdf1.setTimeZone(TimeZone.getTimeZone("GMT"));
			System.out.println("GMT date: " + sdf.format(currentTime));
			System.out.println("GMT time: " + sdf1.format(currentTime));
			
		String insertStatement = "INSERT INTO filereport (IPAddress, DownloadedDate,DownloadedTime, Status, FileID,UserName,FileName,CompanyID,OwnerID) VALUES(?,?,?,?,?,?,?,?,?)";
		getConnection();
		PreparedStatement prepStmt = con.prepareStatement(insertStatement);
        prepStmt.setString(1, file.getIPAddress());
        prepStmt.setString(2, sdf.format(currentTime));
        prepStmt.setString(3, sdf1.format(currentTime));
        prepStmt.setString(4, file.getStatus());
        prepStmt.setInt(5, file.getFileID());
        prepStmt.setString(6,file.getUserName());
        prepStmt.setString(7, file.getFileName());
        prepStmt.setInt(8, file.getCompanyID());
        prepStmt.setString(9, file.getAccountID());
        if (prepStmt.executeUpdate() == 1) {
			status = true;
			prepStmt.close();
			releaseConnection();
			System.out.println("Successful in filereport");
		}

	} catch (SQLException ex) {
		releaseConnection();
		ex.printStackTrace();
	}
	return status;
       
     
	}
	
	public ArrayList<FileReport> getFileReports(int fileId)
	{
		ArrayList<FileReport> m1 = new ArrayList<FileReport>();
		try {
			String selectStatement = "SELECT * FROM filereport WHERE FileID=?";
			getConnection();

			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setInt(1, fileId);

			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				FileReport f1 = new FileReport();
				f1.setID(rs.getInt("ID"));
				f1.setIPAddress(rs.getString("IPAddress"));
				f1.setDownloadedTime(rs.getString("DownloadedTime"));
				f1.setStatus(rs.getString("Status"));
				f1.setFileID(rs.getInt("FileID"));
				f1.setUserName(rs.getString("UserName"));
				f1.setDownloadedDate(rs.getDate("DownloadedDate"));
				f1.setFileName(rs.getString("FileName"));
				f1.setCompanyID(rs.getInt("CompanyID"));
				f1.setAccountID(rs.getString("OwnerID"));
				m1.add(f1);

			}
			
			
			
			prepStmt.close();
			releaseConnection();

		} catch (SQLException ex) {
			releaseConnection();
			ex.printStackTrace();

		}
		return m1;
	}
	
	public ArrayList<FileReport> chartFileReports(int departmentID)
	{
		ArrayList<FileReport> m1 = new ArrayList<FileReport>();
		

		try {
			String selectStatement = "SELECT f.name,COUNT(f.ID) FROM filereport r INNER JOIN file f ON r.FileID=f.ID INNER JOIN privilege p ON f.ID=p.FileID INNER JOIN department d ON p.DepartmentID=d.ID WHERE departmentID=? GROUP BY f.name;";
			getConnection();

			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setInt(1, departmentID);

			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				FileReport f1 = new FileReport();
				f1.setFileName(rs.getString("f.name"));
				System.out.println(rs.getInt("COUNT(f.ID)"));
				m1.add(f1);

			}
			
			
			
			prepStmt.close();
			releaseConnection();

		} catch (SQLException ex) {
			releaseConnection();
			ex.printStackTrace();

		}
		return m1;
	}
	
	protected synchronized Connection getConnection() {
		while (conFree == false) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		conFree = false;
		notify();

		return con;
	}

	public void remove() {
		try {
			con.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	protected synchronized void releaseConnection() {
		while (conFree == true) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}

		conFree = true;
		notify();
	}
	
	public boolean updateFileReport(int id,String name) throws Exception{
    	try{
    		String stmt="UPDATE filereport SET FileName=? WHERE FileID=?";
    		PreparedStatement prepStmt = con.prepareStatement(stmt);
    		prepStmt.setString(1,name);
    		prepStmt.setInt(2, id);
    		
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
	
//	public static void main(String args[])
//	{
//		FileReportDBAO f1;
//		try {
//			f1 = new FileReportDBAO();
//			ArrayList<FileReport> test= f1.getReports(4);
//			for(int i=0;i<test.size();i++)
//			{
//				System.out.println(test.get(i).getFileID());
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
}
