package servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;
import model.Department;
import model.Employee;
import model.Manager;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import utils.FileUploadService;
import database.DepartmentDBAO;
import database.EmployeeDBAO;
import database.ManagerDBAO;

/**
 * Servlet implementation class UpdateDepartment
 */
public class UpdateDepartment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 5; // 5MB
	private static final int REQUEST_SIZE = 1024 * 1024 * 10; // 10MB
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateDepartment() {
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
		// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				System.out.println("Hooray");
				HttpSession session = request.getSession(true);
				// System.out.println(request.getParameter("departmentName"));
				String[] ItemNames;
				String departmentName = null;
				String departmentDescription = null;
				String departmentLogo = null;
				Account currentUser = (Account) session.getAttribute("LoggedInUser");
				ArrayList<String> departmentManagers = new ArrayList<String>();
				ArrayList<String> departmentEmployees = new ArrayList<String>();

				// ItemNames = request.getParameterValues("sometext");
				// for(int i = 0; i < ItemNames.length; i++){
				// System.out.println(ItemNames[i]);
				// }

				
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(THRESHOLD_SIZE);
				factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setFileSizeMax(MAX_FILE_SIZE);
				upload.setSizeMax(REQUEST_SIZE);

				List formItems;
				try {
					formItems = upload.parseRequest(request);
					Iterator iter = formItems.iterator();

					while (iter.hasNext()) {
						FileItem item = (FileItem) iter.next();
						if (!item.isFormField()) {
							System.out.println("pic");	
							
							Scanner sc = new Scanner(item.getContentType());
							sc.useDelimiter("/");
							if(sc.next().equals("application")){
								String contentType = sc.next();

								

								departmentLogo = "https://filehavendata.s3.amazonaws.com/Department_Logo/thumbnailpic.jpg";
							}
							
							else{
							String contentType = sc.next();

							InputStream is = item.getInputStream();

							String url = "";
							FileUploadService uploadService = new FileUploadService();
							url = uploadService.upload("Department_Logo",
									departmentName + ".jpg", is, contentType);

							departmentLogo = url;
							System.out.println(url);
							}
						}

						else {
							if (item.getFieldName().equals("departmentName")) {
								departmentName = item.getString();
								
								System.out
										.println("Department name: " + departmentName);
								System.out.println("Current User"
										+ currentUser.getName());
								
							} else if (item.getFieldName().equals("sometext")) {
								departmentManagers.add(item.getString());
								for (int i = 0; i < departmentManagers.size(); i++) {
									System.out.println("Final managersds"
											+ departmentManagers.get(i));
								}

							}

							else if (item.getFieldName().equals("employees")) {
								departmentEmployees.add(item.getString());
								for (int i = 0; i < departmentEmployees.size(); i++) {
									System.out.println("Final Employees"
											+ departmentEmployees.get(i));
								}

							}

							else if (item.getFieldName()
									.equals("departmentDescription")) {
								departmentDescription = item.getString();
								System.out.println("Department Description"
										+ departmentDescription);
							}

						}

					}

					Department d1 = new Department();
					d1.setDepartmentName(departmentName);
					d1.setDepartmentLogo(departmentLogo);
					d1.setDepartmentDescription(departmentDescription);

					Employee e1 = new Employee();
					e1.setEmployees(departmentEmployees);

					Manager m1 = new Manager();
					m1.setManagers(departmentManagers);
					
					EmployeeDBAO employee = new EmployeeDBAO();
					int dID=(Integer)session.getAttribute("departmentId");
					employee.removeEmployeesFromDepartment(dID, currentUser.getCompanyID());
					
					ManagerDBAO manager = new ManagerDBAO();
					manager.removeManagersFromDepartment(dID, currentUser.getCompanyID());

					DepartmentDBAO db;
					try {
						db = new DepartmentDBAO();
						int departmentId= (Integer)session.getAttribute("departmentId");
						db.updateDepartment(d1, currentUser.getName(),departmentId);
						
						db.updateEmployeeDepartment(e1,departmentName,currentUser);
						//db.updateEmployeesDepartment(e1,departmentName,currentUser.getName(),currentUser);
						
						ManagerDBAO managers = new ManagerDBAO();
						managers.updateManagerDepartment(m1, departmentName, currentUser);
						//manager.updateManagerssDepartment(m1, departmentName, currentUser.getName(),currentUser);
						System.out.println("Success");
						PrintWriter out = response.getWriter();  
						response.setContentType("text/html");  
						out.println("<script type=\"text/javascript\">");  
						out.println("alert('Department Successfully Updated');");  
						out.println("var hosturl = location.origin + '/' + location.pathname.split('/')[1] + '/';");
						out.println("window.location.href=hosturl+'ViewDepartment.jsp';");
						out.println("</script>");
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}

}
