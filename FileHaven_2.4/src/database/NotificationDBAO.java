package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import model.Account;
import model.Employee;
import model.Manager;
import model.Notification;

public class NotificationDBAO {
	
	private static final boolean String = false;
	Connection con;
	private boolean conFree = true;

	// Database configuration
//	 public static String dbdriver = "com.mysql.jdbc.Driver";
//
//	    public static String url = "jdbc:mysql://filehaven.ch30tsalfl52.ap-southeast-1.rds.amazonaws.com:3306/filehaven";
//	    public static String username = "filehaven";
//	    public static String password = "filehaven";
//	 
//	    public static String url = "jdbc:mysql://localhost/FileHaven";
//	    public static String username = "root";
//	    public static String password = "";
	    
	
	public NotificationDBAO() throws Exception {
		try {
			DB db = new DB();
	    	con = db.getConnection();

		} catch (Exception ex) {
			System.out.println("Exception in File Haven: " + ex);
			throw new Exception("Couldn't open connection to database: "
					+ ex.getMessage());
		}
	}
	
	public boolean insertEmployeeNotification(String message,boolean messageRead,Account sender,Employee employees)
	{
		boolean status = false;
		try {
			DepartmentDBAO d1;
			ArrayList<String> userNames = new ArrayList<String>();
			try {
				d1 = new DepartmentDBAO();
				userNames=d1.getEmployeesUserName(employees,sender.getCompanyID());
				d1.remove();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(int i=0;i<employees.getEmployees().size();i++)
			{
			String selectStatement = "INSERT INTO notification (Message,MessageDateTime,MessageRead,Sender,UserName) VALUES (?,?,?,?,?);";
			System.out.println(selectStatement);

			getConnection();
			
			java.util.Date date= new java.util.Date();
//			System.out.println(new Timestamp(date.getTime()));
			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setString(1, message);
			prepStmt.setTimestamp(2, new Timestamp(date.getTime()));
			prepStmt.setBoolean(3, messageRead);
			prepStmt.setString(4, sender.getUserName());
			prepStmt.setString(5, userNames.get(i));
			

			if (prepStmt.executeUpdate() == 1) {
				status = true;
				prepStmt.close();
				releaseConnection();
				System.out.println("Successfully sent a notification to all intended recipients");
			}
				
			
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			releaseConnection();

		}
		return status;
	}
	
	public boolean insertManagerNotification(String message,boolean messageRead,Account sender,Manager employees)
	{
		boolean status = false;
		try {
			ArrayList<String> managerUserName = new ArrayList<String>();
			ManagerDBAO m1;
			try {
				m1 = new ManagerDBAO();
				managerUserName=m1.getManagersUserName(employees, sender.getCompanyID());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			for(int i=0;i<employees.getManagers().size();i++)
			{
			String selectStatement = "INSERT INTO notification (Message,MessageDateTime,MessageRead,Sender,UserName) VALUES (?,?,?,?,?);";
			System.out.println(selectStatement);

			getConnection();
			
			java.util.Date date= new java.util.Date();
//			System.out.println(new Timestamp(date.getTime()));
			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setString(1, message);
			prepStmt.setTimestamp(2, new Timestamp(date.getTime()));
			prepStmt.setBoolean(3, messageRead);
			prepStmt.setString(4, sender.getUserName());
			prepStmt.setString(5, managerUserName.get(i));
			

			if (prepStmt.executeUpdate() == 1) {
				status = true;
				prepStmt.close();
				releaseConnection();
				System.out.println("Successfully sent a notification to all intended recipients");
			}
				
			
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			releaseConnection();

		}
		return status;
	}
	
	public boolean updateNotification(String user)
	{
		
		boolean status = false;
		try {
			
			String selectStatement = "UPDATE notification SET MessageRead=1 WHERE UserName=?";
			System.out.println(selectStatement);

			getConnection();
			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setString(1, user);
			

			if (prepStmt.executeUpdate() == 1) {
				
				status = true;
				prepStmt.close();
				releaseConnection();
				System.out.println("Updated Notification");
			}
				
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			releaseConnection();

		}
		return status;
	}
	
	public ArrayList<Notification> getNotifications(Account currentUser){
		int notification=0;
		ArrayList<Notification> latestNotifications = new ArrayList<Notification>();
		
		try {
			String selectStatement = "SELECT * From notification WHERE UserName=? AND MessageRead=0";
			getConnection();

			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setString(1,  currentUser.getUserName());

			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				Notification notifications = new Notification();
				notifications.setMessage(rs.getString("Message"));
				notifications.setMessageDateTime(rs.getTimestamp("MessageDateTime"));
				notifications.setSender(rs.getString("Sender"));

				latestNotifications.add(notifications);

			}
			prepStmt.close();
			releaseConnection();

		} catch (SQLException ex) {
			releaseConnection();
			ex.printStackTrace();

		}
		
		
		return latestNotifications;
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
	
//	public static void main(String args[])
//	{
//		try {
//			NotificationDBAO notification = new NotificationDBAO();
//			System.out.println(notification.updateNotification("alonso"));
////			for (int i=0;i<notification.getNotifications("alonso",3).size();i++)
////			{
////				System.out.println(notification.getNotifications("alonso", 3).get(i).getMessage());
////			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	


}
