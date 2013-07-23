package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Department;
import model.FileReport;

import database.FileReportDBAO;

/**
 * Servlet implementation class ReportServlet
 */
public class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				String name=null;
				ArrayList<FileReport> reports=null;
				name = "Hello "+request.getParameter("department");
				System.out.println(name);
//				if(request.getParameter("user").toString().equals("")){
//				 name="Hello User";
//				}
				FileReportDBAO  f1=null;
				try {
					f1 = new FileReportDBAO();
					 reports=f1.getFileReports(0);
					 f1.remove();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					f1.remove();
					e.printStackTrace();
				}
				
				response.setContentType("text/html");  
				response.setCharacterEncoding("UTF-8"); 
				 PrintWriter out = response.getWriter();
				 out.println("<table class='table'>");
				 out.println("	<thead>");
				 out.println("		<tr>");
				 out.println("		<th>File Name</th>");
				 out.println("		<th>Name/IP Address</th>");
				 out.println("		<th>Position</th>");
				 out.println("		<th>Access Date</th>");
				 out.println("		<th>Access Time</th>");
				 out.println("	</tr>");
					
				 out.println("</thead>");
					 out.println("<tbody>");
				
					for(int i=0; i<reports.size();i++){
					
					
						out.println("<tr class='success'>");
						out.println("<td>"+reports.get(i).getFileName()+"</td>");
						out.println("<td>"+reports.get(i).getAccountID()+"/"+reports.get(i).getIPAddress()+"</td>");
						out.println("<td>"+reports.get(i).getFileName()+"</td>");
						out.println("<td>"+reports.get(i).getDownloadedDate().toString()+"</td>");
						out.println("<td>"+reports.get(i).getDownloadedTime().toString()+"</td>");
						out.println("</tr>");
				
					}
					
					
				
					out.println("</tbody>");

					out.println("</table>");
//				 out.println("<table class='display' id='table'>");
//				 out.println("<thead>");
//				 out.println("<tr>");
				 out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
