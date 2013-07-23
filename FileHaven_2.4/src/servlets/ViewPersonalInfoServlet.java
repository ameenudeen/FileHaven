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
 * Servlet implementation class ViewPersonalInfo
 */
public class ViewPersonalInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewPersonalInfoServlet() {
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
		session.removeAttribute("viewPersonalInfo");
		
		Account temp = new Account();
		Account acc = null;
		
		temp = (Account) session.getAttribute("LoggedInUser");
		
		String userName = temp.getUserName();
		String name = temp.getName();
		char type = temp.getType();
		
		ManagerDBAO dbm =null;
		CeoDBAO dbc =null;
		EmployeeDBAO dbe =null;
		FilemanagerDBAO dbf =null;
		AdministratorDBAO dbad =null;
		
		try {
			 dbad= new AdministratorDBAO();
			 dbc= new CeoDBAO();
			 dbe = new EmployeeDBAO();
			 dbf= new FilemanagerDBAO();
			 dbm= new ManagerDBAO();
			
			if(type == 'A')
			{
				acc = new Administrator();
				acc = dbad.getAdministratorDetails(userName);
				acc.setUserName(userName);
				acc.setName(name);
				acc.setType(type);
			}
			else if(type == 'C')
			{
				acc = new Ceo();
				acc = dbc.getCeoDetails(userName);
				acc.setUserName(userName);
				acc.setName(name);
				acc.setType(type);
			}
			else if(type == 'E')
			{
				acc = new Employee();
				acc = dbe.getEmployeeDetails(userName);
				acc.setUserName(userName);
				acc.setName(name);
				acc.setType(type);
			}
			else if(type == 'F')
			{
				acc = new Filemanager();
				acc = dbf.getFilemanagerDetails(userName);
				acc.setUserName(userName);
				acc.setName(name);
				acc.setType(type);
			}
			else
			{
				acc = new Manager();
				acc = dbm.getManagerDetails(userName);
				acc.setUserName(userName);
				acc.setName(name);
				acc.setType(type);
			}
			
			session.setAttribute("viewPersonalInfo", acc);
			 dbad.remove();
			 dbc.remove();
			 dbe.remove();
			 dbf.remove();
			 dbm.remove();
			response.sendRedirect("ViewPersonalInfo.jsp");
	}catch (Exception e)
	{ 
		dbad.remove();
		 dbc.remove();
		 dbe.remove();
		 dbf.remove();
		 dbm.remove();
		e.printStackTrace();
	}
		
	}

}
