<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import="com.google.appengine.api.blobstore.BlobInfoFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<% BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Member Page</title>
</head>
<body>
<c:choose>
	<c:when test="${user != null}">
		<p>
		Welcome, ${user.email}!
		You can <a href="${logoutUrl}">sign out</a>.
		</p>
		<c:set var="imageIndex" value="0"/>
		<c:choose>
			<c:when test="${hasUploads}">
					<table border="1" cellpadding="5" align="center">
					<caption><h2>Your Private Pictures</h2></caption>
						<c:forEach var="upload" items="${uploads}">
							<td align="center" valign="center">
								${upload.description}<br/>
								<c:set var="imageIndex" value="${imageIndex + 1}"/>
								
								<a href="/view?key=${upload.uploadKey}">
									<img src="/serve?blob-key=${upload.blobString}" height="150" width="150">
								</a><br/>
								<form action="/delete" method="post">
									<input type="hidden" name="delete" value="${upload.uploadKey}">
									<input type="submit" value="Delete" />
								</form>
								<br/>
							</td>
							<c:if test="${ imageIndex % 5 == 0 }">
								<tr></tr>
							</c:if>
						</c:forEach>
					</table>
				
			</c:when>
			<c:otherwise>
				<p>You have no uploads.</p>
			</c:otherwise>
		</c:choose>
		
		<form action="${uploadUrl}" method="post" enctype="multipart/form-data">
			<label for="description">Description:</label>
			<input type="text" name="description" id="description" /><br />
			<label for="upload">File:</label>
			<input type="file" name="upload" multiple="true" /><br />
			<input type="submit" value="Upload File" />
		</form>
	</c:when>
	<c:otherwise>
		<p>
		Welcome! Please
		<a href="${loginUrl}">sign in or register</a> to upload files.
		</p>
	</c:otherwise>
</c:choose>
</body>
</html>