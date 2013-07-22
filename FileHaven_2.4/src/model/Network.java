package model;

public class Network {
	
	private int id;
	private String ipAddressStart;
	private String ipAddressEnd;
	private String subnetMask;
	private int companyID;
	
	
	public Network() {
		
	}
	

	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIpAddressStart() {
		return ipAddressStart;
	}
	public void setIpAddressStart(String ipAddressStart) {
		this.ipAddressStart = ipAddressStart;
	}
	public String getIpAddressEnd() {
		return ipAddressEnd;
	}
	public void setIpAddressEnd(String ipAddressEnd) {
		this.ipAddressEnd = ipAddressEnd;
	}
	public String getSubnetMask() {
		return subnetMask;
	}
	public void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
	}
	public int getCompanyID() {
		return companyID;
	}
	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}
	
	

}
