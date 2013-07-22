package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ChatSession;

/**
 * Servlet implementation class GetChatInvDescServ
 */
public class GetChatInvDescServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetChatInvDescServ() {
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
    	
    	int invindex=-1;
    	for(int i=0;i<cs.getChatroomList().size();i++){
    		if(cs.getChatInvList().get(i).getClientIid()==Integer.parseInt(request.getParameter("clientiid"))){
    			invindex=i;
    		}
    	}
    	
    	response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
    	
    	out.print(cs.getChatInvList().get(invindex).getDesc()+"<br />" +
    			"<i>Invited by "+cs.getChatInvList().get(invindex).getSender()+"</i>");
    	
    	request.getSession().setAttribute("chatsession", cs);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
