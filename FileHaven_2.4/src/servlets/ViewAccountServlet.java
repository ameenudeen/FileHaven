package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.*;

import model.*;


/**
 * Servlet implementation class ViewAccountServlet
 */
public class ViewAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewAccountServlet() {
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
		session.removeAttribute("accList");
		session.removeAttribute("deptList");
		session.removeAttribute("tempList");
		
		Account acc = (Account) session.getAttribute("LoggedInUser");
		
		String typeFilter = request.getParameter("type");
		String deptFilter = request.getParameter("departmentID");
		
		try
		{
			AccountDBAO dba = new AccountDBAO();
			DepartmentDBAO dbdt = new DepartmentDBAO();
			
			ArrayList<Account> accList = new ArrayList<Account>();
			String wStmt = "";
			
			if(deptFilter == null || deptFilter.equals("All"))
			{
				
			}
			else
			{
				wStmt = "AND DepartmentID = " + deptFilter;
			}
			
			if(acc.getType() == 'C')
			{
				if(typeFilter == null || typeFilter.equals("All"))
				{
					ArrayList<Account> tempList = dba.getCompanyEmployees(acc.getCompanyID(), wStmt);
					accList.addAll(tempList);
					tempList.clear();
					
					tempList = dba.getCompanyFilemanagers(acc.getCompanyID(), wStmt);
					accList.addAll(tempList);
					tempList.clear();
					
					tempList = dba.getCompanyManagers(acc.getCompanyID(), wStmt);
					accList.addAll(tempList);
				}
				else if(typeFilter.equals("E"))
				{
					accList = dba.getCompanyEmployees(acc.getCompanyID(), wStmt);
				}
				else if(typeFilter.equals("F"))
				{
					accList = dba.getCompanyFilemanagers(acc.getCompanyID(), wStmt);
				}
				else
				{
					accList = dba.getCompanyManagers(acc.getCompanyID(), wStmt);
				}
			}
			else
			{
				ManagerDBAO dbm = new ManagerDBAO();
				int deptID = dbm.getManagerDetails(acc.getUserName()).getDepartmentID();
				wStmt = "AND DepartmentID = " + deptID;
				
				accList = dba.getCompanyEmployees(acc.getCompanyID(), wStmt);
			}
			
			ArrayList<Department> tempList = new ArrayList<Department>();
			tempList = dbdt.getCompanyDepartment(acc.getUserName());
			
			ArrayList<String> deptList = new ArrayList<String>();
			
			if(tempList.size() == 0)
			{
				for(int i=0; i<accList.size(); i++)
				{
					deptList.add("None");
				}
			}
			else
			{
				for(int i=0; i<accList.size(); i++)
				{
					int departmentID = 0;
					
					if(accList.get(i).getType() == 'E')
					{
						departmentID = ((Employee) accList.get(i)).getDepartmentID();
					}
					else if(accList.get(i).getType() == 'F')
					{
						departmentID = ((Filemanager) accList.get(i)).getDepartmentID();
					}
					else
					{
						departmentID = ((Manager) accList.get(i)).getDepartmentID();
					}
					
					for(int k=0; k<tempList.size(); k++)
					{
						Department tempDepartment = tempList.get(k);
						if(departmentID == 0)
						{
							deptList.add("None");
							break;
						}
						else if(departmentID == tempDepartment.getId())
						{
							deptList.add(tempDepartment.getDepartmentName());
							break;
						}
					}
				}
			}
			
			session.setAttribute("tempList", tempList);
			session.setAttribute("deptList", deptList);
			session.setAttribute("accList", accList);
			
			session.setAttribute("typeFilter", typeFilter);
			session.setAttribute("deptFilter", deptFilter);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		response.sendRedirect("ViewAccount.jsp");
	}

}
