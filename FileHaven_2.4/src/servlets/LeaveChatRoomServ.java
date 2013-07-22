package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;
import model.ChatSession;

/**
 * Servlet implementation class LeaveChatRoomServ
 */
public class LeaveChatRoomServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LeaveChatRoomServ() {
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
		
		Account loginUser=(Account) request.getSession().getAttribute("LoggedInUser"); 
    	String userid=loginUser.getUserName();
		
		int clientRid=Integer.parseInt(request.getParameter("clientRid"));
		
		int roomindex=-1;
    	for(int i=0;i<cs.getChatroomList().size();i++){
    		if(cs.getChatroomList().get(i).getClientRid()==clientRid){
    			roomindex=i;
    		}
    	}
    	
    	if(cs.getChatroomList().get(roomindex).leaveChatRoom(userid)){
    		cs.getChatroomList().remove(roomindex);
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
