<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Login Page</title>
</head>
<body>
<p>${Message}</p>
<div align="center">
<form action="/login" method="POST">
<table>
<tr>
<td>UserName</td><td><input type="text" name="username" required="required"></td>
</tr>
<tr>
<td>Password</td><td><input type="password" name="password" required="required"></td>
</tr>

<tr>
<td colspan="2" align="center"><input type="submit" name="submit" value="LogIn"></td>
</tr>
</table>
</form>
</div>


</body>
</html>