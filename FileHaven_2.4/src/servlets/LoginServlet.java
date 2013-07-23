package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
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
		
		int cc = 0;
		int uc = 0;
		int lc = 0;
		
		if(session.getAttribute("cc") != null)
		{
			cc = Integer.parseInt(session.getAttribute("cc").toString());
		}
		if(session.getAttribute("uc") != null)
		{
			uc = Integer.parseInt(session.getAttribute("uc").toString());
		}
		if(session.getAttribute("lc") != null)
		{
			lc = Integer.parseInt(session.getAttribute("lc").toString());
		}
		AccountDBAO dba = null;
		try {
			dba=new AccountDBAO();
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
								session.removeAttribute("cc");
								session.removeAttribute("uc");
								session.removeAttribute("lc");
								
								session.setAttribute("VerifyUser", acc);
								
								dba.remove();
								RequestDispatcher rd = request.getRequestDispatcher("/LoginTimeCheckServlet");
								rd.forward(request, response);
							}
							else
							{
								session.setAttribute("alert", "Please check that you have entered your login ID and password correctly.");
								lc++;
							}
						}
						else
						{
							session.setAttribute("alert", "Please check that you have entered your login ID and password correctly.");
							uc++;
						}
					}
					else
					{
						session.setAttribute("alert", "Please check that you have entered your login ID and password correctly.");
						uc++;
					}
				}
				else
				{
					session.setAttribute("alert", "Please check that you have entered the Captcha correctly.");
					cc++;
				}
			}
			else
			{
				session.setAttribute("alert", "Please check that you have entered the Captcha correctly.");
				cc++;
			}
			
			if(cc >= 5 || uc >= 3)
			{
				dba.remove();
				RequestDispatcher rd = request.getRequestDispatcher("/CreateAccountReportServlet");
				rd.forward(request, response);
			}
			else if(lc >= 3)
			{
				
				session.setAttribute("comID", dba.getAccountDetails(userName).getCompanyID());
				session.setAttribute("inUser", userName);
				dba.remove();
				RequestDispatcher rd = request.getRequestDispatcher("/CreateAccountReportServlet");
				rd.forward(request, response);
			}
			else
			{
				session.setAttribute("cc", cc);
				session.setAttribute("uc", uc);
				session.setAttribute("lc", lc);
				
				if(response.isCommitted() == false)
				{
					dba.remove();
					response.sendRedirect("Login.jsp");
				}
			}
			
		}catch (Exception e)
		{
			dba.remove();
			e.printStackTrace();
		}
	}

}
