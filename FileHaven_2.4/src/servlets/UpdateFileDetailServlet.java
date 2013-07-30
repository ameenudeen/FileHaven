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
import model.FileReport;
import model.Files;
import database.FileDBAO;
import database.FileDataDBAO;
import database.FileReportDBAO;
import database.ManagerDBAO;
import model.Privilege;
import database.PrivilegeDBAO;
/**
 * Servlet implementation class UpdateFileDetailServlet
 */
public class UpdateFileDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateFileDetailServlet() {
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
		HttpSession session=request.getSession();
		ArrayList<Privilege> privileges=new ArrayList<Privilege>();
		
		
		Files file;
		FileDBAO fdb = null;
		PrivilegeDBAO pdb=null;
		FileReportDBAO frdb=null;
		String fileName=request.getParameter("FileName");
		try{
			frdb=new FileReportDBAO();
			fdb=new FileDBAO();
			pdb=new PrivilegeDBAO();
			if(session.getAttribute("UpdateFile")==null){
				throw new Exception("Error occur.Please consult FileHaven Administrator");
			}
			if(session.getAttribute("LoggedInUser")==null){
				throw new Exception("Invalid log in.Please login");
			}

			Account login=(Account)session.getAttribute("LoggedInUser");
			
			if(login.getType()=='A'||login.getType()=='E'){
				throw new Exception("Access denied");
			}
			
			
			String base64enc=request.getParameter("hidden_file_id");
			
			if(base64enc!=null){
				if(base64enc.length()==0){
					
					throw new Exception("Error occur.Please consult FileHaven Administrator");
				}
			}
			else{
				throw new Exception("Error occur.Please consult FileHaven Administrator");}
			base64enc=new String(Security.decryptByte(Base64.decodeBase64(base64enc), Security.generateAESKey("SYSTEM_KEY"), "AES"));
			
			file=fdb.getFile(Integer.parseInt(base64enc));

			if(login.getType()!='C'&&login.getType()!='F'){
				ArrayList<Privilege> pList=file.getPrivilege();
				if(pList==null)
					pList=new ArrayList<Privilege>();
				if(pList.isEmpty()){
					if(login.getUserName().equals(file.getAccountID())){
						
					}
					else{
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
			if(fileName==null){
				throw new Exception("Error occur. Please consult FileHaven administrator. Error: File name error");
			}
			if(fileName.length()>20||fileName.length()==0){
				throw new Exception("Error occur. Please consult FileHaven administrator. Error: File name error");
			}
			for(int i=0;i<fileName.length();i++){
				if(!(Character.isAlphabetic(fileName.charAt(i))||Character.isDigit(fileName.charAt(i)))){
					throw new Exception("Error occur. Please consult FileHaven administrator. Error: File name error");
				}
			}
			file.setFileName(fileName);
			
			if(file.getPrivilege()!=null){
				for(int i=0;i<file.getPrivilege().size();i++)
					pdb.deletePrivilege(file.getFileID());
			}
			String[] departments=request.getParameterValues("department");
			if(departments!=null){
				for(String val:departments){
					String id=new String(Security.decryptByte(Base64.decodeBase64(val), Security.generateAESKey("SYSTEM_KEY"), "AES"));
					Privilege p=new Privilege();
					p.setFileID(file.getFileID());
					p.setDepartmentID(Integer.parseInt(id));
					privileges.add(p);
				}
			}
			if(!fdb.updateFile(file, "Name", fileName)){
				throw new Exception("Error occur. Please consult FileHaven administrator. Error: File name error");
			}
			for(Privilege p:privileges){
				if(!pdb.createPrivilege(p)){
					throw new Exception("Error occur. Please consult FileHaven administrator. Error: File name error");
				}
			}

			FileReport r=new FileReport();
			InetAddress clientIp = InetAddress.getLocalHost();
			r.setIPAddress(clientIp.getHostAddress());
			r.setFileID(file.getFileID());
			r.setFileID(file.getFileID());
			r.setStatus("Update");
			r.setFileName(file.getFileName());
			r.setAccountID(file.getAccountID());
			r.setUserName(login.getUserName());
			frdb.insertFileReport(r);
			session.setAttribute("SelectedFile", fdb.getFile(file.getFileID()));
			
			pdb.remove();
			frdb.remove();
			fdb.remove();
			getServletContext().getRequestDispatcher("/ViewFile.jsp").forward(request,response);
		}
		catch(Exception ex){
			if(pdb!=null)pdb.remove();
			if(frdb!=null)frdb.remove();
			if(fdb!=null)fdb.remove();
			session.setAttribute("info_line1", "Update Failed.");
			session.setAttribute("info_line2", ex.getMessage());
			getServletContext().getRequestDispatcher("/Information.jsp").forward(request,response);
		}
	}

}
