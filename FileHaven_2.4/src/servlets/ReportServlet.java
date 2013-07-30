package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.Test;

import model.Department;
import model.FileReport;

import database.DepartmentDBAO;
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

	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		int dep=0;
		DepartmentDBAO d1=null;
		try {
			d1 = new DepartmentDBAO();
			dep = d1.getDepartmentID(request.getParameter("depValue"));
			d1.remove();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			d1.remove();
		}
		
		
		FileReportDBAO f1=null;
		try {
			f1 = new FileReportDBAO();
			ArrayList<FileReport> reports=f1.getFileReports(dep);
			f1.remove();
			session.setAttribute("report",reports);
			response.sendRedirect("Report.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			f1.remove();
			
		}
		
//		try{
//			Test t1 = new Test();
//			t1.writeChartToPDF(chart, width, height, fileName)
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
		
		
		
		
	}

}
