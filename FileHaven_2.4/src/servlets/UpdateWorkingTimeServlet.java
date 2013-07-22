package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;
import model.Company;
import database.CompanyDBAO;

/**
 * Servlet implementation class UpdateWorkingTimeServlet
 */
public class UpdateWorkingTimeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateWorkingTimeServlet() {
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
		
		String startHour = request.getParameter("startHour");
		String endHour = request.getParameter("endHour");
		
		String startMinute = request.getParameter("startMinute");
		String endMinute = request.getParameter("endMinute");
		
		String startTime = startHour + ":" + startMinute + ":00";
		String endTime = endHour + ":" + endMinute + ":00";
		
		try {
			
			CompanyDBAO dbcom = new CompanyDBAO();
			dbcom.updateWorkingTime(acc.getCompanyID(), startTime, endTime);
			
			Company com = dbcom.getCompanyDetails(acc.getCompanyID());
			
			session.setAttribute("wtmsg", "Working Time updated successfully.");
			session.setAttribute("company", com);
			
			response.sendRedirect("UpdateWorkingTime.jsp");
			
		}catch(Exception e)
		{	
			e.printStackTrace();
		}
		
	}

}
