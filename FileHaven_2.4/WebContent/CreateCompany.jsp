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
	if(tempaccount.getType() == 'A')
	{
%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Create Company</title>
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
			            <h1>Company Management</h1>
			            <p>Create Company</p>
			</div>
			<div class="row-fluid" >
			<div class="content_space">
			
			<%
			String commsg = (String) session.getAttribute("commsg");
			if (commsg == null) {
			} else {
			%>
			<table cellspacing=15 cellpadding=10>
			<tr>
			<td>
			<div style="font-weight: bold;">
			<%= commsg%>
			</div>
			<br><br>
			</td>
			</tr>
			</table>
			<%
			} 
			%>
			
			<form action='CreateCompanyServlet' method='post'>
				
				<table cellspacing=15 cellpadding=10>
				<tr><td>Company Name :</td><td><input type=text id="companyName" name="companyName" style="height: 20px; width:180px"/></td></tr>
				<tr><td>Address :</td><td><input type=text id="address" name="address" style="height: 20px; width: 280px;"/></td></tr>
				<tr><td>Storage Space :</td><td><input type=text id="storageSpace" name="storageSpace" style="height: 20px; width: 80px;"/></td>
				
				<%
				String ssmsg = (String) session.getAttribute("ssmsg");
				if(ssmsg == null){
				} else {
				%>
				<td>
				<div class="invalidAlert">
				<%= ssmsg%>
				</div>
				</td>
				<%
				}
				%>
				
				</tr>
				<tr><td>Company Logo :</td><td><input type="file" id="companyLogo" name="companyLogo"></td></tr>
				
				</table>
				
				<p><input type="submit" value="Submit" name="submit" onclick="" class="btn"/></p>
				
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
}
else
{
	response.sendRedirect("Error.jsp");
}
%>

</html>