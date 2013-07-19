package model;

public class Account {
	
	// Attributes
	
	private String userName;
	private String name;
	private String password;
	private char type;
	private String availability;
	private String creatorID;
	private String createdTime;
	private String timeIn;
	private String timeOut;
	private int companyID;
	private String userPattern;
	
	private int clientAid;
	
	// Constructors
	
	public Account() {
		
	}
	
	public Account(String userName, int companyID, char type, String availability, String creatorID) {
		this.userName = userName;
		this.companyID = companyID;
		this.type = type;
		this.availability = availability;
		this.creatorID = creatorID;
	}
	
	// Getters & Setters
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public char getType() {
		return type;
	}
	public void setType(char type) {
		this.type = type;
	}
	public String getAvailability() {
		return availability;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
	}
	public String getCreatorID() {
		return creatorID;
	}
	public void setCreatorID(String creatorID) {
		this.creatorID = creatorID;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getTimeIn() {
		return timeIn;
	}
	public void setTimeIn(String timeIn) {
		this.timeIn = timeIn;
	}
	public String getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}
	public int getCompanyID() {
		return companyID;
	}
	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}
	public String getUserPattern() {
		return userPattern;
	}
	public void setUserPattern(String userPattern) {
		this.userPattern = userPattern;
	}
	
	public int getClientAid() {
		return clientAid;
	}
	public void setClientAid(int clientAid) {
		this.clientAid=clientAid;
	}

} // End Of Class
