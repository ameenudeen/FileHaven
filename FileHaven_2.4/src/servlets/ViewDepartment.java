package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;

import database.DepartmentDBAO;

/**
 * Servlet implementation class ViewDepartment
 */
public class ViewDepartment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewDepartment() {
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
		Account currentUser = (Account) session.getAttribute("LoggedInUser");
		System.out.println("Hello");
		System.out.println(request.getParameter("hiddenField"));
		String departmentName=request.getParameter("hiddenField");
		session.setAttribute("departmentName",request.getParameter("hiddenField"));
		DepartmentDBAO d1=null;
		try {
			
			d1 = new DepartmentDBAO();
			int departmentId=d1.getDepartmentIDOfCompany(departmentName, currentUser.getUserName());
			session.setAttribute("departmentId", departmentId);
			d1.remove();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if(d1!=null)d1.remove();
			e.printStackTrace();
		}
		
		response.sendRedirect("/FileHaven/UpdateDepartment.jsp");
	}

}
