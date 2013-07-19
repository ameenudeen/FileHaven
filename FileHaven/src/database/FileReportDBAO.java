package database;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

import model.Department;
import model.FileReport;
import model.Manager;

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
	            System.out.println("Exception in AccountDBAO: " + ex);
	            throw new Exception("Couldn't open connection to database: " +
	                    ex.getMessage());
	        }
	}
	
	public ArrayList<String> getReports(int companyId)
	{
		ArrayList<String> m1 = new ArrayList<String>();
		ArrayList<String> report=new ArrayList<String>();

		try {
			String selectStatement = "SELECT * FROM department d INNER JOIN privilege p ON d.ID=p.DepartmentID INNER JOIN file r ON r.ID=p.FileID INNER JOIN filereport f ON r.ID=f.FileID WHERE CompanyID=?";
			getConnection();

			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setInt(1, companyId);

			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				Department department = new Department();
				department.setId(rs.getInt("ID"));
				department.setDepartmentName(rs.getString("Name"));
				m1.add(department.getDepartmentName());

			}
			
			ArrayList<String> gasList = m1;
			Set<String> uniqueGas = new HashSet<String>(gasList);
			System.out.println("Unique gas count: " + uniqueGas.toString());
			
			report.clear();
			report.addAll(uniqueGas);
			
			prepStmt.close();
			releaseConnection();

		} catch (SQLException ex) {
			releaseConnection();
			ex.printStackTrace();

		}
		return report;
	}
	
	public Boolean insertFileReport(FileReport file,String user){
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
			
		String insertStatement = "INSERT INTO filereport (IPAddress, DownloadedDate,DownloadedTime, Status, FileID,UserName) VALUES(?,?,?,?,?,?)";
		getConnection();
		PreparedStatement prepStmt = con.prepareStatement(insertStatement);
        prepStmt.setString(1, file.getIPAddress());
        prepStmt.setString(2, sdf.format(currentTime));
        prepStmt.setString(3, sdf1.format(currentTime));
        prepStmt.setString(4, file.getStatus());
        prepStmt.setInt(5, file.getFileID());
        prepStmt.setString(6, user);
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
	
	public ArrayList<FileReport> getFileReports(int departmentID)
	{
		ArrayList<FileReport> m1 = new ArrayList<FileReport>();
		

		try {
			String selectStatement = "SELECT * FROM filereport r INNER JOIN file f ON r.FileID=f.ID INNER JOIN privilege p ON f.ID=p.FileID INNER JOIN department d ON p.DepartmentID=d.ID WHERE departmentID=?";
			getConnection();

			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setInt(1, departmentID);

			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				FileReport f1 = new FileReport();
				f1.setID(rs.getInt("ID"));
				f1.setIPAddress(rs.getString("IPAddress"));
				f1.setDownloadedTime(rs.getString("DownloadedTime"));
				f1.setStatus(rs.getString("Status"));
				f1.setAccountID(rs.getString("AccountID"));
				f1.setFileName(rs.getString("Name"));
				f1.setDownloadedDate(rs.getDate("DownloadedDate"));
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
	
	public static void main(String args[])
	{
		
		final Date currentTime = new Date();

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		final SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm:ss");


		// Give it to me in GMT time.
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		sdf1.setTimeZone(TimeZone.getTimeZone("GMT"));
		System.out.println("GMT date: " + sdf.format(currentTime));
		System.out.println("GMT time: " + sdf1.format(currentTime));
		
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		DateFormat dateFormat1 = new SimpleDateFormat("HH:MM:SS");
//		Date date = new Date();
//		System.out.println(dateFormat.format(date));
//		System.out.println(dateFormat1.format(date));
		
//		
//		try {
//			FileReportDBAO f1 = new FileReportDBAO();
//			ArrayList<String> reports=f1.getReports(3);
//			for(int i=0;i<reports.size();i++)
//			{
//				System.out.println(reports.get(i));
//			}
//			
//		
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
	}

}
