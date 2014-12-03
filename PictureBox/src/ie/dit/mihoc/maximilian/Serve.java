package ie.dit.mihoc.maximilian;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class Serve extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException 
	{
		BlobInfoFactory bi = new BlobInfoFactory();
		BlobKey blobKey = new BlobKey(req.getParameter("blob-key")); 
		String fname = bi.loadBlobInfo(blobKey).getFilename();
		res.setContentType("application/x-download");
		res.setHeader("Content-Disposition", "attachment; filename=" + fname);
		blobstoreService.serve(blobKey, res);
	}
			

}
