package model;

public class Department {
	
	private int id;
	private String departmentName;
	private String departmentDescription;
	private String departmentLogo;
	


	public Department()
	{
		
	}
	
	public Department(int id,String departmentName, String departmentDescription)
	{
		this.id=id;
		this.departmentName=departmentName;
		this.departmentDescription=departmentDescription;
	}
	
	public String getDepartmentDescription() {
		return departmentDescription;
	}

	public void setDepartmentDescription(String departmentDescription) {
		this.departmentDescription = departmentDescription;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentLogo() {
		return departmentLogo;
	}

	public void setDepartmentLogo(String departmentLogo) {
		this.departmentLogo = departmentLogo;
	}
	
	

}
