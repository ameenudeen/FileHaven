package servlets;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.BadPaddingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;
import model.Files;
import model.Privilege;
import model.FileReport;
import database.FileReportDBAO;
import database.FileDataDBAO;
import database.EmployeeDBAO;
import database.FileDBAO;
import database.ManagerDBAO;
import security.Security;
/**
 * Servlet implementation class Download
 * */
public class DownloadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadFileServlet() {
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
		/*
		 * Decrypt from cookie
		 */
		String key=request.getParameter("input_double_enc");
		
		HttpSession session=request.getSession();
		
		Files file=null;
		FileDBAO fdb=null;
		FileDataDBAO fddb=null;
		FileReportDBAO frdb=null;
		
		try{
			frdb=new FileReportDBAO();
			fdb=new FileDBAO();
			fddb=new FileDataDBAO();
			
			if(session.getAttribute("DownloadFile")==null){
				throw new Exception("Error occur.Please consult FileHaven Administrator");
			}
			if(session.getAttribute("LoggedInUser")==null){
				throw new Exception("Invalid log in.Please login");
			}

			Account login=(Account)session.getAttribute("LoggedInUser");
			
			if(login.getType()=='A'){
				throw new Exception("Access denied");
			}
			
			String base64enc=request.getParameter("hidden_file_id");
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
			if(!file.getFileDeletedTime().equals("NIL")){
				throw new Exception("Access denied:File deleted");
			}
			
			file.setData(fddb.getFileData(file.getFileID()));
			
			FileReport r=new FileReport();
			String ipAddress = request.getHeader("X-FORWARDED-FOR");  
			if (ipAddress == null) {  
				ipAddress = request.getRemoteAddr();  
			}
			InetAddress clientIp = InetAddress.getLocalHost();
			r.setIPAddress(clientIp.getHostAddress());
			r.setFileID(file.getFileID());
			r.setStatus("Download");
			frdb.insertFileReport(r,login.getUserName());
			byte[] data;
			if(file.getEncrypted().equals("TRUE")){
				//System.out.println(Security.generateAESKey(key, 1).length);
				data=Security.decryptStream(file.getData(), Security.generateAESKey(key),"AES");
				
				//data=Security.decryptByte(data,Security.generateAESKey("SYSTEM_KEY",0),"AES");
				//data=Security.decryptStream(file.getData(),Security.generateAESKey("SYSTEM_KEY",0),"AES");
			}
			else
				data=Security.decryptStream(file.getData(),Security.generateAESKey("SYSTEM_KEY"),"AES");
			
			//save to report
			ServletOutputStream out = response.getOutputStream();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment;filename="+file.getFileName()+(file.getFileExtension().equals("*No Extension*")?"":"."+file.getFileExtension()));
			fdb.remove();
			frdb.remove();
			fddb.remove();
			
			for(byte b:data){
				out.write(b);
			}
			out.flush(); 
			out.close();
		}
		catch(BadPaddingException ex){
			if(fdb!=null)
				fdb.remove();
			if(frdb!=null)
				frdb.remove();
			if(fddb!=null)
				fddb.remove();
			
			//Badpaddingexception for wrong password
			//save to bad password report
			ServletOutputStream out = response.getOutputStream();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition","attachment;filename="+file.getFileName()+"."+file.getFileExtension());
			int k=0;
			while((k=file.getData().read())!=-1){
				out.write(k);
			}
			out.flush();
			out.close();
		}
		catch(Exception ex){
			if(fdb!=null)
				fdb.remove();
			if(frdb!=null)
				frdb.remove();
			if(fddb!=null)
				fddb.remove();
			session.setAttribute("info_line1", "Download File Failed.");
			session.setAttribute("info_line2", ex.getMessage());
			getServletContext().getRequestDispatcher("/Information.jsp").forward(request,response);
		}
	}
}
