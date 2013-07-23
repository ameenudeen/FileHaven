package servlets;

import java.io.ByteArrayInputStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import database.CompanyDBAO;
import database.FileDBAO;
import database.FileReportDBAO;

import model.Account;
import model.FileReport;
import model.Files;
import model.Privilege;
import security.Security;
/**
 * Servlet implementation class UploadFileServlet
 */
public class UploadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final int THRESHOLD_SIZE = 51*1024*1024;
	private static final int MAX_FILE_SIZE = 52*1024*1024;
	private static final int REQUEST_SIZE = 53*1024*1024;
	private static final int MAX_ALLOW_SIZE=50*1024*1024;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadFileServlet() {
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
		Files file=new Files();
		Account login;
		ArrayList<Privilege> privileges=new ArrayList<Privilege>();
		/*
		 * Encrypt Name
		 * Extension
		 * Data
		 */
		//check space
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(THRESHOLD_SIZE);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		HttpSession session=request.getSession();
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(MAX_FILE_SIZE);
		upload.setSizeMax(REQUEST_SIZE);
		
		try {
			List<FileItem> formItems = upload.parseRequest(request);
			Iterator<FileItem> iter = formItems.iterator();
			boolean double_encrypt=false;
			String key="";
			InputStream is=null;
			
			if(session.getAttribute("LoggedInUser")==null){
				throw new Exception("ERROR OCCUR");
			}
			login=(Account) session.getAttribute("LoggedInUser");
			if(login.getType()=='F'&&login.getType()!='M'&&login.getType()!='C')
				throw new Exception("Access denied");
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {
					String contentName=item.getName().substring(item.getName().lastIndexOf(".")+1);
					if(item.getName().lastIndexOf(".")==-1)
						contentName="*No Extension*";
					if(contentName==null){
						throw new Exception("Error occur. Please consult FileHaven administrator. Error: File Name error");
					}
					if(contentName.length()==0){
						throw new Exception("Error occur. Please consult FileHaven administrator. Error: File Name error");
					}
					file.setFileExtension(contentName);
					is =item.getInputStream();
					if(is.available()>=(MAX_ALLOW_SIZE)){
						throw new Exception("File size too big");
					}
					
					
					file.setHash(Security.hashStream(is, "SHA-256"));
					is.reset();
					file.setFileSize((double)(item.getSize())/1024);
					//in KB			
				}
				else{
					if(item.getFieldName().equals("FileName")){
						String fileName=item.getString();
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
					}
					else if(item.getFieldName().equals("department")){
						//decrypt the value into int
						//check whether department is found
						//if not found,throw Exception
						String id=new String(Security.decryptByte(Base64.decodeBase64(item.getString()), Security.generateAESKey("SYSTEM_KEY"), "AES"));
						
						Privilege p=new Privilege();
						p.setDepartmentID(Integer.parseInt(id));
						privileges.add(p);
					}
					else if(item.getFieldName().equals("encryption_checkbox")){
						if(item.getString().equals("on"))
							double_encrypt=true;
					}
					else if(item.getFieldName().equals("input_double_enc")){
						key=item.getString();
						if(!double_encrypt){
							//not selected to encrypt,do nothing
						}
						else if(key==null){
							throw new Exception("Error occur. Please consult FileHaven administrator. Error: Encryption key error");
							
						}
						else if(key.length()>10||key.length()==0){
							throw new Exception("Error occur. Please consult FileHaven administrator. Error: Encryption key error");
						}
					}
				}
			}//end iter
				file.setFileDeletedTime("NIL");
				file.setAccountID(login.getUserName());
				
				//double_encrypt
				if(double_encrypt){
					file.setEncrypted("TRUE");
					file.setData(new ByteArrayInputStream(Security.encryptStream(is,Security.generateAESKey(key), "AES")));
				}
				else{
					file.setEncrypted("FALSE");
					file.setData(new ByteArrayInputStream(Security.encryptStream(is, Security.generateAESKey("SYSTEM_KEY"), "AES")));
					
				}
				
				GregorianCalendar c=new GregorianCalendar();
				String time=c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR_OF_DAY)+":"+(c.get(Calendar.MINUTE)<10?"0"+c.get(Calendar.MINUTE):c.get(Calendar.MINUTE));
				file.setFileUploadedTime(time);
			if(!privileges.isEmpty()){
				file.setPrivilege(privileges);
			}
			CompanyDBAO cdb=new CompanyDBAO();
			int space=cdb.getCompanyDetails(login.getCompanyID()).getStorageSpace();
			cdb.remove();
			space*=1024;
			space*=1024;
			//in GB
			FileDBAO fdb=new FileDBAO();
			ArrayList<Files> files=fdb.getFileList(login.getUserName());
			double used_space=file.getFileSize();
			for(Files f:files){
				used_space+=f.getFileSize();
			}
			
			if(used_space>=space){
				fdb.remove();
				throw new Exception("Space not enough. Please check company file list.");
			}
			if(!fdb.createFile(file)){
				fdb.remove();
				throw new Exception("File encounter. Please consult FileHaven administrator");
			}
			is.close();
			FileReport r=new FileReport();
			InetAddress clientIp = InetAddress.getLocalHost();
			r.setIPAddress(clientIp.getHostAddress());
			r.setFileID(file.getFileID());
			r.setFileID(file.getFileID());
			r.setStatus("Upload");
			FileReportDBAO frdb=new FileReportDBAO();
			frdb.insertFileReport(r, login.getUserName());
			
			frdb.remove();
			
			session.setAttribute("SelectedFile",fdb.getFile(file.getFileID()));
			fdb.remove();
			getServletContext().getRequestDispatcher("/ViewFile.jsp").forward(request,response);
		} 
		catch (Exception ex) {
			session.setAttribute("info_line1", "Upload File Failed.");
			session.setAttribute("info_line2", ex.getMessage());
			getServletContext().getRequestDispatcher("/Information.jsp").forward(request,response);
		}
		
	}

}
