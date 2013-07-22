package model;

public class Permission {
	
	// Attributes
	
	private int ID;
	private String startTime;
	private String endTime;
	private String extendStart;
	private String extendEnd;
	private String grantBy;
	private String userName;
	private int companyID;
	
	// Constructors
	
	public Permission() {
		
	}
	
	public Permission(int ID, String startTime, String endTime, String extendStart, String extendEnd, String userName, int companyID) {
		this.ID = ID;
		this.startTime = startTime;
		this.endTime = endTime;
		this.extendStart = extendStart;
		this.extendEnd = extendEnd;
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getExtendStart() {
		return extendStart;
	}

	public void setExtendStart(String extendStart) {
		this.extendStart = extendStart;
	}

	public String getExtendEnd() {
		return extendEnd;
	}

	public void setExtendEnd(String extendEnd) {
		this.extendEnd = extendEnd;
	}
	
	public String getGrantBy() {
		return grantBy;
	}

	public void setGrantBy(String grantBy) {
		this.grantBy = grantBy;
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

	public void setcompanyID(int companyID) {
		this.companyID = companyID;
	}

} // End Of Class
