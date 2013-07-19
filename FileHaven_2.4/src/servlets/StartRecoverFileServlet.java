package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Files;

import org.apache.commons.codec.binary.Base64;

import security.Security;
import database.FileDBAO;

/**
 * Servlet implementation class StartRecoverFileServlet
 */
public class StartRecoverFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StartRecoverFileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Files file;
		HttpSession session=request.getSession();
		try{
		String base64enc=request.getParameter("ID");
		base64enc=base64enc.replace(' ', '+');
		base64enc=new String(Security.decryptByte(Base64.decodeBase64(base64enc), Security.generateAESKey("SYSTEM_KEY"), "AES"));
		file=new FileDBAO().getFile(Integer.parseInt(base64enc));
		if(file==null){
			throw new Exception("File not found");
		}
		session.setAttribute("RecoverFile",file);
		getServletContext().getRequestDispatcher("/RecoverDeletedFile.jsp").forward(request,response);
		}
		catch(Exception ex){
			session.setAttribute("info_line1", "File access denied.");
			session.setAttribute("info_line2", ex.getMessage());
			getServletContext().getRequestDispatcher("/Information.jsp").forward(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
