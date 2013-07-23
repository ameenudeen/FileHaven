package servlets;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;

import security.Security;

import model.Files;
import model.Privilege;
import model.Account; 
import model.Manager;
import model.FileReport;
import database.FileDataDBAO;
import database.FileReportDBAO;
import database.FileDBAO;

/**
 * Servlet implementation class DeleteFileServlet
 */
public class DeleteFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteFileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		
		Files file;
		FileDBAO fdb=null;
		FileDataDBAO fddb=null;
		FileReportDBAO frdb=null;
		
		GregorianCalendar c=new GregorianCalendar();
		String time=c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR_OF_DAY)+":"+(c.get(Calendar.MINUTE)<10?"0"+c.get(Calendar.MINUTE):c.get(Calendar.MINUTE));
		try{
			fddb=new FileDataDBAO();
			frdb=new FileReportDBAO();
			fdb=new FileDBAO();
			
			if(session.getAttribute("DeleteFile")==null){
				throw new Exception("Error occur.Please consult FileHaven Administrator");
			}
			if(session.getAttribute("LoggedInUser")==null){
				throw new Exception("Invalid log in.Please login");
			}
			
			Account login=(Account)session.getAttribute("LoggedInUser");
			
			if(login.getType()=='A'||login.getType()=='E'){
				throw new Exception("Access denied");
			}
			String base64enc=request.getParameter("ID");
			
			base64enc=base64enc.replace(' ', '+');
			if(base64enc!=null){
				if(base64enc.length()==0){

					throw new Exception("Error occur.Please consult FileHaven Administrator");
				}
			}
			else{
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
					if(!login.getUserName().equals(file.getAccountID())){
						throw new Exception("Access denied");
					}
				}
				else{
					boolean permit=false;
					for(Privilege p:pList){
						if(p.getDepartmentID()==((Manager)login).getDepartmentID()){
							permit=true;
							break;
						}
					}
					if(!permit){
						throw new Exception("Access denied");
					}
				}
			}
			if(fdb.updateFile(file, "DeletedTime", time)){
				
				file.setData(fddb.getFileData(file.getFileID()));
				FileReport r=new FileReport();
				InetAddress clientIp = InetAddress.getLocalHost();
				r.setIPAddress(clientIp.getHostAddress());
				r.setFileID(file.getFileID());
				r.setFileID(file.getFileID());
				r.setStatus("Delete");
				frdb.insertFileReport(r,login.getUserName());
				
				session.setAttribute("SelectedFile", fdb.getFile(file.getFileID()));
				fdb.remove();
				fddb.remove();
				frdb.remove();
				getServletContext().getRequestDispatcher("/ViewFile.jsp").forward(request,response);
			}
		}
		catch(Exception ex){
			fdb.remove();
			fddb.remove();
			frdb.remove();
			session.setAttribute("info_line1", "Delete File Failed.");
			session.setAttribute("info_line2", ex.getMessage());
			getServletContext().getRequestDispatcher("/Information.jsp").forward(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//check for permission
				//if owner = manager of ceo
				//or himself
				
	}

}
