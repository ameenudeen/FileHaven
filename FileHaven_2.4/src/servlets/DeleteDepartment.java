package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.DepartmentDBAO;
import database.EmployeeDBAO;
import database.ManagerDBAO;

import model.Account;

/**
 * Servlet implementation class DeleteDepartment
 */
public class DeleteDepartment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteDepartment() {
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
		System.out.println("Delete Department");
		HttpSession session = request.getSession(true);
		String departmentName=request.getParameter("departmentName");
		Account currentUser=(Account) session.getAttribute("LoggedInUser");
		
		
		DepartmentDBAO d1=null;
		ManagerDBAO m1=null;
		EmployeeDBAO e1=null;
		
		try {
			d1 = new DepartmentDBAO();
			m1 = new ManagerDBAO();
			e1 = new EmployeeDBAO();
			
			int departmentId=d1.getDepartmentIDOfCompany(departmentName, currentUser.getUserName());
			
			m1.removeManagersFromDepartment(departmentId, currentUser.getCompanyID());
			e1.removeEmployeesFromDepartment(departmentId, currentUser.getCompanyID());
			d1.deleteDepartment(departmentName, currentUser.getCompanyID());
			
			System.out.println("Success");
			
			d1.remove();
			m1.remove();
			e1.remove();
			response.sendRedirect("/FileHaven/ViewDepartment.jsp");
			
		} catch (Exception e) {
			if(d1!=null)d1.remove();
			if(m1!=null)m1.remove();
			if(e1!=null)e1.remove();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
