package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;
import model.ChatSession;

/**
 * Servlet implementation class SetInvStatusServ
 */
public class SetInvStatusServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetInvStatusServ() {
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
		
		int invindex=-1;
    	for(int i=0;i<cs.getChatInvList().size();i++){
    		if(cs.getChatInvList().get(i).getClientIid()==Integer.parseInt(request.getParameter("activeIid"))){
    			invindex=i;
    		}
    	}
    	
    	int status=Integer.parseInt(request.getParameter("status"));
    	String username= (String) ((Account) request.getSession().getAttribute("LoggedInUser")).getUserName() ;
    	
		System.out.println("Invitation Index: "+invindex);
		System.out.println("Invitation Status: "+status);
		
		if(invindex>-1){
			cs.getChatInvList().get(invindex).updateStatus(status,username);

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
