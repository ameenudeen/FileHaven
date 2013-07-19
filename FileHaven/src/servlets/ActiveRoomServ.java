package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ChatSession;

/**
 * Servlet implementation class activeRoomServ
 */
public class ActiveRoomServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActiveRoomServ() {
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
		
    	int clientRid=Integer.parseInt(request.getParameter("clientRid"));
    	cs.setActiveRid(clientRid);
    	
    	request.getSession().setAttribute("chatsession", cs);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
