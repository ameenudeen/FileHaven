<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="model.*, java.util.*,database.*" %>

<%
Account tempaccount = (Account) session.getAttribute("LoggedInUser");

if(tempaccount != null)
{
%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Change Password</title>
    <link href="resources/css/bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="resources/css/bootstrap-responsive.css" rel="stylesheet" type="text/css" />
    <link href="resources/css/application.css" rel="stylesheet" type="text/css" />
    <script src="resources/js/jquery-1.9.1.js"></script>
	<script src="resources/js/jquery-ui-1.9.1.js"></script>
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

<style type="text/css">
	.invalidAlert
	{
	color:#CC0000;
	font-weight: bold;
	}
</style>

</head>
<body>
<%@ include file="header.jsp"%> 
      	<div class="span9">
			<div class="hero-unit">
			            <h1>Change Password</h1>
			            <p></p>
			</div>
			<div class="row-fluid" >
			<div class="content_space">
			
			<%
			String passmsg = (String) session.getAttribute("passmsg");
			if (passmsg == null) {
			} else {
			%>
			<div style="font-weight: bold;">
			<%= passmsg %>
			</div>
			<br><br>
			<%
			} 
			%>
			
			<form action='ChangePasswordServlet' method='post'>
			
				<table cellspacing=30 cellpadding=10>
				<tr><td>Old Password :</td><td><input type=password id="oldPassword" name="oldPassword" style="height: 20px; width: 180px;"/></td></tr>
				
				<tr><td>New Password :</td><td><input type=password id="newPassword" name="newPassword" style="height: 20px; width: 180px;"/></td>
				<td>
				<%
				if(session.getAttribute("conpassmsg") != null)
				{
				%>
				<div class="invalidAlert">
				<%= session.getAttribute("conpassmsg")%>
				</div>
				<%
				}
				%>
				</td></tr>
				
				<tr><td>Confirm New Password :</td><td><input type=password id="conPassword" name="conPassword" style="height: 20px; width: 180px;"/></td></tr>
				<tr><td><input type="submit" value="Submit" name="submit" onclick="" class="btn"/></td></tr>
				
				</table>
			
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