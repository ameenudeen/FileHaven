<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="model.Account, java.util.*" %>

<%
Account tempaccount = (Account) session.getAttribute("LoggedInUser");

if(tempaccount != null)
{
%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Login</title>
    <link href="resources/css/bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="resources/css/bootstrap-responsive.css" rel="stylesheet" type="text/css" />
    <link href="resources/css/application.css" rel="stylesheet" type="text/css" />
    <link href="resources/css/patternlock.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="resources/css/keyboard.css" />
    <script src="resources/js/jquery-1.9.1.js"></script>
	<script src="resources/js/jquery-ui-1.9.1.js"></script>
	<script src="resources/js/patternlock.js"></script>
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
<%@ include file="header.jsp"%> 
      	<div class="span9">
			<div class="hero-unit">
			            <h1>Account Management</h1>
			            <p>Update Secondary Password</p>
			</div>
			<div class="row-fluid" >
			<div class="content_space">
			
			<form method="post" action="UpdatePersonalPatternServlet">
					<h2>Click and drag a pattern to update your secondary password.</h2>

					<div>
						<input type="password" id="userPattern" name="userPattern" class="patternlock" />
					</div>
					
					<table cellspacing=30 cellpadding=10>
					<%
					String uppmsg = (String)session.getAttribute("uppmsg");
					if (uppmsg == null) {
					} else {
					%>
					<div style="font-weight: bold;">
					<%= uppmsg %>
					</div>
					<br><br>
					<%
					} 
					%>
					</table>
					
					<input type="submit" value="Confirm" />
			</form>
			
			</div>
		</div>
	</div>
<%@ include file="footer.jsp"%>
</body>

<%
}
else
{
	response.sendRedirect("Error.jsp");
}
%>

</html>