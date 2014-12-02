package ie.dit.mihoc.maximilian;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Member extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		String emailUser = (String) req.getSession().getAttribute("user");
		System.out.println("user: " + emailUser); 
		req.setAttribute("memberUser", emailUser); 
		 
		 RequestDispatcher disp = req.getRequestDispatcher("/member.jsp");
		 try
		 {
			 disp.forward(req, resp);
		 }
		 catch(ServletException e)
		 {
			 e.printStackTrace();
		 }
	}

}
