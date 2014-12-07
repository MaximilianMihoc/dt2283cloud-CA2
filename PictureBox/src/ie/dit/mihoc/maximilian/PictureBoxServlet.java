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
		
		resp.getWriter().println("<body align=\"center\">");
		resp.getWriter().println("<h1>Welcome to Picture Box Web Application</h1>");

		if(myPrincipal == null) 
		{
			//User Guides
			resp.getWriter().println("<div id=\"guide\"> "
									+ "<p style=\"color: #82004A;\">If you are registered as a member in this application you can Log in with your account pressing the Sing in button."
									+ "<br/>If you are not registered, you can easily register by pressing the Register button"
									+ "<br/>You can also see some Public pictures posted by members of this application if you visid as guest pressing Guest Button"
									+ "</p></div>");
			
			resp.getWriter().println("<a href=\"" + loginURL + "\" class=\"button big\">sign in<span>Log in here</span></a>");
			resp.getWriter().println("<a href=\"" + registerURL + "\"class=\"button big\">register<span>Register here</span></a>");
			resp.getWriter().println(" <a href=\"" + guestURL + "\"class=\"button big\">guest<span>Visit as Guest here</span></a>");	
			resp.getWriter().println("<br/>");
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
				resp.getWriter().println("<h3>Admin: " + emailAddress + "</h3>");
				//resp.getWriter().println("<p>You are Logged in as (email): " + emailAddress + "</p>");
				resp.getWriter().println("<a href=\"" + logoutURL + "\"class=\"button big\">sign out<span>Log out here</span></a>");
				resp.getWriter().println("<a href=\"" + adminURL + "\"class=\"button big\">Admin Page<span>Go to your Admin page</span></a>");

				resp.getWriter().println("<div id=\"adminGuide\" style=\"position:relative; float:left;\"> "
						+ "<h3 alligt=\"centre\">Guide</h3>"
						+ "<p style=\"color: #82004A;\">As Admin of this Application, You can:"
						+ "<br/>1. Sign out or go to home page from the buttons on right."
						+ "<br/>2. If you choose to go to home page, as Admin you will"
						+ "<br/> be able to see all pictures from all users "
						+ "<br/>3. You can download any picture you like, private or public"
						+ "<br/>4. You can delete any Picture that you think is bad or is reported"
						+ "<br/>5. Under each picture you will see the owner of it"
						+ "<br/>6. When uploading a picture, it will be private by default, but you cand choose to make it public"
						+ "</p></div>");
				resp.getWriter().println("<br/>");
			}
			//check if email entered is the email of one of the member users
			else if(members.contains(emailAddress.toLowerCase()))
			{
				String memberURL = "/member";
				resp.getWriter().println("<h3>Member: " + emailAddress + "</h3>");
				//resp.getWriter().println("<p>You are Logged in as (email): " + emailAddress + "</p>");
				resp.getWriter().println("<a href=\"" + logoutURL + "\" class=\"button big\">sign out<span>Log out here</span></a>");
				resp.getWriter().println("<a href=\"" + memberURL + "\" class=\"button big\">Home Page<span>Go to Your Home Page</span></a>");
				
				resp.getWriter().println("<div id=\"memberGuide\" style=\"position:relative; float:left;\"> "
						+ "<h3 alligt=\"centre\">Guide</h3>"
						+ "<p style=\"color: #82004A;\">As member of this Application, You can:"
						+ "<br/>1. Sign out or go to home page from the buttons on right."
						+ "<br/>2. If you choose to go to home page, as a member you will"
						+ "<br/> be able to see your private and public pictures and all "
						+ "<br/>the ather public pictures, you can upload private or "
						+ "<br/> public pictures and you are able to delete only "
						+ "<br/> your pictures, public or private"
						+ "<br/>3. You can also download any picture you like, your private or any public"
						+ "</p></div>");
				resp.getWriter().println("<br/>");
			}
			else 
			{	//e-mails that are not admins or users will be guests and they will have an opportunity to register
				resp.getWriter().println("<h3>Guest</h3>");
				resp.getWriter().println("<a href=\"" + registerURL + "\" class=\"button big\">register<span>Register here</span></a>"); 
				resp.getWriter().println("<a href=\"" + logoutURL + "\" class=\"button big\">sign out<span>Log out here</span></a>");
				resp.getWriter().println("<a href=\"" + guestURL + "\" class=\"button big\">Guest<span>Visit as Guest</span></a>");

				resp.getWriter().println("<div id=\"guestGuide\" style=\"position:relative; float:left;\"> "
						+ "<h3 alligt=\"centre\">Guide</h3>"
						+ "<p style=\"color: #82004A;\">As guest of this Application, You can:"
						+ "<br/>1. See all public pictures"
						+ "<br/>2. Download any picture"
						+ "<br/>If you would like to be a member register and you will be able to "
						+ "<br/> upload private or public pictures and share them with other people"
						+ "</p></div>");
				resp.getWriter().println("<br/>");
			}
		}
		resp.getWriter().println("<img src=\"box.png\" />");
		resp.getWriter().println("</body>");
	}
}
