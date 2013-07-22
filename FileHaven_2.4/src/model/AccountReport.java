package model;

public class AccountReport {
	
	// Attributes
	
	private int ID;
	private String details;
	private String logTime;
	private String userName;
	private int companyID;
	
	// Constructors
	
	public AccountReport() {
		
	}
	
	public AccountReport(int ID, String details, String logTime, String userName, int companyID) {
		this.ID = ID;
		this.details = details;
		this.logTime = logTime;
		this.userName = userName;
		this.companyID = companyID;
	}
	
	// Getters & Setters

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getLogTime() {
		return logTime;
	}

	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public int getCompanyID() {
		return companyID;
	}

	public void setCompanyID(int companyID) {
		companyID = companyID;
	}
	
} // End Of Class
