package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;

import security.Hash;
import database.AccountDBAO;

/**
 * Servlet implementation class UpdatePersonalPatternServlet
 */
public class UpdatePersonalPatternServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePersonalPatternServlet() {
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
		
		Account acc = (Account) session.getAttribute("LoggedInUser");
		
		String userPattern = request.getParameter("userPattern");
		
		try {
			AccountDBAO dba = new AccountDBAO();
			
			String uphash = Hash.hashString(userPattern, acc.getCreatorID(), acc.getCreatedTime());
			
			dba.updateUserPattern(acc.getUserName(), uphash);
			
			session.setAttribute("uppmsg", "Secondary password updated successfully.");
			
		}catch(Exception e)
		{	
			e.printStackTrace();
		}
		
		response.sendRedirect("UpdatePersonalPattern.jsp");
		
	}
	
}
