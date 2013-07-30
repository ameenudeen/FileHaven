package model;

import java.sql.Date;

public class FileReport {
	
	private int ID;
	private String IPAddress;
	private Date downloadedDate;
	private String status;
	private int fileID;
	private String UserName;
	private String downloadedTime;
	private String FileName;
	private int CompanyID;
	private String AccountID;
	
	
	
		
	
	
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public int getCompanyID() {
		return CompanyID;
	}
	public void setCompanyID(int companyID) {
		CompanyID = companyID;
	}
	public Date getDownloadedDate() {
		return downloadedDate;
	}
	public void setDownloadedDate(Date downloadedDate) {
		this.downloadedDate = downloadedDate;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getIPAddress() {
		return IPAddress;
	}
	public void setIPAddress(String iPAddress) {
		IPAddress = iPAddress;
	}
	public String getDownloadedTime() {
		return downloadedTime;
	}
	public void setDownloadedTime(String downloadedTime) {
		this.downloadedTime = downloadedTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getFileID() {
		return fileID;
	}
	public void setFileID(int fileID) {
		this.fileID = fileID;
	}
	public String getAccountID() {
		return AccountID;
	}
	public void setAccountID(String accountID) {
		AccountID = accountID;
	}
	
	
	
	
	

}
