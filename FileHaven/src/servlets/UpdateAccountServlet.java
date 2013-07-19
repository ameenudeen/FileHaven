package servlets;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.*;

import model.*;


/**
 * Servlet implementation class UpdateAccountServlet
 */
public class UpdateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateAccountServlet() {
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
		session.removeAttribute("nricmsg");
		session.removeAttribute("phmsg");
		session.removeAttribute("emmsg");
		
		String button = request.getParameter("button");
		
		String userName = ((Account) session.getAttribute("updateViewAccount")).getUserName();
		char type = request.getParameter("type").charAt(0);
		
		String gender = request.getParameter("gender");
		String dateOfBirth = request.getParameter("dateOfBirth");
		String phoneNumber = request.getParameter("phoneNumber");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String NRIC = request.getParameter("NRIC");
		
		try {
			AccountDBAO dba = new AccountDBAO();
			CeoDBAO dbc = new CeoDBAO();
			EmployeeDBAO dbe = new EmployeeDBAO();
			FilemanagerDBAO dbf = new FilemanagerDBAO();
			ManagerDBAO dbm = new ManagerDBAO();
			
			Account acc = new Account();
			acc = (Account) dba.getAccountDetails(userName);
			
			if(button.equalsIgnoreCase("Update"))
			{
				boolean NRICAvail = false;
				if(NRIC.length() == 9 && Character.isLetter(NRIC.charAt(0)) && Character.isLetter(NRIC.charAt(8)) && NRIC.substring(1, 7).matches("[0-9]+"))
				{
					NRICAvail = true;
				}
				else
				{
					session.setAttribute("nricmsg", "Please enter a valid NRIC.");
				}
				
				boolean phoneNumberAvail = false;
				if (phoneNumber.matches("[0-9]+") && phoneNumber.length() == 8)
				{
					phoneNumberAvail = true;
				}
				else
				{
					session.setAttribute("phmsg", "Please enter a valid phone number.");
				}
				
				Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
				Matcher m = p.matcher(email);
				boolean emailAvail = m.matches();
				
				if(emailAvail == false)
				{
					session.setAttribute("emmsg", "Please enter a valid email address.");
				}
				
				if(NRICAvail && phoneNumberAvail && emailAvail)
				{
					session.removeAttribute("updateViewAccount");
					
					if(type == acc.getType())
					{
						if(type == 'C')
						{
							dbc.updateCeoDetails(userName, gender, dateOfBirth, phoneNumber, email, address, NRIC);
						}
						else if(type == 'E')
						{
							dbe.updateEmployeeDetails(userName, gender, dateOfBirth, phoneNumber, email, address, NRIC);
						}
						else if(type == 'F')
						{
							dbf.updateFilemanagerDetails(userName, gender, dateOfBirth, phoneNumber, email, address, NRIC);
						}
						else
						{
							dbm.updateManagerDetails(userName, gender, dateOfBirth, phoneNumber, email, address, NRIC);
						}
					}
					else
					{
						dba.updateAccountType(userName, type);
					
						if(acc.getType() == 'E')
						{
							Employee emp = new Employee();
							dbe.updateEmployeeDetails(userName, gender, dateOfBirth, phoneNumber, email, address, NRIC);
							emp = (Employee) dbe.getEmployeeDetails(userName);
						
							if(type == 'F')
							{
								dbf.insertFilemanagerCopy(userName, gender, dateOfBirth, phoneNumber, email, address, NRIC, emp.getDepartmentID());
							}
							else
							{
								dbm.insertManagerCopy(userName, gender, dateOfBirth, phoneNumber, email, address, NRIC, emp.getDepartmentID());
							}
						
							dbe.deleteEmployee(userName);
						}
						else if(acc.getType() == 'F')
						{
							Filemanager fm = new Filemanager();
							dbf.updateFilemanagerDetails(userName, gender, dateOfBirth, phoneNumber, email, address, NRIC);
							fm = (Filemanager) dbf.getFilemanagerDetails(userName);
						
							if(type == 'E')
							{
								dbe.insertEmployeeCopy(userName, gender, dateOfBirth, phoneNumber, email, address, NRIC, fm.getDepartmentID());
							}
							else
							{
								dbm.insertManagerCopy(userName, gender, dateOfBirth, phoneNumber, email, address, NRIC, fm.getDepartmentID());
							}
						
							dbf.deleteFilemanager(userName);
						}
						else
						{
							Manager mg = new Manager();
							dbm.updateManagerDetails(userName, gender, dateOfBirth, phoneNumber, email, address, NRIC);
							mg = (Manager) dbm.getManagerDetails(userName);
						
							if(type == 'E')
							{
								dbe.insertEmployeeCopy(userName, gender, dateOfBirth, phoneNumber, email, address, NRIC, mg.getDepartmentID());
							}
							else
							{
								dbf.insertFilemanagerCopy(userName, gender, dateOfBirth, phoneNumber, email, address, NRIC, mg.getDepartmentID());
							}
						
							dbm.deleteManager(userName);
						}
					}
					session.setAttribute("upmsg", "Account Updated Successfully.");
				}
			}
			else
			{
				session.removeAttribute("updateViewAccount");
				dba.updateAccountAvailability(userName);
				session.setAttribute("upmsg", "Account Disabled Successfully.");
			}
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/RetrieveAccountListServlet");
		rd.forward(request, response);
		//response.sendRedirect("UpdateAccount.jsp");
	}

}
