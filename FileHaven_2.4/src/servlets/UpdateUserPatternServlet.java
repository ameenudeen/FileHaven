package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import security.Hash;

import database.AccountDBAO;

/**
 * Servlet implementation class UpdateUserPatternServlet
 */
public class UpdateUserPatternServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserPatternServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		
		String userName = session.getAttribute("createdUser").toString();
		String createdTime = session.getAttribute("createdTime").toString();
		String creatorID = session.getAttribute("creatorID").toString();
		
		String userPattern = request.getParameter("userPattern");
		
		try {
			AccountDBAO dba = new AccountDBAO();
			
			String uphash = Hash.hashString(userPattern, creatorID, createdTime);
			
			dba.updateUserPattern(userName, uphash);
			
		}catch(Exception e)
		{	
			e.printStackTrace();
		}
		
		session.removeAttribute("createdUser");
		session.removeAttribute("createdTime");
		session.removeAttribute("creatorID");
		
		response.sendRedirect("CreateAccount.jsp");
		
	}

}
