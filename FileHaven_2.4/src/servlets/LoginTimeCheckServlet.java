package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;
import model.Company;
import model.Permission;

import database.AccountDBAO;
import database.CompanyDBAO;
import database.PermissionDBAO;

/**
 * Servlet implementation class LoginTimeCheckServlet
 */
public class LoginTimeCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginTimeCheckServlet() {
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
		
		Account acc = (Account) session.getAttribute("VerifyUser");
		session.removeAttribute("VerifyUser");
		
		String userName = request.getParameter("userName");
		
		int tc = 0;
		
		if(session.getAttribute("tc") != null)
		{
			tc = Integer.parseInt(session.getAttribute("tc").toString());
		}
		
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			CompanyDBAO dbcom = new CompanyDBAO();
			Company com = dbcom.getCompanyDetails(acc.getCompanyID());
			
			int startHour = Integer.parseInt(com.getStartTime().substring(0, 2));
			int endHour = Integer.parseInt(com.getEndTime().substring(0, 2));
			
			int startMinute = Integer.parseInt(com.getStartTime().substring(3, 5));
			int endMinute = Integer.parseInt(com.getEndTime().substring(3, 5));
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, startHour);
			cal.set(Calendar.MINUTE, startMinute);
			cal.set(Calendar.SECOND, 0);
			Date startTime = cal.getTime();
			
			cal.set(Calendar.HOUR_OF_DAY, endHour);
			cal.set(Calendar.MINUTE, endMinute);
			cal.set(Calendar.SECOND, 0);
			Date endTime = cal.getTime();
			
			Date now = new Date();
			
			if(now.equals(startTime) || now.after(startTime) && now.before(endTime) || acc.getType() == 'C' || acc.getType() == 'A'
			|| com.getStartTime().substring(0, 2).equalsIgnoreCase("00") && com.getStartTime().substring(3, 5).equalsIgnoreCase("00") && com.getEndTime().substring(0, 2).equalsIgnoreCase("00") && com.getEndTime().substring(3, 5).equalsIgnoreCase("00"))
			{
				AccountDBAO dba = new AccountDBAO();
				
				session.removeAttribute("VerifyUser");
				
				session.setAttribute("LoggedInUser", acc);
				
				String loginTime = sdf.format(now);
				
				dba.updateLoginTime(userName, loginTime);
				
				response.sendRedirect("Index.jsp");
			}
			else
			{
				PermissionDBAO dbpm = new PermissionDBAO();
				
				ArrayList<Integer> IDList = dbpm.getUserPermission(userName, sdf.format(now));
				
				if(IDList.size() != 0)
				{
					boolean pmcheck = false;
					
					for(int i=0; i<IDList.size(); i++)
					{
						Permission pm = dbpm.getPermissionDetails(IDList.get(i));
						
						int extendStartHour = Integer.parseInt(pm.getExtendStart().substring(0, 2));
						int extendEndHour = Integer.parseInt(pm.getExtendEnd().substring(0, 2));
						
						int extendStartMinute = Integer.parseInt(pm.getExtendStart().substring(3, 5));
						int extendEndMinute = Integer.parseInt(pm.getExtendEnd().substring(3, 5));
						
						cal.set(Calendar.HOUR_OF_DAY, extendStartHour);
						cal.set(Calendar.MINUTE, extendStartMinute);
						cal.set(Calendar.SECOND, 0);
						Date extendStartTime = cal.getTime();
						
						cal.set(Calendar.HOUR_OF_DAY, extendEndHour);
						cal.set(Calendar.MINUTE, extendEndMinute);
						cal.set(Calendar.SECOND, 0);
						Date extendEndTime = cal.getTime();
						
						if(now.equals(extendStartTime) || now.after(extendStartTime) && now.before(extendEndTime))
						{
							pmcheck = true;
							break;
						}
					}
					
					if(pmcheck)
					{
						AccountDBAO dba = new AccountDBAO();
						
						session.setAttribute("LoggedInUser", acc);
						
						String loginTime = sdf.format(now);
						
						dba.updateLoginTime(userName, loginTime);
						
						response.sendRedirect("Index.jsp");	
					}
					else
					{
						session.setAttribute("alert", "You are not permitted to login at this time. Please contact your CEO or Manager.");
						tc++;
					}
				}
				else
				{
					session.setAttribute("alert", "You are not permitted to login at this time. Please contact your CEO or Manager.");
					tc++;
				}
			}
			
			if(tc >= 3)
			{
				session.setAttribute("inUser", acc.getUserName());
				session.setAttribute("comID", acc.getCompanyID());
				
				RequestDispatcher rd = request.getRequestDispatcher("/CreateAccountReportServlet");
				rd.forward(request, response);
			}
			else
			{
				session.setAttribute("tc", tc);
				
				session.removeAttribute("VerifyUser");
				
				if(response.isCommitted() == false)
				{
					response.sendRedirect("Login.jsp");
				}
			}

			
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}

}
