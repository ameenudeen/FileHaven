package model;

import java.io.InputStream;
import java.util.ArrayList;

public class Files implements Comparable<Files>{
	private int fileID;
	private String fileName;
	private String fileExtension;
	private Double fileSize;
	private String accountID;
	private String fileDeletedTime;
	private String fileUploadedTime;
	private ArrayList<Privilege> privilege;
	private InputStream data;
	private String encrypted;
	private String hash;
	
	public Files() {
	}


	public Files(int fileID, String fileName, String fileExtension,
			Double fileSize, String accountID, String fileDeletedTime,
			String fileUploadedTime, ArrayList<Privilege> privilege,
			InputStream data, String encrypted, String hash) {
		super();
		this.fileID = fileID;
		this.fileName = fileName;
		this.fileExtension = fileExtension;
		this.fileSize = fileSize;
		this.accountID = accountID;
		this.fileDeletedTime = fileDeletedTime;
		this.fileUploadedTime = fileUploadedTime;
		this.privilege = privilege;
		this.data = data;
		this.encrypted = encrypted;
		this.hash = hash;
	}







	public String getHash() {
		return hash;
	}







	public void setHash(String hash) {
		this.hash = hash;
	}







	public String getEncrypted() {
		return encrypted;
	}




	public void setEncrypted(String encrypted) {
		this.encrypted = encrypted;
	}




	public InputStream getData() {
		return data;
	}



	public void setData(InputStream data) {
		this.data = data;
	}



	public int compareTo(Files file){
		return this.getFileID()-file.getFileID();
	}

	
	public ArrayList<Privilege> getPrivilege() {
		return privilege;
	}



	public void setPrivilege(ArrayList<Privilege> privilege) {
		this.privilege = privilege;
	}



	public int getFileID() {
		return fileID;
	}

	public void setFileID(int fileID) {
		this.fileID = fileID;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public Double getFileSize() {
		return fileSize;
	}

	public void setFileSize(Double fileSize) {
		this.fileSize = fileSize;
	}

	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public String getFileDeletedTime() {
		return fileDeletedTime;
	}

	public void setFileDeletedTime(String fileDeletedTime) {
		this.fileDeletedTime = fileDeletedTime;
	}

	public String getFileUploadedTime() {
		return fileUploadedTime;
	}

	public void setFileUploadedTime(String fileUploadedTime) {
		this.fileUploadedTime = fileUploadedTime;
	}




	
	
}
