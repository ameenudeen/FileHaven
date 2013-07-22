package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	   
	   public static void main(String args[])
	   {
		   NetworkDBAO n1;
		try {
			n1 = new NetworkDBAO();
			Account a1 = new Account();
			   a1.setCompanyID(8);
			   System.out.println(n1.getNetworkDetails(a1).getIpAddressStart());
			   System.out.println(n1.getNetworkDetails(a1).getIpAddressEnd());
			   System.out.println(n1.getNetworkDetails(a1).getSubnetMask());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
	   }

}
