package model;

import database.ChatDBAO;

public class ChatInv {
	private ChatDBAO controller;
	
	private String desc;
	private String timestamp;
	private String user;
	private int clientIid;
	private String roomTitle;
	private int roomId;
	private int id;
	private String sender;
	
	public ChatInv(ChatDBAO controller){
		this.controller=controller;
	}
	
	public ChatInv() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
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
	public int getClientIid() {
		return clientIid;
	}
	public void setClientIid(int clientIid) {
		this.clientIid = clientIid;
	}

	public String getRoomTitle() {
		return roomTitle;
	}

	public void setRoomTitle(String roomTitle) {
		this.roomTitle = roomTitle;
	}
	
	/*public void sendInv(ChatRoom cr){
		try {
			controller.sendInvitation(cr, this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public void updateStatus(int status, String userid){
		String temp="";
		switch(status){
		case(0):temp="pending";break;
		case(1):temp="accepted";break;
		case(2):temp="denied";break;
		}
		try {
			controller.setInvStatus(roomId, this, userid, temp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
}
