package servlets;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Files;
import model.Privilege;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import security.Security;
import database.FileDBAO;

/**
 * Servlet implementation class HashCheckServlet
 */
public class HashCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final int THRESHOLD_SIZE = 51*1024*1024;
	private static final int MAX_FILE_SIZE = 52*1024*1024;
	private static final int REQUEST_SIZE = 53*1024*1024;
	private static final int MAX_ALLOW_SIZE=50*1024*1024;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HashCheckServlet() {
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
		Files originaFile=null;
		boolean result=false;
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
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(MAX_FILE_SIZE);
		upload.setSizeMax(REQUEST_SIZE);
		HttpSession session=request.getSession();
		FileDBAO fdb=null;
		
		try {
			List<FileItem> formItems = upload.parseRequest(request);
			Iterator<FileItem> iter = formItems.iterator();
			InputStream is=null;
			fdb=new FileDBAO();
			
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();				if (!item.isFormField()) {
					String contentName=item.getName().substring(item.getName().lastIndexOf(".")+1);
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
					if(item.getFieldName().equals("hidden_file_id")){
						String base64enc=item.getString();
						base64enc=base64enc.replace(' ', '+');
						base64enc=new String(Security.decryptByte(Base64.decodeBase64(base64enc), Security.generateAESKey("SYSTEM_KEY"), "AES"));
						originaFile=fdb.getFile(Integer.parseInt(base64enc));
						if(originaFile==null){
							throw new Exception("Original file not found");
						}
					}
				}
			}//end iter
			if(file.getHash().equals(originaFile.getHash())){
				result=true;
			}
			else
				result=false;
			is.close();
			fdb.remove();
			session.setAttribute("info_line1", "File compare result : "+(result?"Same":"Not Same"));
			session.setAttribute("info_line2", "Original File SHA-256 HASH :");
			session.setAttribute("info_line3", originaFile.getHash());
			session.setAttribute("info_line4", "Comparing File SHA-256 HASH :");
			session.setAttribute("info_line5", file.getHash());
			
			getServletContext().getRequestDispatcher("/Information.jsp").forward(request,response);
		} 
		catch (Exception ex) {
			if(fdb!=null)fdb.remove();
			session.setAttribute("info_line1", "File access denied.");
			session.setAttribute("info_line2", ex.getMessage());
			getServletContext().getRequestDispatcher("/Information.jsp").forward(request,response);
		}
		
	}

}
