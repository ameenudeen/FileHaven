package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;
import model.ChatSession;

/**
 * Servlet implementation class SendInvServ
 */
public class SendInvServ extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendInvServ() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ChatSession cs;
		if (request.getSession().getAttribute("chatsession") != null) {
			cs = (ChatSession) request.getSession().getAttribute("chatsession");
		} else {
			cs = new ChatSession();
		}

		String desc = request.getParameter("description");
		String selectedUserStr[] = request.getParameter("selectedUser").split(
				",");
		
		Account loginUserAcc=(Account) request.getSession().getAttribute("LoggedInUser"); 
		String loginUser=loginUserAcc.getUserName();		
		
		int selectedUser[] = new int[selectedUserStr.length];
		/*
		 * int selectedUser = Integer.parseInt(request
		 * .getParameter("selectedUser"));
		 */

		for (int i = 0; i < selectedUserStr.length; i++) {
			selectedUser[i] = Integer.parseInt(selectedUserStr[i]);
		}

		// String msg="undefined";
		
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();

		for (int j = 0; j < selectedUser.length; j++) {
			String username = "";
			for (int i = 0; i < cs.getChatinvAccList().size(); i++) {
				if (cs.getChatinvAccList().get(i).getClientAid() == selectedUser[j]) {
					username = cs.getChatinvAccList().get(i).getUserName();
				}
			}

			try {
				cs.sendChatRoomInv(username, desc, loginUser );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/*
		 * String username = ""; for (int i = 0; i <
		 * cs.getChatinvAccList().size(); i++) { if
		 * (cs.getChatinvAccList().get(i).getClientAid() == selectedUser) {
		 * username = cs.getChatinvAccList().get(i).getID(); } }
		 * 
		 * for (int i = 0; i < cs.getChatinvAccList().size(); i++) {
		 * System.out.println("Acc ClientID: " +
		 * cs.getChatinvAccList().get(i).getClientAid()); }
		 * cs.sendChatRoomInv(username, desc, (String) request.getSession()
		 * .getAttribute("userid"));
		 */

		/*
		 * response.setContentType("text/html"); PrintWriter out =
		 * response.getWriter();
		 * 
		 * out.print(msg);
		 */

		request.getSession().setAttribute("chatsession", cs);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
