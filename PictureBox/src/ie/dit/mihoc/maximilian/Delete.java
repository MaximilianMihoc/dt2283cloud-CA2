package ie.dit.mihoc.maximilian;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class Delete extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		BlobstoreService bs = BlobstoreServiceFactory.getBlobstoreService();
		
		String uploadKeyString = req.getParameter("delete");
		List<Key> keysToDelete = new ArrayList<Key>();
		try 
		{
			Entity userUpload = ds.get(KeyFactory.stringToKey(uploadKeyString));
			
			BlobKey blobKey = (BlobKey)userUpload.getProperty("upload");
			Key blobInfoKey = KeyFactory.createKey(BlobInfoFactory.KIND, blobKey.getKeyString());
			keysToDelete.add(blobInfoKey);
			keysToDelete.add(userUpload.getKey());
			
		} 
		catch (EntityNotFoundException e) 
		{
		}
		ds.delete(keysToDelete.toArray(new Key[0]));
		resp.sendRedirect("/picturebox");
	}
}
