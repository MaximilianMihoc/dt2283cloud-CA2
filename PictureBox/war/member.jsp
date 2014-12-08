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
	<link href="styleSheet.css" type="text/css" rel="stylesheet" />
</head>
<body>
<c:choose>
	<c:when test="${user != null}">
		<h1 align="center">Welcome ${userName}</h1>
		<div id="signOutButton"><a href="${logoutUrl}" class="button big">sign out<span>You can Log out here</span></a></div>
		
		
		<!-- Declare Some JSTL variables to use -->
		<c:set var="imageIndex" value="0"/>
		<c:set var="imageIndexPublic" value="0"/>
		<c:set var="imageIndexOther" value="0"/>
		
		<!-- Member private Pictures -->
		<c:choose>
			<c:when test="${hasUploads}">
				<table border="1" cellpadding="5" id="privatePic">
				<caption><h2>${userName} Private Pictures</h2></caption>
					<c:forEach var="upload" items="${uploads}">
						<td align="center" valign="center">
							${upload.description}<br/>
							<c:set var="imageIndex" value="${imageIndex + 1}"/>
							
							<a href="/view?key=${upload.uploadKey}">
								<img src="/serve?blob-key=${upload.blobString}" width="150">
							</a><br/>
							<form action="/delete" method="post">
								<a href="/serve?blob-key=${upload.blobString}"><button type="button">Download</button></a>
								<input type="hidden" name="delete" value="${upload.uploadKey}">
								<input type="submit" value="Delete" />
							</form>
							<br/>
						</td>
						<c:if test="${ imageIndex % 5 == 0 }">
							<tr></tr>
						</c:if>
					</c:forEach>
					<c:if test="${ imageIndex == 0 }">
						<tr><td>You don't have any Private Pictures</td></tr>
					</c:if>		
				</table>
		</c:when>
			<c:otherwise>
			<table border="1" cellpadding="5" id="privatePic">
				<caption><h2>${userName} Private Pictures</h2></caption>
				<tr><td>You don't have any Private Pictures</td></tr>
			</table>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${hasPublicUploads}">
				<!-- Member Public Pictures -->
				<table border="1" cellpadding="5" id="publicPics">
					<caption><h2>${userName} Public Pictures</h2></caption>
					<c:forEach var="upload" items="${publicUploads}">
						<c:if test="${user.email == upload.user.email }">
							<td align="center" valign="center">
								${upload.description}<br/>
								<c:set var="imageIndexPublic" value="${imageIndexPublic + 1}"/>
								
								<a href="/view?key=${upload.uploadKey}">
									<img src="/serve?blob-key=${upload.blobString}" width="150">
								</a><br/>
								<form action="/delete" method="post">
									<a href="/serve?blob-key=${upload.blobString}"><button type="button">Download</button></a>
									<input type="hidden" name="delete" value="${upload.uploadKey}">
									<input type="submit" value="Delete" />
								</form>
								<br/>
							</td>
							<c:if test="${ imageIndexPublic % 5 == 0 }">
								<tr></tr>
							</c:if>
						</c:if>
					</c:forEach>
					<c:if test="${ imageIndexPublic == 0 }">
						<tr><td>You don't have any Public Pictures</td></tr>
					</c:if>
				</table>
					
				<!-- Other Public Pictures -->
				<table border="1" cellpadding="5" id="otherPublicPics">
					<caption><h2>Other Public Pictures</h2></caption>
					<c:forEach var="upload" items="${publicUploads}">
						<c:if test="${user.email != upload.user.email }">
							<td align="center" valign="center">
								${upload.description}<br/>
								<c:set var="imageIndexOther" value="${imageIndexOther + 1}"/>
								<a href="/view?key=${upload.uploadKey}">
									<img src="/serve?blob-key=${upload.blobString}" width="150">
								</a><br/>
								<a href="/serve?blob-key=${upload.blobString}"><button type="button">Download</button></a>
							</td>
							<c:if test="${ imageIndexOther % 5 == 0 }">
								<tr></tr>
							</c:if>
						</c:if> 
					</c:forEach>
					<c:if test="${ imageIndexOther == 0 }">
						<tr><td>There are no other Public Pictures</td></tr>
					</c:if>
				</table>
		</c:when>
			<c:otherwise>
				<p class="message">There are no Public uploads.</p>
			</c:otherwise>
		</c:choose>
			
		<!-- Upload Part -->	
		<div id="uploadDiv">
			<h4 align="center">Upload</h4>
			<form action="${uploadUrl}" method="post" enctype="multipart/form-data">
				<label for="description">Description:</label><br />
				<input type="text" name="description" id="description" /><br />
				<label for="upload">File:</label><br />
				<input type="file" name="upload" multiple="true" /><br />
				<input type="checkbox" checked="checked" name="publicCheckbox" id="publicCheckbox">Public<br/>
				<input type="submit" value="Upload File" />
			</form>
		</div>
	</c:when>
	<c:otherwise>
		<p>Welcome! Please<a href="${loginUrl}">sign in or register</a> to upload files.</p>
	</c:otherwise>
</c:choose>
</body>
</html>