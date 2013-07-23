package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.*;

import model.*;


/**
 * Servlet implementation class RetrieveAccountListServlet
 */
public class RetrieveAccountListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RetrieveAccountListServlet() {
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
		
		int companyID = acc.getCompanyID();
		int departmentID = 0;
		AccountDBAO dba=null;
		
		try {
			dba = new AccountDBAO();
			
			ArrayList<String> userNameList = new ArrayList<String>();
			ArrayList<String> nameList = new ArrayList<String>();

			if(acc.getType() == 'M')
			{
					ManagerDBAO dbm = new ManagerDBAO();
					Manager temp = dbm.getManagerDetails(acc.getUserName());
					dbm.remove();
					
					if((Integer) temp.getDepartmentID() != null)
					{
						departmentID = temp.getDepartmentID();
					}
					
					userNameList = dba.getEmployeeUserNames(companyID, departmentID);
					nameList = dba.getEmployeeNames(companyID, departmentID);
				
			}
			else
			{
				userNameList = dba.getAccountUserNames(companyID, acc.getType());
				nameList = dba.getAccountNames(companyID, acc.getType());
			}
			
			session.removeAttribute("userNameList");
			session.setAttribute("userNameList", userNameList);
			
			session.removeAttribute("nameList");
			session.setAttribute("nameList", nameList);
			dba.remove();
			
			response.sendRedirect("UpdateAccount.jsp");
	}catch (Exception e)
	{
		dba.remove();
		e.printStackTrace();
	}
	}

}
