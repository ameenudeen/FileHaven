<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Home</title>
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
			            <h1>Home</h1>
			            <p></p>
			</div>
			<div class="row-fluid" >
			<div class="content_space">
				<%@page import="model.*"%>
				<% 
				Account acc = (Account) session.getAttribute("LoggedInUser"); 
				if (acc == null) {
				%>
				
				<%
				} else { 
				%>
				Hello <%= acc.getName().toString() %>.
				<%
				} 
				%>
				<table cellspacing=20>
				<tr>
				
				
				<td align="center" valign="middle">
				<form action="RetrieveCompanyListServlet" method='post'>
				<input type="submit" name="submit" Value="Create Account" style="height: 30px; width: 120px;" class="btn" onclick=""/>
				</form>
				</td>
				
				<td align="center" valign="middle">
				<form action='RetrieveAccountListServlet' method='post'>
				<input type="submit" name="submit" Value="Manage Accounts" style="height: 30px; width: 140px;" class="btn" onclick="RetrieveAccountListServlet" />
				</form>
				</td>
				
				<td align="center" valign="middle">
				<form action='ViewAccountServlet' method='post'>
				<input type="submit" name="submit" Value="View Accounts" style="height: 30px; width: 140px;" class="btn" onclick="ViewAccountServlet" />
				</form>
				</td>
				
				</tr>
				</table>
				
				<table cellspacing=20>
				<tr>
				
				<td align="center" valign="middle">
				<a href="<%=request.getContextPath()%>/ViewPersonalInfoServlet">View Personal Info</a>
				</td>
				
				<td align="center" valign="middle">
				<a href="ChangePassword.jsp">Change Password</a>
				</td>
				
				<td align="center" valign="middle">
				<form action='LogoutServlet' method='post'>
				<input type="submit" name="submit" Value="Logout" style="height: 30px; width: 140px;" class="btn" onclick="Logout" />
				</form>
				</td>
				
				</tr>
				</table>
				
				<%
				if(acc.getType() == 'A')
				{
				%>
				
				<table>
				<tr>
				
				<td align="center" valign="middle">
				<input type="submit" name="submit" Value="Create Company" style="height: 30px; width: 140px;" class="btn" onclick="window.location='CreateCompany.jsp'" />
				</td>
				
				</tr>
				</table>
				
				<%
				}
				%>
				
			</div>
		</div>
	</div>
<%@ include file="footer.jsp"%>
</body>
</html>