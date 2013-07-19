package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.CompanyDBAO;

/**
 * Servlet implementation class CreateCompanyServlet
 */
public class CreateCompanyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateCompanyServlet() {
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
		session.removeAttribute("commsg");
		session.removeAttribute("ssmsg");
		
		String companyName = request.getParameter("companyName");
		String address = request.getParameter("address");
		int storageSpace = 0;
		boolean intCheck = false;
		
		try{
			CompanyDBAO dbcom = new CompanyDBAO();
			
			if(companyName != "" && address != "")
			{
				
				try{
					storageSpace = Integer.parseInt(request.getParameter("storageSpace"));
					intCheck = true;
				}catch(Exception ie)
				{
					
				}
				
				if(intCheck)
				{
					dbcom.insertCompany(companyName, address, storageSpace);
				
					session.setAttribute("commsg", "Company created successfully.");
				}
				else
				{
					session.setAttribute("ssmsg", "The value you have entered is not a valid number.");
				}
			}
			else
			{
				session.setAttribute("commsg", "Please fill up all the fields.");
			}
			
			response.sendRedirect("CreateCompany.jsp");
			
		}catch(Exception e)
		{	
			e.printStackTrace();
		}
		
	}

}
