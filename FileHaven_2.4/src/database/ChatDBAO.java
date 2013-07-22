package database;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import model.Account;
import model.ChatInv;
import model.ChatRoom;
import model.ChatSession;
import model.Chatlog;

public class ChatDBAO {

	private Connection con;

	public ChatDBAO() throws Exception {
		try {
			DB db = new DB();
			con = db.getConnection();
		} catch (Exception ex) {
			System.out.println("Exception in FileDB: " + ex);
			throw new Exception("Couldn't open connection to database: "
					+ ex.getMessage());
		}
	}

	public void init() throws Exception {

	}

	public void remove() {
		try {
			if (con != null)
				con.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void initChatSession(int Id) throws Exception {

	}

	public boolean createChatRoom(String roomtitle,String userID) throws Exception {
		PreparedStatement iChatroomstmt = null;
		PreparedStatement sMaxIDstmt = null;
		PreparedStatement iChatinvstmt = null;
		try {
			String iChatroom = "INSERT INTO chatroom(roomTitle,creatorID,startTime) VALUES (?,?,NOW());";
			String sMaxID = "SELECT MAX(chatroomID) AS curID FROM chatroom;";
			String iChatinv = "INSERT INTO chatinvitation(chatroomID,userID,InvDesc,Status,timestamp,senderID) " +
					"VALUES (?,?,?,'accepted',NOW(),?)" ;
			
			con.setAutoCommit(false);
			iChatroomstmt = con.prepareStatement(iChatroom);
			sMaxIDstmt = con.prepareStatement(sMaxID);
			iChatinvstmt = con.prepareStatement(iChatinv);
			

			iChatroomstmt.setString(1, roomtitle);
			iChatroomstmt.setString(2, userID);
			iChatroomstmt.executeUpdate();
			
			ResultSet rs=sMaxIDstmt.executeQuery();
			int curID=-1;
			if(rs.next()){
				curID=rs.getInt("curID");
			}
			
			
			iChatinvstmt.setInt(1, curID);
			iChatinvstmt.setString(2, userID);
			iChatinvstmt.setString(3, roomtitle);
			iChatinvstmt.setString(4, userID);
			iChatinvstmt.executeUpdate();

			con.commit();

		} catch (SQLException ex) {
			ex.printStackTrace();
	        if (con != null) {
	            try {
	                System.err.print("Transaction is being rolled back");
	                con.rollback();
	            } catch(SQLException excep) {
	            	excep.printStackTrace();
	            }
	        }
		}
		finally {
	        if (iChatroomstmt != null) {
	        	iChatroomstmt.close();
	        }
	        if (sMaxIDstmt != null) {
	        	sMaxIDstmt.close();
	        }
	        if (iChatinvstmt != null) {
	        	iChatinvstmt.close();
	        }
	        con.setAutoCommit(true);
	        
	    }
		return true;

	}

	public int storeChatMessage(ChatRoom cr, Chatlog cl) throws Exception {
		try {
			String stmt = "INSERT INTO chatmessages(chatroomID,userID,message,timestamp,msgsign) " +
					"VALUES (?,?,?,?,?)";
			PreparedStatement prepStmt = con.prepareStatement(stmt,Statement.RETURN_GENERATED_KEYS);
			prepStmt.setInt(1, cr.getId());
			prepStmt.setString(2, cl.getUser());
			prepStmt.setString(3, cl.getChatMsg());
			prepStmt.setString(4, cl.getTimestamp());
			prepStmt.setBytes(5, cl.getChatMsgSigned());
			

			int primaryKey=-1;
			if (prepStmt.executeUpdate() == 1) {
				ResultSet rs = prepStmt.getGeneratedKeys();  
			    rs.next();  
			    primaryKey = rs.getInt(1);  
			}
			prepStmt.close();

			return primaryKey;

		} catch (SQLException ex) {
			ex.printStackTrace();
			return -1;
		}

	}
	
	public boolean setMsgUnread(String userid, int chatmsgid) throws Exception {
		try {
			String stmt = "INSERT INTO chatunread(userID,chatmsgID,status) VALUES (?,?,?)";
			PreparedStatement prepStmt = con.prepareStatement(stmt);
			prepStmt.setString(1, userid);
			prepStmt.setInt(2, chatmsgid);
			prepStmt.setString(3, "unread");
			

			if (prepStmt.executeUpdate() == 1) {
				prepStmt.close();
			}
			prepStmt.close();

			return true;

		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}

	}
	
	public boolean setMsgRead(String userid, int chatmsgid) throws Exception {
		try {
			String stmt = "DELETE FROM chatunread WHERE userID=? AND chatmsgID=?";
			PreparedStatement prepStmt = con.prepareStatement(stmt);
			prepStmt.setString(1, userid);
			prepStmt.setInt(2, chatmsgid);
			

			if (prepStmt.executeUpdate() == 1) {
				prepStmt.close();
			}
			prepStmt.close();

			return true;

		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}

	}
	
	public boolean checkMsgUnread(String userid, int chatmsgid) throws Exception {
		try {
			String stmt = "SELECT count(*) FROM chatunread WHERE userID=? AND chatmsgID=?";
			PreparedStatement prepStmt = con.prepareStatement(stmt);
			prepStmt.setString(1, userid);
			prepStmt.setInt(2, chatmsgid);
			
			boolean unread=false;
			ResultSet rs = prepStmt.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)!=0){
					unread=true;
				}
			}
			prepStmt.close();

			return unread;

		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}

	}

	public boolean checkRoomMsgUnread(String userid, int chatroomid) throws Exception {
		try {
			String stmt = "SELECT count(*) FROM chatunread cu " +
					"INNER JOIN chatmessages cm " +
					"ON cm.chatmsgID=cu.chatmsgID " +
					"WHERE cu.userID=? AND cm.chatroomID=?";
			PreparedStatement prepStmt = con.prepareStatement(stmt);
			prepStmt.setString(1, userid);
			prepStmt.setInt(2, chatroomid);
			
			boolean unread=false;
			ResultSet rs = prepStmt.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)!=0){
					unread=true;
				}
			}
			prepStmt.close();

			return unread;

		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}

	}
	
	public boolean leaveChatRoom(ChatRoom cr,String username) throws Exception {
		PreparedStatement prepStmt=null;
		PreparedStatement prepStmt2=null;
		try {
			String stmt = "DELETE FROM chatinvitation WHERE chatroomid=? AND userid=?";
			String stmt2 = "DELETE FROM chatunread WHERE userID=? AND chatmsgID=?";
			
			con.setAutoCommit(false);
			prepStmt = con.prepareStatement(stmt);
			prepStmt2 = con.prepareStatement(stmt2);

			prepStmt.setInt(1, cr.getId());
			prepStmt.setString(2, username);
			prepStmt2.setInt(1, cr.getId());
			prepStmt2.setString(2, username);
			

			prepStmt.executeUpdate();
			prepStmt2.executeUpdate();

			con.commit();

		} catch (SQLException ex) {
			ex.printStackTrace();
	        if (con != null) {
	            try {
	                System.err.print("Transaction is being rolled back");
	                con.rollback();
	            } catch(SQLException excep) {
	            	excep.printStackTrace();
	            }
	        }
		}
		finally{
			if (prepStmt != null) {
				prepStmt.close();
	        }
	        if (prepStmt2 != null) {
	        	prepStmt2.close();
	        }

	        con.setAutoCommit(true);
		}
		return true;
	}
	
	public boolean refreshChatMessage(ChatRoom cr,String username) throws Exception {

		try {

			String selectStatement = "select * from chatmessages cm inner join chatpin cp " +
					"on cm.userID=cp.userName where cm.chatroomID=?;";
			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setInt(1, cr.getId());
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				Chatlog temp = new Chatlog();

				temp.setId(rs.getInt("chatmsgID"));

				boolean logExist = false;
				for (int i = 0; i < cr.getChatlogList().size(); i++) {
					if (cr.getChatlogList().get(i).getId() == temp.getId()) {
						logExist = true;
						break;
					}
				}
				if (!logExist) {
					temp.setUser(rs.getString("cm.userID"));
					temp.setTimestamp(rs.getString("cm.timestamp"));
					temp.setChatMsg(rs.getString("cm.message"));
					
					/* import encoded public key */
					byte[] encKey = rs.getBytes("cp.pubkey"); 

		            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);

		            KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
		            PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);

		            /* input the signature bytes */
		            byte[] sigToVerify = rs.getBytes("cm.msgsign");

		            /* create a Signature object and initialize it with the public key */
		            Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
		            sig.initVerify(pubKey);

		            /* Update and verify the data */

		            sig.update((rs.getInt("cm.chatroomid")+""+rs.getString("cm.userid")+""+
		            		rs.getString("cm.message")+""+rs.getString("cm.timestamp")).getBytes());

		            boolean verifies = sig.verify(sigToVerify);
		            
		            temp.setIntegrityChk(verifies);
		            
		            boolean unread=checkMsgUnread(username, temp.getId());
		            temp.setUnread(unread);
		            if(unread){
		            	setMsgRead(username, temp.getId());
		            }
		            
					cr.getChatlogList().add(temp);
				}
			}

			prepStmt.close();
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public boolean refreshChatRoom(ChatSession cs, String userid)
			throws Exception {

		try {
			String selectStatement = "select * from chatroom cr inner join chatinvitation ci "
					+ "on cr.ChatroomID=ci.ChatroomID where ci.status='accepted' and ci.userid=?";
			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setString(1, userid);
			ResultSet rs = prepStmt.executeQuery();

			// cs.getChatroomList().clear();

			while (rs.next()) {
				ChatRoom temp = new ChatRoom(this);

				temp.setId(rs.getInt("chatroomID"));

				boolean roomExist = false;
				for (int i = 0; i < cs.getChatroomList().size(); i++) {
					if (cs.getChatroomList().get(i).getId() == temp.getId()) {
						roomExist = true;
						break;
					}
				}

				if (!roomExist) {
					temp.setClientRid(cs.getCurrentRid());
					temp.setDescription(rs.getString("RoomTitle"));
					temp.setStartTime(rs.getString("StartTime"));

					cs.getChatroomList().add(temp);
					cs.incrementCurRid();
				}
			}

			prepStmt.close();
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}

	}

	// TODO
	public boolean refreshInvitation(ChatSession cs, String userid)
			throws Exception {

		try {
			String selectStatement = "select * from chatroom cr inner join chatinvitation ci "
					+ "on cr.ChatroomID=ci.ChatroomID where ci.status='pending' and ci.userid=?";
			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setString(1, userid);
			ResultSet rs = prepStmt.executeQuery();

			cs.getChatInvList().clear();
			cs.setCurrentIid(0);

			while (rs.next()) {
				ChatInv temp = new ChatInv(this);

				temp.setId(rs.getInt("chatinvID"));

				/*
				 * boolean invExist=false; for(int
				 * i=0;i<cs.getChatInvList().size();i++){
				 * if(cs.getChatInvList().get(i).getId()==temp.getId()){
				 * invExist=true; break; } }
				 */

				// if(!invExist){
				temp.setClientIid(cs.getCurrentIid());
				temp.setDesc(rs.getString("InvDesc"));
				temp.setTimestamp(rs.getString("Timestamp"));
				temp.setRoomTitle(rs.getString("RoomTitle"));
				temp.setRoomId(rs.getInt("ChatroomId"));
				temp.setSender(rs.getString("SenderId"));

				cs.getChatInvList().add(temp);
				cs.incrementCurIid();
				// }
			}

			prepStmt.close();
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}

	}

	// TODO SendInvServ
	public boolean sendInvitation(ChatRoom cr, ChatInv ci, String status) throws Exception {
		try {
			String stmt = "INSERT INTO chatinvitation(chatroomID,userID,InvDesc,Status,timestamp,senderID) "
					+ "VALUES (?,?,?,?,NOW(),?)";
			PreparedStatement prepStmt = con.prepareStatement(stmt);
			prepStmt.setInt(1, cr.getId());
			prepStmt.setString(2, ci.getUser());
			prepStmt.setString(3, ci.getDesc());
			prepStmt.setString(4, "pending");
			prepStmt.setString(5, ci.getSender());

			if (prepStmt.executeUpdate() == 1) {
				prepStmt.close();
			}
			prepStmt.close();

			return true;

		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}

	}

	// TODO
	public boolean setInvStatus(int roomId, ChatInv ci, String user,
			String status) throws Exception {
		try {
			System.out.println(ci.getId()+" "+user);
			String stmt = "UPDATE chatinvitation "
					+ "SET Status=? WHERE chatinvid=? AND UserID=?";
			PreparedStatement prepStmt = con.prepareStatement(stmt);
			prepStmt.setString(1, status);
			prepStmt.setInt(2, ci.getId());
			prepStmt.setString(3, user);

			if (prepStmt.executeUpdate() == 1) {
				prepStmt.close();
			}
			prepStmt.close();

			return true;

		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}

	}

	public boolean refreshInvAccList(ChatSession cs, String userid, int chatroomid) {
		ArrayList<Account> accList=new ArrayList<Account>();
		
		try {
			
			String selectStatement = "select * from account " +
					"where companyid=(select companyid from account where username=?) " +
					"and username not in " +
					"( select a.username from account a " +
					"inner join chatinvitation ci on a.username = ci.userid " +
					"where a.companyid=(select companyid from account where username=?) " +
					"and (ci.status = 'accepted'or ci.status = 'pending') " +
					"and chatroomid=?)";
			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setString(1, userid);
			prepStmt.setString(2, userid);
			prepStmt.setInt(3, chatroomid);
			ResultSet rs = prepStmt.executeQuery();
			
			cs.getChatinvAccList().clear();
			cs.setCurrentAid(0);
			
			while (rs.next()) {
				
				Account temp=new Account();
				
				temp.setName(rs.getString("Name"));
				temp.setUserName(rs.getString("username"));
				//temp.setAvailability(rs.getString("type"));
				temp.setClientAid(cs.getCurrentAid());
				temp.setType(rs.getString("type").charAt(0));
				accList.add(temp);
				cs.getChatinvAccList().add(temp);
				cs.incrementCurAid();
			}
			
			prepStmt.close();
			return true;

		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}

	}
	
	

	public int checkPinExist(String userid) {
		try {
			String selectStatement = "select * from chatpin where username=?";
			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setString(1, userid);
			ResultSet rs = prepStmt.executeQuery();
			int result;
			
			if (rs.next()) {
				result=2;
			}
			else{
				result=1;
			}
			
			prepStmt.close();
			return result;
			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			return 0;
		}
	}
	
	public int authPassword(String userid,String password) {
		try {
			
			String selectStatement = "select * from account where username=? and password=?";
			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setString(1, userid);
			prepStmt.setString(2, password);
			ResultSet rs = prepStmt.executeQuery();
			int result;
			
			if (rs.next()) {
				result=2;
			}
			else{
				result=1;
			}
			
			prepStmt.close();
			return result;
			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	public boolean registerPin(String userid, byte[] key,
			byte[] sign, String pin2) {
		PreparedStatement chkExistStmt = null;
		PreparedStatement insertStmt = null;

		try {
			String chkExist = "SELECT * FROM chatpin where username=?;";
			String insert = "INSERT INTO chatpin(username,pin,pubkey,signature) " +
					"VALUES (?,?,?,?)" ;
			
			con.setAutoCommit(false);
			chkExistStmt = con.prepareStatement(chkExist);
			insertStmt = con.prepareStatement(insert);

			chkExistStmt.setString(1, userid);
			
			insertStmt.setString(1, userid);
			insertStmt.setString(2, pin2);
			insertStmt.setBytes(3, key);
			insertStmt.setBytes(4, sign);
			
			ResultSet rs=chkExistStmt.executeQuery();
			
			
			if(!rs.next()){
				insertStmt.executeUpdate();
				con.commit();
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
	        if (con != null) {
	            try {
	                System.err.print("Transaction is being rolled back");
	                con.rollback();
	            } catch(SQLException excep) {
	            	excep.printStackTrace();
	            }
	        }
		}
		finally {
			try{
		        if (chkExistStmt != null) {
		        	chkExistStmt.close();
		        }
		        if (insertStmt != null) {
		        	insertStmt.close();
		        }
		        con.setAutoCommit(true);
				}
			catch(Exception e){
				e.printStackTrace();
			}
	    }
		return false;
	}

	public int authPin(String username,String pin) {
		// TODO Auto-generated method stub
		try {
			
			String selectStatement = "select * from chatpin where username=? and pin=?";
			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setString(1, username);
			prepStmt.setString(2, pin);
			ResultSet rs = prepStmt.executeQuery();
			int result;
			
			if (rs.next()) {
				result=2;
			}
			else{
				result=1;
			}
			
			prepStmt.close();
			return result;
			
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			return 0;
		}
	}
	
	public String getTimestamp(){
		try {
			String date="";
			String selectStatement = "SELECT NOW()";
			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			ResultSet rs = prepStmt.executeQuery();
			if(rs.next()){
				date=rs.getString("NOW()");
			}
			prepStmt.close();
			return date;
		} catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}

	public ArrayList<String> getChatUser(ChatRoom cr) {
		// TODO Auto-generated method stub
		try {
			ArrayList<String> tempList=new ArrayList<String>();
			String selectStatement = "select * from chatinvitation where chatroomID=?";
			PreparedStatement prepStmt = con.prepareStatement(selectStatement);
			prepStmt.setInt(1, cr.getId());
			ResultSet rs = prepStmt.executeQuery();
			
			
			while (rs.next()) {
				tempList.add(rs.getString("UserID"));
				
			}
			
			return tempList;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

}