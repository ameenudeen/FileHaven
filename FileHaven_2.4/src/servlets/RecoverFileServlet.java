package servlets;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;

import security.Security;

import model.Account;
import model.Employee;
import model.Files;
import model.Manager;
import model.Privilege;
import model.FileReport;
import database.FileReportDBAO;
import database.FileDBAO;

/**
 * Servlet implementation class RecoverFileServlet
 */
public class RecoverFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecoverFileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check for permission
		//if owner = manager of ceo
		//or himself
		HttpSession session=request.getSession();
		
		//check company space
		Files file;

		FileReportDBAO frdb=null;
		FileDBAO fdb=null;
		
		try{
			frdb=new FileReportDBAO();
			fdb=new FileDBAO();
			
			if(session.getAttribute("RecoverFile")==null){
				throw new Exception("Error occur.Please consult FileHaven Administrator");
			}
			if(session.getAttribute("LoggedInUser")==null){
				throw new Exception("Invalid log in.Please login");
			}

			Account login=(Account)session.getAttribute("LoggedInUser");
			
			if(login.getType()=='A'){
				throw new Exception("Access denied");
			}
			
			String base64enc=request.getParameter("ID");
			base64enc=base64enc.replace(' ', '+');
			if(base64enc!=null){
				if(base64enc.length()==0){

					throw new Exception("Error occur.Please consult FileHaven Administrator");
				}
			}else{

				throw new Exception("Error occur.Please consult FileHaven Administrator");
			}
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
					if(login.getUserName().equals(file.getAccountID())){
						
					}
					else{
						fdb.remove();
						throw new Exception("Access denied");
					}
				}
				else{
					boolean permit=false;
					int departmentID=-1;
					if(login.getType()=='M')
						departmentID=((Manager)login).getDepartmentID();
					else if(login.getType()=='E')
						departmentID=((Employee)login).getDepartmentID();
					for(Privilege p:pList){
						if(p.getDepartmentID()==departmentID){
							permit=true;
							break;
						}
					}
					if(!permit){
						fdb.remove();
						throw new Exception("Access denied");
					}
				}
			}
			if(fdb.updateFile(file, "DeletedTime", "NIL")){
				FileReport r=new FileReport();
				InetAddress clientIp = InetAddress.getLocalHost();
				r.setIPAddress(clientIp.getHostAddress());
				r.setFileID(file.getFileID());
				r.setFileID(file.getFileID());
				r.setStatus("Recover");
				r.setFileName(file.getFileName());
				frdb.insertFileReport(r, login.getUserName(),login);
				session.setAttribute("SelectedFile", fdb.getFile(file.getFileID()));
				
				fdb.remove();
				frdb.remove();
				getServletContext().getRequestDispatcher("/ViewFile.jsp").forward(request,response);
			}
		}
		catch(Exception ex){
			if(fdb!=null)fdb.remove();
			if(frdb!=null)frdb.remove();
			session.setAttribute("info_line1", "Recover File Failed.");
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
