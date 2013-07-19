package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.*;

import model.*;


/**
 * Servlet implementation class RetrieveAccountDetailsServlet
 */
public class RetrieveAccountDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RetrieveAccountDetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);
		session.removeAttribute("upmsg");
		session.removeAttribute("updateViewAccount");
		
		Account temp = new Account();
		Account acc = null;
		String userName = request.getParameter("name");
		
		try {
			AccountDBAO dba = new AccountDBAO();
			CeoDBAO dbc = new CeoDBAO();
			EmployeeDBAO dbe = new EmployeeDBAO();
			FilemanagerDBAO dbf = new FilemanagerDBAO();
			ManagerDBAO dbm = new ManagerDBAO();
			
			temp = (Account) dba.getAccountDetails(userName);
			
			if(userName != null)
			{
				if(temp.getType() == 'C')
				{
					acc = new Ceo();
					acc = dbc.getCeoDetails(userName);
					acc.setUserName(userName);
					acc.setName(temp.getName());
					acc.setType(temp.getType());
					acc.setCreatorID(temp.getCreatorID());
				}
				else if(temp.getType() == 'E')
				{
					acc = new Employee();
					acc = dbe.getEmployeeDetails(userName);
					acc.setUserName(userName);
					acc.setName(temp.getName());
					acc.setType(temp.getType());
					acc.setCreatorID(temp.getCreatorID());
				}
				else if(temp.getType() == 'F')
				{
					acc = new Filemanager();
					acc = dbf.getFilemanagerDetails(userName);
					acc.setUserName(userName);
					acc.setName(temp.getName());
					acc.setType(temp.getType());
					acc.setCreatorID(temp.getCreatorID());
				}
				else
				{
					acc = new Manager();
					acc = dbm.getManagerDetails(userName);
					acc.setUserName(userName);
					acc.setName(temp.getName());
					acc.setType(temp.getType());
					acc.setCreatorID(temp.getCreatorID());
				}
			
				session.setAttribute("updateViewAccount", acc);
			}
			
			response.sendRedirect("UpdateAccount.jsp");
	}catch (Exception e)
	{
		e.printStackTrace();
	}
	}

}
