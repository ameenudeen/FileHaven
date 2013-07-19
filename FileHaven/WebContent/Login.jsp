<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Login</title>
    <link href="http://twitter.github.io/bootstrap/assets/css/bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="http://twitter.github.io/bootstrap/assets/css/bootstrap-responsive.css" rel="stylesheet" type="text/css" />
    <link href="/assets/css/application.css" rel="stylesheet" type="text/css" />
<style>
.content_space{
padding:60px;
			min-height:400px;
			background-color:#ECECE8;
			-webkit-border-radius: 6px;
			-moz-border-radius: 6px;
			border-radius: 6px;
			background-image: linear-gradient(bottom, rgb(242,239,242) 23%, rgb(240,240,240) 62%, rgb(237,237,237) 81%);
			background-image: -o-linear-gradient(bottom, rgb(242,239,242) 23%, rgb(240,240,240) 62%, rgb(237,237,237) 81%);
			background-image: -moz-linear-gradient(bottom, rgb(242,239,242) 23%, rgb(240,240,240) 62%, rgb(237,237,237) 81%);
			background-image: -webkit-linear-gradient(bottom, rgb(242,239,242) 23%, rgb(240,240,240) 62%, rgb(237,237,237) 81%);
			background-image: -ms-linear-gradient(bottom, rgb(242,239,242) 23%, rgb(240,240,240) 62%, rgb(237,237,237) 81%);
			background-image: -webkit-gradient(
				linear,
				left bottom,
				left top,
				color-stop(0.23, rgb(242,239,242)),
				color-stop(0.62, rgb(240,240,240)),
				color-stop(0.81, rgb(237,237,237))
			);
}
</style>

</head>
<body>
<%@ include file="LoginHeader.jsp"%> 
      	<div class="span9">
			<div class="hero-unit">
			            <h1>Login</h1>
			            <p></p>
			</div>
			<div class="row-fluid" >
			<div class="content_space">
			
			<form action='LoginServlet' method='post'>
			
			<table cellspacing=15>
			<tr><td>Username :</td><td><input type=text name="userName" style="height: 20px; width:180px" /></td></tr>
			<tr><td>Password :</td><td><input type=password name="password" style="height: 20px; width:180px" /></td></tr>
			</table>
			
			<table cellspacing=15>
			<tr><td>Please enter the Captcha shown below</td></tr>
			<tr><td><img src="CaptchaServlet"></td></tr>
			<tr><td><input type="text" name="code" style="height: 20px; width: 180px; "/></td></tr>
			<tr><td><input type=submit name="submit" Value="Login" style="height: 30px; width: 60px;" class="btn" onclick="" /></td></tr>
			
			<%
			String alert = (String)session.getAttribute("alert");
			if (alert == null) {
			} else {
			%>
			<tr><td><%= alert%></td></tr>
			<%
			} 
			%>
			
			</table>
			
			</form>
			</div>
		</div>
	</div>
<%@ include file="footer.jsp"%>
</body>
</html>