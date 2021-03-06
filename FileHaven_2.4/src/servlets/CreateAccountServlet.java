package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.*;
import model.*;
import security.*;


/**
 * Servlet implementation class CreateAccountServlet
 */
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CreateAccountServlet() {
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
		
		session.removeAttribute("msg");
		session.removeAttribute("unmsg");
		session.removeAttribute("nricmsg");
		session.removeAttribute("dobmsg");
		session.removeAttribute("phmsg");
		session.removeAttribute("emmsg");
		
		Account acc = (Account) session.getAttribute("LoggedInUser");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String userName = request.getParameter("userName");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		char type = request.getParameter("type").charAt(0);
		
		java.util.Date now = new java.util.Date();
		String createdTime = sdf.format(now);
		
		int companyID = acc.getCompanyID();
		
		String gender = request.getParameter("gender");
		String dateOfBirth = request.getParameter("dateOfBirth");
		String phoneNumber = request.getParameter("phoneNumber");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String NRIC = request.getParameter("NRIC");
		
		int comID = Integer.parseInt(request.getParameter("companyID"));
		
		session.setAttribute("userName", userName);
		session.setAttribute("name", name);
		session.setAttribute("password", password);
		session.setAttribute("type", type);
		session.setAttribute("gender", gender);
		session.setAttribute("dateOfBirth", dateOfBirth);
		session.setAttribute("phoneNumber", phoneNumber);
		session.setAttribute("email", email);
		session.setAttribute("address", address);
		session.setAttribute("NRIC", NRIC);

		AccountDBAO dba = null;
		
		try {
			dba = new AccountDBAO();
			password = Hash.hashString(password, acc.getUserName(), createdTime);
			
			if(userName != "" && name != "" && password != "" && dateOfBirth != "" && phoneNumber != "" && email != "" && address != "" && NRIC != "")
			{	
				boolean userNameAvail = dba.checkUserNameAvailability(userName);
				
				if(userNameAvail == false)
				{
					session.setAttribute("unmsg", "Username is not available.");
				}
				
				boolean NRICAvail = false;
				if(NRIC.length() == 9 && Character.isLetter(NRIC.charAt(0)) && Character.isLetter(NRIC.charAt(8)) && NRIC.substring(1, 7).matches("[0-9]+"))
				{
					NRICAvail = true;
				}
				else
				{
					session.setAttribute("nricmsg", "Please enter a valid NRIC.");
				}
				
				Pattern pd = Pattern.compile("\\b[0-9]{4}-[0-9]{2}-[0-9]{2}\\b");
				Matcher m = pd.matcher(dateOfBirth);
				boolean dateOfBirthAvail = m.matches();
				if (dateOfBirthAvail == false)
				{
			        session.setAttribute("dobmsg", "Please enter a valid date. (YYYY-MM-DD)");
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
				
				Pattern pe = Pattern.compile(".+@.+\\.[a-z]+");
				m = pe.matcher(email);
				boolean emailAvail = m.matches();
				
				if(emailAvail == false)
				{
					session.setAttribute("emmsg", "Please enter a valid email address.");
				}
				
				if(userNameAvail && NRICAvail && dateOfBirthAvail && phoneNumberAvail && emailAvail)
				{
					session.removeAttribute("userName");
					session.removeAttribute("name");
					session.removeAttribute("password");
					session.removeAttribute("type");
					session.removeAttribute("gender");
					session.removeAttribute("dateOfBirth");
					session.removeAttribute("phoneNumber");
					session.removeAttribute("email");
					session.removeAttribute("address");
					session.removeAttribute("NRIC");
					
					if(type == 'C')
					{
						dba.insertAccount(userName, name, password, type, acc.getUserName(), createdTime, comID);
					}
					else
					{
						dba.insertAccount(userName, name, password, type, acc.getUserName(), createdTime, companyID);
					}
					
					if(type == 'E')
					{
						EmployeeDBAO dbe = new EmployeeDBAO();
						dbe.insertEmployee(userName, gender, dateOfBirth, phoneNumber, email, address, NRIC);
					}
					else if(type == 'F')
					{
						FilemanagerDBAO dbf = new FilemanagerDBAO();
						dbf.insertFilemanager(userName, gender, dateOfBirth, phoneNumber, email, address, NRIC);
					}
					else if(type == 'M')
					{
						ManagerDBAO dbm = new ManagerDBAO();
						dbm.insertManager(userName, gender, dateOfBirth, phoneNumber, email, address, NRIC);
					}
					else
					{
						CeoDBAO dbc = new CeoDBAO();
						dbc.insertCeo(userName, gender, dateOfBirth, phoneNumber, email, address, NRIC);
					}
					session.setAttribute("msg", "Account created successfully.");
					session.setAttribute("createdUser", userName);
					session.setAttribute("createdTime", createdTime);
					session.setAttribute("creatorID", acc.getUserName());
					
					response.sendRedirect("UpdatePatternLock.jsp");
				}
				else
				{
					if(response.isCommitted() == false)
					{
						response.sendRedirect("CreateAccount.jsp");
					}
				}
			}
			else
			{	
				session.setAttribute("msg", "*Please fill up all the fields.");
				
				if(response.isCommitted() == false)
				{
					response.sendRedirect("CreateAccount.jsp");
				}
			}
			
		}catch(Exception e)
		{
			if(dba!=null)dba.remove();
			AccountDBAO db=null;
			try {
			db = new AccountDBAO();
			db.deleteAccount(userName);
			}catch(Exception ex)
			{
				ex.printStackTrace();
				if(db!=null)db.remove();
				System.out.println("userName catch : " + userName);
			}
			
			e.printStackTrace();
		}
		
	}

}
