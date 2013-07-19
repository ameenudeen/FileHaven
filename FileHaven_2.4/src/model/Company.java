package model;

public class Company {
	
	// Attributes
	
	private int companyID;
	private String companyName;
	private String address;
	private int storageSpace;
	private String companyLogo;
	
	// Constructors
	
	public Company()
	{
		
	}
	
	public Company(int companyID, String companyName)
	{
		this.companyID=companyID;
		this.companyName=companyName;
	}
	
	public Company(int companyID, String companyName, String address, int storageSpace, String companyLogo)
	{
		this.companyID = companyID;
		this.companyName = companyName;
		this.address = address;
		this.storageSpace = storageSpace;
		this.companyLogo = companyLogo;
	}
	
	// Getters & Setters
	
	public int getCompanyID() {
		return companyID;
	}
	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getStorageSpace() {
		return storageSpace;
	}
	public void setStorageSpace(int storageSpace) {
		this.storageSpace = storageSpace;
	}
	public String getCompanyLogo() {
		return companyLogo;
	}
	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

} // End Of Class
