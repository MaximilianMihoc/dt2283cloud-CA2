package ie.dit.mihoc.maximilian;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.UploadOptions;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class Admin extends HttpServlet
{	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{	
		//get email of the current member user
		UserService userService = UserServiceFactory.getUserService();
		//System.out.println(userService.isUserAdmin());
		User user = userService.getCurrentUser();
		
		String loginUrl = userService.createLoginURL("/");
		String logoutUrl = userService.createLogoutURL("/");
		
		
		System.out.println("admin: " + user.getEmail()); 
		
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		//define the maximum size of Bolbs that are going to be uploaded 
		UploadOptions uploadOptions = UploadOptions.Builder.withMaxUploadSizeBytesPerBlob(1024L * 1024L * 1024L).maxUploadSizeBytes(10L * 1024L * 1024L * 1024L);
		//create the upload URL
		String uploadUrl = blobstoreService.createUploadUrl("/upload", uploadOptions);
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		//get blob info factory informations 
		BlobInfoFactory blobInfoFactory = new BlobInfoFactory();
		List<Map<String, Object>> uploads = new ArrayList<Map<String, Object>>();
		
		//Key userGroupKey = KeyFactory.createKey("UserUploadGroup", user.getEmail());
		Query q = new Query("UserUpload");
		
		PreparedQuery pq = ds.prepare(q);
		Iterable<Entity> results = pq.asIterable();
		for (Entity result : results) 
		{
			Map<String, Object> upload = new HashMap<String, Object>();
			upload.put("description", (String) result.getProperty("description"));
			BlobKey blobKey = (BlobKey) result.getProperty("upload");
			//store the user so You know what are the public pictures for a specific user
			upload.put("user", result.getProperty("user"));
			upload.put("blob", blobInfoFactory.loadBlobInfo(blobKey));
			upload.put("blobString", blobKey.getKeyString());
			upload.put("uploadKey", KeyFactory.keyToString(result.getKey()));
			uploads.add(upload);
		}
		
		//create list with public Pictures
		List<Map<String, Object>> publicUploads = new ArrayList<Map<String, Object>>();
		
		//create query for Public Pictures
		Query q2 = new Query("PublicUploads");
		//prepare query to execute and iterate through results
		PreparedQuery pq2 = ds.prepare(q2);
		Iterable<Entity> publicResults = pq2.asIterable();
		for (Entity result : publicResults) 
		{
			Map<String, Object> upload = new HashMap<String, Object>();
			upload.put("description", (String) result.getProperty("description"));
			BlobKey blobKey = (BlobKey) result.getProperty("upload");
			//store the user so You know what are the public pictures for a specific user
			upload.put("user", result.getProperty("user"));
			upload.put("blob", blobInfoFactory.loadBlobInfo(blobKey));
			upload.put("blobString", blobKey.getKeyString());
			upload.put("uploadKey", KeyFactory.keyToString(result.getKey()));
			publicUploads.add(upload);
		}
		
		req.setAttribute("userName", "Admin");
		req.setAttribute("user", user);
		req.setAttribute("loginUrl", loginUrl);
		req.setAttribute("logoutUrl", logoutUrl);
		req.setAttribute("uploadUrl", uploadUrl);
		req.setAttribute("uploads", uploads);
		req.setAttribute("publicUploads", publicUploads);
		req.setAttribute("hasUploads", !uploads.isEmpty());
		req.setAttribute("hasPublicUploads", !publicUploads.isEmpty());
		
		resp.setContentType("text/html");
		
		RequestDispatcher disp = req.getRequestDispatcher("/admin.jsp");
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
