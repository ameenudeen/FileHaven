package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;
import model.Permission;
import database.PermissionDBAO;

/**
 * Servlet implementation class ViewPermissionServlet
 */
public class ViewPermissionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewPermissionServlet() {
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
		
		session.removeAttribute("pmList");
		
		Account acc = (Account) session.getAttribute("LoggedInUser");
		
		String dateFilter = request.getParameter("date");
		PermissionDBAO dbpm=null;
		try
		{
			 dbpm = new PermissionDBAO();
			
			ArrayList<Permission> pmList = new ArrayList<Permission>();
			String dStmt = "AND (EndTime > DATE_SUB(now(), INTERVAL " + dateFilter + " DAY))";
			
			if(dateFilter == null || dateFilter.equals("0"))
			{
				dStmt = "";
			}
			
			if(dateFilter == null || dateFilter.equals("0"))
			{
				pmList = dbpm.getCompanyPermissions(acc.getCompanyID());
			}
			else
			{
				pmList = dbpm.getCompanyPermissions(acc.getCompanyID(), dStmt);
			}
			
			session.setAttribute("pmList", pmList);
			
			session.setAttribute("dateFilter", dateFilter);
			dbpm.remove();
		}catch(Exception e)
		{
			dbpm.remove();
			e.printStackTrace();
		}
		
		response.sendRedirect("ViewPermission.jsp");
	}

}
