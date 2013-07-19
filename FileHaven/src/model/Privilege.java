package model;

public class Privilege {
	private int fileID;
	private int departmentID;
	
	
	public Privilege() {
	}


	public int getFileID() {
		return fileID;
	}


	public void setFileID(int fileID) {
		this.fileID = fileID;
	}


	public int getDepartmentID() {
		return departmentID;
	}


	public void setDepartmentID(int departmentID) {
		this.departmentID = departmentID;
	}


	public Privilege(int fileID, int departmentID) {
		super();
		this.fileID = fileID;
		this.departmentID = departmentID;
	}

 
}
