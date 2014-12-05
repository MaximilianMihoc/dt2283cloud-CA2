<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Registration</title>
	<link href="styleSheet.css" type="text/css" rel="stylesheet" />
</head>
<body align="center">
	<h1>Here is the registration Page</h1>
	<form action="/register" method="post">
			<label for="userName">UserName:</label>
			<input type="text" name="userName" id="userName" /><br />
			<label for="email">E-mail:</label>
			<input type="email" name="email" id="email"/><br />
			<input type="submit" value="Register" />
	</form>
</body>
</html>