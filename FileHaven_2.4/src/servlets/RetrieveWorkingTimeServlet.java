package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;
import model.Company;

import database.AccountDBAO;
import database.CompanyDBAO;

/**
 * Servlet implementation class RetrieveWorkingTimeServlet
 */
public class RetrieveWorkingTimeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RetrieveWorkingTimeServlet() {
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
		
		session.removeAttribute("wtmsg");
		session.removeAttribute("company");
		
		Account acc = (Account) session.getAttribute("LoggedInUser");
		
		try {
			
			CompanyDBAO dbcom = new CompanyDBAO();
			Company com = dbcom.getCompanyDetails(acc.getCompanyID());
			
			session.setAttribute("company", com);
			
			response.sendRedirect("UpdateWorkingTime.jsp");
			
		}catch(Exception e)
		{	
			e.printStackTrace();
		}
		
	}

}
