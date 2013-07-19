package model;

import java.sql.Timestamp;

public class Notification {
	
	private int id;
	private String message;
	private Timestamp messageDateTime;
	private boolean messageRead;
	private String userName;
	private String sender;
	
	public Notification()
	{
		
	}
	
	
	public String getSender() {
		return sender;
	}


	public void setSender(String sender) {
		this.sender = sender;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Timestamp getMessageDateTime() {
		return messageDateTime;
	}
	public void setMessageDateTime(Timestamp messageDateTime) {
		this.messageDateTime = messageDateTime;
	}
	public boolean isMessageRead() {
		return messageRead;
	}
	public void setMessageRead(boolean messageRead) {
		messageRead = messageRead;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	

}
