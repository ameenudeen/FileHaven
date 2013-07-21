package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;
import database.DepartmentDBAO;
import database.NotificationDBAO;

/**
 * Servlet implementation class DepartmentAvailability
 */
public class DepartmentAvailability extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DepartmentAvailability() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(true);
		Account account=(Account)session.getAttribute("LoggedInUser");
		boolean status=false;
				
		try {
			DepartmentDBAO d1 = new DepartmentDBAO();
			status=d1.checkAvailability(request.getParameter("user"), account.getCompanyID());
			System.out.println(status);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		response.setContentType("text/plain");  
		response.setCharacterEncoding("UTF-8"); 
		
		if(status==true)
		{
			response.getWriter().write("Not available"); 
		}
		
		else if(status==false){
			response.getWriter().write("Available"); 
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
