package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.PermissionDBAO;

import model.Account;
import model.Permission;

/**
 * Servlet implementation class CreatePermissionServlet
 */
public class CreatePermissionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatePermissionServlet() {
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
		
		session.removeAttribute("supmsg");
		session.removeAttribute("damsg");
		session.removeAttribute("permission");
		
		String grantBy = ((Account) session.getAttribute("LoggedInUser")).getUserName();
		int companyID = ((Account) session.getAttribute("LoggedInUser")).getCompanyID();
		
		String userName = request.getParameter("name");
		
		String startTimeYear = request.getParameter("startTimeYear");
		String startTimeMonth = request.getParameter("startTimeMonth");
		String startTimeDay = request.getParameter("startTimeDay");
		
		String startTime = "";
		
		boolean stcheck = false;
		
		if (startTimeYear.matches("[0-9]+") && startTimeYear.length() == 4 && startTimeMonth.matches("[0-9]+")
		&& startTimeMonth.length() == 2 && startTimeDay.matches("[0-9]+") && startTimeDay.length() == 2)
		{
			startTime = startTimeYear + "-" + startTimeMonth + "-" + startTimeDay + " 00:00:00";
			stcheck = true;
		}
		
		String endTimeYear = request.getParameter("endTimeYear");
		String endTimeMonth = request.getParameter("endTimeMonth");
		String endTimeDay = request.getParameter("endTimeDay");
		
		String endTime = "";
		
		boolean etcheck = false;
		
		if (endTimeYear.matches("[0-9]+") && endTimeYear.length() == 4 && endTimeMonth.matches("[0-9]+")
		&& endTimeMonth.length() == 2 && endTimeDay.matches("[0-9]+") && endTimeDay.length() == 2)
		{
			endTime = endTimeYear + "-" + endTimeMonth + "-" + endTimeDay + " 00:00:00";
			etcheck = true;
		}
		
		String startHour = request.getParameter("startHour");
		String endHour = request.getParameter("endHour");
		
		String startMinute = request.getParameter("startMinute");
		String endMinute = request.getParameter("endMinute");
		
		String extendStart = startHour + ":" + startMinute + ":00";
		String extendEnd = endHour + ":" + endMinute + ":00";

		PermissionDBAO dbpm = null;
		try {
			dbpm=new PermissionDBAO();
			
			if(stcheck && etcheck)
			{
				if(dbpm.checkDateClash(userName, startTime, endTime) == false)
				{
					dbpm.insertPermission(startTime, endTime, extendStart, extendEnd, grantBy, userName, companyID);
				
					session.setAttribute("supmsg", "Permission was set successfully.");
				}
				else
				{
					Permission pm = new Permission();
				
					pm.setStartTime(startTime);
					pm.setEndTime(endTime);
					pm.setExtendStart(extendStart);
					pm.setExtendEnd(extendEnd);
					pm.setUserName(userName);
				
					session.setAttribute("permission", pm);
					session.setAttribute("supmsg", "A permission has already been set for this user for the date range entered.");
				}
			}
			else
			{
				session.setAttribute("damsg", "Please check that you have entered a valid date value for the start and end date.");
			}
			dbpm.remove();
			response.sendRedirect("SetUserPermission.jsp");
			
		}catch (Exception e) 
		{
			if(dbpm!=null)dbpm.remove();
			e.printStackTrace();
		}
		
	}

}
