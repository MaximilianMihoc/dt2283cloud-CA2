<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Admin Page</title>
	<link href="styleSheet.css" type="text/css" rel="stylesheet" />
</head>
<body>
<h3 align="center">You are logged in as Admin</h3>
<c:choose>
	<c:when test="${user != null}">
		<p>Welcome, ${userName}! You can <a href="${logoutUrl}">sign out here</a>.</p>
		
		<!-- Declare Some JSTL variables to use -->
		<c:set var="imageIndex" value="0"/>
		<c:set var="imageIndexPrivate" value="0"/>
		<c:set var="imageIndexPublic" value="0"/>
		<c:set var="imageIndexOther" value="0"/>
		
		<!-- Admin private Pictures -->
		<c:choose>
			<c:when test="${hasUploads}">
				<table border="1" cellpadding="5" id="privatePic">
				<caption><h2>${userName} Private Pictures</h2></caption>
					<c:forEach var="upload" items="${uploads}">
						<c:if test="${user.email == upload.user.email }">
							<td align="center" valign="center">
								${upload.description}<br/>
								<c:set var="imageIndex" value="${imageIndex + 1}"/>
								
								<a href="/view?key=${upload.uploadKey}">
									<img src="/serve?blob-key=${upload.blobString}" height="150" width="150">
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
						</c:if>
					</c:forEach>
					<c:if test="${ imageIndex == 0 }">
						<tr><td>You don't have any Private Pictures</td></tr>
					</c:if>		
				</table>
		</c:when>
			<c:otherwise>
				<p class="message">You have no Private uploads.</p>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${hasPublicUploads}">
				<!-- Admin Public Pictures -->
				<table border="1" cellpadding="5" id="publicPics">
					<caption><h2>${userName} Public Pictures</h2></caption>
					<c:forEach var="upload" items="${publicUploads}">
						<c:if test="${user.email == upload.user.email }">
							<td align="center" valign="center">
								${upload.description}<br/>
								<c:set var="imageIndexPublic" value="${imageIndexPublic + 1}"/>
								
								<a href="/view?key=${upload.uploadKey}">
									<img src="/serve?blob-key=${upload.blobString}" height="150" width="150">
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
					
				<!-- Other Members Private Pictures -->	
				<c:choose>
					<c:when test="${hasUploads}">
						<table border="1" cellpadding="5" id="privatePic">
						<caption><h2>Other Private Pictures</h2></caption>
							<c:forEach var="upload" items="${uploads}">
								<c:if test="${user.email != upload.user.email }">
									<td align="center" valign="center">
										${upload.description}<br/>
										<c:set var="imageIndexPrivate" value="${imageIndexPrivate + 1}"/>
										
										<a href="/view?key=${upload.uploadKey}">
											<img src="/serve?blob-key=${upload.blobString}" height="150" width="150">
										</a><br/>
										<form action="/delete" method="post">
											<a href="/serve?blob-key=${upload.blobString}"><button type="button">Download</button></a>
											<input type="hidden" name="delete" value="${upload.uploadKey}">
											<input type="submit" value="Delete" />
										</form>
										<br/>
									</td>
									<c:if test="${ imageIndexPrivate % 5 == 0 }">
										<tr></tr>
									</c:if>
								</c:if>
							</c:forEach>
							<c:if test="${ imageIndexPrivate == 0 }">
								<tr><td>You don't have any Private Pictures</td></tr>
							</c:if>		
						</table>
					</c:when>
				<c:otherwise>
					<p class="message2">There are no other Private Pictures.</p>
				</c:otherwise>
				</c:choose>	
					
				<!-- Other Public Pictures -->
				<table border="1" cellpadding="5" id="otherPublicPics">
					<caption><h2>Other Public Pictures</h2></caption>
					<c:forEach var="upload" items="${publicUploads}">
						<c:if test="${user.email != upload.user.email }">
							<td align="center" valign="center">
								${upload.description}<br/>
								<c:set var="imageIndexOther" value="${imageIndexOther + 1}"/>
								
								<a href="/view?key=${upload.uploadKey}">
									<img src="/serve?blob-key=${upload.blobString}" height="150" width="150">
								</a><br/>
								<form action="/delete" method="post">
									<a href="/serve?blob-key=${upload.blobString}"><button type="button">Download</button></a>
									<input type="hidden" name="delete" value="${upload.uploadKey}">
									<input type="submit" value="Delete" />
								</form>
								<br/>
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