package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;
import model.AccountReport;
import database.AccountReportDBAO;

/**
 * Servlet implementation class ViewAccountReportServlet
 */
public class ViewAccountReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewAccountReportServlet() {
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
		
		session.removeAttribute("arList");
		
		Account acc = (Account) session.getAttribute("LoggedInUser");
		
		String knownFilter = request.getParameter("userName");
		String dateFilter = request.getParameter("date");
		AccountReportDBAO dbar=null;
		
		try
		{
			dbar = new AccountReportDBAO();
			
			ArrayList<AccountReport> arList = new ArrayList<AccountReport>();
			String uStmt = "";
			String dStmt = "AND (LogTime > DATE_SUB(now(), INTERVAL " + dateFilter + " DAY))";
			
			if(knownFilter == null || knownFilter.equals("All"))
			{
				
			}
			else if(knownFilter.equals("Known"))
			{
				uStmt = "AND UserName <> 'Unknown'";
			}
			else
			{
				uStmt = "AND UserName = 'Unknown'";
			}
			
			if(dateFilter == null || dateFilter.equals("0"))
			{
				dStmt = "";
			}
			
			if(knownFilter == null || knownFilter.equals("All") && dateFilter.equals("0"))
			{
				arList = dbar.getCompanyAccountReports(acc.getCompanyID());
			}
			else
			{
				arList = dbar.getCompanyAccountReports(acc.getCompanyID(), uStmt, dStmt);
			}
			
			session.setAttribute("arList", arList);
			dbar.remove();
			session.setAttribute("knownFilter", knownFilter);
			session.setAttribute("dateFilter", dateFilter);
			
		}catch(Exception e)
		{
			dbar.remove();
			e.printStackTrace();
		}
		
		response.sendRedirect("ViewAccountReport.jsp");
		
	}

}
