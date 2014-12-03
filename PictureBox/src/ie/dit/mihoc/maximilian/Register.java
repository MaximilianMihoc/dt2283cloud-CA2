package ie.dit.mihoc.maximilian;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class Register extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws IOException 
	{
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		
		String userName = (String) req.getParameter("userName");
		String email = (String) req.getParameter("email");
		
		System.out.println("usrName: " + userName + "\nemail: " + email); 
		
		Entity usr = new Entity("AppUser");
		usr.setProperty("userName", userName);
		usr.setProperty("email", email);
		
		ds.put(usr);
		resp.sendRedirect("/member");
	}
}
