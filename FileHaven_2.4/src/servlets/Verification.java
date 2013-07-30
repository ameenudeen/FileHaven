package servlets;

import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import security.Hash;
import security.Security;
import model.Account;

import org.apache.commons.codec.binary.Base64;
/**
 * Servlet implementation class Verification
 */
public class Verification extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Verification() {
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
		String password=request.getParameter("password");
		Account currentUser=(Account) session.getAttribute("LoggedInUser");
		
		try{
			if(session.getAttribute("externalcompany").equals("false")){
				getServletContext().getRequestDispatcher("/Index.jsp").forward(request,response);
				return;
			}
			Hash h1 = new Hash();
			String hashedValue=h1.hashString(password, currentUser.getCreatorID(),currentUser.getCreatedTime());
			
			if(!hashedValue.equals(currentUser.getUserPattern()))
			{
				throw new Exception("Invalid Pattern");
			}
			
			String base64enc=request.getParameter("hidden_timestamp");
			base64enc=new String(Security.decryptByte(Base64.decodeBase64(base64enc), Security.generateAESKey("SYSTEM_KEY"), "AES"));
			
			Scanner sc=new Scanner(base64enc);
			sc.useDelimiter(":");
			int count=0;
			int val[]=new int[5];
			//year,month,date,hour,min
			while(sc.hasNext())
				val[count++]=sc.nextInt();
			sc.close();
			
			if(count!=5){
				throw new Exception("Error occur.Please consult FileHaven administrator");
			}
			
			//checking timestamp
			GregorianCalendar c=new GregorianCalendar();
			int time[]={c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE),c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE)};
			for(int i=0;i<time.length;i++){
				System.out.println("Time="+time[i]+" Value="+val[i]);
				if(time[i]<val[i] && i!=4){
					//u do not time travel
					//only minute allow to 
					throw new Exception("Timestamp error.Please reload and redo validation");
				}
				
				if(i<3){
					//checking about year.month.date
					if(time[i]!=val[i]){
						//u dont do validation using 1 day time
						throw new Exception("Timestamp error.Please reload and redo validation");
					}
				}
				else if(i==3){
					//u do not need 1 hour to do validation
					//you will not redirect to information.jsp if session timeout
					//you will be redirected to login.jsp
					if((time[i]==0?24:time[i])>(val[i]==0?24:val[i])+1)
						throw new Exception("Timestamp expired.Please reload and redo validation");
				}
				else if(i==4){
					if(time[3]==val[3]){
						if(val[i]-time[i]>30)
								throw new Exception("Timestamp expired.Please reload and redo validation");
					}//end same hour
					else{
						if(time[i]+60-val[i]>30)
							throw new Exception("Timestamp expired.Please reload and redo validation");
					}//1 hour extra
				}
			}
			session.setAttribute("externalcompany", "false");
			response.sendRedirect("Index.jsp");
			
		}
		catch(Exception ex){
			//exception handle: include wrong padding exception
			session.setAttribute("info_line1", "Verification error");
			session.setAttribute("info_line2", ex.getMessage());
			getServletContext().getRequestDispatcher("/Information.jsp").forward(request,response);
			
		}
	}

}
