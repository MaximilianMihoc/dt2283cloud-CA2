package ie.dit.mihoc.maximilian;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.*;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class PictureBoxServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		
		UserService userService = UserServiceFactory.getUserService(); 
		Principal myPrincipal = req.getUserPrincipal();
		String emailAddress = null;
		
		String thisURL = req.getRequestURI(); 
		String loginURL = userService.createLoginURL(thisURL);
		String logoutURL = userService.createLogoutURL(thisURL);
		String registerURL = "/register.jsp";
		
		//define admins List 
		List<String> admins = new ArrayList<String>();
		admins.add(("MaximiliaMihoc@gmail.com").toLowerCase());
		admins.add(("mark.deegan@dit.ie").toLowerCase());
		
		//define members List
		List<String> members = new ArrayList<String>();
		members.add(("member@mail.com").toLowerCase());
		
		
		resp.setContentType("text/html");
		resp.getWriter().println("<h1>Welcome to Picture Box Web Application</h1>");
		
		if(myPrincipal == null) 
		{
			resp.getWriter().println("<p>You are Not Logged In</p>");
			resp.getWriter().println("<p>You can <a href=\"" + loginURL + "\">sign in here</a>.</p>");
			resp.getWriter().println("<p>Not registred You can <a href=\"" + registerURL + "\">Register here</a>.</p>");
		} 
		
		if(myPrincipal != null) 
		{
			emailAddress = myPrincipal.getName();
			
			//store the user email in session: this will be used later to know who is the user
			req.setAttribute("user", emailAddress); 
			
			if(admins.contains(emailAddress.toLowerCase()))
			{
				resp.getWriter().println("<h3>Admin</h3>");
				resp.getWriter().println("<p>You are Logged in as (email): " + emailAddress + "</p>");
				resp.getWriter().println("<p>You can <a href=\"" + logoutURL + "\">sign out</a>.</p>");
			}
			else if(members.contains(emailAddress.toLowerCase()))
			{
				String memberURL = "/member";
				resp.getWriter().println("<h3>Member</h3>");
				resp.getWriter().println("<p>You are Logged in as (email): " + emailAddress + "</p>");
				resp.getWriter().println("<p>You can <a href=\"" + logoutURL + "\">sign out</a>.</p>");
				resp.getWriter().println("<p><a href=\"" + memberURL + "\">Home</a></p>");
			}
			else 
			{
				resp.getWriter().println("<h3>Guest</h3>");
				resp.getWriter().println("<p>You are not Logged in You can <a href=\"" + loginURL + "\">sign in here</a>");
			}
		}
	}
}
