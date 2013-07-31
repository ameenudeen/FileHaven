package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.PieChartDemo;
import utils.Test;

import model.Account;
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
		Account currentUser = (Account)session.getAttribute("LoggedInUser");
		FileReportDBAO f1=null;
		try {
			f1 = new FileReportDBAO();
			ArrayList<FileReport> reports=f1.getFileReports(Integer.parseInt(request.getParameter("depValue")));
			f1.remove();
			session.setAttribute("report",reports);

//			Test t1 = new Test();
//			t1.writeChartToPDF(PieChartDemo.generatePieChart((ArrayList<FileReport>)session.getAttribute("report")), 500, 400, "\\FileHaven\\WebContent\\"+currentUser.getUserName()+"_"+request.getParameter("depValue")+".pdf",(ArrayList<FileReport>)session.getAttribute("report"),PieChartDemo.generateBarChart((ArrayList<FileReport>)session.getAttribute("report")));
			response.sendRedirect("Report.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			f1.remove();
			
		}
		
		
	}

}
