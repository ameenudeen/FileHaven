package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.AccountDBAO;

import security.Hash;

import model.Account;

/**
 * Servlet implementation class ChangePasswordServlet
 */
public class ChangePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePasswordServlet() {
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
		
		session.removeAttribute("passmsg");
		session.removeAttribute("conpassmsg");
		
		Account acc = (Account) session.getAttribute("LoggedInUser");
		AccountDBAO  dba=null;
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String conPassword = request.getParameter("conPassword");
		
		try {
			dba = new AccountDBAO();
			
			if(oldPassword != "" && newPassword != "" && conPassword != "")
			{	
				oldPassword = Hash.hashString(oldPassword, acc.getCreatorID(), acc.getCreatedTime());
				String oldHash = dba.getPassword(acc.getUserName());
				
				if(oldPassword.equals(oldHash))
				{
					if(newPassword.equals(conPassword))
					{
						String newHash = Hash.hashString(newPassword, acc.getCreatorID(), acc.getCreatedTime());
						dba.updateAccountPassword(acc.getUserName(), newHash);
						
						session.setAttribute("passmsg", "Password successfully changed.");
					}
					else
					{
						session.setAttribute("conpassmsg", "New password and Confirm password do not match.");
					}
				}
				else
				{
					session.setAttribute("passmsg", "Please check that you have entered the correct password.");
				}
			}
			else
			{
				session.setAttribute("passmsg", "Please fill up all the fields.");
			}
			
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		dba.remove();
		response.sendRedirect("ChangePassword.jsp");
		
	}

}
