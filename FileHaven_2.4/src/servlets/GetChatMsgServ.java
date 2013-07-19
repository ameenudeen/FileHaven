package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ChatSession;

/**
 * Servlet implementation class GetChatMsgServ
 */
public class GetChatMsgServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetChatMsgServ() {
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

		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    if(roomindex>-1){
	    	for(int i=cs.getChatroomList().get(roomindex).getChatlogList().size()-1;i>-1;i--){
	    		out.println("<p style='color:"+ (cs.getChatroomList().get(roomindex).getChatlogList().get(i).isIntegrityChk() ? "black" : "red")+";'><b> "+
	    				cs.getChatroomList().get(roomindex).getChatlogList().get(i).getUser() +"</b>: "+ cs.getChatroomList().get(roomindex).getChatlogList().get(i).getChatMsg()+"</p>");
	    	}
	    	
	    	cs.getChatroomList().get(roomindex).refreshChatMsg();
	    }
	    //System.out.println("GetChatMsgServ");
	    request.getSession().setAttribute("chatsession", cs);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
