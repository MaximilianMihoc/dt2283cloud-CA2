<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Registration</title>
	<link href="styleSheet.css" type="text/css" rel="stylesheet" />
	<script>
	function validateForm() {
	    var x = document.forms["myForm"]["email"].value;
	    var atpos = x.indexOf("@");
	    var dotpos = x.lastIndexOf(".");
	    if (atpos<1 || dotpos<atpos+2 || dotpos+2>=x.length) {
	        alert("Not a valid e-mail address");
	        return false;
	    }
	}
	</script>
	
</head>
<body align="center">
	<h1>Here is the registration Page</h1>
	<form name="myForm" action="/register" onsubmit="return validateForm();" method="post">
			<label for="userName">UserName:</label>
			<input type="text" name="userName" id="userName" /><br />
			<label for="email">E-mail:</label>
			<input type="email" name="email" id="email"/><br />
			<input type="submit" value="Register" />
	</form>
</body>
</html>