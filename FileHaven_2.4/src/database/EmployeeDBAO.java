package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Employee;
import model.Manager;

public class EmployeeDBAO {
	
	private static final boolean String = false;
	Connection con;
	private boolean conFree = true;

	// Database configuration
	 public static String dbdriver = "com.mysql.jdbc.Driver";
//	   
//	    public static String url = "jdbc:mysql://filehaven.ch30tsalfl52.ap-southeast-1.rds.amazonaws.com:3306/filehaven";
//	    public static String username = "filehaven";
//	    public static String password = "filehaven";
	 
//	    public static String url = "jdbc:mysql://localhost/FileHaven";
//	    public static String username = "root";
//	    public static String password = "";
	    

	public EmployeeDBAO() throws Exception {
		try {
			DB db = new DB();
	    	con = db.getConnection();

		} catch (Exception ex) {
			System.out.println("Exception in File Haven: " + ex);
			throw new Exception("Couldn't open connection to database: "
					+ ex.getMessage());
		}
	}
	
	public ArrayList<Employee> getDepartmentEmployees(int departmentId,int companyID) {
		ArrayList<Employee> m1 = new ArrayList<Employee>();

		try {
			String selectStatement = "SELECT * FROM employee m INNER JOIN account a ON m.UserName=a.UserName WHERE DepartmentID=? AND CompanyID=?";
			getConnection();

			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setInt(1, departmentId);
			prepStmt.setInt(2, companyID);

			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				Employee employee = new Employee();
				employee.setName(rs.getString(rs.findColumn("Name")));
				employee.setGender(rs.getString("Gender"));
				employee.setDOB(rs.getDate("DateOfBirth"));
				employee.setPhoneNumber(rs.getString("PhoneNumber"));
				employee.setEmail(rs.getString("Email"));
				employee.setAddress(rs.getString("NRIC"));
				employee.setDepartmentID(rs.getInt("DepartmentID"));
				

				m1.add(employee);

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
	
	public ArrayList<Employee> getAllEmployees(int companyId){
		ArrayList<Employee> m1 = new ArrayList<Employee>();
				
		try {
			String selectStatement =  "SELECT * FROM employee e INNER JOIN account a ON e.UserName=a.UserName WHERE CompanyID=? AND e.DepartmentID IS NULL";
			getConnection();

			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setInt(1, companyId);

			ResultSet rs = prepStmt.executeQuery();
			
		
			while (rs.next()) {
				Employee employee = new Employee();
				employee.setName(rs.getString(rs.findColumn("Name")));
				employee.setGender(rs.getString("Gender"));
				employee.setDOB(rs.getDate("DateOfBirth"));
				employee.setPhoneNumber(rs.getString("PhoneNumber"));
				employee.setEmail(rs.getString("Email"));
				employee.setAddress(rs.getString("NRIC"));
				employee.setDepartmentID(rs.getInt("DepartmentID"));
				
				m1.add(employee);
					
							
			}
			prepStmt.close();
			releaseConnection();
					

		} catch (SQLException ex) {
			ex.printStackTrace();
			releaseConnection();

			
		}
		
		return m1;
	}
	
	public ArrayList<Employee> getSelectedEmployees(int companyId){
		ArrayList<Employee> m1 = new ArrayList<Employee>();
				
		try {
			String selectStatement =  "SELECT * FROM employee e INNER JOIN account a ON e.UserName=a.UserName WHERE CompanyID=? ";
			getConnection();

			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setInt(1, companyId);

			ResultSet rs = prepStmt.executeQuery();
			
		
			while (rs.next()) {
				Employee employee = new Employee();
				employee.setName(rs.getString(rs.findColumn("Name")));
				employee.setGender(rs.getString("Gender"));
				employee.setDOB(rs.getDate("DateOfBirth"));
				employee.setPhoneNumber(rs.getString("PhoneNumber"));
				employee.setEmail(rs.getString("Email"));
				employee.setAddress(rs.getString("NRIC"));
				employee.setDepartmentID(rs.getInt("DepartmentID"));
				
				m1.add(employee);
					
							
			}
			prepStmt.close();
			releaseConnection();
					

		} catch (SQLException ex) {
			ex.printStackTrace();
			releaseConnection();

			
		}
		
		return m1;
	}
	
	public boolean removeEmployeesFromDepartment(int departmentId,int companyID)
	{
		boolean status = false;
		try {
			ArrayList<Employee> employees =getDepartmentEmployees(departmentId,companyID);
			for(int i=0;i<employees.size();i++)
			{
				
			
			String selectStatement = "UPDATE employee e INNER JOIN account a ON e.UserName=a.UserName SET e.DepartmentID = NULL"
			 + " WHERE a.Name = ? AND a.CompanyID=?";
			System.out.println(selectStatement);

			getConnection();

			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setString(1, employees.get(i).getName());
			prepStmt.setInt(2, companyID);
			

			if (prepStmt.executeUpdate() == 1) {
				status = true;
				prepStmt.close();
				releaseConnection();
				System.out.println("Successfully Removed Employees from Department");
			}
				
			
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			releaseConnection();

		}
		return status;
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
//			EmployeeDBAO e1 = new EmployeeDBAO();
//		
//			System.out.println(e1.removeEmployeesFromDepartment(5, 3));		
//			for(int i=0;i<man.size();i++)
//			{
//				System.out.println("Hello"+man.get(i).getName());
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
// Insert values into Employee table
    
    public void insertEmployee(String userName, String gender, String dateOfBirth, String phoneNumber, String email, String address, String NRIC) throws Exception {
        
        try {
        	
            String insertStatement = "INSERT INTO employee (UserName, Gender, DateOfBirth, PhoneNumber, Email, Address, NRIC) VALUES(?, ?, ?, ?, ?, ?, ?)";
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
            throw new Exception("insertEmployee Exception : " + ex.getMessage());
        }

    }
    
    // Insert values into Employee table (Update.jsp) 
    
    public void insertEmployeeCopy(String userName, String gender, String dateOfBirth, String phoneNumber, String email, String address, String NRIC, int departmentID) throws Exception {
        
        try {
        	
            String insertStatement = "INSERT INTO employee (UserName, Gender, DateOfBirth, PhoneNumber, Email, Address, NRIC, DepartmentID) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
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
            throw new Exception("insertEmployeeCopy Exception : " + ex.getMessage());
        }

    }
    
    // Retrieve a single employee's details
    
    public Employee getEmployeeDetails(String userName) throws Exception {
        Employee Emp = new Employee();
        
        try {
            String selectStatement = "SELECT * FROM employee where userName = ?";
            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
            prepStmt.setString(1, userName);
            ResultSet rs = prepStmt.executeQuery();
            
            while (rs.next()) {
                Emp.setUserName(userName);
                Emp.setGender(rs.getString("Gender"));
                Emp.setDOB(rs.getDate("DateOfBirth"));
                Emp.setPhoneNumber(rs.getString("PhoneNumber"));
                Emp.setEmail(rs.getString("Email"));
                Emp.setAddress(rs.getString("Address"));
                Emp.setNRIC(rs.getString("NRIC"));
                Emp.setDepartmentID(rs.getInt("DepartmentID"));
            }
            
            rs.close();
            prepStmt.close();
        } catch (SQLException ex) {
            throw new Exception("getEmployeeDetails Exception : " + ex.getMessage());
        }
        
        return Emp;
    }
    
    // Update details in Employee table
    
    public void updateEmployeeDetails(String userName, String gender, String dateOfBirth, String phoneNumber, String email, String address, String NRIC) throws Exception {
        
        try {
        	
            String updateStatement = "UPDATE employee SET Gender=?, DateOfBirth=?, PhoneNumber=?, Email=?, Address=?, NRIC=? WHERE UserName = ?";
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
            throw new Exception("updateEmployeeDetails Exception : " + ex.getMessage());
        }

    }
    
    // Delete entry from Employee table
    
    public void deleteEmployee(String userName) throws Exception {
        
        try {
        	
            String deleteStatement = "DELETE FROM employee WHERE UserName = ?";
            PreparedStatement prepStmt = con.prepareStatement(deleteStatement);
            prepStmt.setString(1, userName);
            prepStmt.executeUpdate();
            
        } catch (SQLException ex) {
            throw new Exception("deleteEmployee Exception : " + ex.getMessage());
        }

    }
    
//    public static void main(String args[])
//    {
//    	EmployeeDBAO e1;
//		try {
//			e1 = new EmployeeDBAO();
//			e1.removeEmployeesFromDepartment(33, 8);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	
//    }

}
