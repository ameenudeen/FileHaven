package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;
import model.ChatSession;
import model.Chatlog;

/**
 * Servlet implementation class SendChatMsgServ
 */
public class SendChatMsgServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//Random Strings - used for testing servlet, not important
	//private java.util.Random r = new java.util.Random ();
	
    /**
     * Default constructor. 
     */
    public SendChatMsgServ() {
        // TODO Auto-generated constructor stub
    	
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ChatSession cs;
		if(request.getSession().getAttribute("chatsession")!=null){
			cs=(ChatSession) request.getSession().getAttribute("chatsession");
		}
		else {
			cs=new ChatSession();
		}
		
		int roomindex=-1;
    	for(int i=0;i<cs.getChatroomList().size();i++){
    		if(cs.getChatroomList().get(i).getClientRid()==cs.getActiveRid()){
    			roomindex=i;
    		}
    	}
		
		String msg=request.getParameter("msg");
		
    	Account loginUser=(Account) request.getSession().getAttribute("LoggedInUser"); 
    	String userid=loginUser.getUserName();
		
		if(roomindex>-1){
			cs.generateKeyPairs(userid);
			cs.generateSignature();
			
			Chatlog temp=new Chatlog();
			temp.setChatMsg(msg);
			temp.setUser(userid);
			temp.setTimestamp(cs.getTimestamp());
			temp.setChatMsgSigned(cs.generateSignedData(cs.getChatroomList().get(roomindex).getId(),
					userid,msg,temp.getTimestamp()));
			cs.getChatroomList().get(roomindex).storeChatMessage(temp);
		}
		
		request.getSession().setAttribute("chatsession", cs);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
