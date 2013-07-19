package servlets;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import model.Account;
import model.ChatSession;

/**
 * Servlet implementation class VerifyChatPinServ
 */
public class VerifyChatPinServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyChatPinServ() {
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
		ChatSession cs;
		if (request.getSession().getAttribute("chatsession") != null) {
			cs = (ChatSession) request.getSession().getAttribute("chatsession");
		} else {
			cs = new ChatSession();
		}
		
    	Account loginUser=(Account) request.getSession().getAttribute("LoggedInUser"); 
    	String username=loginUser.getUserName();
		String pin = request.getParameter("pin");
		String pin1 = null;
		String pin2 = null;
		
		InputStream pinIS=IOUtils.toInputStream(pin);
		try {
			pin1=security.Security.hashStream(pinIS, "SHA-512");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStream pinIS1=IOUtils.toInputStream(pin1);
		
		try {
			pin2=security.Security.hashStream(pinIS1, "SHA-512");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(pin);
		System.out.println(pin1);
		System.out.println(pin2);
		
		if(cs.authPin(username,pin2)==2){
			cs.setPin(pin1);
		}
		
		request.getSession().setAttribute("chatsession", cs);
	}

}
