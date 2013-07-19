package model;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.util.ArrayList;

import database.ChatDBAO;

public class ChatSession {
	private ChatDBAO controller;
	private int activeRid;
	private int activeIid;
	private int currentRid=0; //Room id
	private int currentIid=0; //Invitation id
	private int currentAid=0; //User id

	private String pin="";
	private PrivateKey privateKey;
	private PublicKey publicKey; 
	private Signature dsa;
	private byte[] bufferedMsg;
	
	private ArrayList<ChatRoom> chatroomList=new ArrayList<ChatRoom>();
	private ArrayList<ChatInv> chatinvList=new ArrayList<ChatInv>();
	private ArrayList<Account> chatinvAccList=new ArrayList<Account>();
	
	private static int useridtemp=0;
	
	public ChatSession(){

		System.out.println("New chatsession created");
		try {
			controller=new ChatDBAO();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static int getUserIdTemp(){
		return useridtemp;
	}
	public static void userIdTempIncre(){
		useridtemp++;
	}
	
	public int getCurrentRid() {
		return currentRid;
	}

	public void setCurrentRid(int currentRid) {
		this.currentRid = currentRid;
	}
	
	public void incrementCurRid() {
		this.currentRid++;
	}
	
	public int getCurrentIid() {
		return currentIid;
	}

	public void setCurrentIid(int currentIid) {
		this.currentIid = currentIid;
	}

	public void incrementCurIid() {
		this.currentIid++;
	}

	public ChatDBAO getController() {
		return controller;
	}

	public void setController(ChatDBAO controller) {
		this.controller = controller;
	}

	public int getActiveRid() {
		return activeRid;
	}

	public void setActiveRid(int activeRid) {
		this.activeRid = activeRid;
	}

	public ArrayList<ChatRoom> getChatroomList() {
		return chatroomList;
	}

	public ArrayList<ChatInv> getChatInvList() {
		return chatinvList;
	}
	
	public ArrayList<Account> getChatinvAccList() {
		return chatinvAccList;
	}
	
	public ChatSession(ChatDBAO controller){
		this.controller=controller;
	}
	
	public void refreshChatRoom(String userid) throws Exception{

		controller.refreshChatRoom(this,userid);

	}
	
	public void refreshChatInv(String userid) throws Exception{

		controller.refreshInvitation(this,userid);

	}
	
	public void refreshInvUser(String userid){
		
		int roomid=-1;
    	for(int i=0;i<getChatroomList().size();i++){
    		if(chatroomList.get(i).getClientRid()==activeRid){
    			roomid=chatroomList.get(i).getId();
    		}
    	}
		
		controller.refreshInvAccList(this, userid, roomid);
	}
	
	public void sendChatRoomInv(String username,String desc,String loggedUser) throws Exception{
		ChatInv temp=new ChatInv();

		
		temp.setUser(username);
		temp.setDesc(desc);
		temp.setSender(loggedUser);
		
		int roomindex=-1;
    	for(int i=0;i<getChatroomList().size();i++){
    		if(getChatroomList().get(i).getClientRid()==getActiveRid()){
    			roomindex=i;
    		}
    	}
    	
		controller.sendInvitation(getChatroomList().get(roomindex), temp, "pending");

	}
	
	public void createChatRoom(String roomname,String userid) throws Exception{
		controller.createChatRoom(roomname, userid);
	}

	public int getActiveIid() {
		return activeIid;
	}

	public void setActiveIid(int activeIid) {
		this.activeIid = activeIid;
	}

	public int getCurrentAid() {
		return currentAid;
	}

	public void setCurrentAid(int currentAid) {
		this.currentAid = currentAid;
	}

	public void incrementCurAid() {
		this.currentAid++;
	}

	public void setPin(String pin1) {
		this.pin = pin1;
	}
	
	public void generateKeyPairs(String username){
		try{
            /* Generate a key pair */

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed((username+""+pin).getBytes("us-ascii"));

            keyGen.initialize(1024, random);

            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey priv = pair.getPrivate();
            PublicKey pub = pair.getPublic();
            
            privateKey=priv;
            publicKey=pub;

            /* Now that all the data to be signed has been read in, 
                    generate a signature for it */

            /*byte[] realSig = dsa.sign();

            publicKey = pub.getEncoded();
            privateKey = priv.getEncoded();*/
            
            

        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
	}
	
	public void generateSignature(){
		/* Create a Signature object and initialize it with the private key */
		try{
	        dsa = Signature.getInstance("SHA1withDSA", "SUN"); 
	        dsa.initSign(privateKey);
        	
		}
		catch(Exception e){
			System.err.println("Caught exception " + e.toString());
		}
	}
	
	public byte[] generateSignedData(int chatroomID, String userID,String msg,String timestamp){
        /* Update and sign the data */
		try{
		    dsa.update((chatroomID+""+userID+""+msg+""+timestamp).getBytes());
		    
		    return dsa.sign();
			
	        /*InputStream msgStream=IOUtils.toInputStream(msg);
	        BufferedInputStream bufin = new BufferedInputStream(msgStream);
	        byte[] buffer = new byte[1024];
	        int len;
	        while (bufin.available() != 0) {
	            len = bufin.read(buffer);
	            dsa.update(buffer, 0, len);
	        };
	        
	        bufin.close();
	        bufferedMsg=buffer;*/
		}
		catch(Exception e){
			System.err.println("Caught exception " + e.toString());
			return null;
		}
	}
	
	public boolean pinIsEmpty(){
		return pin.isEmpty();
	}

	public int checkPinExist(String userid) {
		// TODO Auto-generated method stub
		int result=controller.checkPinExist(userid);
		return result;
	}
	
	public int authPassword(String userid,String password) {
		// TODO Auto-generated method stub
		int result=controller.authPassword(userid, password);
		return result;
	}
	
	public boolean registerPin(String pin2,String userid){
		try {
			return controller.registerPin(userid,publicKey.getEncoded(),dsa.sign(),pin2);
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public int authPin(String username, String pin2) {
		// TODO Auto-generated method stub
		int result=controller.authPin(username,pin2);
		return result;
	}

	public byte[] getBufferedMsg() {
		return bufferedMsg;
	}
	
	public String getTimestamp(){
		String date="";
		do{
			date=controller.getTimestamp();
		}while(date.isEmpty());
		
		return date;
	}
}
