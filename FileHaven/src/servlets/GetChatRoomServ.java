package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;
import model.ChatSession;

/**
 * Servlet implementation class GetChatRoomServ
 */
public class GetChatRoomServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetChatRoomServ() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ChatSession cs;
		if(request.getSession().getAttribute("chatsession")!=null){
			cs=(ChatSession) request.getSession().getAttribute("chatsession");
		}
		else {
			cs=new ChatSession();
		}
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    
    	Account loginUser=(Account) request.getSession().getAttribute("LoggedInUser"); 
    	String username=loginUser.getUserName();
	    
	    if(cs.pinIsEmpty()){
	    	int status=cs.checkPinExist(username);
	    	if(status==1){
		    	out.println("<div style='margin:0;padding:0;width:100%;height:100%;' class='disHover'>" +
		    			"<li id='chatPinReg' style='text-align:center;list-style-type: none;cursor:pointer;'>" +
		    			"<img style='width=60px;height=60px' src='resources/img/chat/lock.png' /><br />" +
		    			"<h4>Register a New Pin</h4></li></div>");
	    	}else if(status==2)
	    	{
	    		out.println("<div style='margin:0;padding:0;width:100%;height:100%;' class='disHover'>" +
		    			"<li id='chatPinAuth' style='text-align:center;list-style-type: none;cursor:pointer;'>" +
		    			"<img style='width=60px;height=60px' src='resources/img/chat/lock.png' /><br />" +
		    			"<h4>Authenticate Pin</h4></li></div>");
	    	}
	    	else{
	    		out.println("Database error. Please try again later.");
	    	}
	    }
	    else
	    {
			try {
				cs.refreshChatRoom(username);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				cs.refreshChatInv(username);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int daTabVal=0;
			try{
				daTabVal=Integer.parseInt(request.getParameter("tabFilter"));
			}
			catch(Exception e){
				daTabVal=0;
			}
			if(daTabVal!=2){
			    for(int i=0;i<cs.getChatInvList().size();i++){
			    	out.println("<div class='disHover'><li class='displayInv' data-clientiid="+cs.getChatInvList().get(i).getClientIid()+" style='cursor:pointer;'><img src='resources/img/chat/envelope.png' />"+cs.getChatInvList().get(i).getRoomTitle()+"</li></div>");
			    }
			}
			if(daTabVal!=1){
			    for(int i=0;i<cs.getChatroomList().size();i++){
					out.println("<div class='disHover'><li class='displayChat' data-clientrid="+cs.getChatroomList().get(i).getClientRid()+" style='cursor:pointer;'><img src='resources/img/chat/iconmonstr-speech-bubble-15-icon.png' />"+cs.getChatroomList().get(i).getDescription()+"</li></div>");
				}
			}
			out.println("<div class='disHover'><li id='newChat' style='cursor:pointer;'><img src='resources/img/chat/plus.png' />Create Chatroom</li></div>");
	    }
		//System.out.println("GetChatRoomServ");
		request.getSession().setAttribute("chatsession", cs);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
