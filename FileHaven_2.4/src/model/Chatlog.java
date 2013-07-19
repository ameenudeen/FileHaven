package model;

public class Chatlog {
	private byte[] chatMsgSigned;
	private String chatMsg;
	private String timestamp;
	private String user;
	private int id;
	private boolean integrityChk;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getChatMsg() {
		return chatMsg;
	}
	public void setChatMsg(String chatMsg) {
		this.chatMsg = chatMsg;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public byte[] getChatMsgSigned() {
		return chatMsgSigned;
	}
	public void setChatMsgSigned(byte[] chatMsgSigned) {
		this.chatMsgSigned = chatMsgSigned;
	}
	public boolean isIntegrityChk() {
		return integrityChk;
	}
	public void setIntegrityChk(boolean integrityChk) {
		this.integrityChk = integrityChk;
	}
	
	
}
