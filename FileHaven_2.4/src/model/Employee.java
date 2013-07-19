package model;

import java.util.ArrayList;
import java.util.Date;

public class Employee extends Account{
	
	// Attributes
	
	private String userName;
	private String gender;
	private Date DOB;
	private String phoneNumber;
	private String email;
	private String address;
	private String NRIC;
	private int departmentID;
	private ArrayList<String> employees;
	
	// Constructors
	
	public Employee()
	{
		
	}
	
	public Employee(String userName, String gender, Date DOB, String phoneNumber, String email, String address, String NRIC, int departmentID)
	{
		this.userName = userName;
		this.gender = gender;
		this.DOB = DOB;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.address = address;
		this.NRIC = NRIC;
		this.departmentID = departmentID;
	}
	
	// Getters & Setters
	
	public String getUserName() {
		return userName;
	}
	public ArrayList<String> getEmployees() {
		return employees;
	}

	public void setEmployees(ArrayList<String> employees) {
		this.employees = employees;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getDOB() {
		return DOB;
	}
	public void setDOB(Date dOB) {
		this.DOB = dOB;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNRIC() {
		return NRIC;
	}
	public void setNRIC(String NRIC) {
		this.NRIC = NRIC;
	}
	public int getDepartmentID() {
		return departmentID;
	}
	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}

} // End Of Class
