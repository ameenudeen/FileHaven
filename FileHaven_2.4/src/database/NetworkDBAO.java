package database;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Account;
import model.Network;

public class NetworkDBAO {
	 Connection con;
	 
	 public NetworkDBAO() throws Exception {
	        try {
	        	DB db = new DB();
		    	con = db.getConnection();
	        } catch (Exception ex) {
	            System.out.println("Exception in NetworkDBAO: " + ex);
	            throw new Exception("Couldn't open connection to database: " +
	                    ex.getMessage());
	        }
	    }
	    
	    public void remove() {
	        try {
	            con.close();
	        } catch (SQLException ex) {
	            System.out.println(ex.getMessage());
	        }
	    }
	    
	    
	   public Network getNetworkDetails(Account currentUser)
	   {
		   Network network = new Network();
		   try {
	            String selectStatement = "SELECT * FROM network WHERE companyID=?";
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	            prepStmt.setInt(1, currentUser.getCompanyID());
	            ResultSet rs = prepStmt.executeQuery();
	            
	            while (rs.next()) {
	            	network.setId(rs.getInt("ID"));
	            	network.setIpAddressStart(rs.getString("IPAddressStart"));
	            	network.setIpAddressEnd(rs.getString("IPAddressEnd"));
	            	network.setSubnetMask(rs.getString("SubnetMask"));
	            	network.setCompanyID(rs.getInt("CompanyID"));
	            	
	            }
	            
	            rs.close();
	            prepStmt.close();
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
		   
		   return network;
	        
	   }
	   
	   public static long ipToLong(InetAddress ip) {
	        byte[] octets = ip.getAddress();
	        long result = 0;
	        for (byte octet : octets) {
	            result <<= 8;
	            result |= octet & 0xff;
	        }
	        return result;
	    }
	   
	    // Insert values into Network table
	    
	    public void insertNetwork(String IPAddressStart, String IPAddressEnd, String subnetMask, int companyID) throws Exception {
	        
	        try {
	        	
	            String insertStatement = "INSERT INTO network (IPAddressStart, IPAddressEnd, subnetMask, CompanyID) VALUES(?, ?, ?, ?)";
	            PreparedStatement prepStmt = con.prepareStatement(insertStatement);
	            prepStmt.setString(1, IPAddressStart);
	            prepStmt.setString(2, IPAddressEnd);
	            prepStmt.setString(3, subnetMask);
	            prepStmt.setInt(4, companyID);
	            prepStmt.executeUpdate();
	            
	        } catch (SQLException ex) {
	            throw new Exception("insertNetwork Exception : " + ex.getMessage());
	        }

	    }
	    
	    // Retrieve details from Network table
	    
	    public Network getNetworkDetails(int ID) throws Exception {
	    	Network nw = new Network();
	    	
	        try {
	            String selectStatement = "SELECT * FROM network where ID = ? ";
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	            prepStmt.setInt(1, ID);
	            ResultSet rs = prepStmt.executeQuery();
	            
	            while (rs.next()) {
	            	nw.setId(ID);
	            	nw.setIpAddressStart((rs.getString("IPAddressStart")));
	            	nw.setIpAddressEnd(rs.getString("IPAddressEnd"));
	            	nw.setSubnetMask(rs.getString("SubnetMask"));
	                nw.setCompanyID(rs.getInt("CompanyID"));
	            }
	            
	            rs.close();
	            prepStmt.close();
	        } catch (SQLException ex) {
	            throw new Exception("getNetworkDetails Exception : " + ex.getMessage());
	        }
	        
	        return nw;
	    }
	    
	    // Retrieve ID from Network table by companyID
	    
	    public ArrayList<Integer> getCompanyNetworkID(int companyID) throws Exception {
	    	ArrayList<Integer> IDList = new ArrayList<Integer>();
	    	
	        try {
	            String selectStatement = "SELECT ID FROM network WHERE CompanyID = ? ORDER BY ID";
	            PreparedStatement prepStmt = con.prepareStatement(selectStatement);
	            prepStmt.setInt(1, companyID);
	            ResultSet rs = prepStmt.executeQuery();
	            
	            while (rs.next()) {
	            	int ID = rs.getInt("ID");
	                
	                IDList.add(ID);
	            }
	            
	            rs.close();
	            prepStmt.close();
	        } catch (SQLException ex) {
	            throw new Exception("getCompanyNetworkID Exception : " + ex.getMessage());
	        }
	        
	        return IDList;
	    }
	    
	    // Update details in Network table
	    
	    public void updateNetworkDetails(int ID, String IPAddressStart, String IPAddressEnd, String subnetMask) throws Exception {
	        
	        try {
	        	
	            String updateStatement = "UPDATE network SET IPAddressStart=?, IPAddressEnd=?, SubnetMask=? WHERE ID = ?";
	            PreparedStatement prepStmt = con.prepareStatement(updateStatement);
	            prepStmt.setString(1, IPAddressStart);
	            prepStmt.setString(2, IPAddressEnd);
	            prepStmt.setString(3, subnetMask);
	            prepStmt.setInt(4, ID);
	            prepStmt.executeUpdate();
	            
	        } catch (SQLException ex) {
	            throw new Exception("updateNetworkDetails Exception : " + ex.getMessage());
	        }

	    }
	    
	    // Delete entry from Network table
	    
	    public void deleteNetwork(int ID) throws Exception {
	        
	        try {
	        	
	            String deleteStatement = "DELETE FROM network WHERE ID = ?";
	            PreparedStatement prepStmt = con.prepareStatement(deleteStatement);
	            prepStmt.setInt(1, ID);
	            prepStmt.executeUpdate();
	            
	        } catch (SQLException ex) {
	            throw new Exception("deleteNetwork Exception : " + ex.getMessage());
	        }

	    }
	   
//	   public static void main(String args[])
//	   {
//		   NetworkDBAO n1;
//		try {
//			n1 = new NetworkDBAO();
//			Account a1 = new Account();
//			   a1.setCompanyID(8);
//			   System.out.println(n1.getNetworkDetails(a1).getIpAddressStart());
//			   System.out.println(n1.getNetworkDetails(a1).getIpAddressEnd());
//			   System.out.println(n1.getNetworkDetails(a1).getSubnetMask());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		   
//	   }

}
