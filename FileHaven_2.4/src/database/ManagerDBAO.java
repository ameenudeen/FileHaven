package database;

import java.sql.*;
import java.util.ArrayList;

import model.Account;
import model.Employee;
import model.Manager;

public class ManagerDBAO {

	private static final boolean String = false;
	Connection con;
	private boolean conFree = true;

	// Database configuration
	// Database configuration
//		 public static String dbdriver = "com.mysql.jdbc.Driver";
//		  
//		 
//		    public static String url = "jdbc:mysql://filehaven.ch30tsalfl52.ap-southeast-1.rds.amazonaws.com:3306/filehaven";
//		    public static String username = "filehaven";
//		    public static String password = "filehaven";		 
////		    public static String url = "jdbc:mysql://localhost/FileHaven";
////		    public static String username = "root";
////		    public static String password = "";

	public ManagerDBAO() throws Exception {
		try {
			DB db = new DB();
	    	con = db.getConnection();

		} catch (Exception ex) {
			System.out.println("Exception in ManagerDBAO: " + ex);
			throw new Exception("Couldn't open connection to database: "
					+ ex.getMessage());
		}
	}
	
	// Insert values into Manager table
    
    public void insertManager(String userName, String gender, String dateOfBirth, String phoneNumber, String email, String address, String NRIC) throws Exception {
        
        try {
        	
            String insertStatement = "INSERT INTO manager (UserName, Gender, DateOfBirth, PhoneNumber, Email, Address, NRIC) VALUES(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prepStmt = con.prepareStatement(insertStatement);
            prepStmt.setString(1, userName);
            prepStmt.setString(2, gender);
            prepStmt.setString(3, dateOfBirth);
            prepStmt.setString(4, phoneNumber);
            prepStmt.setString(5, email);
            prepStmt.setString(6, address);
            prepStmt.setString(7, NRIC);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("insertManager Exception : " + ex.getMessage());
        }

    }
    
    // Insert values into Manager table (Update.jsp) 
    
    public void insertManagerCopy(String userName, String gender, String dateOfBirth, String phoneNumber, String email, String address, String NRIC, int departmentID) throws Exception {
        
        try {
        	
            String insertStatement = "INSERT INTO manager (UserName, Gender, DateOfBirth, PhoneNumber, Email, Address, NRIC, DepartmentID) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prepStmt = con.prepareStatement(insertStatement);
            prepStmt.setString(1, userName);
            prepStmt.setString(2, gender);
            prepStmt.setString(3, dateOfBirth);
            prepStmt.setString(4, phoneNumber);
            prepStmt.setString(5, email);
            prepStmt.setString(6, address);
            prepStmt.setString(7, NRIC);
            prepStmt.setInt(8, departmentID);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("insertManagerCopy Exception : " + ex.getMessage());
        }

    }
    
    // Retrieve a single manager's details
    
    public Manager getManagerDetails(String userName) throws Exception {
        Manager Mg = new Manager();
        
        try {
            String selectStatement = "SELECT * FROM manager where UserName = ?";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setString(1, userName);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
                Mg.setUserName(userName); 
                Mg.setGender(rs.getString("Gender"));
                Mg.setDOB(rs.getDate("DateOfBirth"));
                Mg.setPhoneNumber(rs.getString("PhoneNumber"));
                Mg.setEmail(rs.getString("Email"));
                Mg.setAddress(rs.getString("Address"));
                Mg.setNRIC(rs.getString("NRIC"));
                Mg.setDepartmentID(rs.getInt("DepartmentID"));
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getManagerDetails Exception : " + ex.getMessage());
        }
        
        return Mg;
    }
    
    // Update details in Manager table
    
    public void updateManagerDetails(String userName, String gender, String dateOfBirth, String phoneNumber, String email, String address, String NRIC) throws Exception {
        
        try {
        	
            String updateStatement = "UPDATE manager SET Gender=?, DateOfBirth=?, PhoneNumber=?, Email=?, Address=?, NRIC=? WHERE UserName = ?";
            PreparedStatement prepStmt = con.prepareStatement(updateStatement);
            prepStmt.setString(1, gender);
            prepStmt.setString(2, dateOfBirth);
            prepStmt.setString(3, phoneNumber);
            prepStmt.setString(4, email);
            prepStmt.setString(5, address);
            prepStmt.setString(6, NRIC);
            prepStmt.setString(7, userName);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("updateManagerDetails Exception : " + ex.getMessage());
        }

    }
    
