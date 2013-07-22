package servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Company;
import model.Files;
import model.Privilege;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import security.Security;
import utils.FileUploadService;

import database.CompanyDBAO;
import database.NetworkDBAO;

/**
 * Servlet implementation class CreateCompanyServlet
 */
public class CreateCompanyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 5; // 5MB
	private static final int REQUEST_SIZE = 1024 * 1024 * 10; // 10MB
       
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
		session.removeAttribute("nwmsg");
		
		String companyName = "";
		String address = "";
		int storageSpace = 0;
		String companyLogo = "";
		
		boolean intCheck = false;
		boolean emptyCheck = false;
		boolean networkCheck = false;
		
		String IPAddressStart = "";
		String IPAddressEnd = "";
		String subnetMask = "";
		
		try{
					Company com = new Company();
					InputStream is = null;
					
					DiskFileItemFactory factory = new DiskFileItemFactory();
					factory.setSizeThreshold(THRESHOLD_SIZE);
					factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

					ServletFileUpload upload = new ServletFileUpload(factory);
					upload.setFileSizeMax(MAX_FILE_SIZE);
					upload.setSizeMax(REQUEST_SIZE);
					
					List<FileItem> formItems = upload.parseRequest(request);
					Iterator<FileItem> iter = formItems.iterator();
					
					while (iter.hasNext()) {
						FileItem item = (FileItem) iter.next();
						if (!item.isFormField()) {
							
							String temp = companyName.replaceAll(" ", "_");
							
							Scanner sc = new Scanner(item.getContentType());
							sc.useDelimiter(".");
							sc.next();
							String contentType = sc.next();
							
							is = item.getInputStream();
							
							String url = "";
							FileUploadService uploadService = new FileUploadService();
							url = uploadService.upload("Company_Logo",
									temp + ".jpg", is, contentType);

							companyLogo = url;	
							
							is.reset();
						}
						else{
							if(item.getFieldName().equals("companyName")){
								companyName = item.getString();
								
								if(companyName.equals(""))
								{
									session.setAttribute("commsg", "Please fill up all the fields.");
								}
								else
								{
									emptyCheck = true;
								}
							}
							else if(item.getFieldName().equals("address")){
								address = item.getString();
								
								if(address.equals(""))
								{
									emptyCheck = false;
								}
								else
								{
									session.setAttribute("commsg", "Please fill up all the fields.");
								}
								
							}
							else if(item.getFieldName().equals("storageSpace")){
								
								try {
									storageSpace = Integer.parseInt(item.getString());
									intCheck = true;
								}catch (Exception sse)
								{
									session.setAttribute("ssmsg", "The value you have entered is not a valid number.");
									sse.printStackTrace();
								}
							}
							else if(item.getFieldName().equals("ipStart")){
								IPAddressStart = item.getString();
								
								Pattern p = Pattern.compile(
										"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
								        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
								        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
								        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
							    Matcher m = p.matcher(IPAddressStart);
							    if(m.matches())
							    {
							    	networkCheck = true;
							    }
							    else
							    {
							    	session.setAttribute("nwmsg", "Please enter a valid ip address and subnet mask.");
							    }
							}
							else if(item.getFieldName().equals("ipEnd")){
								IPAddressEnd = item.getString();
								
								Pattern p = Pattern.compile(
										"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
								        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
								        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
								        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
							    Matcher m = p.matcher(IPAddressEnd);
							    if(m.matches())
							    {
							    	networkCheck = true;
							    }
							    else
							    {
							    	networkCheck = false;
							    	session.setAttribute("nwmsg", "Please enter a valid ip address and subnet mask.");
							    }
							}
							else if(item.getFieldName().equals("subnetMask")){
								subnetMask = item.getString();
								
								if (subnetMask.matches("[0-9]+") && subnetMask.length() <= 2 && subnetMask.length() > 0)
								{
									networkCheck = true;
								}
								else
								{
									networkCheck = false;
									session.setAttribute("nwmsg", "Please enter a valid ip address and subnet mask.");
								}
							}
						}
					}
					
					if(emptyCheck && intCheck && networkCheck)
					{
						CompanyDBAO dbcom = new CompanyDBAO();
						dbcom.insertCompany(companyName, address, storageSpace, companyLogo);
				
						int companyID = dbcom.getCompanyID(companyName);
						
						NetworkDBAO dbnw = new NetworkDBAO();
						dbnw.insertNetwork(IPAddressStart, IPAddressEnd, subnetMask, companyID);
						
						session.setAttribute("commsg", "Company created successfully.");
					}
					
					response.sendRedirect("CreateCompany.jsp");
			
		}catch(Exception e)
		{	
			e.printStackTrace();
		}
		
	}

}
