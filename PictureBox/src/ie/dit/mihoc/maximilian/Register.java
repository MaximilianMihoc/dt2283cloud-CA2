package ie.dit.mihoc.maximilian;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Register extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws IOException 
	{
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		
		String userName = (String) req.getParameter("userName");
		String email = (String) req.getParameter("email");
		
		//System.out.println("usrName: " + userName + "\nemail: " + email); 
		
		//check if user already in database
		//when a user tries to register, if his email is already in the 
		//database he will see his page without having to register again
		List<String> userEmails = new ArrayList<String>();
		
		//query to return all entities from AppUser DataStore 
		Query q = new Query("AppUser");
		PreparedQuery pq = ds.prepare(q);
		Iterable<Entity> userResults = pq.asIterable();
		for (Entity result : userResults) 
		{
			String emailUsr = (String) result.getProperty("email");
			userEmails.add(emailUsr.toLowerCase());
		}
		
		//check if email in list and if it's not, add it
		if(!(userEmails.contains(email.toLowerCase())))
		{
			Entity usr = new Entity("AppUser");
			usr.setProperty("userName", userName);
			usr.setProperty("email", email);
			ds.put(usr);
		}
		resp.sendRedirect("/member");
	}
}
