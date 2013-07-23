package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.CompanyDBAO;
import model.Company;

/**
 * Servlet implementation class RetrieveCompanyListServlet
 */
public class RetrieveCompanyListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RetrieveCompanyListServlet() {
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
		session.removeAttribute("msg");
		CompanyDBAO dbcom=null;
		
		try {
			dbcom = new CompanyDBAO();
			
			ArrayList<Company> companyList = dbcom.getCompanysList();
			
			session.setAttribute("companyList", companyList);
			dbcom.remove();
			response.sendRedirect("CreateAccount.jsp");
		}catch(Exception e)
		{
			if(dbcom!=null)dbcom.remove();
			e.printStackTrace();
		}
	}

}
