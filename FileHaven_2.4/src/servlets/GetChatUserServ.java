package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ChatSession;

/**
 * Servlet implementation class GetChatUserServ
 */
public class GetChatUserServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetChatUserServ() {
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
		
		int roomindex=-1;
    	for(int i=0;i<cs.getChatroomList().size();i++){
    		if(cs.getChatroomList().get(i).getClientRid()==cs.getActiveRid()){
    			roomindex=i;
    		}
    	}
		
    	ArrayList<String> userList = cs.getChatroomList().get(roomindex).getChatUserList();
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    if(roomindex>-1){
	    	for(int i=0;i<userList.size();i++){
	    		out.println("<li>"+userList.get(i)+"</li>");
	    	}
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
