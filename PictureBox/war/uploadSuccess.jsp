<%@ page 
import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%BlobstoreService blobstoreService = 
BlobstoreServiceFactory.getBlobstoreService(); %>

<html>
 <head>
 <title>Flop Box</title>
 </head>
 <body>
 	<h2>File Uploaded Successfully</h2>
 	<a href="index.html">Back</a>
 </body>
</html>