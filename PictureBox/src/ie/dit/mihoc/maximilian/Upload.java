package ie.dit.mihoc.maximilian;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class Upload extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		BlobstoreService bs = BlobstoreServiceFactory.getBlobstoreService();
		Map<String, List<BlobKey>> blobFields = bs.getUploads(req);
		List<BlobKey> blobKeys = blobFields.get("upload");
		Key userGroupKey = KeyFactory.createKey("UserUploadGroup", user.getEmail());
		
		for (BlobKey blobKey : blobKeys) 
		{
			Entity userUpload = new Entity("UserUpload", userGroupKey);
			userUpload.setProperty("user", user);
			userUpload.setProperty("description", req.getParameter("description"));
			userUpload.setProperty("upload", blobKey);
			ds.put(userUpload);
		}
		res.sendRedirect("/");
	}
}
