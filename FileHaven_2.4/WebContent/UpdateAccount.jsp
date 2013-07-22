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
	if(tempaccount.getType() == 'A' || tempaccount.getType() == 'C' || tempaccount.getType() == 'M')
	{
%>

<script type="text/javascript">
	function updateViewAccount(){
        <%
        Account temp = (Account) session.getAttribute("updateViewAccount");
        
        if(temp == null){
        %>
		<%
		}
		else
		{
			if(temp.getType() == 'C')
			{
				Ceo acc = (Ceo) temp;
		%>
		
		var ddlg = document.getElementById("gender");
		var optsg = ddlg.options.length;
		for (var i=0; i<optsg; i++){
    		if (ddlg.options[i].value == '<%= acc.getGender()%>'){
        	ddlg.options[i].selected = true;
        	break;
    		}
		}
		
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
		%>
		
		var ddlg = document.getElementById("gender");
		var optsg = ddlg.options.length;
		for (var i=0; i<optsg; i++){
    		if (ddlg.options[i].value == '<%= acc.getGender()%>'){
        	ddlg.options[i].selected = true;
        	break;
    		}
		}
		
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
		%>
		
		var ddlg = document.getElementById("gender");
		var optsg = ddlg.options.length;
		for (var i=0; i<optsg; i++){
    		if (ddlg.options[i].value == '<%= acc.getGender()%>'){
        	ddlg.options[i].selected = true;
        	break;
    		}
		}
		
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
		%>
		
		var ddlg = document.getElementById("gender");
		var optsg = ddlg.options.length;
		for (var i=0; i<optsg; i++){
    		if (ddlg.options[i].value == '<%= acc.getGender()%>'){
        	ddlg.options[i].selected = true;
        	break;
    		}
		}
		
		document.getElementById("dateOfBirth").value = '<%= acc.getDOB().toString()%>';
		document.getElementById("phoneNumber").value = '<%= acc.getPhoneNumber()%>';
		document.getElementById("email").value = '<%= acc.getEmail()%>';
		document.getElementById("address").value = '<%= acc.getAddress()%>';
		document.getElementById("NRIC").value = '<%= acc.getNRIC()%>';
		
		<%
			}
		%>
		
		var ddln = document.getElementById("name");
		var optsn = ddln.options.length;
		for (var i=0; i<optsn; i++){
    		if (ddln.options[i].value == '<%= temp.getUserName()%>'){
        	ddln.options[i].selected = true;
        	break;
    		}
		}
		
		var ddlt = document.getElementById("type");
		var optst = ddlt.options.length;
		for (var i=0; i<optst; i++){
    		if (ddlt.options[i].value == '<%= temp.getType()%>'){
        	ddlt.options[i].selected = true;
        	break;
    		}
		}
		<%
		}
		%>
}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Update Account</title>
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
<body onload='updateViewAccount()'>
<%@ include file="header.jsp"%> 
      	<div class="span9">
			<div class="hero-unit">
			            <h1>Account Management</h1>
			            <p></p>
			</div>
			<div class="row-fluid" >
			<div class="content_space">
			
			<table cellspacing=15 cellpadding=10>
			<% 
			if (session.getAttribute("upmsg") == null) {
			%>
			
			<%
			} else { 
			%>
			<tr><td><div style="font-weight: bold;"><%= session.getAttribute("upmsg")%></div></td></tr>
			<%
			} 
			%>
			</table>
			
			<br>
			
			<form action='RetrieveAccountDetailsServlet' method='post'>
			
			<table cellspacing=15 cellpadding=10>
			<tr><td>Name :</td><td>
			<select id="name" name="name" style="height: 30px; width:180px;" onChange="">
			<%
				ArrayList<String> userNameList = (ArrayList) session.getAttribute("userNameList");
				ArrayList<String> nameList = (ArrayList) session.getAttribute("nameList");
				
				for (int i=0; i<nameList.size(); i++){
				%>
        		<option value="<%= userNameList.get(i)%>"><%out.print(nameList.get(i));%></option>
   				<%}%>	
			</select></td>
			<td><input type="submit" value="View" name="view" onClick="" class="btn"/></td>
			</tr>
			</table>
				
			<br>
				
			</form>
			
			<form action='UpdateAccountServlet' method='post'>
				
				<table cellspacing=15 cellpadding=10>
				<tr><td>Account Type :</td><td>
				<select id="type" name="type" style="height: 30px; width:120px;">
				<option value="M">Manager</option>
				<option value="F">Filemanager</option>
				<option value="E">Employee</option>
				</select></td>
				</tr>
				
				<tr><td>NRIC :</td><td><input type=text id="NRIC" name="NRIC" style="height: 20px; width:120px;"/></td>
				<td>
				<%
				if(session.getAttribute("nricmsg") != null)
				{
				%>
				<div class="invalidAlert">
				<%= session.getAttribute("nricmsg")%>
				</div>
				<%
				}
				%>
				</td></tr>
				
				<tr><td>Gender :</td><td>
				<select id="gender" name="gender" style="height: 30px; width:100px;">
				<option value="Male">Male</option>
				<option value="Female">Female</option>
				</select></td>
				</tr>
				
				<tr><td>Date of Birth :</td><td><input type=text id="dateOfBirth" name="dateOfBirth" style="height: 20px; width: 120px;"/></td>
				<td>
				<%
				if(session.getAttribute("dobmsg") != null)
				{
				%>
				<div class="invalidAlert">
				<%= session.getAttribute("dobmsg")%>
				</div>
				<%
				}
				else
				{
				%>
				(YYYY-MM-DD)
				<%
				}
				%>
				</td>
				</tr>
				<tr><td>Phone Number :</td><td><input type=text id="phoneNumber" name="phoneNumber" style="height: 20px; width:100px;"/></td>
				<td>
				<%
				if(session.getAttribute("phmsg") != null)
				{
				%>
				<div class="invalidAlert">
				<%= session.getAttribute("phmsg")%>
				</div>
				<%
				}
				%>
				</td></tr>
				
				<tr><td>Email</td><td><input type=text id="email" name="email" style="height: 20px; width: 180px;"/></td>
				<td>
				<%
				if(session.getAttribute("emmsg") != null)
				{
				%>
				<div class="invalidAlert">
				<%= session.getAttribute("emmsg")%>
				</div>
				<%
				}
				%>
				</td>
				</tr>
				
				<tr><td>Address</td><td><input type=text id="address" name="address" style="height: 20px; width: 180px;"/></td></tr>
				<tr><td><input type="submit" value="Update" name="button" onClick="" class="btn"/></td><td><input type="submit" value="Disable" name="button" onClick="" class="btn"/></td></tr>
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
}
else
{
	response.sendRedirect("Error.jsp");
}
%>

</html>