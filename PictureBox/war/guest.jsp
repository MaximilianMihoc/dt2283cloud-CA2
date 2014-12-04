<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Guest Page</title>
	<link href="styleSheet.css" type="text/css" rel="stylesheet" />
</head>
<body>
	<h3 align="center">You are logged in as Guest</h3>
	<p align="center">You can  
	<a href="/register.jsp"><button type="button" id="regButton">Register</button></a>
	Here</p>
	
	<c:set var="imageIndexOther" value="0"/>
	<c:choose>
		<c:when test="${hasPublicUploads}">
		<table border="1" cellpadding="5" id="otherPublicPics">
			<caption><h2>Other Public Pictures</h2></caption>
			<c:forEach var="upload" items="${publicUploads}">
				<td align="center" valign="center">
					${upload.description}<br/>
					<c:set var="imageIndexOther" value="${imageIndexOther + 1}"/>
					
					<a href="/view?key=${upload.uploadKey}">
						<img src="/serve?blob-key=${upload.blobString}" height="150" width="150">
					</a><br/>
				</td>
				<c:if test="${ imageIndexOther % 5 == 0 }">
					<tr></tr>
				</c:if> 
			</c:forEach>
		</table>
	</c:when>
	<c:otherwise>
		<p class="message">There are no Public uploads.</p>
	</c:otherwise>
	</c:choose>
</body>
</html>