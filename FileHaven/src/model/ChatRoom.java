package model;

import java.util.ArrayList;

import database.ChatDBAO;

public class ChatRoom {
	private ChatDBAO controller;
	
	public int getClientRid() {
		return clientRid;
	}

	public void setClientRid(int clientRid) {
		this.clientRid = clientRid;
	}
	private int id;
	private int clientRid;
	private String description;
	private String startTime;
	private ArrayList<Chatlog> chatlogList=new ArrayList<Chatlog>();
	private ArrayList<ChatInv> prepInvList=new ArrayList<ChatInv>();
	private ArrayList<String> userList=new ArrayList<String>();
	
	public ChatRoom(ChatDBAO controller){
		this.controller=controller;
	}
	
	public void storeChatMessage(Chatlog cl){
		try {
			controller.storeChatMessage(this,cl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*public void sendInvitation(){
		try {
			for(int i=0;i<prepInvList.size();i++){
				controller.sendInvitation(this,prepInvList.get(i));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public ArrayList<Chatlog> getChatlogList() {
		return chatlogList;
	}
	public ArrayList<ChatInv> getPrepInvList() {
		return prepInvList;
	}
	public ArrayList<String> getUserList(){
		return userList;
	}
	
	public void refreshChatMsg(){
		try {
			controller.refreshChatMessage(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
