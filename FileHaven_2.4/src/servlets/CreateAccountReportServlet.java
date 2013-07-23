package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.AccountReportDBAO;

/**
 * Servlet implementation class CreateAccountReportServlet
 */
public class CreateAccountReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAccountReportServlet() {
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		java.util.Date now = new java.util.Date();
		AccountReportDBAO dbar = null;
		int cc = 0;
		int uc = 0;
		int lc = 0;
		int tc = 0;
		int comID = 0;
		
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
		if(session.getAttribute("tc") != null)
		{
			tc = Integer.parseInt(session.getAttribute("tc").toString());
		}
		if(session.getAttribute("comID") != null)
		{
			comID = Integer.parseInt(session.getAttribute("comID").toString());
		}
		
		
		String details = "";
		String logTime = sdf.format(now);
		String inUser = "";
		
		try {
			dbar=new AccountReportDBAO();
			if(session.getAttribute("inUser") == null)
			{
				inUser = "Unknown";
			}
			else
			{
				inUser = session.getAttribute("inUser").toString();
			}
			
			if(cc >= 4)
			{
				details = "Captcha attempt failed " + (cc + 1) + " times. Possible brute force attack by bots.";
			}
			else if(uc >= 2)
			{
				details = "Login attempt failed " + (uc + 1) + " times. Entered username did not match any known account. Possible attack attempt.";
			}
			else if(lc >= 2)
			{
				details = "Login attempt failed " + (lc + 1) + " times for user " + inUser + " due to incorrect password.";
			}
			else
			{
				details = "Login attempt failed " + (tc + 1) + " times for user " + inUser + " due to login attempt at non-working time/lack of permission.";
			}
			
			
			
			dbar.insertAccountReport(details, logTime, inUser, comID);
			dbar.remove();
			session.removeAttribute("cc");
			session.removeAttribute("uc");
			session.removeAttribute("lc");
			session.removeAttribute("tc");
			session.removeAttribute("inUser");
			session.removeAttribute("comID");
			
			response.sendRedirect("Login.jsp");
			
		}catch(Exception e)
		{
			dbar.remove();
			e.printStackTrace();
		}
	}

}
