<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Signup Page</title>
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>

</head>
<body>
<p>${Message}</p>
<div align="center">
<form action="/saveUser" method="POST">

<table>

<tr>
<td>UserName</td><td><input type="text" name="userName" required="required"></td>
</tr>
<tr>
<td>Password</td><td><input type="password" name="password" required="required"></td>
</tr>
<td>Email</td><td><input type="text" name="userEmail" required="required"></td>
</tr>
<tr>
<td>Phone</td><td><input type="text" name="userPhone" required="required"></td>
</tr>
<tr>
<td colspan="2" align="center"><input type="submit" name="submit" id="submit" value="SignUp"></td>
</tr>
</table>

</div>


</body>
</html>