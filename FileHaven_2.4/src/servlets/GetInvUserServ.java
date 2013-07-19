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
 * Servlet implementation class GetInvUserServ
 */
public class GetInvUserServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetInvUserServ() {
        super();
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
		
		request.getSession().setAttribute("chatsession", cs);
		
    	Account loginUser=(Account) request.getSession().getAttribute("LoggedInUser"); 
    	String username=loginUser.getUserName();
		
		cs.refreshInvUser(username);

		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    
	    out.println("{ \"aaData\": [");
	    for(int i=0;i<cs.getChatinvAccList().size();i++){
	    	
	    	String type;
	    	switch(cs.getChatinvAccList().get(i).getType()){
	    	case('A'):type="Adminstrator";break;
	    	case('C'):type="CEO";break;
	    	case('M'):type="Manager";break;
	    	case('E'):type="Employee";break;
	    	default:type="Undefined";
	    	}
	    	
    		out.println("[");
    		out.println("\"" +cs.getChatinvAccList().get(i).getClientAid()+ "\",");
    		out.println("\"" +cs.getChatinvAccList().get(i).getUserName()+ "\",");
    		out.println("\"" +cs.getChatinvAccList().get(i).getName()+ "\",");
    		out.println("\"" +type+ "\"");
    		out.println("]");
    		if(i!=cs.getChatinvAccList().size()-1){
    			out.println(",");
    		}
    	}
	    out.println("]}");
	    
	    request.getSession().setAttribute("chatsession", cs);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
