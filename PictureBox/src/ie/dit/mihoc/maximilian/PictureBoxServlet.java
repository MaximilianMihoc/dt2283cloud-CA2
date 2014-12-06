package ie.dit.mihoc.maximilian;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class PictureBoxServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		resp.setContentType("text/plain");
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		
		UserService userService = UserServiceFactory.getUserService(); 
		Principal myPrincipal = req.getUserPrincipal();
		String emailAddress = null;
		
		String thisURL = req.getRequestURI(); 
		String loginURL = userService.createLoginURL(thisURL);
		String logoutURL = userService.createLogoutURL(thisURL);
		String registerURL = "/register.jsp";
		String guestURL = "/guest";
		
		//define admin List 
		List<String> admins = new ArrayList<String>();
		admins.add(("MaximiliaMihoc@gmail.com").toLowerCase());
		admins.add(("mark.deegan@dit.ie").toLowerCase());
		
		//define members List
		List<String> members = new ArrayList<String>();
		
		/*
		 * Create a query that will return all users from AppUser DataStore table GAE
		 * put their email addresses into members array to check if they are members
		 * if they are members, redirect them to Member.jsp page
		 * */
		
		Query q = new Query("AppUser");
		PreparedQuery pq = ds.prepare(q);
		Iterable<Entity> results = pq.asIterable();
		for (Entity result : results) 
		{
			String emailUsr = (String) result.getProperty("email");
			
			System.out.println("email: " + emailUsr); 
			
			members.add(emailUsr.toLowerCase());
		}
		
		resp.setContentType("text/html");
		//<link href="styleSheet.css" type="text/css" rel="stylesheet" />
		resp.getWriter().println("<head><title>Portal</title><link href=\"styleSheet.css\" type=\"text/css\" rel=\"stylesheet\" /></head>");
		
		resp.getWriter().println("<h1>Welcome to Picture Box Web Application</h1>");
		
		if(myPrincipal == null) 
		{
			resp.getWriter().println("<p>You are Not Logged In</p>");
			resp.getWriter().println("<p>You can <a href=\"" + loginURL + "\">sign in here</a>.</p>");
			resp.getWriter().println("<p>Not registred You can <a href=\"" + registerURL + "\">Register here</a>.</p>");
			resp.getWriter().println("<p>Visit as Guest <a href=\"" + guestURL + "\">here</a>");
		} 
		
		if(myPrincipal != null) 
		{
			emailAddress = myPrincipal.getName();
			
			//store the user email in session: this will be used later to know who is the user
			req.setAttribute("user", emailAddress); 
			//check if email entered is the email of one of the admin users
			if(admins.contains(emailAddress.toLowerCase()))
			{
				String adminURL = "/admin";
				resp.getWriter().println("<h3>Admin</h3>");
				resp.getWriter().println("<p>You are Logged in as (email): " + emailAddress + "</p>");
				resp.getWriter().println("<p>You can <a href=\"" + logoutURL + "\">sign out</a>.</p>");
				resp.getWriter().println("<p><a href=\"" + adminURL + "\">Admin Page</a></p>");
			}
			//check if email entered is the email of one of the member users
			else if(members.contains(emailAddress.toLowerCase()))
			{
				String memberURL = "/member";
				resp.getWriter().println("<h3>Member</h3>");
				resp.getWriter().println("<p>You are Logged in as (email): " + emailAddress + "</p>");
				resp.getWriter().println("<p>You can <a href=\"" + logoutURL + "\">sign out</a>.</p>");
				resp.getWriter().println("<p><a href=\"" + memberURL + "\">Home</a></p>");
			}
			else 
			{	//e-mails that are not admins or users will be guests and they will have an opportunity to register
				resp.getWriter().println("<h3>Guest</h3>");
				resp.getWriter().println("<p>You can <a href=\"" + registerURL + "\">Register here</a>"); 
				resp.getWriter().println("<p>You can <a href=\"" + logoutURL + "\">sign out</a>.</p>");
				resp.getWriter().println("<p>Some Public Pictures can be found <a href=\"" + guestURL + "\">here</a>");
			}
		}
	}
}
