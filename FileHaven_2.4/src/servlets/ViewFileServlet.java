package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;
import model.Employee;
import model.Files;
import model.Manager;
import model.Privilege;

import org.apache.commons.codec.binary.Base64;
import org.apache.catalina.Session;

import security.Security;

import database.AccountDBAO;
import database.EmployeeDBAO;
import database.FileDBAO;
import database.ManagerDBAO;
/**
 * Servlet implementation class ViewFileServlet
 */
public class ViewFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewFileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		FileDBAO fdb=null;
		Files file=null;
		try{
			fdb=new FileDBAO();
			if(session.getAttribute("LoggedInUser")==null){
				throw new Exception("Invalid log in.Please login");
			}

			Account login=(Account)session.getAttribute("LoggedInUser");
			
			if(login.getType()=='A'){
				throw new Exception("Access denied");
			}
			if(request.getParameter("FileID")==null)
				throw new Exception("Invalid File ID");
			String base64enc=request.getParameter("FileID");
			base64enc=base64enc.replace(' ', '+');
			//System.out.println(Base64.decodeBase64(base64enc).length);
			base64enc=new String(Security.decryptByte(Base64.decodeBase64(base64enc), Security.generateAESKey("SYSTEM_KEY"), "AES"));
			file=fdb.getFile(Integer.parseInt(base64enc));
			if(file==null){
				throw new Exception("File not found");
			}

			if(login.getType()!='C'&&login.getType()!='F'){
				ArrayList<Privilege> pList=file.getPrivilege();
				if(pList==null)
					pList=new ArrayList<Privilege>();
				if(pList.isEmpty()){
					if(!login.getUserName().equals(file.getAccountID())){
						throw new Exception("Access denied");
					}
				}
				else{
					boolean permit=false;
					int departmentID=-1;
					if(login.getType()=='M'){
						ManagerDBAO mdb=new ManagerDBAO();
						departmentID=(mdb.getManagerDetails(login.getUserName())).getDepartmentID();
						mdb.remove();
					}
					else if(login.getType()=='E'){
						EmployeeDBAO edb=new EmployeeDBAO();
						departmentID=(edb.getEmployeeDetails(login.getUserName())).getDepartmentID();
						edb.remove();
					}
					for(Privilege p:pList){
						if(p.getDepartmentID()==departmentID){
							permit=true;
							break;
						}
					}
					if(!permit)
						throw new Exception("Access denied");
				}
			}
			
			session.setAttribute("SelectedFile",file);
			fdb.remove();
			getServletContext().getRequestDispatcher("/ViewFile.jsp").forward(request,response);
		}
		catch(Exception ex){
			//handle exception
			if(fdb!=null)fdb.remove();
			session.setAttribute("info_line1", "View File Failed.");
			session.setAttribute("info_line2", ex.getMessage());
			getServletContext().getRequestDispatcher("/Information.jsp").forward(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
