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

<script type="text/javascript">
	function viewPersonalInfo(){
        <%
        Account temp = (Account) session.getAttribute("viewPersonalInfo");
        
        if(temp == null){
        %>
		<%
		}
		else
		{
			String type = "";
			
			if(temp.getType() == 'A')
			{
				Administrator acc = (Administrator) temp;
				type = "Administrator";
		%>
		
		document.getElementById("gender").value = '<%= acc.getGender()%>';
		document.getElementById("dateOfBirth").value = '<%= acc.getDOB().toString()%>';
		document.getElementById("phoneNumber").value = '<%= acc.getPhoneNumber()%>';
		document.getElementById("email").value = '<%= acc.getEmail()%>';
		document.getElementById("address").value = '<%= acc.getAddress()%>';
		document.getElementById("NRIC").value = '<%= acc.getNRIC()%>';
		
		<%
			}
			else if(temp.getType() == 'C')
			{
				Ceo acc = (Ceo) temp;
				type = "Ceo";
		%>

		document.getElementById("gender").value = '<%= acc.getGender()%>';
		document.getElementById("dateOfBirth").value = '<%= acc.getDOB().toString()%>';
		document.getElementById("phoneNumber").value = '<%= acc.getPhoneNumber()%>';
		document.getElementById("email").value = '<%= acc.getEmail()%>';
		document.getElementById("address").value = '<%= acc.getAddress()%>';
		document.getElementById("NRIC").value = '<%= acc.getNRIC()%>';
		
		<%
			}
			else if(temp.getType() == 'E')
			{
				Employee acc = (Employee) temp;
				type = "Employee";
		%>

		document.getElementById("gender").value = '<%= acc.getGender()%>';
		document.getElementById("dateOfBirth").value = '<%= acc.getDOB().toString()%>';
		document.getElementById("phoneNumber").value = '<%= acc.getPhoneNumber()%>';
		document.getElementById("email").value = '<%= acc.getEmail()%>';
		document.getElementById("address").value = '<%= acc.getAddress()%>';
		document.getElementById("NRIC").value = '<%= acc.getNRIC()%>';
		
		<%
			}
			else if(temp.getType() == 'F')
			{
				Filemanager acc = (Filemanager) temp;
				type = "File manager";
		%>

		document.getElementById("gender").value = '<%= acc.getGender()%>';
		document.getElementById("dateOfBirth").value = '<%= acc.getDOB().toString()%>';
		document.getElementById("phoneNumber").value = '<%= acc.getPhoneNumber()%>';
		document.getElementById("email").value = '<%= acc.getEmail()%>';
		document.getElementById("address").value = '<%= acc.getAddress()%>';
		document.getElementById("NRIC").value = '<%= acc.getNRIC()%>';
		
		<%
			}
			else
			{
				Manager acc = (Manager) temp;
				type = "Manager";
		%>

		document.getElementById("gender").value = '<%= acc.getGender()%>';
		document.getElementById("dateOfBirth").value = '<%= acc.getDOB().toString()%>';
		document.getElementById("phoneNumber").value = '<%= acc.getPhoneNumber()%>';
		document.getElementById("email").value = '<%= acc.getEmail()%>';
		document.getElementById("address").value = '<%= acc.getAddress()%>';
		document.getElementById("NRIC").value = '<%= acc.getNRIC()%>';
		
		<%
			}
		%>
		
		document.getElementById("userName").value = '<%= temp.getUserName()%>';
		document.getElementById("name").value = '<%= temp.getName()%>';
		document.getElementById("type").value = '<%= type%>';
		
		<%
		}
		%>
}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Personal Account Info</title>
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


</head>
<body onload='viewPersonalInfo()'>
<%@ include file="header.jsp"%> 
      	<div class="span9">
			<div class="hero-unit">
			            <h1>Account</h1>
			            <p></p>
			</div>
			<div class="row-fluid" >
			<div class="content_space">
			
			<form action='' method='post'>
			
				<table cellspacing=30 cellpadding=10>
				<tr><td>Username :</td><td><input type=text id="userName" name="userName" style="height: 20px;" readonly/></td></tr>
				<tr><td>Name :</td><td><input type=text id="name" name="name" style="height: 20px;" readonly/></td></tr>
				<tr><td>Account Type :</td><td><input type=text id="type" name="type" style="height: 20px;" readonly/></td></tr>
				<tr><td>NRIC :</td><td><input type=text id="NRIC" name="NRIC" style="height: 20px;" readonly/></td></tr>
				<tr><td>Gender :</td><td><input type=text id="gender" name="gender" style="height: 20px;" readonly/></td></tr>
				<tr><td>Date of Birth :</td><td><input type=text id="dateOfBirth" name="dateOfBirth" style="height: 20px;" readonly/></td></tr>
				<tr><td>Phone Number :</td><td><input type=text id="phoneNumber" name="phoneNumber" style="height: 20px;" readonly/></td></tr>
				<tr><td>Email</td><td><input type=text id="email" name="email" style="height: 20px;" readonly/></td></tr>
				<tr><td>Address</td><td><input type=text id="address" name="address" style="height: 20px;" readonly/></td></tr>
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