package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;

import utils.PDFWriter;
import utils.Test;

/**
 * Servlet implementation class PDF
 */
public class PDF extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PDF() {
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
		
		PDFWriter p1 = new PDFWriter();
		Test t1 = new Test();
		t1.main(null);
		
//		try {
//			File file = null;
//			file=p1.writeEventTicket();
//				
//			
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}


		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String filename = "piechart.pdf"; 
		String filepath = "c:\\temp\\"; 
		response.setContentType("APPLICATION/OCTET-STREAM"); 
		response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\""); 
		
		FileInputStream fileInputStream = new FileInputStream(filepath + filename);
				  
		int i; 
		while ((i=fileInputStream.read()) != -1) {
		out.write(i); 
		} 
		fileInputStream.close(); 
		out.close(); 
		}
		
		




		
	

}
