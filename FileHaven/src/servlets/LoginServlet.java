package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import security.Hash;

import database.AccountDBAO;

import model.Account;


/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		session.removeAttribute("alert");
		
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		
		String captcha = session.getAttribute("captcha").toString();
		String code = request.getParameter("code");
		
		try {
			AccountDBAO dba = new AccountDBAO();

			if (captcha != null && code != null && code != "") 
			{
				if (captcha.equals(code))
				{
					if(userName != "" && !(userName.equalsIgnoreCase("/' OR 1 = 1")) && password != "")
					{
						if(dba.getPassword(userName) != "")
						{
							Account acc = dba.getAccountDetails(userName);
							
							password = Hash.hashString(password, acc.getCreatorID(), acc.getCreatedTime());
							
							if(password.equals(dba.getPassword(userName)))
							{
								session.setAttribute("LoggedInUser", acc);
								
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								java.util.Date now = new java.util.Date();
								String loginTime = sdf.format(now);
								
								dba.updateLoginTime(userName, loginTime);
								
								response.sendRedirect("Index.jsp");
							}
							else
							{
								session.setAttribute("alert", "Please check that you have entered your login ID and password correctly.");
								response.sendRedirect("Login.jsp");
							}
						}
						else
						{
							session.setAttribute("alert", "Please check that you have entered your login ID and password correctly.");
							response.sendRedirect("Login.jsp");
						}
					}
					else
					{
						session.setAttribute("alert", "Please check that you have entered your login ID and password correctly.");
						response.sendRedirect("Login.jsp");
					}
				}
				else
				{
					session.setAttribute("alert", "Please check that you have entered the Captcha correctly.");
					response.sendRedirect("Login.jsp");
				}
			}
			else
			{
				session.setAttribute("alert", "Please check that you have entered the Captcha correctly.");
				response.sendRedirect("Login.jsp");
			}
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
