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
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class Guest extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		//get blob info factory informations 
		BlobInfoFactory blobInfoFactory = new BlobInfoFactory();
		
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
			upload.put("blob", blobInfoFactory.loadBlobInfo(blobKey));
			upload.put("blobString", blobKey.getKeyString());
			upload.put("uploadKey", KeyFactory.keyToString(result.getKey()));
			publicUploads.add(upload);
		}
		
		req.setAttribute("publicUploads", publicUploads);
		req.setAttribute("hasPublicUploads", !publicUploads.isEmpty());
		
		resp.setContentType("text/html");
		
		RequestDispatcher disp = req.getRequestDispatcher("/guest.jsp");
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