    // Delete entry from Manager table
    
    public void deleteManager(String userName) throws Exception {
        
        try {
        	
            String deleteStatement = "DELETE FROM manager WHERE UserName = ?";
            PreparedStatement prepStmt = con.prepareStatement(deleteStatement);
            prepStmt.setString(1, userName);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("deleteManager Exception : " + ex.getMessage());
        }

    }

	public ArrayList<Manager> getAllManagers(int companyId) {
		ArrayList<Manager> m1 = new ArrayList<Manager>();

		try {
			String selectStatement = "SELECT * FROM manager m INNER JOIN account a ON m.UserName=a.UserName WHERE CompanyID=? AND m.DepartmentID IS NULL";
			getConnection();

			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setInt(1, companyId);

			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				Manager manager = new Manager();
				manager.setUserName(rs.getString("UserName"));
				manager.setGender(rs.getString("Gender"));
				manager.setDOB(rs.getDate("DateOfBirth"));
				manager.setPhoneNumber(rs.getString("PhoneNumber"));
				manager.setEmail(rs.getString("Email"));
				manager.setAddress(rs.getString("Address"));
				manager.setNRIC(rs.getString("NRIC"));
				manager.setDepartmentID(rs.getInt("DepartmentID"));
				manager.setName(rs.getString("Name"));

				m1.add(manager);

			}
			prepStmt.close();
			releaseConnection();

		} catch (SQLException ex) {
			releaseConnection();
			ex.printStackTrace();

		}
		return m1;
	}
	
	public ArrayList<Manager> getSelectedManagers(int companyId) {
		ArrayList<Manager> m1 = new ArrayList<Manager>();

		try {
			String selectStatement = "SELECT * FROM manager m INNER JOIN account a ON m.UserName=a.UserName WHERE CompanyID=?";
			System.out.println(selectStatement);
			getConnection();

			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setInt(1, companyId);

			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				Manager manager = new Manager();
				manager.setUserName(rs.getString("UserName"));
				manager.setGender(rs.getString("Gender"));
				manager.setDOB(rs.getDate("DateOfBirth"));
				manager.setPhoneNumber(rs.getString("PhoneNumber"));
				manager.setEmail(rs.getString("Email"));
				manager.setAddress(rs.getString("Address"));
				manager.setNRIC(rs.getString("NRIC"));
				manager.setDepartmentID(rs.getInt("DepartmentID"));
				manager.setName(rs.getString("Name"));

				m1.add(manager);

			}
			prepStmt.close();
			releaseConnection();

		} catch (SQLException ex) {
			releaseConnection();
			ex.printStackTrace();

		}
		return m1;
	}
	
	public ArrayList<String> getManagersUserName(Manager manager,int companyID){

		ArrayList<String> userNames= new ArrayList<String>();
		try {
			
			for (int i = 0; i < manager.getManagers().size(); i++) {
			String sql = "SELECT UserName FROM account WHERE Name=? AND companyID=?";
			System.out.println(sql);
			getConnection();
			PreparedStatement prest = con.prepareStatement(sql);
			prest.setString(1, manager.getManagers().get(i));
			prest.setInt(2, companyID);
			ResultSet rs = prest.executeQuery();

			while (rs.next()) {
				userNames.add(rs.getString("UserName"));
			}
			
			prest.close();
			releaseConnection();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			releaseConnection();
		}

		return userNames;
	}
	
	public ArrayList<Manager> getDepartmentManagers(int departmentId,int companyID) {
		ArrayList<Manager> m1 = new ArrayList<Manager>();

		try {
			String selectStatement = "SELECT * FROM manager m INNER JOIN account a ON m.UserName=a.UserName WHERE DepartmentID=? AND CompanyID=?";
			getConnection();

			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setInt(1, departmentId);
			prepStmt.setInt(2, companyID);

			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				Manager manager = new Manager();
				manager.setUserName(rs.getString("UserName"));
				manager.setGender(rs.getString("Gender"));
				manager.setDOB(rs.getDate("DateOfBirth"));
				manager.setPhoneNumber(rs.getString("PhoneNumber"));
				manager.setEmail(rs.getString("Email"));
				manager.setAddress(rs.getString("Address"));
				manager.setNRIC(rs.getString("NRIC"));
				manager.setDepartmentID(rs.getInt("DepartmentID"));
				manager.setName(rs.getString("Name"));

				m1.add(manager);

			}
			prepStmt.close();
			releaseConnection();

		} catch (SQLException ex) {
			ex.printStackTrace();
			releaseConnection();
			return null;

		}
		return m1;
	}
	
	public boolean updateManagerDepartment(Manager manager,String departmentName,Account user) {

		boolean status = false;
		int departmentId = 0;

		try {
			DepartmentDBAO d1;
			try {
				d1 = new DepartmentDBAO();
				departmentId=d1.getDepartmentIDOfCompany(departmentName,user.getUserName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			ArrayList<String> managerUserName=getManagersUserName(manager,user.getCompanyID());
			for (int i = 0; i < manager.getManagers().size(); i++) {
				String sql = "UPDATE manager e INNER JOIN account a ON e.UserName=a.UserName SET e.DepartmentID ="+departmentId+" WHERE a.UserName = ?";
				System.out.println(sql);
				getConnection();
				PreparedStatement prest = con.prepareStatement(sql);
				prest.setString(1, managerUserName.get(i));

				if (prest.executeUpdate() == 1) {
					status = true;
					prest.close();
					releaseConnection();
					System.out.println("Successful in Manager");
				}

			}
		}

		catch (SQLException ex) {
			
			ex.printStackTrace();
			releaseConnection();
		}

		return status;
	}
	
	public int getDepartmentIDOfCompany(String departmentName, String user) {

		int id = 0;
		try {
			String sql = "SELECT ID FROM department d WHERE d.name='"
					+ departmentName
					+ "' AND CompanyID=(SELECT CompanyID From account a WHERE a.name='"
					+ user + "')";
			System.out.println(sql);
			getConnection();
			PreparedStatement prest = con.prepareStatement(sql);
			ResultSet rs = prest.executeQuery();

			while (rs.next()) {
				id = rs.getInt(rs.findColumn("ID"));
			}

			prest.close();
			releaseConnection();
		} catch (SQLException e) {
			// Sql Statement: use username instead of a.name
			e.printStackTrace();
			releaseConnection();
		}

		return id;
	}
	
	public boolean removeManagersFromDepartment(int departmentId,int companyID)
	{
		boolean status = false;
		try {
			ArrayList<Manager> manager =getDepartmentManagers(departmentId,companyID);
			for(int i=0;i<manager.size();i++)
			{
				
			
			String selectStatement = "UPDATE manager e INNER JOIN account a ON e.UserName=a.UserName SET e.DepartmentID = NULL"
			 + " WHERE a.Name = ? AND a.CompanyID=?";
			System.out.println(selectStatement);

			getConnection();

			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setString(1, manager.get(i).getName());
			prepStmt.setInt(2, companyID);
			

			if (prepStmt.executeUpdate() == 1) {
				status = true;
				prepStmt.close();
				releaseConnection();
				System.out.println("Successfully Removed managers from Department");
			}
				
			
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			releaseConnection();

		}
		return status;
	}
	
//	public boolean updateManagerssDepartment(Manager manager,
//			String departmentName, String user,Account currentUser) {
//
//		boolean status = false;
//		 
//		
//			int departmentId = getDepartmentIDOfCompany(departmentName, user);
//			ArrayList<Manager> a1 = getSelectedManagers(currentUser.getCompanyID());
//			System.out.println("test"+departmentId);
//			System.out.println("test2"+a1.get(0).getDepartment_DepartmentID());
//			try {
//			for (int i = 0; i < a1.size(); i++) {
//				
//
//				if (departmentId == a1.get(i).getDepartment_DepartmentID() && !manager.getManagers().get(i).equals(a1.get(i).getName())) {
//					// set it to null;
//
//					String sql = "UPDATE Manager e INNER JOIN account a ON e.AccountID=a.ID SET e.DepartmentID = NULL"
//							 + " WHERE a.Name = ?";
//					System.out.println(sql);
//					getConnection();
//					PreparedStatement prest = con.prepareStatement(sql);
//					System.out.println(a1.get(i).getName());
//					prest.setString(1, a1.get(i).getName());
//
//					if (prest.executeUpdate() == 1) {
//						status = true;
//						prest.close();
//						releaseConnection();
//						System.out.println("Successful in Managerss");
//					}
//				}
//
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			releaseConnection();
//			e.printStackTrace();
//			
//		}
//		
//				
//		return status;
//	}

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
//	
//		try {
//			ManagerDBAO m1 = new ManagerDBAO();
//			System.out.println(m1.removeManagersFromDepartment(39, 8));
////			System.out.println(test.size());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}

}
